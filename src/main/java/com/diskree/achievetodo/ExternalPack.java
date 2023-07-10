package com.diskree.achievetodo;

public enum ExternalPack {
    BACAP(
            "BlazeandCave's Advancements Pack",
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-1-12/",
            "https://www.mediafire.com/file/9fpg9fh6xl1i8hh/BlazeandCave%2527s_Advancements_Pack_1.16.1.zip/file"
    ),
    BACAP_HARDCORE(
            "BlazeandCave's Advancements Pack (Hardcore version)",
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-hardcore-version/",
            "https://www.mediafire.com/file/5anxgeucs1udpml/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_H%2Ardc%2Ar%2A_1.16.zip/file"
    ),
    BACAP_TERRALITH(
            "BlazeandCave's Advancements Pack (Terralith version)",
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/nw72t4a0028mugy/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_Terralith_1.16.zip/file"
    ),
    BACAP_AMPLIFIED_NETHER(
            "BlazeandCave's Advancements Pack (Amplified Nether version)",
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/g8waepaojfi5c4p/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_1.15_Amplified_Nether.zip/file"
    ),
    BACAP_NULLSCAPE(
            "BlazeandCave's Advancements Pack (Nullscape version)",
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/m3p4cunby2y6co5/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_1.15_Nullscape.zip/file"
    ),
    TERRALITH(
            "Terralith (World Generation Pack)",
            "https://www.planetminecraft.com/data-pack/terralith-overworld-evolved-100-biomes-caves-and-more/",
            "https://www.stardustlabs.net/datapacks#Terralith"
    ),
    AMPLIFIED_NETHER(
            "Amplified Nether (World Generation Pack)",
            "https://www.planetminecraft.com/data-pack/amplified-nether-1-18/",
            "https://www.planetminecraft.com/data-pack/amplified-nether-1-18/download/file/15159370/"
    ),
    NULLSCAPE(
            "Nullscape (World Generation Pack)",
            "https://www.planetminecraft.com/data-pack/nullscape/",
            "https://www.stardustlabs.net/datapacks#Nullscape"
    );

    private final String name;
    private final String pageUrl;
    private final String downloadUrl;

    ExternalPack(String name, String pageUrl, String downloadUrl) {
        this.name = name;
        this.pageUrl = pageUrl;
        this.downloadUrl = downloadUrl;
    }

    public String getName() {
        return name;
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
