package com.diskree.achievetodo;

import net.minecraft.util.Formatting;

public enum ExternalPack {
    BACAP(
            "BlazeandCave's Advancements Pack",
            Formatting.LIGHT_PURPLE,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-1-12/",
            "https://www.mediafire.com/file/6mj2x3v6jtikdet/BlazeandCave%2527s_Advancements_Pack_1.16.2.zip/file"
    ),
    BACAP_HARDCORE(
            "BlazeandCave's Advancements Pack (Hardcore version)",
            Formatting.RED,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-hardcore-version/",
            "https://www.mediafire.com/file/5anxgeucs1udpml/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_H%2Ardc%2Ar%2A_1.16.zip/file"
    ),
    BACAP_TERRALITH(
            "BlazeandCave's Advancements Pack (Terralith version)",
            Formatting.GREEN,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/xl0h8rm7u7e0pk1/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_Terralith_1.16.0.1.zip/file"
    ),
    BACAP_AMPLIFIED_NETHER(
            "BlazeandCave's Advancements Pack (Amplified Nether version)",
            Formatting.DARK_RED,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/g8waepaojfi5c4p/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_1.15_Amplified_Nether.zip/file"
    ),
    BACAP_NULLSCAPE(
            "BlazeandCave's Advancements Pack (Nullscape version)",
            Formatting.DARK_PURPLE,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/m3p4cunby2y6co5/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_1.15_Nullscape.zip/file"
    ),
    TERRALITH(
            "Terralith (World Generation Pack)",
            Formatting.GREEN,
            "https://www.planetminecraft.com/data-pack/terralith-overworld-evolved-100-biomes-caves-and-more/",
            "https://www.stardustlabs.net/datapacks#Terralith"
    ),
    AMPLIFIED_NETHER(
            "Amplified Nether (World Generation Pack)",
            Formatting.DARK_RED,
            "https://www.planetminecraft.com/data-pack/amplified-nether-1-18/",
            "https://www.planetminecraft.com/data-pack/amplified-nether-1-18/download/file/15159370/"
    ),
    NULLSCAPE(
            "Nullscape (World Generation Pack)",
            Formatting.DARK_PURPLE,
            "https://www.planetminecraft.com/data-pack/nullscape/",
            "https://www.stardustlabs.net/datapacks#Nullscape"
    );

    private final String name;
    private final Formatting color;
    private final String pageUrl;
    private final String downloadUrl;

    ExternalPack(String name, Formatting color, String pageUrl, String downloadUrl) {
        this.name = name;
        this.color = color;
        this.pageUrl = pageUrl;
        this.downloadUrl = downloadUrl;
    }

    public String getName() {
        return name;
    }

    public Formatting getColor() {
        return color;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String toFileName() {
        return name().toLowerCase() + ".zip";
    }
}
