package com.diskree.achievetodo.ancient_city_portal;

import com.diskree.achievetodo.ClientPlayNetworkHandlerImpl;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;

public class AncientCityPortalExperienceOrbSpawnS2CPacket implements Packet<ClientPlayPacketListener> {

    private final int id;
    private final double x;
    private final double y;
    private final double z;
    private final int experience;
    private final BlockPos target;

    public AncientCityPortalExperienceOrbSpawnS2CPacket(AncientCityPortalExperienceOrbEntity experienceOrbEntity) {
        this.id = experienceOrbEntity.getId();
        this.x = experienceOrbEntity.getX();
        this.y = experienceOrbEntity.getY();
        this.z = experienceOrbEntity.getZ();
        this.experience = experienceOrbEntity.getExperienceAmount();
        this.target = experienceOrbEntity.getTarget();
    }

    public AncientCityPortalExperienceOrbSpawnS2CPacket(PacketByteBuf buf) {
        this.id = buf.readVarInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.experience = buf.readShort();
        this.target = buf.readBlockPos();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.id);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeShort(this.experience);
        buf.writeBlockPos(this.target);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        ((ClientPlayNetworkHandlerImpl) clientPlayPacketListener).onAncientCityPortalExperienceOrbSpawn(this);
    }

    public int getId() {
        return this.id;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public int getExperience() {
        return this.experience;
    }

    public BlockPos getTarget() {
        return target;
    }
}
