package com.wulian.awesomesheepswell.forge;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.Config;
import com.wulian.awesomesheepswell.IThickness;
import com.wulian.awesomesheepswell.mixin.SheepAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Random;

@Mod(AwesomeSheepSwell.MOD_ID)
public class AwesomeSheepSwellForge {
    @SuppressWarnings("removal")
    public AwesomeSheepSwellForge() {
        if (FMLLoader.getDist().isClient()) {
            AwesomeSheepSwell.init();
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> AutoConfig.getConfigScreen(Config.class, screen).get())
            );
        }

        MinecraftForge.EVENT_BUS.<PlayerInteractEvent.EntityInteractSpecific>addListener(event -> {
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

            sheep.setSheared(true);
            sheep.getWorld().playSoundFromEntity(null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            event.getItemStack().damage(1, event.getEntity(), (player) -> player.sendToolBreakStatus(event.getHand()));
        });
    }
}