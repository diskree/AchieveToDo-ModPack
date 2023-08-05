package com.diskree.achievetodo.advancements.hints;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class AncientCityPortalParticleFactory implements ParticleFactory<DefaultParticleType> {
    private final SpriteProvider spriteProvider;

    public AncientCityPortalParticleFactory(SpriteProvider spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        AncientCityPortalParticle portalParticle = new AncientCityPortalParticle(clientWorld, d, e, f, g, h, i);
        portalParticle.setSprite(this.spriteProvider);
        return portalParticle;
    }

}
