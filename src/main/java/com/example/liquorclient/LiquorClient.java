package com.example.liquorclient;

import com.example.liquorclient.autokillaura.AutoAttackMod;
import com.example.liquorclient.scaffold.ScaffoldMod;
import com.example.liquorclient.aimassist.AimAssistMod;
import com.example.liquorclient.Keybinds;
import net.fabricmc.api.ClientModInitializer;

public class LiquorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoAttackMod.register();
        ScaffoldMod.register();
        AimAssistMod.register();
        Keybinds.register();
    }
}