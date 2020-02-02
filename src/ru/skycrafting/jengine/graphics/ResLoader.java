/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author SkyCrafting_
 */
public class ResLoader {

    public static final String PATH = "C:\\Users\\SkyCrafting_\\Desktop\\NETBEANS\\The Engine_2\\res" + File.separator;

    public static BufferedImage loadImage(String filleName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(PATH + filleName));
        } catch (IOException e) {
            System.err.println("Error image loader!");
            e.printStackTrace();
            System.exit(1);
        }
        return image;
    }
}
