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
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;

public class AncientCityPortalAdvancementEntity extends DisplayEntity.ItemDisplayEntity {

    public static final int RITUAL_RADIUS = 20;
    public static final int PORTAL_WIDTH = 22;
    public static final int PORTAL_HEIGHT = 8;

    public static final BooleanProperty REINFORCED_DEEPSLATE_CHARGED_STATE = BooleanProperty.of("charged");

    @Nullable
    private BlockPos jukeboxPos;
    private boolean isCharging;
    private final EntityGameEventHandler<JukeboxEventListener> jukeboxEventHandler;
    private int spawnExpDelay;

    public AncientCityPortalAdvancementEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.jukeboxEventHandler = new EntityGameEventHandler<>(new JukeboxEventListener(new EntityPositionSource(AncientCityPortalAdvancementEntity.this, 0), AchieveToDoMod.JUKEBOX_PLAY.getRange()));
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
        if (!isPortalActivated() || entity.isRemoved() || spawnExpDelay != 0) {
            return;
        }
        if (entity instanceof AncientCityPortalExperienceOrbEntity) {
            if (!isCharging) {
                return;
            }
        } else {
            if (isCharging) {
                return;
            }
        }
        isCharging = true;
        entity.kill();
        if (entity instanceof AncientCityPortalExperienceOrbEntity) {
            ArrayList<BlockPos> blocksToCharge = new ArrayList<>();
            for (BlockPos pos : getPortalBlocks(true, true)) {
                if (!isReinforcedDeepslate(pos) || getWorld().getBlockState(pos).get(REINFORCED_DEEPSLATE_CHARGED_STATE)) {
                    continue;
                }
                blocksToCharge.add(pos);
            }
            Collections.shuffle(blocksToCharge);
            BlockPos randomPortalFrameBlockPos = blocksToCharge.get(0);
            getWorld().setBlockState(randomPortalFrameBlockPos, Blocks.REINFORCED_DEEPSLATE.getDefaultState().with(REINFORCED_DEEPSLATE_CHARGED_STATE, true));
            getWorld().playSound(null, (double) randomPortalFrameBlockPos.getX() + 0.5, (double) randomPortalFrameBlockPos.getY() + 0.5, (double) randomPortalFrameBlockPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (blocksToCharge.size() == 1) {
                for (BlockPos pos : getPortalBlocks(false)) {
                    if (!isPortal(pos)) {
                        continue;
                    }
                    getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            } else {
                spawnExpDelay = 5;
            }
        } else {
            spawnExpDelay = 5;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (spawnExpDelay > 0) {
            if (--spawnExpDelay == 0) {
                if (AchieveToDoMod.getPlayer() == null) {
                    return;
                }
                Vec3d playerPos = AchieveToDoMod.getPlayer().getBlockPos().toCenterPos();
                ArrayList<BlockPos> portalBlocks = getPortalBlocks(false);
                Collections.shuffle(portalBlocks);
                getWorld().spawnEntity(new AncientCityPortalExperienceOrbEntity(getWorld(), playerPos.getX(), playerPos.getY(), playerPos.getZ(), portalBlocks.get(0)));
            }
        }
    }

    public void checkCharging() {
        if (isPortalActivated()) {
            return;
        }
        isCharging = false;
        for (BlockPos pos : getPortalBlocks(true, true)) {
            if (!isReinforcedDeepslate(pos)) {
                continue;
            }
            getWorld().setBlockState(pos, Blocks.REINFORCED_DEEPSLATE.getDefaultState().with(REINFORCED_DEEPSLATE_CHARGED_STATE, false));
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

    public static int getPortalFrameLightLevel(BlockState state) {
        return state.get(REINFORCED_DEEPSLATE_CHARGED_STATE) ? 15 : 0;
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
        nbt.putBoolean("IsCharging", isCharging);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("JukeboxPos", NbtElement.COMPOUND_TYPE)) {
            jukeboxPos = NbtHelper.toBlockPos(nbt.getCompound("JukeboxPos"));
        }
        isCharging = nbt.getBoolean("IsCharging");
        System.out.println("readCustomDataFromNbt = " + isCharging);
    }

    private void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
//        if (this.jukeboxPos != null && !this.jukeboxPos.equals(jukeboxPos)) {
//            stopJukebox(jukeboxPos);
//            return;
//        }
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

    private boolean isReinforcedDeepslate(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isOf(Blocks.REINFORCED_DEEPSLATE);
    }

    private boolean isAir(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isAir();
    }

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
                AncientCityPortalAdvancementEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), true);
                return true;
            }
            if (event == AchieveToDoMod.JUKEBOX_STOP_PLAY) {
                AncientCityPortalAdvancementEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), false);
                return true;
            }
            return false;
        }
    }
}
