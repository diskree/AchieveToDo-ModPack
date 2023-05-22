package com.diskree.achievetodo.ancient_city_portal;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class AncientCityPortalAdvancementEntity extends DisplayEntity.ItemDisplayEntity {

    private static final int PORTAL_WIDTH = 21;
    private static final int PORTAL_HEIGHT = 7;

    @Nullable
    private BlockPos jukeboxPos;
    private final EntityGameEventHandler<JukeboxEventListener> jukeboxEventHandler;

    private boolean isPortalActivationInProgress;

    public AncientCityPortalAdvancementEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.jukeboxEventHandler = new EntityGameEventHandler<>(new JukeboxEventListener(new EntityPositionSource(AncientCityPortalAdvancementEntity.this, 0), 19));
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        if (getWorld() instanceof ServerWorld serverWorld) {
            callback.accept(this.jukeboxEventHandler, serverWorld);
        }
    }

    private void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
        if (playing && checkPlayingDisk(jukeboxPos) && checkPortalFrame()) {
            if (!this.isPortalActivationInProgress) {
                this.jukeboxPos = jukeboxPos;
                this.isPortalActivationInProgress = true;
            }

        } else if (jukeboxPos.equals(this.jukeboxPos) || this.jukeboxPos == null) {
            this.jukeboxPos = null;
            this.isPortalActivationInProgress = false;
        }
    }

    private boolean checkPlayingDisk(BlockPos jukeboxPos) {
        World world = getWorld();
        if (jukeboxPos == null || world == null) {
            return false;
        }
        BlockEntity blockEntity = world.getBlockEntity(jukeboxPos);
        if (!(blockEntity instanceof JukeboxBlockEntity jukebox)) {
            return false;
        }
        return jukebox.getStack() != null && jukebox.getStack().getItem().equals(Items.MUSIC_DISC_5);
    }

    private boolean checkPortalFrame() {
        BlockPos leftTopCornerPos = getBlockPos().up(3).offset(getHorizontalFacing().rotateYCounterclockwise(), 11);
        for (int x = 0; x <= PORTAL_WIDTH; x++) {
            for (int y = 0; y <= PORTAL_HEIGHT; y++) {
                BlockPos pos = leftTopCornerPos.offset(getHorizontalFacing().rotateYClockwise(), x).down(y);
                if (x == 0 || y == 0 || x == PORTAL_WIDTH || y == PORTAL_HEIGHT) {
                    if (!isReinforcedDeepslate(pos)) {
                        return false;
                    }
                } else {
                    if (!isAir(pos)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isReinforcedDeepslate(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isOf(Blocks.REINFORCED_DEEPSLATE);
    }

    private boolean isAir(BlockPos pos) {
        World world = getWorld();
        return world != null && world.getBlockState(pos).isAir();
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
            if (event == GameEvent.JUKEBOX_PLAY) {
                AncientCityPortalAdvancementEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), true);
                return true;
            }
            if (event == GameEvent.JUKEBOX_STOP_PLAY) {
                AncientCityPortalAdvancementEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), false);
                return true;
            }
            return false;
        }
    }
}
