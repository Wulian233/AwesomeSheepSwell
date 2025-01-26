package com.wulian.awesomesheepswell.entities.rendering;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.IThickness;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;

public class SheepWoolEntityModel extends QuadrupedEntityModel<SheepEntity> {
    private static final float MAX_SCALE = 2;
    private final float childHeadYOffset = 8.0F;
    private final float childHeadZOffset = 4.0F;
    private final float invertedChildBodyScale = 2.0F;
    private final float childBodyYOffset = 24;
    private float scale = 1;
    private float headAngle;

    public SheepWoolEntityModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public void animateModel(SheepEntity sheepEntity, float f, float g, float h) {
        super.animateModel(sheepEntity, f, g, h);
        this.head.pivotY = 6.0F + sheepEntity.getNeckAngle(h) * 9.0F;
        this.headAngle = sheepEntity.getHeadAngle(h);

        scale = ((((IThickness) sheepEntity).getThickness() / (float) AwesomeSheepSwell.getMaxThickness()) * (MAX_SCALE - 1)) + 1;
    }

    public void setAngles(SheepEntity sheepEntity, float f, float g, float h, float i, float j) {
        super.setAngles(sheepEntity, f, g, h, i, j);
        this.head.pitch = this.headAngle;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
                       float blue, float alpha) {
        if (this.child) {
            renderChild(matrices, vertices, light, overlay, red, green, blue, alpha);
        } else {
            renderAdult(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }

    private void renderChild(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
                             float blue, float alpha) {
        matrices.push();
        matrices.translate(0.0D, this.childHeadYOffset / 16.0F, this.childHeadZOffset / 16.0F);
        this.getHeadParts().forEach(headPart -> headPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        matrices.pop();

        matrices.push();
        float scale = 1.0F / this.invertedChildBodyScale;
        matrices.scale(scale, scale, scale);
        matrices.translate(0.0D, this.childBodyYOffset / 16.0F, 0.0D);
        this.getBodyParts().forEach(bodyPart -> bodyPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        matrices.pop();
    }

    private void renderAdult(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
                             float blue, float alpha) {
        renderScale(this.head, scale, scale, 1, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.body, scale, scale, 1, 0.5f, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.leftFrontLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.rightFrontLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.leftHindLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.rightHindLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    private void renderScale(ModelPart model, float xScale, float yScale, float zScale, MatrixStack matrices,
                             VertexConsumer vertices, int light, int overlay, float red, float green, float blue,
                             float alpha) {
        renderScale(model, xScale, yScale, zScale, 0, matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    private void renderScale(ModelPart model, float xScale, float yScale, float zScale, float yOffset,
                             MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red,
                             float green, float blue, float alpha) {
        matrices.push();
        matrices.translate(model.pivotX / 16f, model.pivotY / 16f + yOffset, model.pivotZ / 16f);
        matrices.scale(xScale, yScale, zScale);
        matrices.translate(-model.pivotX / 16f, -model.pivotY / 16f - yOffset, -model.pivotZ / 16f);
        model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }
}
