package slaughter.ware.client.features.implementations.combat;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.features.api.SliderSetting;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

public final class TriggerBot extends Module {

    private static final long ATTACK_DELAY = 618L;
    private final SliderSetting distance = new SliderSetting("Distance").value(3F).range(3F, 6F).step(0.1f);
    private long lastAttackTime;

    public TriggerBot() {
        super("TriggerBot", GLFW.GLFW_KEY_R, ModuleCategory.COMBAT);
        addSettings(distance);
    }

    @Override
    public void onUpdate() {
        if (fullNullCheck() || mc().interactionManager == null) {
            return;
        }

        long now = System.currentTimeMillis();
        if (now - lastAttackTime < ATTACK_DELAY) {
            return;
        }

        ClientPlayerEntity player = mc().player;
        if (player == null || player.isUsingItem()) {
            return;
        }

        if (player.getAttackCooldownProgress(0.0f) < 1.0f) {
            return;
        }

        HitResult hitResult = mc().crosshairTarget;
        if (!(hitResult instanceof EntityHitResult entityHitResult)) {
            return;
        }

        Entity target = entityHitResult.getEntity();
        if (!isValidTarget(player, target)) {
            return;
        }

        mc().interactionManager.attackEntity(player, target);
        player.swingHand(Hand.MAIN_HAND);
        lastAttackTime = now;
    }

    private boolean isValidTarget(ClientPlayerEntity player, Entity target) {
        if (target == null || !target.isAlive() || target == player) {
            return false;
        }

        if (player.squaredDistanceTo(target) > getAttackRange() * getAttackRange()) {
            return false;
        }

        if (target instanceof EndCrystalEntity) {
            return true;
        }

        if (!(target instanceof LivingEntity livingTarget)) {
            return false;
        }

        return !livingTarget.isSleeping();
    }

    private float getAttackRange() {
        Float value = distance.getValue();
        return value == null ? 3.0f : value;
    }
}
