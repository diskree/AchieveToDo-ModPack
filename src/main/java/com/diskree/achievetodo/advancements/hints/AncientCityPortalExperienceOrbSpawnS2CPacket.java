package com.diskree.achievetodo.advancements.hints;

import com.diskree.achievetodo.client.ClientPlayNetworkHandlerImpl;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;

public class AncientCityPortalExperienceOrbSpawnS2CPacket implements Packet<ClientPlayPacketListener> {

    private final int id;
    private final double x;
    private final double y;
    private final double z;
    private final BlockPos portalTarget;
    private final BlockPos inclineTarget;
    private final int size;

    public AncientCityPortalExperienceOrbSpawnS2CPacket(AncientCityPortalExperienceOrbEntity experienceOrbEntity) {
        this.id = experienceOrbEntity.getId();
        this.x = experienceOrbEntity.getX();
        this.y = experienceOrbEntity.getY();
        this.z = experienceOrbEntity.getZ();
        this.portalTarget = experienceOrbEntity.getPortalTarget();
        this.inclineTarget = experienceOrbEntity.getInclineTarget();
        this.size = experienceOrbEntity.getOrbSize();
    }

    public AncientCityPortalExperienceOrbSpawnS2CPacket(PacketByteBuf buf) {
        this.id = buf.readVarInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.portalTarget = buf.readBlockPos();
        this.inclineTarget = buf.readBlockPos();
        this.size = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.id);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeBlockPos(this.portalTarget);
        buf.writeBlockPos(this.inclineTarget);
        buf.writeInt(this.size);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        ((ClientPlayNetworkHandlerImpl) clientPlayPacketListener).achieveToDo$onAncientCityPortalExperienceOrbSpawn(this);
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

    public BlockPos getPortalTarget() {
        return portalTarget;
    }

    public BlockPos getInclineTarget() {
        return inclineTarget;
    }

    public int getSize() {
        return size;
    }
}
