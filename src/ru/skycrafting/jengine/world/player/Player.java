/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.player;

import ru.skycrafting.jengine.entity.EntityTypes;
import ru.skycrafting.jengine.entity.Entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.graphics.Sprite;
import ru.skycrafting.jengine.graphics.SpriteSheet;
import ru.skycrafting.jengine.graphics.TextureAtlas;

/**
 *
 * @author SkyCrafting_
 */
public class Player extends Entity {

    public final static int SPRITE_SCALE = 16;
    private String name;
    private float x, y;
    protected TheEngine instance;

    private Direction direction;
    private Map<Direction, Sprite> spriteMap;
    private final PlayerHandler playerHandler;
    private final PlayerChat chat;

    private final int ZOOM = 2;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Player(TheEngine instance, String name, float x, float y) {
        super(EntityTypes.PLAYER, x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.instance = instance;
        direction = Direction.UP;
        spriteMap = new HashMap<>();

        for (Direction d : Direction.values()) {
            SpriteSheet sheet = new SpriteSheet(d.image(instance.getAtlas()), 1, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, ZOOM);
            spriteMap.put(d, sprite);
        }
        this.playerHandler = new PlayerHandler(instance, this);

        chat = new PlayerChat(this, instance);
    }

    public PlayerChat getChat() {
        return chat;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Direction {
        UP(0 * 16, 8 * 16, SPRITE_SCALE, SPRITE_SCALE),
        DOWN(2 * 16, 8 * 16, SPRITE_SCALE, SPRITE_SCALE),
        LEFT(0 * 16, 9 * 16, SPRITE_SCALE, SPRITE_SCALE),
        RIGHT(3 * 16, 9 * 16, SPRITE_SCALE, SPRITE_SCALE);
        private final int x, y, w, h;

        Direction(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;

            this.w = w;
            this.h = h;
        }

        protected BufferedImage image(TextureAtlas atlas) {
            return atlas.cutTile(x, y, w, h);
        }
    }

    public void update() {
        playerHandler.update();
        instance.getCamera().centerOnEntity(this);
    }

    public Direction getDirection() {
        return direction;
    }

    public static Direction getDirectionByName(String name) {
        switch (name) {
            case "UP":{
                return Direction.UP;
            }
            case "DOWN":{
                return Direction.DOWN;
            }
            case "LEFT":{
                return Direction.LEFT;
            }
            case "RIGHT":{
                return Direction.RIGHT;
            }
            default:
                return Direction.UP;
        }
    }

    public float getRenderX() {
        return this.getX() - instance.getCamera().getxOffSet();
    }

    public float getRenderY() {
        return this.getY() - instance.getCamera().getyOffSet();
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), 16 * ZOOM, 16 * ZOOM);
    }

    @Override
    public void render(Graphics2D g) {
        // g.translate((int) -TheEngine.instance.getCamera().getxOffSet(), (int) -TheEngine.instance.getCamera().getyOffSet());
        if (!getName().contains(instance.getThePlayerName())) {
            //    spriteMap.get(direction).render(g, getRenderX(), getRenderY());
        }
        spriteMap.get(direction).render(g, getRenderX(), getRenderY());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
