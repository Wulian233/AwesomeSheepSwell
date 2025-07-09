package com.wulian.awesomesheepswell.neoforge;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.Config;
import com.wulian.awesomesheepswell.IThickness;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(AwesomeSheepSwell.MOD_ID)
public class AwesomeSheepSwellNeoForge {
    public AwesomeSheepSwellNeoForge() {
        if (FMLLoader.getDist().isClient()) {
            AwesomeSheepSwell.init();
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> AutoConfig.getConfigScreen(Config.class, screen).get())
            );
        }

        NeoForge.EVENT_BUS.<PlayerInteractEvent.EntityInteractSpecific>addListener(event -> {
            if (!(event.getTarget() instanceof SheepEntity sheep) || event.getItemStack().getItem() != Items.SHEARS || sheep.isSheared() || sheep.isBaby()) {
                return;
            }

            event.setCanceled(true);

            int thickness = ((IThickness) sheep).getThickness();
            AwesomeSheepSwell.dropWool(sheep, thickness);

            ((IThickness) sheep).setThickness(0);
            sheep.setSheared(true);
            sheep.getWorld().playSoundFromEntity(null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            event.getItemStack().damage(1, event.getEntity(), (player) -> player.sendToolBreakStatus(event.getHand()));
        });
    }
}
