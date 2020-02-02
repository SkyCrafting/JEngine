package ru.skycrafting.jengine.world.player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import ru.skycrafting.jengine.InputHandler;
import ru.skycrafting.jengine.TheEngine;

/**
 *
 * @author SkyCrafting_
 */
public class PlayerHandler {

    private final Player p;

    public float velX, velY;
    private boolean keyUp, keyDown, keyLeft, keyRight;
    private final InputHandler handler;
    private final TheEngine instance;

    public PlayerHandler(TheEngine instance, Player player) {
        this.instance = instance;
        this.p = player;
        handler = instance.getHandler();
    }

    //instance.getNetwork().send(p.getX() + "~" + p.getY() + "~");
    private final int speed = 5;
    
    private boolean blockedInput = false;

    public void update() {
        if (!p.getName().contains(instance.getThePlayerName())) {
            return;
        }
        p.setX(p.getX() + velX);
        p.setY(p.getY() + velY);

        collision();
        if (!blockedInput) {
            if (handler.getKey(KeyEvent.VK_W)) {
                velY = -speed;
                p.setDirection(Player.Direction.UP);
            }
            if (handler.getKey(KeyEvent.VK_S)) {
                velY = speed;
                p.setDirection(Player.Direction.DOWN);
            }
            if (handler.getKey(KeyEvent.VK_A)) {
                velX = -speed;
                p.setDirection(Player.Direction.RIGHT);
            }
            if (handler.getKey(KeyEvent.VK_D)) {
                velX = speed;
                p.setDirection(Player.Direction.LEFT);
            }
        }
    }

    private int score = 0;

    private void collision() {

        int worldSize = instance.getWorld().getLoader().getWorldSize();

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                int id = instance.getWorld().getLoader().getWorld()[y][x];
                switch (id) {
                    case 0:
                        if (p.getBounds().intersects(new Rectangle(
                                instance.getWorld().getTileManager().getSCALE_TILE_SIZE() * x,
                                instance.getWorld().getTileManager().getSCALE_TILE_SIZE() * y, 32, 32).getBounds())) {
                            p.setX(p.getX() + velX * -1f);
                            p.setY(p.getY() + velY * -1f);
                        }
                        break;
                    case 3:
                        if (p.getBounds().intersects(new Rectangle(
                                instance.getWorld().getTileManager().getSCALE_TILE_SIZE() * x,
                                instance.getWorld().getTileManager().getSCALE_TILE_SIZE() * y, 32, 32).getBounds())) {
                            p.setX(p.getX() + velX * -1f);
                            p.setY(p.getY() + velY * -1f);
                            instance.getWorld().getLoader().getWorld()[y][x] = 2;
                            score++;
                        }
                        break;
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isKeyUp() {
        return keyUp;
    }

    public void setKeyUp(boolean keyUp) {
        this.keyUp = keyUp;
    }

    public boolean isKeyDown() {
        return keyDown;
    }

    public void setKeyDown(boolean keyDown) {
        this.keyDown = keyDown;
    }

    public boolean isKeyLeft() {
        return keyLeft;
    }

    public void setKeyLeft(boolean keyLeft) {
        this.keyLeft = keyLeft;
    }

    public boolean isKeyRight() {
        return keyRight;
    }

    public void setKeyRight(boolean keyRight) {
        this.keyRight = keyRight;
    }

    public void setBlockedInput(boolean blockedInput) {
        this.blockedInput = blockedInput;
    }
}
