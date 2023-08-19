package com.diskree.achievetodo.client;

import com.google.gson.*;
import net.minecraft.util.Identifier;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class AdvancementsEncryptor {

    private static final String CRITERIA_KEY = "criteria";
    private static final String REQUIREMENTS_KEY = "requirements";

    public static Path unzip(Path source, Path destination) throws IOException {
        Path firstExtractedFile = null;
        try (ZipFile zipFile = new ZipFile(source.toFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                Path outputPath = destination.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(outputPath);
                } else {
                    if (firstExtractedFile == null) {
                        firstExtractedFile = outputPath;
                    }
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = Files.newOutputStream(outputPath)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    }
                }
            }
        }
        return firstExtractedFile;
    }

    public static void zip(Path sourceDir, Path outputZipFile) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(outputZipFile.toFile());
                ZipOutputStream zos = new ZipOutputStream(fos);
                Stream<Path> stream = Files.walk(sourceDir)
        ) {
            stream.filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                try {
                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException ignored) {
                }
            });
        }
    }

    public static void encrypt(Path sourceDir, long seed) throws IOException {
        Map<Identifier, Map<String, String>> advancementToEncryptedCriteriaMap = new HashMap<>();
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith(".json")) {
                    return FileVisitResult.CONTINUE;
                }
                String json = Files.readString(file);
                JsonElement jsonElement = JsonParser.parseString(json);
                if (!jsonElement.isJsonObject()) {
                    return FileVisitResult.CONTINUE;
                }
                JsonObject advancement = jsonElement.getAsJsonObject();
                if (advancement.has(CRITERIA_KEY)) {
                    String path = file.getParent().getFileName().toString() + "/" + file.getFileName().toString().replace(".json", "");
                    String namespace = file.getParent().getParent().getParent().getFileName().toString();
                    Identifier advancementId = new Identifier(namespace, path);
                    Map<String, String> encryptedCriteriaMap = new HashMap<>();
                    JsonObject criteria = advancement.getAsJsonObject(CRITERIA_KEY);
                    for (String criterion : new HashSet<>(criteria.keySet())) {
                        String encryptedCriteria = encryptCriterion(advancementId, criterion, seed);
                        encryptedCriteriaMap.put(criterion, encryptedCriteria);
                        JsonObject criterionObj = criteria.getAsJsonObject(criterion);
                        criteria.remove(criterion);
                        criteria.add(encryptedCriteria, criterionObj);
                    }
                    shuffleCriteria(criteria);
                    if (advancement.has(REQUIREMENTS_KEY)) {
                        JsonArray requirementsArray = advancement.getAsJsonArray(REQUIREMENTS_KEY);
                        for (int i = 0; i < requirementsArray.size(); i++) {
                            JsonArray requirementArray = requirementsArray.get(i).getAsJsonArray();
                            for (int j = 0; j < requirementArray.size(); j++) {
                                String oldRequirement = requirementArray.get(j).getAsString();
                                String encryptedRequirement = encryptedCriteriaMap.get(oldRequirement);
                                if (encryptedRequirement != null) {
                                    requirementArray.set(j, new JsonPrimitive(encryptedRequirement));
                                }
                            }
                            shuffleRequirements(requirementArray);
                        }
                    }
                    advancementToEncryptedCriteriaMap.put(advancementId, encryptedCriteriaMap);
                    Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(advancement));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        encryptCommands(sourceDir, advancementToEncryptedCriteriaMap);
    }

    private static void encryptCommands(Path sourceDir, Map<Identifier, Map<String, String>> advancementToEncryptedCriteriaMap) throws IOException {
        Pattern pattern = Pattern.compile("\\bonly\\s+(\\w+:\\w+/\\w+)\\s+(\\w+)\\b");
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith(".mcfunction")) {
                    return FileVisitResult.CONTINUE;
                }
                String content = Files.readString(file);
                Matcher matcher = pattern.matcher(content);
                StringBuilder sb = null;
                while (matcher.find()) {
                    String advancementId = matcher.group(1);
                    String criterion = matcher.group(2);
                    Map<String, String> encryptedCriteriaMap = advancementToEncryptedCriteriaMap.get(new Identifier(advancementId));
                    if (encryptedCriteriaMap != null) {
                        String encryptedCriterion = encryptedCriteriaMap.get(criterion);
                        if (encryptedCriterion != null) {
                            if (sb == null) {
                                sb = new StringBuilder();
                            }
                            matcher.appendReplacement(sb, "only " + advancementId + " " + encryptedCriterion);
                        }
                    }
                }
                if (sb != null) {
                    matcher.appendTail(sb);
                    Files.writeString(file, sb.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void shuffleCriteria(JsonObject criteria) {
        List<Map.Entry<String, JsonElement>> entryList = new ArrayList<>(criteria.entrySet());
        Collections.shuffle(entryList);
        JsonObject newCriteria = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : entryList) {
            newCriteria.add(entry.getKey(), entry.getValue());
        }
        criteria.entrySet().clear();
        for (Map.Entry<String, JsonElement> entry : newCriteria.entrySet()) {
            criteria.add(entry.getKey(), entry.getValue());
        }
    }

    private static void shuffleRequirements(JsonArray innerArray) {
        List<JsonElement> list = new ArrayList<>();
        for (int j = 0; j < innerArray.size(); j++) {
            list.add(innerArray.get(j));
        }
        Collections.shuffle(list);
        for (int j = 0; j < list.size(); j++) {
            innerArray.set(j, list.get(j));
        }
    }

    private static String encryptCriterion(Identifier advancementId, String criterion, long seed) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest((seed + advancementId.toString()).getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(criterion.getBytes());
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(combined);
        } catch (Exception e) {
            return criterion;
        }
    }

    public static String decryptCriterion(Identifier advancementId, String criterion, long seed) {
        try {
            byte[] combined = Base64.getUrlDecoder().decode(criterion);
            byte[] iv = Arrays.copyOfRange(combined, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(combined, 16, combined.length);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest((seed + advancementId.toString()).getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] originalTextBytes = cipher.doFinal(cipherText);
            return new String(originalTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return criterion;
        }
    }
}
