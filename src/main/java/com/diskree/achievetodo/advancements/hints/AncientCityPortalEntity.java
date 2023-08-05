package com.diskree.achievetodo.advancements.hints;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.server.AchieveToDoServer;
import com.diskree.achievetodo.ItemDisplayEntityImpl;
import com.diskree.achievetodo.advancements.AdvancementGenerator;
import com.diskree.achievetodo.advancements.AdvancementHint;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.DynamicGameEventListener;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class AncientCityPortalEntity extends DisplayEntity.ItemDisplayEntity {

    public static final int RITUAL_RADIUS = 20;
    public static final int PORTAL_WIDTH = 22;
    public static final int PORTAL_HEIGHT = 8;
    public static final int HINTS_LIMIT = 10;

    public static final BooleanProperty REINFORCED_DEEPSLATE_CHARGED_PROPERTY = BooleanProperty.of("charged");
    public static final BooleanProperty REINFORCED_DEEPSLATE_BROKEN_PROPERTY = BooleanProperty.of("broken");
    private final DynamicGameEventListener<JukeboxEventListener> jukeboxEventHandler;
    @Nullable
    private BlockPos jukeboxPos;
    private boolean isDragonEggGranted;
    private int spawnExperienceTick;
    private int receivedHints;

    public AncientCityPortalEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.jukeboxEventHandler = new DynamicGameEventListener<>(new JukeboxEventListener(new EntityPositionSource(AncientCityPortalEntity.this, 0), AchieveToDo.JUKEBOX_PLAY.getRange()));
    }

    @Nullable
    public static AncientCityPortalEntity findForBlock(WorldAccess world, BlockPos blockPos) {
        Box box = new Box(
                blockPos
                        .offset(Direction.Axis.X, -AncientCityPortalEntity.PORTAL_WIDTH)
                        .offset(Direction.Axis.Z, -AncientCityPortalEntity.PORTAL_WIDTH)
                        .down(AncientCityPortalEntity.PORTAL_HEIGHT),
                blockPos
                        .offset(Direction.Axis.X, AncientCityPortalEntity.PORTAL_WIDTH)
                        .offset(Direction.Axis.Z, AncientCityPortalEntity.PORTAL_WIDTH)
                        .up(AncientCityPortalEntity.PORTAL_HEIGHT)
        );
        List<AncientCityPortalEntity> portalEntities = world.getEntitiesByClass(AncientCityPortalEntity.class, box, (portalEntity) -> portalEntity.isPortalBlock(blockPos));
        if (portalEntities.size() == 1) {
            return portalEntities.get(0);
        }
        return null;
    }

    public static boolean isReinforcedDeepslate(float hardness, float resistance) {
        return hardness == 55.0f && resistance == 1200.0f;
    }

    public static boolean isJukebox(WorldAccess world, BlockPos pos) {
        Box box = new Box(
                pos
                        .offset(Direction.Axis.X, -AncientCityPortalEntity.RITUAL_RADIUS)
                        .offset(Direction.Axis.Z, -AncientCityPortalEntity.RITUAL_RADIUS)
                        .down(AncientCityPortalEntity.RITUAL_RADIUS),
                pos
                        .offset(Direction.Axis.X, AncientCityPortalEntity.RITUAL_RADIUS)
                        .offset(Direction.Axis.Z, AncientCityPortalEntity.RITUAL_RADIUS)
                        .up(AncientCityPortalEntity.RITUAL_RADIUS)
        );
        if (world != null) {
            List<AncientCityPortalEntity> portalEntities = world.getEntitiesByClass(AncientCityPortalEntity.class, box, (portalEntity) -> true);
            return portalEntities.size() == 1;
        }
        return false;
    }

    public static int getPortalFrameLightLevel(BlockState state) {
        return state.get(REINFORCED_DEEPSLATE_CHARGED_PROPERTY) ? 15 : 0;
    }

    public boolean isPortalBlock(BlockPos pos) {
        for (BlockPos portalBlock : getPortalBlocks(false)) {
            if (portalBlock.equals(pos)) {
                return true;
            }
        }
        for (BlockPos portalBlock : getPortalBlocks(true, true)) {
            if (portalBlock.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public boolean charge(boolean isExperience) {
        if (!isPortalActivated()) {
            return false;
        }
        if (!checkPlayerRadius()) {
            discharge();
            AchieveToDoServer.grantHintsAdvancement("so_long_and_thanks_for_the_xp");
            return isExperience;
        }
        if (isExperience) {
            if (!isDragonEggGranted) {
                return false;
            }
        } else {
            if (isDragonEggGranted) {
                return false;
            }
            isDragonEggGranted = true;
            stopJukebox(jukeboxPos);
        }
        if (isExperience) {
            ArrayList<BlockPos> blocksToCharge = new ArrayList<>();
            for (BlockPos pos : getPortalBlocks(true, true)) {
                if (!isReinforcedDeepslate(pos) || getWorld().getBlockState(pos).get(REINFORCED_DEEPSLATE_CHARGED_PROPERTY)) {
                    continue;
                }
                blocksToCharge.add(pos);
            }
            Collections.shuffle(blocksToCharge);
            BlockPos randomPortalFrameBlockPos = blocksToCharge.get(0);
            getWorld().setBlockState(randomPortalFrameBlockPos, Blocks.REINFORCED_DEEPSLATE.getDefaultState().with(REINFORCED_DEEPSLATE_CHARGED_PROPERTY, true));
            getWorld().playSound(null, (double) randomPortalFrameBlockPos.getX() + 0.5, (double) randomPortalFrameBlockPos.getY() + 0.5, (double) randomPortalFrameBlockPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0f, 0.8f);
            if (blocksToCharge.size() == 1) {
                discharge();
                AdvancementHint advancementHint = AdvancementGenerator.generateForHint();
                if (advancementHint != null) {
                    showAdvancementTab(advancementHint.tab());
                    showAdvancementHint(advancementHint.hint(), advancementHint.dropHint());
                    showAdvancement(advancementHint.advancement());
                    AchieveToDoServer.grantHintsAdvancement("soul_energizer");
                    receivedHints++;
                    if (receivedHints >= HINTS_LIMIT) {
                        for (BlockPos pos : getPortalBlocks(true, true)) {
                            if (!isReinforcedDeepslate(pos)) {
                                continue;
                            }
                            getWorld().setBlockState(pos, Blocks.REINFORCED_DEEPSLATE.getDefaultState().with(REINFORCED_DEEPSLATE_BROKEN_PROPERTY, true));
                        }
                        AchieveToDoServer.grantHintsAdvancement("nothing_lasts_forever");
                    }
                } else {
                    ItemStack barrierItem = new ItemStack(Items.BARRIER);
                    showAdvancementTab(barrierItem);
                    showAdvancementHint(barrierItem, false);
                    showAdvancement(barrierItem);
                    AchieveToDoServer.grantHintsAdvancement("no_new_updates");
                }
            } else {
                spawnExperienceTick = 3;
            }
        } else {
            spawnExperienceTick = 10;
        }
        return true;
    }

    private void discharge() {
        spawnExperienceTick = 0;
        if (isDragonEggGranted) {
            isDragonEggGranted = false;
            dropItem(Items.DRAGON_EGG);
        }
        for (BlockPos pos : getPortalBlocks(false)) {
            if (!isPortal(pos)) {
                continue;
            }
            getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        for (BlockPos pos : getPortalBlocks(true, true)) {
            if (!isReinforcedDeepslate(pos)) {
                continue;
            }
            getWorld().setBlockState(pos, Blocks.REINFORCED_DEEPSLATE.getDefaultState());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (spawnExperienceTick > 0 && --spawnExperienceTick == 0) {
            World world = getWorld();
            if (world != null) {
                MinecraftServer server = getWorld().getServer();
                if (server != null) {
                    ServerPlayerEntity player = server.getPlayerManager().getPlayerList().get(0);
                    Vec3d playerPos = player.getPos().add(0, 0.6, 0);
                    int experienceCount = player.totalExperience;
                    if (experienceCount < 10) {
                        player.damage(getWorld().getDamageSources().badRespawnPoint(playerPos), 1);
                        AchieveToDoServer.grantHintsAdvancement("beethoven_mistake");
                    } else {
                        player.addExperience(-10);
                    }
                    ArrayList<BlockPos> portalBlocks = getPortalBlocks(false);
                    Collections.shuffle(portalBlocks);
                    double playerX = playerPos.getX();
                    double playerY = playerPos.getY();
                    double playerZ = playerPos.getZ();
                    BlockPos inclinePos = new BlockPos((int) playerX + random.rangeClosed(-1, 1), (int) playerY + random.rangeClosed(-1, 1), (int) playerZ + random.rangeClosed(-1, 1));
                    getWorld().spawnEntity(new AncientCityPortalExperienceOrbEntity(getWorld(), playerX, playerY, playerZ, portalBlocks.get(0), inclinePos, random.rangeClosed(1, 10)));
                }
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    public void showAdvancementTab(ItemStack tabItem) {
        BlockPos tabEntityPos = getBlockPos().offset(getHorizontalFacing().rotateYCounterclockwise(), 5);
        List<AncientCityPortalTabEntity> tabEntities = getWorld().getEntitiesByType(AchieveToDo.ANCIENT_CITY_PORTAL_TAB, Box.of(tabEntityPos.ofCenter(), 1, 1, 1), (entity) -> true);
        if (tabEntities != null && tabEntities.size() == 1) {
            AncientCityPortalTabEntity tabEntity = tabEntities.get(0);
            ((ItemDisplayEntityImpl) tabEntity).achieveToDo$publicSetStack(tabItem);
        }
    }

    public void showAdvancementHint(ItemStack hintItem, boolean drop) {
        BlockPos hintEntityPos = getBlockPos().offset(getHorizontalFacing().rotateYClockwise(), 5);
        List<AncientCityPortalPromptEntity> hintEntities = getWorld().getEntitiesByType(AchieveToDo.ANCIENT_CITY_PORTAL_HINT, Box.of(hintEntityPos.ofCenter(), 1, 1, 1), (entity) -> true);
        if (hintEntities != null && hintEntities.size() == 1) {
            AncientCityPortalPromptEntity hintEntity = hintEntities.get(0);
            if (drop) {
                ItemStack arrow = new ItemStack(AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM);
                NbtCompound nbt = new NbtCompound();
                nbt.putInt("Damage", 47);
                arrow.setNbt(nbt);
                ((ItemDisplayEntityImpl) hintEntity).achieveToDo$publicSetStack(arrow);
                getWorld().spawnEntity(new ItemEntity(getWorld(), hintEntity.getX(), hintEntity.getY() - 1, hintEntity.getZ(), hintItem, 0, 0, 0));
            } else {
                ((ItemDisplayEntityImpl) hintEntity).achieveToDo$publicSetStack(hintItem);
            }
        }
    }

    public void showAdvancement(ItemStack advancementItem) {
        ((ItemDisplayEntityImpl) this).achieveToDo$publicSetStack(advancementItem);
    }

    public void checkCharging(BlockState state) {
        if (isPortalActivated()) {
            return;
        }
        isDragonEggGranted = false;
        for (BlockPos pos : getPortalBlocks(true, true)) {
            if (!isReinforcedDeepslate(pos)) {
                continue;
            }
            state = state.with(REINFORCED_DEEPSLATE_CHARGED_PROPERTY, false);
            if (receivedHints >= HINTS_LIMIT) {
                state = state.with(REINFORCED_DEEPSLATE_BROKEN_PROPERTY, true);
            }
            getWorld().setBlockState(pos, state);
        }
    }

    public boolean isPortalActivated() {
        for (BlockPos pos : getPortalBlocks(false)) {
            if (!isPortal(pos)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPortalActivationInProgress() {
        if (isPortalActivated()) {
            return false;
        }
        for (BlockPos pos : getPortalBlocks(false)) {
            if (isPortal(pos)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerWorld> updater) {
        World var3 = this.getWorld();
        if (var3 instanceof ServerWorld serverWorld) {
            updater.accept(jukeboxEventHandler, serverWorld);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (jukeboxPos != null) {
            nbt.put("JukeboxPos", NbtHelper.fromBlockPos(jukeboxPos));
        }
        nbt.putBoolean("DragonEggGranted", isDragonEggGranted);
        nbt.putInt("SpawnExperienceTick", spawnExperienceTick);
        nbt.putInt("ReceivedHints", receivedHints);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("JukeboxPos", NbtElement.COMPOUND_TYPE)) {
            jukeboxPos = NbtHelper.toBlockPos(nbt.getCompound("JukeboxPos"));
        }
        isDragonEggGranted = nbt.getBoolean("DragonEggGranted");
        spawnExperienceTick = nbt.getInt("SpawnExperienceTick");
        receivedHints = nbt.getInt("ReceivedHints");
    }

    private void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
        if (this.jukeboxPos != null && !this.jukeboxPos.equals(jukeboxPos) && isPortalActivationInProgress()) {
            stopJukebox(jukeboxPos);
            return;
        }
        boolean isPortalActivated = isPortalActivated();
        if (isPortalActivated) {
            return;
        }
        if (playing && checkPlayerInvisibility() && checkPlayerRadius() && checkDisk(jukeboxPos) && checkPortal()) {
            ItemStack airItem = new ItemStack(Items.AIR);
            showAdvancementTab(airItem);
            showAdvancementHint(airItem, false);
            showAdvancement(airItem);

            this.jukeboxPos = jukeboxPos;

            Pair<BlockPos, BlockPos> blocks = getNextActivationSpiralBlocks();
            if (blocks != null) {
                BlockState state = AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK.getDefaultState().with(AncientCityPortalBlock.AXIS, getHorizontalFacing().rotateYClockwise().getAxis());
                getWorld().setBlockState(blocks.getLeft(), state);
                getWorld().setBlockState(blocks.getRight(), state);
            }
            isPortalActivated = isPortalActivated();
            for (BlockPos pos : getPortalBlocks(false)) {
                if (!isPortal(pos)) {
                    continue;
                }
                AncientCityPortalBlock portalBlock = (AncientCityPortalBlock) getWorld().getBlockState(pos).getBlock();
                if (isPortalActivated) {
                    portalBlock.impulseParticles();
                } else {
                    portalBlock.hideParticles();
                }
            }
            if (isPortalActivated) {
                AchieveToDoServer.grantHintsAdvancement("ancient_light");
            }
        } else {
            stopJukebox(jukeboxPos);
            this.jukeboxPos = null;
            for (BlockPos pos : getPortalBlocks(false)) {
                if (!isPortal(pos)) {
                    continue;
                }
                getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    private boolean checkPlayerInvisibility() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        return player != null && player.hasStatusEffect(StatusEffects.INVISIBILITY);
    }

    private boolean checkPlayerRadius() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !player.isAlive()) {
            return false;
        }
        return player.getWorld().getRegistryKey() == getWorld().getRegistryKey() && player.getPos().distanceTo(getBlockPos().ofCenter()) <= RITUAL_RADIUS;
    }

    private boolean checkDisk(BlockPos jukeboxPos) {
        if (jukeboxPos == null) {
            return false;
        }
        World world = getWorld();
        if (world == null) {
            return false;
        }
        BlockEntity blockEntity = world.getBlockEntity(jukeboxPos);
        if (!(blockEntity instanceof JukeboxBlockEntity jukebox)) {
            return false;
        }
        ItemStack stack = jukebox.getStack(0);
        return stack != null && Items.MUSIC_DISC_5.equals(stack.getItem());
    }

    private boolean checkPortal() {
        if (receivedHints >= HINTS_LIMIT) {
            return false;
        }
        for (BlockPos pos : getPortalBlocks(false)) {
            if (!isPortalReplaceable(pos) && !isPortal(pos)) {
                return false;
            }
        }
        for (BlockPos pos : getPortalBlocks(true)) {
            if (!isReinforcedDeepslate(pos)) {
                return false;
            }
        }
        return true;
    }

    private void stopJukebox(BlockPos jukeboxPos) {
        if (!checkDisk(jukeboxPos)) {
            return;
        }
        BlockEntity blockEntity = getWorld().getBlockEntity(jukeboxPos);
        if (blockEntity instanceof JukeboxBlockEntity jukebox) {
            jukebox.dropDisc();
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isReinforcedDeepslate(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isOf(Blocks.REINFORCED_DEEPSLATE);
    }

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "deprecation"})
    private boolean isPortalReplaceable(BlockPos pos) {
        World world = getWorld();
        if (world == null) {
            return false;
        }
        BlockState state = world.getBlockState(pos);
        return state.isAir() || state.isOf(Blocks.SCULK_VEIN) || state.isLiquid();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isPortal(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isOf(AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK);
    }

    private ArrayList<BlockPos> getPortalBlocks(boolean perimeter) {
        return getPortalBlocks(perimeter, false);
    }

    private ArrayList<BlockPos> getPortalBlocks(boolean frame, boolean includeCorners) {
        int axisWidth = PORTAL_WIDTH - 1;
        int axisHeight = PORTAL_HEIGHT - 1;
        Direction fromOriginDirection = getFromOriginDirection();
        BlockPos origin = getPortalFrameOrigin();
        ArrayList<BlockPos> blocks = new ArrayList<>();
        for (int x = 0; x <= axisWidth; x++) {
            for (int y = 0; y <= axisHeight; y++) {
                if (!includeCorners && (x == 0 && y == 0 || x == 0 && y == axisHeight || x == axisWidth && y == 0 || x == axisWidth && y == axisHeight)) {
                    continue;
                }
                BlockPos pos = origin.offset(fromOriginDirection, x).down(y);
                if (x == 0 || y == 0 || x == axisWidth || y == axisHeight) {
                    if (frame) {
                        blocks.add(pos);
                    }
                } else {
                    if (!frame) {
                        blocks.add(pos);
                    }
                }
            }
        }
        return blocks;
    }

    private Pair<BlockPos, BlockPos> getNextActivationSpiralBlocks() {
        BlockPos origin = getPortalFrameOrigin().offset(getFromOriginDirection()).down();
        ArrayList<Pair<Point, Point>> spiralPoints = new ArrayList<>();

        spiralPoints.add(new Pair<>(new Point(0, 0), new Point(19, 5)));
        spiralPoints.add(new Pair<>(new Point(0, 1), new Point(19, 4)));
        spiralPoints.add(new Pair<>(new Point(0, 2), new Point(19, 3)));
        spiralPoints.add(new Pair<>(new Point(0, 3), new Point(19, 2)));
        spiralPoints.add(new Pair<>(new Point(0, 4), new Point(19, 1)));
        spiralPoints.add(new Pair<>(new Point(0, 5), new Point(19, 0)));

        spiralPoints.add(new Pair<>(new Point(1, 5), new Point(18, 0)));
        spiralPoints.add(new Pair<>(new Point(1, 4), new Point(18, 1)));
        spiralPoints.add(new Pair<>(new Point(1, 3), new Point(18, 2)));
        spiralPoints.add(new Pair<>(new Point(1, 2), new Point(18, 3)));
        spiralPoints.add(new Pair<>(new Point(1, 1), new Point(18, 4)));
        spiralPoints.add(new Pair<>(new Point(1, 0), new Point(18, 5)));

        spiralPoints.add(new Pair<>(new Point(2, 0), new Point(17, 5)));
        spiralPoints.add(new Pair<>(new Point(2, 1), new Point(17, 4)));
        spiralPoints.add(new Pair<>(new Point(2, 2), new Point(17, 3)));
        spiralPoints.add(new Pair<>(new Point(2, 3), new Point(17, 2)));
        spiralPoints.add(new Pair<>(new Point(2, 4), new Point(17, 1)));
        spiralPoints.add(new Pair<>(new Point(2, 5), new Point(17, 0)));

        spiralPoints.add(new Pair<>(new Point(3, 5), new Point(16, 0)));
        spiralPoints.add(new Pair<>(new Point(3, 4), new Point(16, 1)));
        spiralPoints.add(new Pair<>(new Point(3, 3), new Point(16, 2)));
        spiralPoints.add(new Pair<>(new Point(3, 2), new Point(16, 3)));
        spiralPoints.add(new Pair<>(new Point(3, 1), new Point(16, 4)));
        spiralPoints.add(new Pair<>(new Point(3, 0), new Point(16, 5)));

        spiralPoints.add(new Pair<>(new Point(4, 0), new Point(15, 5)));
        spiralPoints.add(new Pair<>(new Point(4, 1), new Point(15, 4)));
        spiralPoints.add(new Pair<>(new Point(4, 2), new Point(15, 3)));
        spiralPoints.add(new Pair<>(new Point(4, 3), new Point(15, 2)));
        spiralPoints.add(new Pair<>(new Point(4, 4), new Point(15, 1)));
        spiralPoints.add(new Pair<>(new Point(4, 5), new Point(15, 0)));

        spiralPoints.add(new Pair<>(new Point(5, 5), new Point(14, 0)));
        spiralPoints.add(new Pair<>(new Point(5, 4), new Point(14, 1)));
        spiralPoints.add(new Pair<>(new Point(5, 3), new Point(14, 2)));
        spiralPoints.add(new Pair<>(new Point(5, 2), new Point(14, 3)));
        spiralPoints.add(new Pair<>(new Point(5, 1), new Point(14, 4)));
        spiralPoints.add(new Pair<>(new Point(5, 0), new Point(14, 5)));

        spiralPoints.add(new Pair<>(new Point(6, 0), new Point(13, 5)));
        spiralPoints.add(new Pair<>(new Point(6, 1), new Point(13, 4)));
        spiralPoints.add(new Pair<>(new Point(6, 2), new Point(13, 3)));
        spiralPoints.add(new Pair<>(new Point(6, 3), new Point(13, 2)));
        spiralPoints.add(new Pair<>(new Point(6, 4), new Point(13, 1)));
        spiralPoints.add(new Pair<>(new Point(6, 5), new Point(13, 0)));

        spiralPoints.add(new Pair<>(new Point(7, 5), new Point(12, 0)));
        spiralPoints.add(new Pair<>(new Point(8, 5), new Point(11, 0)));
        spiralPoints.add(new Pair<>(new Point(9, 5), new Point(10, 0)));
        spiralPoints.add(new Pair<>(new Point(10, 5), new Point(9, 0)));
        spiralPoints.add(new Pair<>(new Point(11, 5), new Point(8, 0)));
        spiralPoints.add(new Pair<>(new Point(12, 5), new Point(7, 0)));

        spiralPoints.add(new Pair<>(new Point(12, 4), new Point(7, 1)));
        spiralPoints.add(new Pair<>(new Point(12, 3), new Point(7, 2)));
        spiralPoints.add(new Pair<>(new Point(12, 2), new Point(7, 3)));
        spiralPoints.add(new Pair<>(new Point(12, 1), new Point(7, 4)));

        spiralPoints.add(new Pair<>(new Point(11, 1), new Point(8, 4)));
        spiralPoints.add(new Pair<>(new Point(10, 1), new Point(9, 4)));
        spiralPoints.add(new Pair<>(new Point(9, 1), new Point(10, 4)));
        spiralPoints.add(new Pair<>(new Point(8, 1), new Point(11, 4)));

        spiralPoints.add(new Pair<>(new Point(8, 2), new Point(11, 3)));
        spiralPoints.add(new Pair<>(new Point(8, 3), new Point(11, 2)));

        spiralPoints.add(new Pair<>(new Point(9, 3), new Point(10, 2)));
        spiralPoints.add(new Pair<>(new Point(10, 3), new Point(9, 2)));

        for (Pair<Point, Point> spiralPoint : spiralPoints) {
            Point leftSpiralPoint = spiralPoint.getLeft();
            Point rightSpiralPoint = spiralPoint.getRight();
            BlockPos leftPos = origin.offset(getFromOriginDirection(), leftSpiralPoint.x).down(leftSpiralPoint.y);
            BlockPos rightPos = origin.offset(getFromOriginDirection(), rightSpiralPoint.x).down(rightSpiralPoint.y);
            if (isPortalReplaceable(leftPos) && isPortalReplaceable(rightPos)) {
                return new Pair<>(leftPos, rightPos);
            }
        }
        return null;
    }

    private BlockPos getPortalFrameOrigin() {
        Direction facingDirection = getHorizontalFacing();
        Direction toOriginDirection = facingDirection.rotateYCounterclockwise();
        return getBlockPos().offset(toOriginDirection, 11).up(3);
    }

    private Direction getFromOriginDirection() {
        return getHorizontalFacing().rotateYClockwise();
    }

    class JukeboxEventListener implements GameEventListener {
        private final PositionSource positionSource;
        private final int range;

        public JukeboxEventListener(PositionSource positionSource, int range) {
            this.positionSource = positionSource;
            this.range = range;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public int getRange() {
            return this.range;
        }

        @Override
        public boolean listen(ServerWorld world, GameEvent event, GameEvent.Context context, Vec3d pos) {
            if (event == AchieveToDo.JUKEBOX_PLAY) {
                AncientCityPortalEntity.this.updateJukeboxPos(BlockPos.fromPosition(pos), true);
                return true;
            }
            if (event == AchieveToDo.JUKEBOX_STOP_PLAY) {
                AncientCityPortalEntity.this.updateJukeboxPos(BlockPos.fromPosition(pos), false);
                return true;
            }
            return false;
        }
    }
}
