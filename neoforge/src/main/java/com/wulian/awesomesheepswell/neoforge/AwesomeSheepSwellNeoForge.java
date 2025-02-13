package com.wulian.awesomesheepswell.neoforge;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.Config;
import com.wulian.awesomesheepswell.IThickness;
import com.wulian.awesomesheepswell.mixin.SheepAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Random;
import java.util.function.Function;

@Mod(AwesomeSheepSwell.MOD_ID)
public class AwesomeSheepSwellNeoForge {
    public AwesomeSheepSwellNeoForge() {
        if (FMLLoader.getDist().isClient()) {
            AwesomeSheepSwell.init();
            registerConfigScreen(AwesomeSheepSwell.MOD_ID, screen -> AutoConfig.getConfigScreen(Config.class, screen).get());
        }

        NeoForge.EVENT_BUS.<PlayerInteractEvent.EntityInteractSpecific>addListener(event -> {
            if (!(event.getTarget() instanceof SheepEntity sheep) || event.getItemStack().getItem() != Items.SHEARS || sheep.isSheared() || sheep.isBaby()) {
                return;
            }

            event.setCanceled(true);

            int thickness = ((IThickness) sheep).getThickness();
            final Random random = new Random();
            int dropCount = thickness == 0 ? random.nextInt(3) + 1 : thickness + random.nextInt(3);

            for (int i = 0; i < dropCount; i++) {
                ItemEntity itemEntity = sheep.dropItem(SheepAccessor.getDrops().get(sheep.getColor()), 1);
                if (itemEntity != null) {
                    itemEntity.setVelocity(itemEntity.getVelocity().add(
                            (random.nextFloat() - random.nextFloat()) * 0.1F,
                            random.nextFloat() * 0.05F,
                            (random.nextFloat() - random.nextFloat()) * 0.1F
                    ));
                }
            }

            ((IThickness) sheep).setThickness(0);
            sheep.setSheared(true);
            sheep.getWorld().playSoundFromEntity(null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            event.getItemStack().damage(1, event.getEntity(), event.getItemStack().getEquipmentSlot());
        });
    }

    public static void registerConfigScreen(String modid, Function<Screen, Screen> screenFunction) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (client, screen) -> screenFunction.apply(screen));
    }
}