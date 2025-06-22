package com.wulian.awesomesheepswell.mixin;


import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.IThickness;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "drop(Lnet/minecraft/entity/damage/DamageSource;)V", at = @At("HEAD"))
    private void onDropLoot(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (!(entity instanceof SheepEntity sheep) || sheep.isBaby() || !entity.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) return;

        int thickness = ((IThickness) sheep).getThickness();
        AwesomeSheepSwell.dropWool(sheep, thickness);
    }
}
