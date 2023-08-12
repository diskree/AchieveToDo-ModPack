package com.diskree.achievetodo.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbEntityRenderer;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbSpawnS2CPacket;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalItemDisplayEntityRenderer;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalParticleFactory;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@ClientOnly
public class AchieveToDoClient implements ClientModInitializer {

    public static final int ADVANCEMENTS_SCREEN_MARGIN = 30;

    @Override
    public void onInitializeClient(ModContainer mod) {
        BlockRenderLayerMap.put(RenderLayer.getTranslucent(), AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK);
        ModelPredicateProviderRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM, ModelPredicateProviderRegistry.DAMAGE_ID, ModelPredicateProviderRegistry.DAMAGE_PROVIDER);
        UnclampedModelPredicateProvider potionsPredicateProvider = (stack, world, entity, seed) -> {
            NbtCompound nbt = stack.getNbt();
            if (nbt == null) {
                return 0.0f;
            }
            String potion = nbt.getString("Potion");
            if (potion == null) {
                return 0.0f;
            }
            Identifier potionId = new Identifier(potion);
            if (potionId.getPath().startsWith("long_")) {
                return 0.5f;
            }
            if (potionId.getPath().startsWith("strong_")) {
                return 1.0f;
            }
            return 0.0f;
        };
        ModelPredicateProviderRegistry.register(Items.POTION, new Identifier("long_or_strong"), potionsPredicateProvider);
        ModelPredicateProviderRegistry.register(Items.SPLASH_POTION, new Identifier("long_or_strong"), potionsPredicateProvider);
        ModelPredicateProviderRegistry.register(Items.LINGERING_POTION, new Identifier("long_or_strong"), potionsPredicateProvider);
        ModelPredicateProviderRegistry.register(Items.TIPPED_ARROW, new Identifier("long_or_strong"), potionsPredicateProvider);
        ParticleFactoryRegistry.getInstance().register(AchieveToDo.ANCIENT_CITY_PORTAL_PARTICLES, AncientCityPortalParticleFactory::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_TAB, AncientCityPortalItemDisplayEntityRenderer::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_ADVANCEMENT, AncientCityPortalItemDisplayEntityRenderer::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_HINT, AncientCityPortalItemDisplayEntityRenderer::new);
        EntityRendererRegistry.register(AchieveToDo.ANCIENT_CITY_PORTAL_EXPERIENCE_ORB, AncientCityPortalExperienceOrbEntityRenderer::new);
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(AchieveToDo.ID, "ancient_city_portal_experience_c2s_packet"), (client, handler, buf, responseSender) -> new AncientCityPortalExperienceOrbSpawnS2CPacket(buf).apply(handler));
    }
}
