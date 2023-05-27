package com.diskree.achievetodo.ancient_city_portal;

import com.diskree.achievetodo.AchieveToDoMod;
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

    private int amount;
    private BlockPos target;

    public AncientCityPortalExperienceOrbEntity(World world, double x, double y, double z, BlockPos pos) {
        this(AchieveToDoMod.EXPERIENCE_ORB, world);
        target = pos;
        amount = 10;
        noClip = true;
        setPosition(x, y, z);
        setNoGravity(true);
    }

    public AncientCityPortalExperienceOrbEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void setTarget(BlockPos target) {
        this.target = target;
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
        checkBlockCollision();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.target != null) {
            Vec3d vec3d = new Vec3d(this.target.getX() + 0.5f - this.getX(), this.target.getY() + 0.5f - this.getY(), this.target.getZ() + 0.5f - this.getZ())
                    .add((random.nextDouble() - 0.5) * 0.5, (random.nextDouble() - 0.5) * 0.5, (random.nextDouble() - 0.5) * 0.5);
            this.setVelocity(this.getVelocity().add(vec3d.normalize().multiply(0.35)));
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.98, 0.98, 0.98));
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
        this.amount = nbt.getShort("Value");
        if (nbt.contains("Target", NbtElement.COMPOUND_TYPE)) {
            target = NbtHelper.toBlockPos(nbt.getCompound("Target"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Value", (short) this.amount);
        if (target != null) {
            nbt.put("Target", NbtHelper.fromBlockPos(target));
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    public int getOrbSize() {
        return 10;
    }

    public BlockPos getTarget() {
        return target;
    }

    public int getExperienceAmount() {
        return this.amount;
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
