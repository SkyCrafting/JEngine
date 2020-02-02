package ru.skycrafting.jengine.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private final BufferedImage sheet;
    private final int spriteCount;
    private final int scale;
    private final int spritesInWidht;

    public static int[] pixels;
    public int width, height;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale) {
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;
        this.spritesInWidht = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int index) {
        index = index % spriteCount;
        int x = index % spritesInWidht * scale;
        int y = index / spritesInWidht * scale;
        return sheet.getSubimage(x, y, scale, scale);
    }

}
