package com.github.sialsialsial.xainocheat;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {

    // Module
    public static class Module {
        public String name;
        public boolean enabled;

        public Module(String name, boolean enabled) {
            this.name = name;
            this.enabled = enabled;
        }
    }

    // Category
    public static class Panel {
        public String title;
        public int x, y;
        public boolean dragging;
        public int dragOffsetX, dragOffsetY;
        public boolean expanded = true;
        public List<Module> modules = new ArrayList<>();

        public Panel(String title, int x, int y) {
            this.title = title;
            this.x = x;
            this.y = y;
        }
    }

    private final List<Panel> panels = new ArrayList<>();

    // Componing Constanst
    private static final int PANEL_WIDTH = 110;
    private static final int TITLE_HEIGHT = 16;
    private static final int ROW_HEIGHT = 14;

    @Override
    public void initGui() {
        panels.clear();

        Panel combat = new Panel("Combat", 20, 40);
        combat.modules.add(new Module("KillAura", false));
        combat.modules.add(new Module("Reach", false));
        combat.modules.add(new Module("Velocity", true));

        Panel movement = new Panel("Movement", 140, 40);
        movement.modules.add(new Module("Speed", false));
        movement.modules.add(new Module("Fly", false));
        movement.modules.add(new Module("NoFall", true));

        Panel render = new Panel("Render", 260, 40);
        render.modules.add(new Module("ESP", true));
        render.modules.add(new Module("Tracers", false));
        render.modules.add(new Module("FullBright", false));

        panels.add(combat);
        panels.add(movement);
        panels.add(render);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0x66000000);
        for (Panel panel : panels) {
            drawPanel(panel, mouseX, mouseY);
        }
      
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    // Panel (Render, propirties)
    private void drawPanel(Panel panel, int mouseX, int mouseY) {
        // Catogery Name (drag zone)
        boolean hoverTitle = isHovered(mouseX, mouseY, panel.x, panel.y, PANEL_WIDTH, TITLE_HEIGHT);
        int titleColor = panel.dragging ? 0xFF3A3A3A : (hoverTitle ? 0xFF2E2E2E : 0xFF1F1F1F);
        drawRect(panel.x, panel.y, panel.x + PANEL_WIDTH, panel.y + TITLE_HEIGHT, titleColor);
        
        // Category Name
        fontRendererObj.drawStringWithShadow(panel.title, panel.x + 5, panel.y + 4, 0xFFFFFFFF);

        // Something
        String arrow = panel.expanded ? "-" : "+";
        fontRendererObj.drawString(arrow, panel.x + PANEL_WIDTH - 12, panel.y + 4, 0xFFAAAAAA);

        int currentY = panel.y + TITLE_HEIGHT;

        if (panel.expanded) {
            for (Module module : panel.modules) {
                boolean hoverRow = isHovered(mouseX, mouseY, panel.x, currentY, PANEL_WIDTH, ROW_HEIGHT);

                // Background of string
                int rowColor = hoverRow ? 0xFF2A2A2A : 0xFF1A1A1A;
                drawRect(panel.x, currentY, panel.x + PANEL_WIDTH, currentY + ROW_HEIGHT, rowColor);

                // Status Bar
                int barColor = module.enabled ? 0xFF2ECC71 : 0xFF555555;
                drawRect(panel.x, currentY, panel.x + 2, currentY + ROW_HEIGHT, barColor);

                // Module Name
                int textColor = module.enabled ? 0xFFFFFFFF : 0xFFAAAAAA;
                fontRendererObj.drawString(module.name, panel.x + 7, currentY + 3, textColor);

                currentY += ROW_HEIGHT;
            }
        }
    }

    // Mouse Clicked Event
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton != 0) return;
        for (Panel panel : panels) {
            if (isHovered(mouseX, mouseY, panel.x, panel.y, PANEL_WIDTH, TITLE_HEIGHT)) {
                if (isHovered(mouseX, mouseY, panel.x + PANEL_WIDTH - 14, panel.y, 14, TITLE_HEIGHT)) {
                    panel.expanded = !panel.expanded;
                } else {
                    panel.dragging = true;
                    panel.dragOffsetX = mouseX - panel.x;
                    panel.dragOffsetY = mouseY - panel.y;
                }
                return;
            }

            if (panel.expanded) {
                int currentY = panel.y + TITLE_HEIGHT;
                for (Module module : panel.modules) {
                    if (isHovered(mouseX, mouseY, panel.x, currentY, PANEL_WIDTH, ROW_HEIGHT)) {
                        module.enabled = !module.enabled;
                        return;
                    }
                    currentY += ROW_HEIGHT;
                }
            }
        }
    }

    // Mouse Released Event
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel panel : panels) {
            panel.dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        for (Panel panel : panels) {
            if (panel.dragging) {
                panel.x = mouseX - panel.dragOffsetX;
                panel.y = mouseY - panel.dragOffsetY;
            }
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    private boolean isHovered(int mouseX, int mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
