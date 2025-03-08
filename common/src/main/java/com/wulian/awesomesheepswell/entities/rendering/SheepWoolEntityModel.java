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

    public SheepWoolEntityModel() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);

        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.6F);
        this.head.setPivot(0.0F, 6.0F, -8.0F);
        this.body = new ModelPart(this, 28, 8);
        this.body.addCuboid(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 1.75F);
        this.body.setPivot(0.0F, 5.0F, 2.0F);
        this.backRightLeg = new ModelPart(this, 0, 16);
        this.backRightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.backRightLeg.setPivot(-3.0F, 12.0F, 7.0F);
        this.backLeftLeg = new ModelPart(this, 0, 16);
        this.backLeftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.backLeftLeg.setPivot(3.0F, 12.0F, 7.0F);
        this.frontRightLeg = new ModelPart(this, 0, 16);
        this.frontRightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.frontRightLeg.setPivot(-3.0F, 12.0F, -5.0F);
        this.frontLeftLeg = new ModelPart(this, 0, 16);
        this.frontLeftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.frontLeftLeg.setPivot(3.0F, 12.0F, -5.0F);
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
        renderScale(this.frontLeftLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.frontRightLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.backLeftLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
        renderScale(this.backRightLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green, blue, alpha);
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
