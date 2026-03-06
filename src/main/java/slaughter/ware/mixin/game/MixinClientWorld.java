package slaughter.ware.mixin.game;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slaughter.ware.SlaughterWare;
import slaughter.ware.client.features.implementations.visual.Ambience;
import slaughter.ware.client.modules.ModuleRepository;

@Mixin(World.class)
public class MixinClientWorld {

    @Inject(method = "getTimeOfDay()J", at = @At("RETURN"), cancellable = true)
    private void onGetTime(CallbackInfoReturnable<Long> cir) {
        SlaughterWare main = SlaughterWare.getInstance();
        if (main == null) {
            return;
        }

        ModuleRepository repository = main.getModuleRepository();
        if (repository == null) {
            return;
        }

        Ambience ambience = repository.getModule(Ambience.class);
        if (ambience == null) {
            return;
        }

        long original = cir.getReturnValue();
        cir.setReturnValue(ambience.getTime(original));
    }
}
