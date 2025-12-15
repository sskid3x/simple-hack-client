package com.example.liquorclient.scaffold;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.util.InputUtil;

public class ScaffoldMod {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean enabled = false;
    private static boolean forcedSneak = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player == null || mc.world == null) return;

            KeyBinding sneakKey = mc.options.sneakKey;
            boolean playerPressingSneak = InputUtil.isKeyPressed(
                    mc.getWindow().getHandle(),
                    sneakKey.getDefaultKey().getCode()
            );

            // Respect manual crouch
            if (playerPressingSneak) {
                if (forcedSneak) {
                    sneakKey.setPressed(false);
                    forcedSneak = false;
                }
                return;
            }

            if (!enabled) {
                if (forcedSneak) {
                    sneakKey.setPressed(false);
                    forcedSneak = false;
                }
                return;
            }

            // Require on ground
            if (!mc.player.isOnGround()) {
                if (forcedSneak) {
                    sneakKey.setPressed(false);
                    forcedSneak = false;
                }
                return;
            }

            BlockPos below = mc.player.getBlockPos().down();
            boolean airBelow = mc.world.getBlockState(below).isAir();

            if (airBelow) {
                sneakKey.setPressed(true);
                forcedSneak = true;
            } else if (forcedSneak) {
                sneakKey.setPressed(false);
                forcedSneak = false;
            }
        });
    }

    public static void toggle() {
        enabled = !enabled;
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("Scaffold " + (enabled ? "Enabled ✅" : "Disabled ❌")), false);
        }

        if (!enabled && forcedSneak && mc.player != null) {
            mc.options.sneakKey.setPressed(false);
            forcedSneak = false;
        }
    }
}