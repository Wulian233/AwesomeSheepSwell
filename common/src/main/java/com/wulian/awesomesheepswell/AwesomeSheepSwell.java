package com.wulian.awesomesheepswell;

import com.wulian.awesomesheepswell.entities.rendering.SheepRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.entity.EntityType;

public class AwesomeSheepSwell {
    public static final String MOD_ID = "awesomesheepswell";
    public static Config config;

    public static void init() {
        AutoConfig.register(Config.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();
        EntityRendererRegistry.register(() -> EntityType.SHEEP, SheepRenderer::new);
    }

    public static int getMaxThickness() {
        return config.maxThickness;
    }
}
