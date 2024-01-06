package com.diskree.achievetodo.datagen;

import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class FunctionProvider implements DataProvider {

    protected final FabricDataOutput dataOutput;

    protected FunctionProvider(FabricDataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    public abstract void generateFunctions(Consumer<Function> consumer);

    @SuppressWarnings({"UnstableApiUsage", "deprecation"})
    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {
            final Set<Function> functions = Sets.newHashSet();
            generateFunctions(functions::add);
            try {
                for (Function function : functions) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream);
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(hashingOutputStream, StandardCharsets.UTF_8))) {
                        bufferedWriter.write(function.command());
                    }
                    writer.write(getFunctionPath(function.name()), byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
                }
            } catch (IOException ignored) {
            }
        }, Util.getMainWorkerExecutor());
    }

    private Path getFunctionPath(String name) {
        return dataOutput
                .getResolver(DataOutput.OutputType.DATA_PACK, "functions")
                .resolve(new Identifier("achievetodo_blocked_actions/" + name), "mcfunction");
    }

    @Override
    public String getName() {
        return "Functions";
    }
}
