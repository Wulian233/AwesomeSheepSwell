package com.wulian.awesomesheepswell.forge;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.Config;
import com.wulian.awesomesheepswell.IThickness;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fmlclient.ConfigGuiHandler;

@Mod(AwesomeSheepSwell.MOD_ID)
public class AwesomeSheepSwellForge {
    public AwesomeSheepSwellForge() {
        if (FMLLoader.getDist().isClient()) {
            AwesomeSheepSwell.init();
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigGuiHandler.ConfigGuiFactory.class,
                    () -> new ConfigGuiHandler.ConfigGuiFactory((client, screen) -> AutoConfig.getConfigScreen(Config.class, screen).get())
            );
        }

        MinecraftForge.EVENT_BUS.<PlayerInteractEvent.EntityInteractSpecific>addListener(event -> {
            if (!(event.getTarget() instanceof SheepEntity sheep) || event.getItemStack().getItem() != Items.SHEARS || sheep.isSheared() || sheep.isBaby()) {
                return;
            }

            event.setCanceled(true);

            int thickness = ((IThickness) sheep).getThickness();
            AwesomeSheepSwell.dropWool(sheep, thickness);

            ((IThickness) sheep).setThickness(0);
            sheep.setSheared(true);
            sheep.world.playSoundFromEntity(null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            event.getItemStack().damage(1, event.getPlayer(), (player) -> player.sendToolBreakStatus(event.getHand()));
        });
    }
}