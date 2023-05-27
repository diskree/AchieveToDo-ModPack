package com.diskree.achievetodo.ancient_city_portal;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.JukeboxBlockEntityImpl;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class AncientCityPortalEntity extends DisplayEntity.ItemDisplayEntity {

    public static final int RITUAL_RADIUS = 20;
    public static final int PORTAL_WIDTH = 22;
    public static final int PORTAL_HEIGHT = 8;

    @Nullable
    private BlockPos jukeboxPos;
    private boolean isEnderDragonEggGranted;
    private final EntityGameEventHandler<JukeboxEventListener> jukeboxEventHandler;

    public AncientCityPortalEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.jukeboxEventHandler = new EntityGameEventHandler<>(new JukeboxEventListener(new EntityPositionSource(AncientCityPortalEntity.this, 0), AchieveToDoMod.JUKEBOX_PLAY.getRange()));
    }

    @Nullable
    public static AncientCityPortalEntity findForBlock(WorldAccess world, BlockPos blockPos) {
        Box box = new Box(
                blockPos
                        .offset(Direction.Axis.X, -AncientCityPortalEntity.PORTAL_WIDTH)
                        .offset(Direction.Axis.Z, -AncientCityPortalEntity.PORTAL_WIDTH)
                        .down(AncientCityPortalEntity.PORTAL_HEIGHT),
                blockPos.offset(Direction.Axis.X, AncientCityPortalEntity.PORTAL_WIDTH)
                        .offset(Direction.Axis.Z, AncientCityPortalEntity.PORTAL_WIDTH)
                        .up(AncientCityPortalEntity.PORTAL_HEIGHT)
        );
        List<AncientCityPortalEntity> portalEntities = world.getEntitiesByClass(AncientCityPortalEntity.class, box, (portalEntity) -> portalEntity.isPortalBlock(blockPos));
        if (portalEntities.size() == 1) {
            return portalEntities.get(0);
        }
        return null;
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

    public void charge(Entity entity) {
        if (!isPortalActivated()) {
            return;
        }
        if (entity instanceof AncientCityPortalExperienceOrbEntity) {
            if (!isEnderDragonEggGranted) {
                return;
            }
        } else {
            if (isEnderDragonEggGranted) {
                return;
            }
        }
        entity.kill();
        isEnderDragonEggGranted = true;
        if (entity instanceof AncientCityPortalExperienceOrbEntity) {
            ArrayList<BlockPos> blocksToCharge = new ArrayList<>();
            for (BlockPos pos : getPortalBlocks(true, true)) {
                if (!isReinforcedDeepslate(pos) || getWorld().getBlockState(pos).get(AchieveToDoMod.REINFORCED_DEEPSLATE_CHARGED_PROPERTY)) {
                    continue;
                }
                blocksToCharge.add(pos);
            }
            Collections.shuffle(blocksToCharge);
            BlockPos randomPortalFrameBlockPos = blocksToCharge.get(0);
            getWorld().setBlockState(randomPortalFrameBlockPos, Blocks.REINFORCED_DEEPSLATE.getDefaultState().with(AchieveToDoMod.REINFORCED_DEEPSLATE_CHARGED_PROPERTY, true));
            getWorld().playSound(null, (double) randomPortalFrameBlockPos.getX() + 0.5, (double) randomPortalFrameBlockPos.getY() + 0.5, (double) randomPortalFrameBlockPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (blocksToCharge.size() == 1) {
                for (BlockPos pos : getPortalBlocks(false)) {
                    if (!isPortal(pos)) {
                        continue;
                    }
                    getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
        if (!isPortalCharged()) {
            if (AchieveToDoMod.getPlayer() == null) {
                return;
            }
            ArrayList<BlockPos> portalBlocks = getPortalBlocks(false);
            Collections.shuffle(portalBlocks);
            Vec3d playerPos = AchieveToDoMod.getPlayer().getPos().offset(Direction.UP, 1);
            getWorld().spawnEntity(new AncientCityPortalExperienceOrbEntity(getWorld(), playerPos.getX(), playerPos.getY(), playerPos.getZ(), portalBlocks.get(0)));
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    public void checkCharging() {
        if (isPortalActivated()) {
            return;
        }
        isEnderDragonEggGranted = false;
        for (BlockPos pos : getPortalBlocks(true, true)) {
            if (!isReinforcedDeepslate(pos)) {
                continue;
            }
            getWorld().setBlockState(pos, Blocks.REINFORCED_DEEPSLATE.getDefaultState().with(AchieveToDoMod.REINFORCED_DEEPSLATE_CHARGED_PROPERTY, false));
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

    public boolean isPortalCharged() {
        if (!isPortalActivated()) {
            return false;
        }
        return getPortalFrameUnchargedBlocks().isEmpty();
    }

    public ArrayList<BlockPos> getPortalFrameUnchargedBlocks() {
        ArrayList<BlockPos> unchargedBlocks = new ArrayList<>();
        for (BlockPos pos : getPortalBlocks(true, true)) {
            if (!isReinforcedDeepslate(pos) || getWorld().getBlockState(pos).get(AchieveToDoMod.REINFORCED_DEEPSLATE_CHARGED_PROPERTY)) {
                continue;
            }
            unchargedBlocks.add(pos);
        }
        return unchargedBlocks;
    }

    public static int getPortalFrameLightLevel(BlockState state) {
        return state.get(AchieveToDoMod.REINFORCED_DEEPSLATE_CHARGED_PROPERTY) ? 15 : 0;
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        if (getWorld() instanceof ServerWorld serverWorld) {
            callback.accept(this.jukeboxEventHandler, serverWorld);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (jukeboxPos != null) {
            nbt.put("JukeboxPos", NbtHelper.fromBlockPos(jukeboxPos));
        }
        nbt.putBoolean("EnderDragonEggGranted", isEnderDragonEggGranted);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("JukeboxPos", NbtElement.COMPOUND_TYPE)) {
            jukeboxPos = NbtHelper.toBlockPos(nbt.getCompound("JukeboxPos"));
        }
        isEnderDragonEggGranted = nbt.getBoolean("EnderDragonEggGranted");
    }

    private void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
        if (this.jukeboxPos != null && !this.jukeboxPos.equals(jukeboxPos) && isPortalActivationInProgress()) {
            stopJukebox(jukeboxPos);
            return;
        }
        boolean isPortalActivated = isPortalActivated();
        boolean isDiskRelaxPartPlaying = isDiskRelaxPartPlaying(jukeboxPos);
        if (isPortalActivated || isDiskRelaxPartPlaying) {
            if (!isPortalActivated || !isDiskRelaxPartPlaying) {
                stopJukebox(jukeboxPos);
                this.jukeboxPos = null;
            }
            return;
        }
        if (playing && checkPlayer() && checkDisk(jukeboxPos) && checkPortal()) {
            this.jukeboxPos = jukeboxPos;
            ArrayList<BlockPos> portalAirBlocks = getPortalAirBlocks();
            Collections.shuffle(portalAirBlocks);
            for (int i = 0; i < (portalAirBlocks.size() % 10 == 0 ? 4 : 3); i++) {
                if (portalAirBlocks.isEmpty()) {
                    break;
                }
                getWorld().setBlockState(portalAirBlocks.get(0), AchieveToDoMod.ANCIENT_CITY_PORTAL_BLOCK.getDefaultState().with(AncientCityPortalBlock.AXIS, getHorizontalFacing().rotateYClockwise().getAxis()));
                portalAirBlocks.remove(0);
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

    private boolean checkPlayer() {
        PlayerEntity player = AchieveToDoMod.getPlayer();
        return player != null && player.hasStatusEffect(StatusEffects.INVISIBILITY);
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
        return jukebox.getStack() != null && Items.MUSIC_DISC_5.equals(jukebox.getStack().getItem());
    }

    private boolean checkPortal() {
        for (BlockPos pos : getPortalBlocks(false)) {
            if (!isAir(pos) && !isPortal(pos)) {
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

    private ArrayList<BlockPos> getPortalAirBlocks() {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        for (BlockPos pos : getPortalBlocks(false)) {
            if (!isAir(pos)) {
                continue;
            }
            blocks.add(pos);
        }
        return blocks;
    }

    private boolean isDiskRelaxPartPlaying(BlockPos jukeboxPos) {
        BlockEntity blockEntity = getWorld().getBlockEntity(jukeboxPos);
        if (blockEntity instanceof JukeboxBlockEntityImpl jukebox) {
            return jukebox.isDiskRelaxPartPlaying();
        }
        return true;
    }

    private void stopJukebox(BlockPos jukeboxPos) {
        if (!checkDisk(jukeboxPos)) {
            return;
        }
        BlockEntity blockEntity = getWorld().getBlockEntity(jukeboxPos);
        if (blockEntity instanceof JukeboxBlockEntity jukebox) {
            jukebox.dropRecord();
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isReinforcedDeepslate(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isOf(Blocks.REINFORCED_DEEPSLATE);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isAir(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isAir();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isPortal(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isOf(AchieveToDoMod.ANCIENT_CITY_PORTAL_BLOCK);
    }

    private ArrayList<BlockPos> getPortalBlocks(boolean perimeter) {
        return getPortalBlocks(perimeter, false);
    }

    private ArrayList<BlockPos> getPortalBlocks(boolean frame, boolean includeCorners) {
        int axisWidth = PORTAL_WIDTH - 1;
        int axisHeight = PORTAL_HEIGHT - 1;
        Direction facingDirection = getHorizontalFacing();
        Direction toOriginDirection = facingDirection.rotateYCounterclockwise();
        Direction fromOriginDirection = facingDirection.rotateYClockwise();
        BlockPos origin = getBlockPos().offset(toOriginDirection, axisWidth / 2).up(axisHeight / 2);
        if (facingDirection.getAxis() == Direction.Axis.X && facingDirection.getDirection() == Direction.AxisDirection.POSITIVE || facingDirection.getAxis() == Direction.Axis.Z && facingDirection.getDirection() == Direction.AxisDirection.NEGATIVE) {
            origin = origin.offset(toOriginDirection, 1);
        }
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
        public boolean listen(ServerWorld world, GameEvent event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (event == AchieveToDoMod.JUKEBOX_PLAY) {
                AncientCityPortalEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), true);
                return true;
            }
            if (event == AchieveToDoMod.JUKEBOX_STOP_PLAY) {
                AncientCityPortalEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), false);
                return true;
            }
            return false;
        }
    }
}
