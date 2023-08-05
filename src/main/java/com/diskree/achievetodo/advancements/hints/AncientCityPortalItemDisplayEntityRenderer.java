package com.diskree.achievetodo.advancements.hints;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.DisplayEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.DisplayEntity;
import org.jetbrains.annotations.Nullable;

public class AncientCityPortalItemDisplayEntityRenderer extends DisplayEntityRenderer<DisplayEntity.ItemDisplayEntity, DisplayEntity.ItemDisplayEntity.RenderData> {

    private final ItemRenderer itemRenderer;

    public AncientCityPortalItemDisplayEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Nullable
    protected DisplayEntity.ItemDisplayEntity.RenderData getRenderData(DisplayEntity.ItemDisplayEntity itemDisplayEntity) {
        return itemDisplayEntity.getRenderData();
    }

    public void renderInner(
            DisplayEntity.ItemDisplayEntity itemDisplayEntity,
            DisplayEntity.ItemDisplayEntity.RenderData renderData,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int i,
            float f
    ) {
        this.itemRenderer.renderItem(
                renderData.itemStack(),
                renderData.itemTransform(),
                i,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                itemDisplayEntity.getWorld(),
                itemDisplayEntity.getId()
        );
    }
}
