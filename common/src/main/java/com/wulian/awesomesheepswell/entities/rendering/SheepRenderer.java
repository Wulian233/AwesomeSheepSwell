package com.wulian.awesomesheepswell.entities.rendering;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;

public class SheepRenderer extends MobEntityRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    public SheepRenderer(EntityRendererFactory.Context context) {
        super(context, new SheepEntityModel<>(context.getPart(EntityModelLayers.SHEEP)), 0.7f);
        this.addFeature(new SheepWoolFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(SheepEntity entity) {
        return new Identifier("textures/entity/sheep/sheep.png");
    }
}
