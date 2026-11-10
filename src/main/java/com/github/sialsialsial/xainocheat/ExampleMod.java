package com.github.sialsialsial.xainocheat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.*;
import java.awt.*;

@Mod(modid = "xainocheat", useMetadata = true)
public class ExampleMod {
    public static boolean flyEnabled = false;
    public static boolean noFallEnabled = true;
    public static boolean fullbrightEnabled = true;

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static JFrame frame;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        // Swing GUI
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("ClickGUI");
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setSize(300, 250);
            frame.setLayout(new FlowLayout());

            JToggleButton flyBtn = new JToggleButton("Fly: OFF");
            flyBtn.addActionListener(e -> {
                flyEnabled = flyBtn.isSelected();
                flyBtn.setText("Fly: " + (flyEnabled ? "ON" : "OFF"));
            });

            JToggleButton noFallBtn = new JToggleButton("NoFall: ON");
            noFallBtn.setSelected(true);
            noFallBtn.addActionListener(e -> {
                noFallEnabled = noFallBtn.isSelected();
                noFallBtn.setText("NoFall: " + (noFallEnabled ? "ON" : "OFF"));
            });

            JToggleButton fbBtn = new JToggleButton("Fullbright: ON");
            fbBtn.setSelected(true);
            fbBtn.addActionListener(e -> {
                fullbrightEnabled = fbBtn.isSelected();
                fbBtn.setText("Fullbright: " + (fullbrightEnabled ? "ON" : "OFF"));
            });

            frame.add(flyBtn);
            frame.add(noFallBtn);
            frame.add(fbBtn);
            frame.setVisible(true);
        });
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null) return;

        // Fly
        if (flyEnabled) {
            mc.thePlayer.motionY = 0.0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) mc.thePlayer.motionY = 0.5;
            if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY = -0.5;
            mc.thePlayer.capabilities.isFlying = true;
        } else {
            mc.thePlayer.capabilities.isFlying = false;
        }

        // Fullbright
        if (fullbrightEnabled) {
            mc.gameSettings.gammaSetting = 100.0F;
        }
    }

    // NoFall (fixed compilation error, now code is for 1.8.9 API)
    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (noFallEnabled && event.entityLiving == mc.thePlayer) {
            event.distance = 0.0F;
        }
    }
}
