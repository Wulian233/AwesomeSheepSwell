package com.wulian.awesomesheepswell.entities.rendering;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class SheepWoolFeatureRenderer extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/sheep/sheep_fur.png");
    private final SheepWoolEntityModel model;

    public SheepWoolFeatureRenderer(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new SheepWoolEntityModel(loader.getModelPart(EntityModelLayers.SHEEP_FUR));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, SheepEntity sheep,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (sheep.isSheared() || sheep.isInvisible()) {
            return;
        }

        float[] color;
        if (sheep.hasCustomName() && "jeb_".equals(sheep.getName().asString())) {
            int time = sheep.age / 25 + sheep.getId();
            int dyeAmount = DyeColor.values().length;
            int dyeId = time % dyeAmount;
            int nextDyeId = (time + 1) % dyeAmount;
            float percentage = ((float) (sheep.age % 25) + tickDelta) / 25.0F;
            float[] currentColor = SheepEntity.getRgbColor(DyeColor.byId(dyeId));
            float[] nextColor = SheepEntity.getRgbColor(DyeColor.byId(nextDyeId));
            color = new float[]{
                    currentColor[0] * (1.0F - percentage) + nextColor[0] * percentage,
                    currentColor[1] * (1.0F - percentage) + nextColor[1] * percentage,
                    currentColor[2] * (1.0F - percentage) + nextColor[2] * percentage
            };
        } else {
            color = SheepEntity.getRgbColor(sheep.getColor());
        }

        matrixStack.push();
        this.getContextModel().copyStateTo(this.model);
        this.model.animateModel(sheep, limbAngle, limbDistance, tickDelta);
        this.model.setAngles(sheep, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(SKIN));
        this.model.render(matrixStack, vertexConsumer, light, LivingEntityRenderer.getOverlay(sheep, 0.0F), color[0], color[1], color[2], 1.0F);
        matrixStack.pop();
    }
}
