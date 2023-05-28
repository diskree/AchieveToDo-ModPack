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
    private final BlockPos target;
    private final int size;

    public AncientCityPortalExperienceOrbSpawnS2CPacket(AncientCityPortalExperienceOrbEntity experienceOrbEntity) {
        this.id = experienceOrbEntity.getId();
        this.x = experienceOrbEntity.getX();
        this.y = experienceOrbEntity.getY();
        this.z = experienceOrbEntity.getZ();
        this.target = experienceOrbEntity.getTarget();
        this.size = experienceOrbEntity.getOrbSize();
    }

    public AncientCityPortalExperienceOrbSpawnS2CPacket(PacketByteBuf buf) {
        this.id = buf.readVarInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.target = buf.readBlockPos();
        this.size = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.id);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeBlockPos(this.target);
        buf.writeInt(this.size);
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

    public BlockPos getTarget() {
        return target;
    }

    public int getSize() {
        return size;
    }
}
