package com.diskree.achievetodo.ancient_city_portal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(value=EnvType.CLIENT)
public class AncientCityPortalExperienceOrbEntityRenderer
extends EntityRenderer<AncientCityPortalExperienceOrbEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/experience_orb.png");
    private static final RenderLayer LAYER = RenderLayer.getItemEntityTranslucentCull(TEXTURE);

    public AncientCityPortalExperienceOrbEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.15f;
        this.shadowOpacity = 0.75f;
    }

    @Override
    protected int getBlockLight(AncientCityPortalExperienceOrbEntity experienceOrbEntity, BlockPos blockPos) {
        return MathHelper.clamp(super.getBlockLight(experienceOrbEntity, blockPos) + 7, 0, 15);
    }

    @Override
    public void render(AncientCityPortalExperienceOrbEntity experienceOrbEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        int j = experienceOrbEntity.getOrbSize();
        float h = (float)(j % 4 * 16) / 64.0f;
        float k = (float)(j % 4 * 16 + 16) / 64.0f;
        float l = (float)(j / 4 * 16) / 64.0f;
        float m = (float)(j / 4 * 16 + 16) / 64.0f;
        float r = ((float)experienceOrbEntity.age + g) / 2.0f;
        int s = (int)((MathHelper.sin(r + 0.0f) + 1.0f) * 0.5f * 255.0f);
        int u = (int)((MathHelper.sin(r + 4.1887903f) + 1.0f) * 0.1f * 255.0f);
        matrixStack.translate(0.0f, 0.1f, 0.0f);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
        matrixStack.scale(0.3f, 0.3f, 0.3f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, -0.5f, -0.25f, s, u, h, m, i);
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, 0.5f, -0.25f, s, u, k, m, i);
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, 0.5f, 0.75f, s, u, k, l, i);
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, -0.5f, 0.75f, s, u, h, l, i);
        matrixStack.pop();
        super.render(experienceOrbEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, int red, int blue, float u, float v, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, 0.0f).color(red, 255, blue, 128).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next();
    }

    @Override
    public Identifier getTexture(AncientCityPortalExperienceOrbEntity experienceOrbEntity) {
        return TEXTURE;
    }
}

