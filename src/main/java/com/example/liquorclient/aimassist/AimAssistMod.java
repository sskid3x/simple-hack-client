package com.example.liquorclient.aimassist;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class AimAssistMod {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean enabled = false;

    private static final float ROTATION_SPEED = 0.01f;

    public static void toggle() {
        enabled = !enabled;
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("AimBot " + (enabled ? "Enabled ✅" : "Disabled ❌")), false);
        }
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!enabled || mc.player == null || mc.world == null) return;

            PlayerEntity closest = null;
            double closestDist = Double.MAX_VALUE;

            for (PlayerEntity target : mc.world.getPlayers()) {
                if (target == mc.player || !target.isAlive()) continue;
                double dist = mc.player.squaredDistanceTo(target);
                if (dist <= 12.25 && dist < closestDist) {
                    closest = target;
                    closestDist = dist;
                }
            }

            if (closest != null) {
                double dx = closest.getX() - mc.player.getX();
                double dy = closest.getEyeY() - mc.player.getEyeY();
                double dz = closest.getZ() - mc.player.getZ();

                double distXZ = Math.sqrt(dx * dx + dz * dz);
                float targetYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
                float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, distXZ)));

                mc.player.setYaw(lerpAngle(mc.player.getYaw(), targetYaw, ROTATION_SPEED));
                mc.player.setPitch(lerp(mc.player.getPitch(), targetPitch, ROTATION_SPEED));
            }
        });
    }

    private static float lerp(float current, float target, float speed) {
        return current + (target - current) * speed;
    }

    private static float lerpAngle(float current, float target, float speed) {
        float delta = wrapDegrees(target - current);
        return current + delta * speed;
    }

    private static float wrapDegrees(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) angle -= 360.0f;
        if (angle < -180.0f) angle += 360.0f;
        return angle;
    }
}