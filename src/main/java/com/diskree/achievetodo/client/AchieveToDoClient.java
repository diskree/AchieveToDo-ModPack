package com.diskree.achievetodo.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbEntityRenderer;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalItemDisplayEntityRenderer;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalParticleFactory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AchieveToDoClient implements ClientModInitializer {

    public static final Identifier BACAP_BETTER_RU = new Identifier(AchieveToDo.ID, "bacap_better_ru");

    public static final Identifier ADVANCEMENTS_SEARCH_ID = new Identifier(AchieveToDo.ID, "advancements_search/root");
    public static final int ADVANCEMENTS_SCREEN_MARGIN = 30;

    @Override
    public void onInitializeClient() {
        registerResourcePacks();

        BlockRenderLayerMap.INSTANCE.putBlock(AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK, BlendMode.TRANSLUCENT.blockRenderLayer);
        ModelPredicateProviderRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM, ModelPredicateProviderRegistry.DAMAGE_ID, ModelPredicateProviderRegistry.DAMAGE_PROVIDER);
        ParticleFactoryRegistry.getInstance().register(AchieveToDo.ANCIENT_CITY_PORTAL_PARTICLES, AncientCityPortalParticleFactory::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_TAB, AncientCityPortalItemDisplayEntityRenderer::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_ADVANCEMENT, AncientCityPortalItemDisplayEntityRenderer::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_HINT, AncientCityPortalItemDisplayEntityRenderer::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_EXPERIENCE_ORB, AncientCityPortalExperienceOrbEntityRenderer::new);
    }

    private void registerResourcePacks() {
        FabricLoader.getInstance().getModContainer(AchieveToDo.ID).ifPresent(modContainer ->
                ResourceManagerHelper.registerBuiltinResourcePack(BACAP_BETTER_RU, modContainer, Text.translatable("pack.bacap_better_ru.title"), ResourcePackActivationType.DEFAULT_ENABLED)
        );
    }
}
