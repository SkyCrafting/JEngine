package ru.skycrafting.jengine.graphics;

import java.awt.image.BufferedImage;

public class TextureAtlas {

    BufferedImage image;

    public TextureAtlas(String imageName) {
        image = ResLoader.loadImage(imageName);
    }

    public BufferedImage cutTile(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }
}
