package com.diskree.achievetodo.ancient_city_portal;

import com.diskree.achievetodo.AchieveToDoMod;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class AncientCityPortalExperienceOrbEntityRenderer extends EntityRenderer<AncientCityPortalExperienceOrbEntity> {

    private static final Identifier TEXTURE = new Identifier(AchieveToDoMod.MOD_ID, "textures/entity/ancient_city_portal_experience_orb.png");
    private static final RenderLayer LAYER = RenderLayer.getItemEntityTranslucentCull(TEXTURE);

    public AncientCityPortalExperienceOrbEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.15f;
        this.shadowOpacity = 0.75f;
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float u, float v, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, 0.0f).color(255, 255, 255, 128).uv(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next();
    }

    @Override
    protected int getBlockLight(AncientCityPortalExperienceOrbEntity experienceOrbEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(AncientCityPortalExperienceOrbEntity experienceOrbEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        int j = experienceOrbEntity.getOrbSize();
        float h = (float) (j % 4 * 16) / 64.0f;
        float k = (float) (j % 4 * 16 + 16) / 64.0f;
        float l = (float) (j / 4 * 16) / 64.0f;
        float m = (float) (j / 4 * 16 + 16) / 64.0f;
        matrixStack.translate(0.0f, 0.1f, 0.0f);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Axis.Y_POSITIVE.rotationDegrees(180.0f));
        matrixStack.scale(0.3f, 0.3f, 0.3f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getModel();
        Matrix3f matrix3f = entry.getNormal();
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, -0.5f, -0.25f, h, m, i);
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, 0.5f, -0.25f, k, m, i);
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, 0.5f, 0.75f, k, l, i);
        AncientCityPortalExperienceOrbEntityRenderer.vertex(vertexConsumer, matrix4f, matrix3f, -0.5f, 0.75f, h, l, i);
        matrixStack.pop();
        super.render(experienceOrbEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(AncientCityPortalExperienceOrbEntity experienceOrbEntity) {
        return TEXTURE;
    }
}

