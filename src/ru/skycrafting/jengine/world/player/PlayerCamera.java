/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.player;

import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.world.World;

/**
 *
 * @author SkyCrafting_
 */
public class PlayerCamera {

    private float xOffSet, yOffSet;
    protected TheEngine instance;

    public PlayerCamera(int xOffSet, int yOffSet) {
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
        this.instance = TheEngine.instance;
    }

    /* <-- чекает пустое пространство --> */
    public void checkVoidSpace() {
        World loader = instance.getWorld();

        int x = (loader.getLoader().getWorldSize()) * (loader.getTileManager().getSCALE_TILE_SIZE()) - instance.getWindow().getWidth();
        int y = (loader.getLoader().getWorldSize()) * (loader.getTileManager().getSCALE_TILE_SIZE()) - instance.getWindow().getHeight();

        if (xOffSet < 0) {
            xOffSet = 0;
        } else if (xOffSet > x) {
            xOffSet = x;
        }
        if (yOffSet < 0) {
            yOffSet = 0;
        } else if (yOffSet > y) {
            yOffSet = y;
        }
    }

    public void centerOnEntity(Player p) {
        if(!p.getName().contains(TheEngine.instance.getThePlayerName())){ return; }
        xOffSet = p.getX() - instance.getWindow().getWidth() / 2;
        yOffSet = p.getY() - instance.getWindow().getHeight() / 2;
        checkVoidSpace();
    }

    public void move(int xAmount, int yAmount) {
        xOffSet += xAmount;
        yOffSet += yAmount;
        checkVoidSpace();
    }
    
    public float getxOffSet() {
        return xOffSet;
    }

    public void setxOffSet(float xOffSet) {
        this.xOffSet = xOffSet;
    }

    public float getyOffSet() {
        return yOffSet;
    }

    public void setyOffSet(float yOffSet) {
        this.yOffSet = yOffSet;
    }
}
