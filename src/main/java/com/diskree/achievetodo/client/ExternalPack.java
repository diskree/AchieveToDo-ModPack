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
            "https://cdn.modrinth.com/data/VoVJ47kN/versions/fVpQcesg/BlazeandCave%27s%20Advancements%20Pack%201.16.7.zip",
            null,
            "ecdde0fa0f5b0cda52ba07cf611a04b3517f53df"
    ),
    BACAP_HARDCORE(
            "BlazeandCave's Advancements Pack (Hardcore version)",
            Formatting.RED,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-hardcore-version/",
            "https://www.mediafire.com/file/samh3amrlhk15pu/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_H%2Ardc%2Ar%2A_1.16.0.1.zip/file",
            "dabbf0610d3d31a89609efe232c15b083db99ca7",
            "b9ab297a6689b1a91007befdc968aca01362ba12"
    ),
    BACAP_TERRALITH(
            "BlazeandCave's Advancements Pack (Terralith version)",
            Formatting.GREEN,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/qzpcc9qm753acrv/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_Terralith_1.16.7.zip/file",
            "7d534f71b9300b0503dd4a0b0a5730bb5ec1a85e",
            "fe3918b5999c0f2b08506979790762b9c0e957ba"
    ),
    BACAP_AMPLIFIED_NETHER(
            "BlazeandCave's Advancements Pack (Amplified Nether version)",
            Formatting.DARK_RED,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/ja33aagm4ho2anc/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_Amplified_Nether_1.16.6.zip/file",
            "bbefbafb824d2ccc53f74e78b91d8024685c106e",
            "458320faa30b30f44e462e307134acef3d853590"
    ),
    BACAP_NULLSCAPE(
            "BlazeandCave's Advancements Pack (Nullscape version)",
            Formatting.DARK_PURPLE,
            "https://www.planetminecraft.com/data-pack/blazeandcave-s-advancements-pack-terralith-version/",
            "https://www.mediafire.com/file/0rfb5fqr14k6sk5/%255BUNZIP_ME%255D_BlazeandCave%2527s_Advancements_Pack_Nullscape_1.16.6.zip/file",
            "0faf0ef14f7f35615c9325540d16fc08026f3193",
            "9120f6db7c68032561211a388d88e5c8f217300a"
    ),
    TERRALITH(
            "Terralith (World Generation Pack)",
            Formatting.GREEN,
            "https://www.planetminecraft.com/data-pack/terralith-overworld-evolved-100-biomes-caves-and-more/",
            "https://github.com/Stardust-Labs-MC/Terralith/releases/download/v2.4.11/Terralith_1.20.4_v2.4.11.zip",
            null,
            "c7e7f40e6a4da281bbd26eff16a3c7417b096b85"
    ),
    AMPLIFIED_NETHER(
            "Amplified Nether (World Generation Pack)",
            Formatting.DARK_RED,
            "https://www.planetminecraft.com/data-pack/amplified-nether-1-18/",
            "https://github.com/Stardust-Labs-MC/Amplified-Nether/releases/download/v1.2.4/Amplified_Nether_1.20.4_v1.2.4.zip",
            null,
            "c67ce6110fe14271da660598384cffb44591c49b"
    ),
    NULLSCAPE(
            "Nullscape (World Generation Pack)",
            Formatting.DARK_PURPLE,
            "https://www.planetminecraft.com/data-pack/nullscape/",
            "https://github.com/Stardust-Labs-MC/Nullscape/releases/download/v1.2.4/Nullscape_1.20.4_v1.2.4.zip",
            null,
            "2554559b4ab7d498c64ba5157515cd3f16d77d6d"
    );

    private final String name;
    private final Formatting color;
    private final String pageUrl;
    private final String downloadUrl;
    private final String wrapperSha1;
    private final String sha1;

    ExternalPack(String name, Formatting color, String pageUrl, String downloadUrl, String wrapperSha1, String sha1) {
        this.name = name;
        this.color = color;
        this.pageUrl = pageUrl;
        this.downloadUrl = downloadUrl;
        this.wrapperSha1 = wrapperSha1;
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

    public String getWrapperSha1() {
        return wrapperSha1;
    }

    public String getSha1() {
        return sha1;
    }

    public String toFileName() {
        return name().toLowerCase() + ".zip";
    }
}
