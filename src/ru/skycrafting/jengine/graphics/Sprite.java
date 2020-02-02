package ru.skycrafting.jengine.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage image;

    public Sprite(SpriteSheet sheet, int scale) {
        this.image = sheet.getSprite(0);
        this.image = reSize(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
    }
    
    public Sprite(SpriteSheet sheet, int width, int height) {
        this.image = sheet.getSprite(0);
        this.image = reSize(image, width, height);
    }

    public void render(Graphics2D g, float x, float y) {
        g.drawImage(image, (int) (x), (int)y, null);
    }

    public static BufferedImage reSize(BufferedImage image, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        return newImage;
    }
}
