package com.diskree.achievetodo.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public enum ExternalPack {
    BACAP(
            "BlazeandCave's Advancements Pack",
            Formatting.LIGHT_PURPLE,
            "https://modrinth.com/datapack/blazeandcaves-advancements-pack",
            "https://cdn.modrinth.com/data/VoVJ47kN/versions/OjTY8pAl/BlazeandCave%27s%20Advancements%20Pack%201.16.6.zip",
            "4d2ddaee3b17b9a8c93bb9a176d3bf9cddb006cd"
    ),
    BACAP_HARDCORE(
            "BlazeandCave's Advancements Pack (Hardcore version)",
            Formatting.RED,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-hardcore-version/",
            "https://www.mediafire.com/file/samh3amrlhk15pu/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_H%2Ardc%2Ar%2A_1.16.0.1.zip/file",
            "dabbf0610d3d31a89609efe232c15b083db99ca7"
    ),
    BACAP_TERRALITH(
            "BlazeandCave's Advancements Pack (Terralith version)",
            Formatting.GREEN,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/xl0h8rm7u7e0pk1/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_Terralith_1.16.0.1.zip/file",
            "017bafbf346084c02a44c195dd977ab719b98d7b"
    ),
    BACAP_AMPLIFIED_NETHER(
            "BlazeandCave's Advancements Pack (Amplified Nether version)",
            Formatting.DARK_RED,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/g8waepaojfi5c4p/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_1.15_Amplified_Nether.zip/file",
            "37aef8a4dfdaf15720941a74738f745a4fe2303c"
    ),
    BACAP_NULLSCAPE(
            "BlazeandCave's Advancements Pack (Nullscape version)",
            Formatting.DARK_PURPLE,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/m3p4cunby2y6co5/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_1.15_Nullscape.zip/file",
            "480534c93a91c358ea3a94560d75b24e69022b88"
    ),
    TERRALITH(
            "Terralith (World Generation Pack)",
            Formatting.GREEN,
            "https://www.planetminecraft.com/data-pack/terralith-overworld-evolved-100-biomes-caves-and-more/",
            "https://github.com/Stardust-Labs-MC/Terralith/releases/download/v2.4.11/Terralith_1.20.4_v2.4.11.zip",
            "c7e7f40e6a4da281bbd26eff16a3c7417b096b85"
    ),
    AMPLIFIED_NETHER(
            "Amplified Nether (World Generation Pack)",
            Formatting.DARK_RED,
            "https://www.planetminecraft.com/data-pack/amplified-nether-1-18/",
            "https://github.com/Stardust-Labs-MC/Amplified-Nether/releases/download/v1.2.4/Amplified_Nether_1.20.4_v1.2.4.zip",
            "c67ce6110fe14271da660598384cffb44591c49b"
    ),
    NULLSCAPE(
            "Nullscape (World Generation Pack)",
            Formatting.DARK_PURPLE,
            "https://www.planetminecraft.com/data-pack/nullscape/",
            "https://github.com/Stardust-Labs-MC/Nullscape/releases/download/v1.2.4/Nullscape_1.20.4_v1.2.4.zip",
            "2554559b4ab7d498c64ba5157515cd3f16d77d6d"
    );

    private final String name;
    private final Formatting color;
    private final String pageUrl;
    private final String downloadUrl;
    private final String sha1;

    ExternalPack(String name, Formatting color, String pageUrl, String downloadUrl, String sha1) {
        this.name = name;
        this.color = color;
        this.pageUrl = pageUrl;
        this.downloadUrl = downloadUrl;
        this.sha1 = sha1;
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

    public String getSha1() {
        return sha1;
    }

    public String toFileName() {
        return name().toLowerCase() + ".zip";
    }
}
