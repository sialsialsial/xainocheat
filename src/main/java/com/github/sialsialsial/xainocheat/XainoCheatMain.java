package com.github.sialsialsial.xainocheat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.awt.*;

@Mod(modid = "xainocheat", useMetadata = true)
public class XainoCheatMain {
    public static boolean creativeFlyEnabled = false;
    public static boolean flyEnabled = false;
    public static boolean noFallEnabled = false;
    public static boolean fullbrightEnabled = false;
    public static boolean espEnabled = false;
    public static boolean jumpHackEnabled = false;
    public static boolean legacyUIEnabled = false;
    public static boolean killAuraEnabled = false;

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

            JLabel MovementLabel = new JLabel("Movement");
            
            //Fly button
            JToggleButton flyBtn = new JToggleButton("Fly: OFF");
            flyBtn.addActionListener(e -> {
                flyEnabled = flyBtn.isSelected();
                flyBtn.setText("Fly: " + (flyEnabled ? "ON" : "OFF"));
            });

            //CreativeFly button
            JToggleButton creativeFlyBtn = new JToggleButton("CreativeFly: OFF");
            creativeFlyBtn.addActionListener(e -> {
                creativeFlyEnabled = creativeFlyBtn.isSelected();
                creativeFlyBtn.setText("CreativeFly: " + (creativeFlyEnabled ? "ON" : "OFF"));
            });

            //JumpHack button
            JToggleButton jumpHackBtn = new JToggleButton("JumpHack: OFF");
            jumpHackBtn.setSelected(true);
            jumpHackBtn.addActionListener(e -> {
                jumpHackEnabled = jumpHackBtn.isSelected();
                jumpHackBtn.setText("JumpHack: " + (jumpHackEnabled ? "ON" : "OFF"));
            });

            //NoFall button
            JToggleButton noFallBtn = new JToggleButton("NoFall: OFF");
            noFallBtn.setSelected(true);
            noFallBtn.addActionListener(e -> {
                noFallEnabled = noFallBtn.isSelected();
                noFallBtn.setText("NoFall: " + (noFallEnabled ? "ON" : "OFF"));
            });

            JLabel RenderLabel = new JLabel("Render");
            
            //FullBright button
            JToggleButton fbBtn = new JToggleButton("Fullbright: OFF");
            fbBtn.setSelected(true);
            fbBtn.addActionListener(e -> {
                fullbrightEnabled = fbBtn.isSelected();
                fbBtn.setText("Fullbright: " + (fullbrightEnabled ? "ON" : "OFF"));
            });

            //ESP button
            JToggleButton espBtn = new JToggleButton("ESP: OFF");
            espBtn.setSelected(true);
            espBtn.addActionListener(e -> {
                espEnabled = espBtn.isSelected();
                espBtn.setText("ESP: " + (espEnabled ? "ON" : "OFF"));
            });
            frame.add(MovementLabel);
            
            frame.add(flyBtn);
            frame.add(creativeFlyBtn);
            frame.add(jumpHackBtn);
            frame.add(noFallBtn);
            
            frame.add(RenderLabel);
            
            frame.add(fbBtn);
            frame.add(espBtn);
            //frame.setVisible(true);
        });
    }

    // Added: The New ClickGui`s menu bind
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            if (!(Minecraft.getMinecraft().currentScreen instanceof ClickGui)) {
                Minecraft.getMinecraft().displayGuiScreen(new ClickGui());
            }
        }
    }
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null) return;

        // Fly & CreativeFly & JumpHack
        // Now swing click-gui are legacy
        if (flyEnabled) {
            mc.thePlayer.motionY = 0.0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) mc.thePlayer.motionY = 0.5;
            if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY = -0.5;
            mc.thePlayer.capabilities.isFlying = true;
        } else if (creativeFlyEnabled) {
            mc.thePlayer.motionY = 0.0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) mc.thePlayer.motionY = 0.5;
            if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY = -0.5;
        } else if (jumpHackEnabled) {
            if (mc.gameSettings.keyBindJump.isKeyDown()) mc.thePlayer.motionY = 0.5;
            if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY = -0.5;
        } else if (legacyUIEnabled == false) {
            frame.setVisible(false);
        } else if (legacyUIEnabled) {
            frame.setVisible(true);
        }
        
        // Fullbright
        if (fullbrightEnabled) {
            mc.gameSettings.gammaSetting = 150.0F;
        }

        // Added: KillAura
        //if (killAuraEnabled) {
        //EntityPlayer target = null;
        //double closestDist = 4.0;

        //for (EntityPlayer p : mc.theWorld.playerEntities) {
            //if (p == mc.thePlayer || p.isDead || p.getHealth() <= 0) continue;
        
            //double dist = mc.thePlayer.getDistanceToEntity(p);
            //if (dist < closestDist) {
                //closestDist = dist;
                //target = p;
            //}
        //}

        //if (target != null) {
            //double dx = target.posX - mc.thePlayer.posX;
            //double dz = target.posZ - mc.thePlayer.posZ;
            //double dy = (target.posY + target.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
            //double dist = MathHelper.sqrt_double(dx * dx + dz * dz);
            
            //float yaw = (float) (Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
            //float pitch = (float) (-(Math.atan2(dy, dist) * 180.0 / Math.PI));
            
            //mc.thePlayer.rotationYaw = yaw;
            //mc.thePlayer.rotationPitch = pitch;
            //mc.playerController.attackEntity(mc.thePlayer, target);
            //mc.thePlayer.swingItem();
        //}
    }

    // NoFall
    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (noFallEnabled && event.entity == mc.thePlayer) {
            event.setCanceled(true);
        }
    }

    // ESP
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!espEnabled || mc.theWorld == null || mc.thePlayer == null) return;

        float partialTicks = event.partialTicks;
        EntityPlayer player = mc.thePlayer;

        double px = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double py = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double pz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        // saving OpenGL status
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2.0F);
        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);

        for (EntityPlayer target : mc.theWorld.playerEntities) {
            if (target == player) continue;

            double tx = target.lastTickPosX + (target.posX - target.lastTickPosX) * partialTicks;
            double ty = target.lastTickPosY + (target.posY - target.lastTickPosY) * partialTicks;
            double tz = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * partialTicks;


            AxisAlignedBB box = target.getEntityBoundingBox()
            .offset(-px, -py, -pz)
            .expand(0.1, 0.1, 0.1);


            GL11.glBegin(GL11.GL_LINES);

            GL11.glVertex3d(box.minX, box.minY, box.minZ); GL11.glVertex3d(box.maxX, box.minY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.minZ); GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
            GL11.glVertex3d(box.maxX, box.minY, box.maxZ); GL11.glVertex3d(box.minX, box.minY, box.maxZ);
            GL11.glVertex3d(box.minX, box.minY, box.maxZ); GL11.glVertex3d(box.minX, box.minY, box.minZ);

            GL11.glVertex3d(box.minX, box.maxY, box.minZ); GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.maxY, box.minZ); GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
            GL11.glVertex3d(box.maxX, box.maxY, box.maxZ); GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
            GL11.glVertex3d(box.minX, box.maxY, box.maxZ); GL11.glVertex3d(box.minX, box.maxY, box.minZ);

            GL11.glVertex3d(box.minX, box.minY, box.minZ); GL11.glVertex3d(box.minX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.minZ); GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.maxZ); GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
            GL11.glVertex3d(box.minX, box.minY, box.maxZ); GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
            GL11.glEnd();
        }

        // Recovery OpenGL
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
