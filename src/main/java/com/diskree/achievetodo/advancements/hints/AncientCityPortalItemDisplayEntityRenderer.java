package com.diskree.achievetodo.advancements.hints;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.DisplayEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.DisplayEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AncientCityPortalItemDisplayEntityRenderer extends DisplayEntityRenderer<DisplayEntity.ItemDisplayEntity, DisplayEntity.ItemDisplayEntity.Data> {

    private final ItemRenderer itemRenderer;

    public AncientCityPortalItemDisplayEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    @Nullable
    protected DisplayEntity.ItemDisplayEntity.Data getData(DisplayEntity.@NotNull ItemDisplayEntity itemDisplayEntity) {
        return itemDisplayEntity.getData();
    }

    @Override
    public void render(
            DisplayEntity.ItemDisplayEntity itemDisplayEntity,
            DisplayEntity.ItemDisplayEntity.Data renderData,
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
