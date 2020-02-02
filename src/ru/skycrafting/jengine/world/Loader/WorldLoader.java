/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.Loader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.graphics.IRender;
import ru.skycrafting.jengine.world.Loader.tile.Tile;
import ru.skycrafting.jengine.world.player.PlayerCamera;

/**
 *
 * @author SkyCrafting_
 */
public class WorldLoader implements IRender {

    private int worldSize = 100;
    private final byte[][] world;
    private Random r = new Random();
    protected TheEngine instance;

    public byte[][] getWorld() {
        return world;
    }
    
    public int getTileSizeByID(int id){
        int temp = 0;
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                int idd = getWorld()[y][x];
                if(idd == id){
                    temp++;
                }
            }
        }
        return temp;
    }

    public WorldLoader(int size) {
        this.worldSize = size;
        this.world = new byte[size][size];
        this.instance = TheEngine.instance;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                world[y][x] = (byte) TheEngine.instance.getRandomNumber(0, 30);
            }
        }
        
        for (int offset = 1; offset < 2; offset++) {
            for (int y = 0; y < world.length; y++) {
                for (int x = 0; x < world[y].length; x++) {
                    if (x < offset) {
                        world[y][x] = 0;
                    }
                    if (x < offset) {
                        world[y][x] = 0;
                    }

                    if (x > offset) {
                        world[worldSize - offset][x] = 0;
                    }
                    if (x > offset) {
                        world[y][worldSize - offset] = 0;
                    }
                }
            }
        }
        
        TheEngine.instance.getRenderManager().setMaxApple(getTileSizeByID(3));
    }

    public Tile getTileByPos(int x, int y) {
        if ((x >= 0 && x <= worldSize - 1) && (y >= 0 && y <= worldSize - 1)) {
            Tile t = instance.getWorld().getTileManager().getTileById(world[y][x]);
            return t;
        }
        return TheEngine.instance.getWorld().getTileManager().getTileById(0);
    }

    private Tile tempTile;

    @Override
    public void render(Graphics g) {
        int tileScale = instance.getWorld().getTileManager().getSCALE_TILE_SIZE();
        PlayerCamera cam = instance.getCamera();

        /* <-- Оптимизация - наше всё--> */
        int xStart = (int) Math.max(0, cam.getxOffSet() / tileScale);
        int xEnd = (int) Math.min(world.length, (cam.getxOffSet() + (instance.getWindow().getWidth() + tileScale)) / tileScale);

        int yStart = (int) Math.max(0, cam.getyOffSet() / tileScale);
        int yEnd = (int) Math.min(world.length, (cam.getyOffSet() + instance.getWindow().getHeight()) / tileScale);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                tempTile = instance.getWorld().getTileManager().getTileById(world[y][x]);
                tempTile.render((Graphics2D) g,
                        (int) (x * tileScale - cam.getxOffSet()),
                        (int) (y * tileScale - cam.getyOffSet()));
            }
        }
    }

    @Override
    public void update() {

    }

    public int getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(int worldSize) {
        this.worldSize = worldSize;
    }
}
