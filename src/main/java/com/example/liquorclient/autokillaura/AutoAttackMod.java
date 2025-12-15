package com.example.liquorclient.autokillaura;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoAttackMod {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean enabled = false;

    public static void toggle() {
        enabled = !enabled;
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("Killaura " + (enabled ? "Enabled ✅" : "Disabled ❌")), false);
        }
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!enabled || mc.player == null || mc.interactionManager == null) return;

            HitResult hit = mc.crosshairTarget;
            if (hit instanceof EntityHitResult entityHit) {
                Entity target = entityHit.getEntity();
                if (target.isAlive() && mc.player.squaredDistanceTo(target) <= 12.25) {
                    if (mc.player.getAttackCooldownProgress(0.0f) >= 1.0f) {
                        mc.interactionManager.attackEntity(mc.player, target);
                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        });
    }
}