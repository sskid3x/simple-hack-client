package com.example.liquorclient;

import com.example.liquorclient.autokillaura.AutoAttackMod;
import com.example.liquorclient.scaffold.ScaffoldMod;
import com.example.liquorclient.aimassist.AimAssistMod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    private static KeyBinding autoAttackKey;
    private static KeyBinding scaffoldKey;
    private static KeyBinding aimAssistKey;
    private static KeyBinding espKey;

    public static void register() {
        autoAttackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Killaura",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "Liquor Client"
        ));

        scaffoldKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Scaffold",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "Liquor Client"
        ));

        aimAssistKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "AimBot",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "Liquor Client"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (autoAttackKey.wasPressed()) AutoAttackMod.toggle();
            while (scaffoldKey.wasPressed()) ScaffoldMod.toggle();
            while (aimAssistKey.wasPressed()) AimAssistMod.toggle();
        });
    }
}