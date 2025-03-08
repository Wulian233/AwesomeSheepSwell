package com.wulian.awesomesheepswell.entities.rendering;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;

public class SheepRenderer extends MobEntityRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/sheep/sheep.png");

    public SheepRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SheepEntityModel<>(), 0.7F);
        this.addFeature(new SheepWoolFeatureRenderer(this));
    }

    public Identifier getTexture(SheepEntity sheepEntity) {
        return TEXTURE;
    }
}
