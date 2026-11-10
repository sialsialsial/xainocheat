package com.github.sialsialsial.xainocheat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.*;
import java.awt.*;

@Mod(modid = "xainocheat", useMetadata = true)
public class ExampleMod {
    public static boolean flyEnabled = false;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static JFrame frame;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        // ClickGUI (ModMenu)
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("ClickGUI");
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JToggleButton flyBtn = new JToggleButton("Fly: OFF");
            flyBtn.addActionListener(e -> {
                flyEnabled = flyBtn.isSelected();
                flyBtn.setText("Fly: " + (flyEnabled ? "ON" : "OFF"));
            });

            frame.add(flyBtn);
            frame.setVisible(true);
        });

        System.out.println("XAINOCHEAT - Success ClickGUI init.");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null) return;

        if (flyEnabled) {
            mc.thePlayer.motionY = 0.0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = 0.5;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -0.5;
            }
            mc.thePlayer.capabilities.isFlying = true;
        } else {
            mc.thePlayer.capabilities.isFlying = false;
        }
    }
}
