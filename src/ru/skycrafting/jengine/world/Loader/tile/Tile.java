/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.Loader.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ru.skycrafting.jengine.graphics.Sprite;

/**
 *
 * @author SkyCrafting_
 */
public class Tile {

    private final BufferedImage image;
    private String type;
    private int renderX, renderY;
    private boolean glowing;
    private boolean solid = false;

    public boolean isSolid() {
        return solid;
    }

    public Tile(String type, BufferedImage image, int width, int height) {
        this.type = type;
        this.image = Sprite.reSize(image, width, height);
    }

    public Tile(boolean isSolid, String type, BufferedImage image, int size) {
        this.solid = isSolid;
        this.image = Sprite.reSize(image, image.getWidth() * size, image.getHeight() * size);
        this.type = type;
    }

    public void render(Graphics2D g, int x, int y) {
        this.renderX = x;
        this.renderY = y;
        g.drawImage(image, x, y, null);
    }

    public boolean isGlowing() {
        return glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public int getRenderX() {
        return renderX;
    }

    public int getRenderY() {
        return renderY;
    }

    public String getType() {
        return type;
    }
}
