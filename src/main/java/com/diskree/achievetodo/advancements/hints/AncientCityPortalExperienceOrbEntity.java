package com.diskree.achievetodo.advancements.hints;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.server.AchieveToDoServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AncientCityPortalExperienceOrbEntity extends Entity {

    private BlockPos portalTarget;
    private BlockPos inclineTarget;
    private int size;
    private int inclineTicksCount;

    public AncientCityPortalExperienceOrbEntity(World world, double x, double y, double z, BlockPos portalTarget, BlockPos inclineTarget, int size) {
        this(AchieveToDo.ANCIENT_CITY_PORTAL_EXPERIENCE_ORB, world);
        this.size = size;
        this.portalTarget = portalTarget;
        this.inclineTarget = inclineTarget;
        noClip = true;
        setPosition(x, y, z);
        setNoGravity(true);
    }

    public AncientCityPortalExperienceOrbEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
        checkBlockCollision();
    }

    @Override
    public void tick() {
        super.tick();
        if (portalTarget != null) {
            double targetX = inclineTarget != null ? inclineTarget.getX() : portalTarget.getX() + 0.5f;
            double targetY = inclineTarget != null ? inclineTarget.getY() : portalTarget.getY() + 0.5f;
            double targetZ = inclineTarget != null ? inclineTarget.getZ() : portalTarget.getZ() + 0.5f;
            Vec3d vec3d = new Vec3d(
                    targetX - getX(),
                    targetY - getY(),
                    targetZ - getZ()
            );
            setVelocity(getVelocity().add(vec3d.normalize().multiply(0.2)));
            move(MovementType.SELF, getVelocity());
            setVelocity(getVelocity().multiply(0.98d, 0.98d, 0.98d));
        }
        if (inclineTarget != null) {
            inclineTicksCount++;
            if (inclineTicksCount >= 5) {
                inclineTarget = null;
            }
        }
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("PortalTarget", NbtElement.COMPOUND_TYPE)) {
            portalTarget = NbtHelper.toBlockPos(nbt.getCompound("PortalTarget"));
        }
        if (nbt.contains("InclineTarget", NbtElement.COMPOUND_TYPE)) {
            inclineTarget = NbtHelper.toBlockPos(nbt.getCompound("InclineTarget"));
        }
        size = nbt.getInt("OrbSize");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (portalTarget != null) {
            nbt.put("PortalTarget", NbtHelper.fromBlockPos(portalTarget));
        }
        if (inclineTarget != null) {
            nbt.put("InclineTarget", NbtHelper.fromBlockPos(inclineTarget));
        }
        nbt.putInt("OrbSize", size);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    public int getOrbSize() {
        return size;
    }

    public BlockPos getPortalTarget() {
        return portalTarget;
    }

    public BlockPos getInclineTarget() {
        return inclineTarget;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new AncientCityPortalExperienceOrbSpawnS2CPacket(this);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }
}
