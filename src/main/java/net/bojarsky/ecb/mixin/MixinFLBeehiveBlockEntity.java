package net.bojarsky.ecb.mixin;

import com.eerussianguy.firmalife.common.blockentities.FLBeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FLBeehiveBlockEntity.class)
public class MixinFLBeehiveBlockEntity {


    @ModifyVariable(method = "tickPosition", at = @At(value = "STORE", target = "F"), ordinal = 0, remap = false)
    private float modifyNut(float nut) {

        if (nut > 0) {
            float modifiedNut = nut * 0.6f;
            return modifiedNut;
        }

        return nut;
    }
}