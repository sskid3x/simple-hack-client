package com.example.liquorclient;

import com.example.liquorclient.autokillaura.AutoAttackMod;
import com.example.liquorclient.scaffold.ScaffoldMod;
import com.example.liquorclient.aimassist.AimAssistMod;
import com.example.liquorclient.gui.ModMenuScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    private static KeyBinding openMenuKey;
    private static KeyBinding autoAttackKey;
    private static KeyBinding scaffoldKey;
    private static KeyBinding aimbotKey;

    public static void register() {
        // GUI menu (Right Shift)
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open hax",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "Liquor Client"
        ));

        // Kill Aura (R)
        autoAttackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Kill Aura",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "Liquor Client"
        ));

        // Scaffold (C)
        scaffoldKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Scaffold",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "Liquor Client"
        ));

        // Aimbot (X)
        aimbotKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Aimbot",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "Liquor Client"
        ));

        // Event loop
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) client.setScreen(new ModMenuScreen());
            while (autoAttackKey.wasPressed()) AutoAttackMod.toggle();
            while (scaffoldKey.wasPressed()) ScaffoldMod.toggle();
            while (aimbotKey.wasPressed()) AimAssistMod.toggle();
        });
    }
}
