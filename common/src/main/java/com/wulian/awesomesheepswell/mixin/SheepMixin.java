package com.wulian.awesomesheepswell.mixin;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import com.wulian.awesomesheepswell.IThickness;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SheepEntity.class)
public abstract class SheepMixin implements IThickness {
    @Unique
    private static final TrackedData<Integer> THICKNESS = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Unique
    private SheepEntity getSheep() {
        return (SheepEntity) (Object) this;
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void injectDataTracker(CallbackInfo ci) {
        getSheep().getDataTracker().startTracking(THICKNESS, 0);
    }

    @Inject(method = "sheared", at = @At(value = "HEAD"))
    private void injectSheared(CallbackInfo ci) {
        SheepEntity sheep = getSheep();

        final Random random = new Random();
        int dropCount = getThickness();

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
        setThickness(0);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("thickness", getThickness());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        setThickness(nbt.getInt("thickness"));
    }

    @Inject(method = "onEatingGrass", at = @At("HEAD"))
    private void injectOnEatingGrass(CallbackInfo ci) {
        SheepEntity sheep = getSheep();
        if (!sheep.isSheared()) {
            setThickness(getThickness() + 1);
        }
    }

    @Inject(method = "initialize", at = @At("TAIL"))
    private void injectInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        setThickness(AwesomeSheepSwell.getRandomThickness());
    }

    @Unique
    public int getThickness() {
        return Math.min(getSheep().getDataTracker().get(THICKNESS), AwesomeSheepSwell.getMaxThickness());
    }

    @Unique
    public void setThickness(int thickness) {
        thickness = Math.min(AwesomeSheepSwell.getMaxThickness(), thickness);
        getSheep().getDataTracker().set(THICKNESS, thickness);
    }
}