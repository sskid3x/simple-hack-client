package com.example.liquorclient.gui;

import com.example.liquorclient.autokillaura.AutoAttackMod;
import com.example.liquorclient.scaffold.ScaffoldMod;
import com.example.liquorclient.aimassist.AimAssistMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModMenuScreen extends Screen {
    public ModMenuScreen() {
        super(Text.literal("Liquor Client Menu"));
    }

    private ButtonWidget scaffoldButton;
    private ButtonWidget auraButton;
    private ButtonWidget aimbotButton;

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        scaffoldButton = ButtonWidget.builder(
                Text.literal("Scaffold " + (ScaffoldMod.isEnabled() ? "[ON]" : "[OFF]")),
                b -> {
                    ScaffoldMod.toggle();
                    scaffoldButton.setMessage(Text.literal("Scaffold " + (ScaffoldMod.isEnabled() ? "[ON]" : "[OFF]")));
                }
        ).dimensions(centerX - 100, centerY - 40, 200, 20).build();
        this.addDrawableChild(scaffoldButton);

        auraButton = ButtonWidget.builder(
                Text.literal("Kill Aura " + (AutoAttackMod.isEnabled() ? "[ON]" : "[OFF]")),
                b -> {
                    AutoAttackMod.toggle();
                    auraButton.setMessage(Text.literal("Kill Aura " + (AutoAttackMod.isEnabled() ? "[ON]" : "[OFF]")));
                }
        ).dimensions(centerX - 100, centerY - 10, 200, 20).build();
        this.addDrawableChild(auraButton);

        aimbotButton = ButtonWidget.builder(
                Text.literal("Aimbot " + (AimAssistMod.isEnabled() ? "[ON]" : "[OFF]")),
                b -> {
                    AimAssistMod.toggle();
                    aimbotButton.setMessage(Text.literal("Aimbot " + (AimAssistMod.isEnabled() ? "[ON]" : "[OFF]")));
                }
        ).dimensions(centerX - 100, centerY + 20, 200, 20).build();
        this.addDrawableChild(aimbotButton);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> {
            this.close();
        }).dimensions(centerX - 100, centerY + 50, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xAA000000);
        context.drawCenteredTextWithShadow(this.textRenderer,
                "Combat Modules",
                this.width / 2,
                30,
                0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }
}
