package com.diskree.achievetodo

import com.diskree.achievetodo.advancements.AchieveToDoToast
import com.diskree.achievetodo.ancient_city_portal.*
import com.google.common.collect.Lists
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.minecraft.advancement.Advancement
import net.minecraft.block.AbstractBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.Entity
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.resource.ResourcePackProfile
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.village.VillagerProfession
import net.minecraft.world.World
import java.util.stream.Collectors

class AchieveToDoMod : ModInitializer {

    override fun onInitialize() {
        registerPacks()
        registerBlocks()
        registerItems()
        registerParticles()
        registerAncientCityPortalEntities()

        AttackBlockCallback.EVENT.register(AttackBlockCallback { player: PlayerEntity, world: World?, _: Hand?, pos: BlockPos?, _: Direction? ->
            if (world != null && world.registryKey == World.OVERWORLD && pos != null) {
                if (pos.y >= 0 && isActionBlocked(BlockedAction.BREAK_BLOCKS_IN_POSITIVE_Y) || pos.y < 0 && isActionBlocked(
                        BlockedAction.BREAK_BLOCKS_IN_NEGATIVE_Y
                    )
                ) {
                    return@AttackBlockCallback ActionResult.FAIL
                }
            }
            if (isToolBlocked(player.inventory.mainHandStack)) {
                return@AttackBlockCallback ActionResult.FAIL
            }
            ActionResult.PASS
        })
        AttackEntityCallback.EVENT.register(AttackEntityCallback { player: PlayerEntity, _: World?, _: Hand?, _: Entity?, _: EntityHitResult? ->
            if (isToolBlocked(player.inventory.mainHandStack)) {
                return@AttackEntityCallback ActionResult.FAIL
            }
            ActionResult.PASS
        })
        UseBlockCallback.EVENT.register(UseBlockCallback { player: PlayerEntity, _: World?, _: Hand?, _: BlockHitResult? ->
            val itemStack = player.inventory.mainHandStack
            if (isToolBlocked(itemStack)) {
                return@UseBlockCallback ActionResult.FAIL
            }
            ActionResult.PASS
        })
        ServerWorldEvents.LOAD.register(ServerWorldEvents.Load { server: MinecraftServer, _: ServerWorld? ->
            val resourcePackManager = server.dataPackManager
            val list = Lists.newArrayList(resourcePackManager.enabledProfiles)
            list.remove(resourcePackManager.getProfile(BACAP_MAIN_DATA_PACK_ID.toString()))
            list.remove(resourcePackManager.getProfile(CORE_DATA_PACK_ID.toString()))
            list.remove(resourcePackManager.getProfile(HARDCORE_DATA_PACK_ID.toString()))
            list.add(resourcePackManager.getProfile(BACAP_MAIN_DATA_PACK_ID.toString()))
            list.add(resourcePackManager.getProfile(CORE_DATA_PACK_ID.toString()))
            if (server.isHardcore) {
                list.add(resourcePackManager.getProfile(HARDCORE_DATA_PACK_ID.toString()))
            }
            server.reloadResources(list.stream().map { obj: ResourcePackProfile? -> obj!!.name }
                .collect(Collectors.toList()))
        })
    }

    private fun registerPacks() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent { modContainer: ModContainer? ->
            ResourceManagerHelper.registerBuiltinResourcePack(
                BACAP_MAIN_DATA_PACK_ID,
                modContainer,
                Text.of("BACAP Data Pack"),
                ResourcePackActivationType.ALWAYS_ENABLED
            )
            ResourceManagerHelper.registerBuiltinResourcePack(
                CORE_DATA_PACK_ID,
                modContainer,
                Text.of("AchieveToDo Core Data Pack"),
                ResourcePackActivationType.ALWAYS_ENABLED
            )
            ResourceManagerHelper.registerBuiltinResourcePack(
                HARDCORE_DATA_PACK_ID,
                modContainer,
                Text.of("BACAP Hardcore Data Pack"),
                ResourcePackActivationType.NORMAL
            )
            ResourceManagerHelper.registerBuiltinResourcePack(
                BACAP_LANGUAGE_RESOURCE_PACK_ID,
                modContainer,
                Text.of("BACAP Language Resource Pack"),
                ResourcePackActivationType.DEFAULT_ENABLED
            )
        }
    }

    private fun registerBlocks() {
        Registry.register(Registries.BLOCK, Identifier(ID, "ancient_city_portal"), ANCIENT_CITY_PORTAL_BLOCK)
        BlockRenderLayerMap.INSTANCE.putBlock(ANCIENT_CITY_PORTAL_BLOCK, RenderLayer.getTranslucent())
    }

    private fun registerItems() {
        Registry.register(Registries.ITEM, Identifier(ID, "locked_action"), LOCKED_ACTION_ITEM)
    }

    private fun registerParticles() {
        Registry.register(
            Registries.PARTICLE_TYPE,
            Identifier(ID, "ancient_city_portal_particles"),
            ANCIENT_CITY_PORTAL_PARTICLES
        )
        ParticleFactoryRegistry.getInstance()
            .register(ANCIENT_CITY_PORTAL_PARTICLES, ::AncientCityPortalParticleFactory)
    }

    private fun registerAncientCityPortalEntities() {
        val tabEntity = Registry.register(
            Registries.ENTITY_TYPE,
            ANCIENT_CITY_PORTAL_TAB_ENTITY_ID,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AncientCityPortalTabEntity).build()
        )
        val advancementEntity = Registry.register(
            Registries.ENTITY_TYPE,
            ANCIENT_CITY_PORTAL_ADVANCEMENT_ENTITY_ID,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AncientCityPortalAdvancementEntity).build()
        )
        val promptEntity = Registry.register(
            Registries.ENTITY_TYPE,
            ANCIENT_CITY_PORTAL_PROMPT_ENTITY_ID,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AncientCityPortalPromptEntity).build()
        )
        val lifeEntity = Registry.register(
            Registries.ENTITY_TYPE,
            ANCIENT_CITY_PORTAL_LIFE_ENTITY_ID,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AncientCityPortalLifeEntity).build()
        )

        EntityRendererRegistry.register(tabEntity, ::AncientCityPortalItemDisplayEntityRenderer)
        EntityRendererRegistry.register(advancementEntity, ::AncientCityPortalItemDisplayEntityRenderer)
        EntityRendererRegistry.register(promptEntity, ::AncientCityPortalItemDisplayEntityRenderer)
        EntityRendererRegistry.register(lifeEntity, ::AncientCityPortalItemDisplayEntityRenderer)
    }

    private fun isToolBlocked(itemStack: ItemStack): Boolean {
        if (getPlayer() == null || getPlayer()!!.isCreative || !itemStack.isDamageable) {
            return false
        }
        val item = itemStack.item
        if (item is ToolItem) {
            val toolMaterial = item.material
            return toolMaterial == ToolMaterials.STONE && isActionBlocked(BlockedAction.USING_STONE_TOOLS) ||
                    toolMaterial == ToolMaterials.IRON && isActionBlocked(BlockedAction.USING_IRON_TOOLS) ||
                    toolMaterial == ToolMaterials.DIAMOND && isActionBlocked(BlockedAction.USING_DIAMOND_TOOLS) ||
                    toolMaterial == ToolMaterials.NETHERITE && isActionBlocked(BlockedAction.USING_NETHERITE_TOOLS)
        }
        return false
    }

    companion object {
        const val ID = "achievetodo"

        const val ADVANCEMENT_PATH_PREFIX = "action/"
        private const val ADVANCEMENT_CRITERIA_PREFIX = "action_"

        private val BACAP_MAIN_DATA_PACK_ID = Identifier(ID, "bacap")
        private val CORE_DATA_PACK_ID = Identifier(ID, "bacap_achievetodo-core")
        private val HARDCORE_DATA_PACK_ID = Identifier(ID, "bacap_hc")
        private val BACAP_LANGUAGE_RESOURCE_PACK_ID = Identifier(ID, "bacap_lp")

        @JvmField
        val ANCIENT_CITY_PORTAL_BLOCK = AncientCityPortalBlock(
            AbstractBlock.Settings.create()
                .noCollision()
                .ticksRandomly()
                .strength(-1.0f)
                .sounds(BlockSoundGroup.GLASS)
                .luminance { 11 }
                .pistonBehavior(PistonBehavior.BLOCK)
        )

        private val LOCKED_ACTION_ITEM = Item(FabricItemSettings())

        val ANCIENT_CITY_PORTAL_TAB_ENTITY_ID = Identifier(ID, "ancient_city_portal_tab_entity")
        val ANCIENT_CITY_PORTAL_ADVANCEMENT_ENTITY_ID = Identifier(ID, "ancient_city_portal_advancement_entity")
        val ANCIENT_CITY_PORTAL_PROMPT_ENTITY_ID = Identifier(ID, "ancient_city_portal_prompt_entity")
        val ANCIENT_CITY_PORTAL_LIFE_ENTITY_ID = Identifier(ID, "ancient_city_portal_life_entity")

        @JvmField
        val ANCIENT_CITY_PORTAL_PARTICLES = FabricParticleTypes.simple()

        @JvmField
        var lastAchievementsCount = 0

        @JvmStatic
        fun getPlayer() = MinecraftClient.getInstance().player

        @JvmStatic
        fun showFoodBlockedDescription(food: FoodComponent) {
            if (getPlayer() == null) {
                return
            }
            BlockedAction.values().find { it.foodComponent == food }?.let { action ->
                getPlayer()!!.sendMessage(action.buildLockDescription(), true)
            }
        }

        @JvmStatic
        fun setAchievementsCount(count: Int) {
            if (count == 0 || count < lastAchievementsCount) {
                lastAchievementsCount = 0
            }
            if (lastAchievementsCount == count) {
                return
            }
            val oldCount = lastAchievementsCount
            lastAchievementsCount = count
            val actionsToUnlock: MutableList<BlockedAction> = ArrayList()
            BlockedAction.values().forEach { action ->
                if (action.isUnlocked() && oldCount < action.achievementsCountToUnlock && oldCount != 0) {
                    actionsToUnlock.add(action)
                }
            }
            val server = MinecraftClient.getInstance().server ?: return
            actionsToUnlock.forEach { action ->
                val advancement = server.advancementLoader[action.buildAdvancementId()]
                MinecraftClient.getInstance().toastManager.add(AchieveToDoToast(advancement, action))
            }
        }

        @JvmStatic
        fun isActionBlocked(action: BlockedAction): Boolean {
            if (getPlayer() == null || getPlayer()!!.isCreative || action.isUnlocked()) {
                return false
            }
            getPlayer()?.sendMessage(action.buildLockDescription(), true)
            grantActionAdvancement(action)
            return true
        }

        @JvmStatic
        fun isFoodBlocked(food: FoodComponent): Boolean {
            if (getPlayer() == null || getPlayer()!!.isCreative) {
                return false
            }
            return BlockedAction.values().find { it.foodComponent == food }?.let { action ->
                grantActionAdvancement(action)
                return@let action.isUnlocked()
            } ?: false
        }

        @JvmStatic
        fun isEquipmentBlocked(stack: Item): Boolean {
            if (stack === Items.ELYTRA && isActionBlocked(BlockedAction.EQUIP_ELYTRA)) {
                return true
            }
            if (stack is ArmorItem) {
                val armorMaterial = stack.material
                return armorMaterial === ArmorMaterials.IRON && isActionBlocked(BlockedAction.EQUIP_IRON_ARMOR) ||
                        armorMaterial === ArmorMaterials.DIAMOND && isActionBlocked(BlockedAction.EQUIP_DIAMOND_ARMOR) ||
                        armorMaterial === ArmorMaterials.NETHERITE && isActionBlocked(BlockedAction.EQUIP_NETHERITE_ARMOR)
            }
            return false
        }

        @JvmStatic
        fun isVillagerBlocked(profession: VillagerProfession): Boolean {
            return BlockedAction.values().find { it.villagerProfession == profession }?.let { action ->
                return@let isActionBlocked(action)
            } ?: false
        }

        @JvmStatic
        fun getBlockedActionFromAdvancement(advancement: Advancement): BlockedAction? {
            if (advancement.id.namespace == ID && advancement.id.path.startsWith(ADVANCEMENT_PATH_PREFIX)) {
                val key = advancement.id.path
                    .split(ADVANCEMENT_PATH_PREFIX.toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1]
                return BlockedAction.map(key)
            }
            return null
        }

        @JvmStatic
        fun isNotInternalDatapack(resourceId: String): Boolean {
            return resourceId != BACAP_MAIN_DATA_PACK_ID.toString() &&
                    resourceId != CORE_DATA_PACK_ID.toString() &&
                    resourceId != HARDCORE_DATA_PACK_ID.toString()
        }

        private fun grantActionAdvancement(action: BlockedAction) {
            val server = MinecraftClient.getInstance().server ?: return
            val tab = server.advancementLoader[action.buildAdvancementId()]
            server.playerManager.playerList[0]?.advancementTracker?.grantCriterion(
                tab,
                ADVANCEMENT_CRITERIA_PREFIX + action.name
            )
        }
    }
}
