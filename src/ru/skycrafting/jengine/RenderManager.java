package ru.skycrafting.jengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Map;
import ru.skycrafting.jengine.world.Loader.tile.Tile;
import ru.skycrafting.jengine.world.player.Player;
import ru.skycrafting.jengine.world.player.PlayerManager;

/**
 *
 * @author SkyCrafting_
 */
public class RenderManager {

    private final TheEngine instance;
    private int width, height;
    private Graphics g;

    public RenderManager(TheEngine instance) {
        this.instance = instance;
    }

    public int getMaxApple() {
        return maxApple;
    }

    public void setMaxApple(int maxApple) {
        this.maxApple = maxApple;
    }

    private int maxApple = 0;

    public void render() {
        this.width = instance.getWindow().getWidth();
        this.height = instance.getWindow().getHeight();
        BufferStrategy bs = instance.getWindow().getBufferStrategy();
        if (bs == null) {
            instance.getWindow().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);
        /*<-- RENDER -->*/

        instance.getWorld().getLoader().render(g);

        g.translate((int) instance.getCamera().getxOffSet(), (int) TheEngine.instance.getCamera().getyOffSet());

        PlayerManager pManager = instance.getWorld().getPlayerManager();
        for (Map.Entry<String, Player> e : pManager.getPlayers().entrySet()) {
            Player p = e.getValue();
            String name = e.getKey();
            p.render((Graphics2D) g);

            if (name.contains(instance.getThePlayerName())) {
                renderName(p, name, Color.green, 15, 7);
                p.getChat().render(height, width, g);
            } else {
                renderName(p, name, Color.red, 15, 7);
            }
        }

        renderGui(g);

        /*<-- RENDER -->*/
        bs.show();
        g.dispose();
    }

    private void renderName(Player p, String name, Color c, int x, int y) {
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.73f));
        g.fillRect(((int) p.getRenderX() - 12) - x, ((int) p.getRenderY() - 13) - y, (name + " (Вы)").length() * 7, 18);
        g.setColor(c);
        g.drawString(name + " (Вы)", ((int) p.getRenderX() - 10) - x, ((int) p.getRenderY()) - y);
    }


    private void renderGui(Graphics g) {
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.73f));
        g.fillRect(2, 2, 120, 50);
        g.setColor(Color.red);
        Tile apple = instance.getWorld().getTileManager().getTileById(3);
        apple.render((Graphics2D) g, 10, 10);
        g.drawString("Score: " + instance.getThePlayer().getPlayerHandler().getScore() + "/" + maxApple, 50, 30);
    }
    
    public void update() {
        instance.getWorld().getLoader().update();

        PlayerManager pManager = TheEngine.instance.getWorld().getPlayerManager();
        for (Map.Entry<String, Player> e : pManager.getPlayers().entrySet()) {
            Player p = e.getValue();
            p.update();
        }
    }
}
