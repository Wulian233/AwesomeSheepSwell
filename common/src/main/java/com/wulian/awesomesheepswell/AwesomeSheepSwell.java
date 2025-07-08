package com.wulian.awesomesheepswell;

import com.wulian.awesomesheepswell.entities.rendering.SheepRenderer;
import com.wulian.awesomesheepswell.mixin.SheepAccessor;
import me.shedaniel.architectury.registry.entity.EntityRenderers;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

import java.util.Map;
import java.util.Random;

public class AwesomeSheepSwell {
    public static final String MOD_ID = "awesomesheepswell";
    public static Config config;

    public static void ConfigInitializer() {
        getConfig();
    }

    public static void ClientInitializer() {
        EntityRenderers.register(EntityType.SHEEP, SheepRenderer::new);
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

    public static void dropWool(SheepEntity sheep, int thickness) {
        final Random random = new Random();
        int dropCount = thickness == 0 ? random.nextInt(3) + 1 : thickness + random.nextInt(3);

        Map<DyeColor, ItemConvertible> drops = SheepAccessor.getDrops();
        ItemConvertible woolItem = drops.getOrDefault(sheep.getColor(), Items.WHITE_WOOL);

        for (int i = 0; i < dropCount; i++) {
            ItemEntity itemEntity = sheep.dropItem(woolItem, 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add(
                        (random.nextFloat() - random.nextFloat()) * 0.1F,
                        random.nextFloat() * 0.05F,
                        (random.nextFloat() - random.nextFloat()) * 0.1F
                ));
            }
        }
    }
}