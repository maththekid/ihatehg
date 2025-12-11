package net.superblaubeere27.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.superblaubeere27.gui.utils.GLUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

// lazy to make it :sob:
public class ClientBaseRendererImpl implements IRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void drawRect(double x, double y, double w, double h, Color c) {
        GLUtil.drawRect(GL11.GL_QUADS, x / 2, y / 2, x / 2 + w / 2, y / 2 + h / 2, GLUtil.toRGBA(c));
    }

    @Override
    public void drawOutline(double x, double y, double w, double h, float lineWidth, Color c) {
        GL11.glLineWidth(lineWidth);
        GLUtil.drawRect(GL11.GL_LINE_LOOP, x / 2, y / 2, x / 2 + w / 2, y / 2 + h / 2, GLUtil.toRGBA(c));
    }

    @Override
    public void setColor(Color c) {
        GLUtil.setColor(c);
    }

    @Override
    public void drawString(int x, int y, String text, Color color) {
        mc.fontRendererObj.drawString(text, x / 2f, y / 2f, GLUtil.toRGBA(color), false);
    }

    @Override
    public int getStringWidth(String str) {
        return mc.fontRendererObj.getStringWidth(str) * 2;
    }

    @Override
    public int getStringHeight(String str) {
        return mc.fontRendererObj.FONT_HEIGHT * 2;
    }

    @Override
    public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        setColor(color);

        worldrenderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x1, y1, 0.0D).endVertex();
        worldrenderer.pos(x2, y2, 0.0D).endVertex();
        worldrenderer.pos(x3, y3, 0.0D).endVertex();

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    @Override
    public void initMask() {
    }

    @Override
    public void useMask() {
    }

    @Override
    public void disableMask() {
    }
}
