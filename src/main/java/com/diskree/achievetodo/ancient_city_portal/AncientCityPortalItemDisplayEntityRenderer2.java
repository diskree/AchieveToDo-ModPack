package com.diskree.achievetodo.ancient_city_portal;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.DisplayEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.DisplayEntity;
import org.jetbrains.annotations.Nullable;

public class AncientCityPortalItemDisplayEntityRenderer2 extends DisplayEntityRenderer<DisplayEntity.ItemDisplayEntity, DisplayEntity.ItemDisplayEntity.Data> {

    private final ItemRenderer itemRenderer;

    public AncientCityPortalItemDisplayEntityRenderer2(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    @Nullable
    protected DisplayEntity.ItemDisplayEntity.Data getData(DisplayEntity.ItemDisplayEntity itemDisplayEntity) {
        return itemDisplayEntity.getData();
    }

    @Override
    public void render(DisplayEntity.ItemDisplayEntity itemDisplayEntity, DisplayEntity.ItemDisplayEntity.Data data, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, float f) {
        this.itemRenderer.renderItem(data.itemStack(), data.itemTransform(), i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, itemDisplayEntity.getWorld(), itemDisplayEntity.getId());
    }
}
