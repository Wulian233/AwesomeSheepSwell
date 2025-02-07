package com.wulian.awesomesheepswell;

import com.wulian.awesomesheepswell.entities.rendering.SheepRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.entity.EntityType;

import java.util.Random;

public class AwesomeSheepSwell {
    public static final String MOD_ID = "awesomesheepswell";
    public static Config config;

    public static void init() {
        EntityRendererRegistry.register(() -> EntityType.SHEEP, SheepRenderer::new);
        getConfig();
    }

    public static Config getConfig() {
        if (config == null) {
            AutoConfig.register(Config.class, JanksonConfigSerializer::new);
            config = AutoConfig.getConfigHolder(Config.class).getConfig();
        }
        return config;
    }

    public static int getMaxThickness() {
        return getConfig().maxThickness;
    }

    public static int getRandomThickness() {
        Random random = new Random();
        int[] weights = {8, 7, 6, 5, 4, 3, 2, 1};
        int totalWeight = 0;

        for (int weight : weights) {
            totalWeight += weight;
        }

        int rnd = random.nextInt(totalWeight);
        for (int i = 0; i < weights.length; i++) {
            rnd -= weights[i];
            if (rnd < 0) {
                return i + 1;
            }
        }
        return 1;
    }
}
