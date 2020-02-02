package ru.skycrafting.jengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
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
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);
        /*<-- RENDER -->*/

        //  if (instance.isIsConnected()) {
        instance.getWorld().getLoader().render(g);
        try {
            renderPlayers(g);
        } catch (Exception e) {
        }
        instance.getThePlayer().getChat().render(height, width, g);
        //}
        renderGui(g);

        /*<-- RENDER -->*/
        bs.show();
        g.dispose();
    }

    private void renderPlayers(Graphics g) {
        PlayerManager pManager = instance.getWorld().getPlayerManager();
        for (Map.Entry<String, Player> e : pManager.getPlayers().entrySet()) {
            Player p = e.getValue();
            String name = e.getKey();
            p.render((Graphics2D) g);

            if (name.contains(instance.getThePlayerName())) {
                renderName(g, p, name + " [Вы]", Color.green, 15, 7);
            } else {
                renderName(g, p, name, Color.red, 15, 7);
            }
        }
    }

    private void renderName(Graphics g, Player p, String name, Color c, int x, int y) {
        int dlina = g.getFontMetrics().stringWidth(name) + 5;

        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.73f));
        g.fillRect(((int) p.getRenderX() - 12) - x, ((int) p.getRenderY() - 13) - y, dlina, 18);
        g.setColor(c);
        g.drawString(name, ((int) p.getRenderX() - 10) - x, ((int) p.getRenderY()) - y);
    }

    private void renderGui(Graphics g) {
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.73f));
        g.fillRect(2, 2, 160, 50);
        g.setColor(Color.red);
        Tile apple = instance.getWorld().getTileManager().getTileById(3);
        apple.render((Graphics2D) g, 10, 10);
        g.drawString("Score: " + instance.getThePlayer().getPlayerHandler().getScore() + "/" + maxApple, 50, 23);
        g.drawString("Players: " + instance.getOnlinePlayers(), 50, 40);

        //  if(instance.getNetwork().isDownloading()){
        //    renderProgressBar(g);
        // }
    }

    private int progressBarValue = 0;
    private int max = 100;

    public void setProgressBarValue(int progressBarValue) {
        this.progressBarValue = progressBarValue;
    }

    public int getProgressBarValue() {
        return progressBarValue;
    }

    public void setProgressBarMaxValue(int value) {
        this.max = value;
    }

    private void renderProgressBar(Graphics g) {
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.70f));
        g.fillRect(0, 0, width, height);
        int barWidth = (int) (progressBarValue * ((width - (width / 5)) - 8 * 2) / max);
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.73f));
        g.fillRect(width / 12, height / 3, width - (width / 5), height / 10);
        g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.73f));
        g.drawRect((width / 12) + 8, (height / 3) + 13 + 9, ((width - (width / 5)) - 8 * 2), (height / 10) - 15 * 2);
        g.setColor(new Color(0.8f, 0.0f, 0.0f, 0.73f));
        g.fillRect((width / 12) + 8, (height / 3) + 13 + 9, barWidth, (height / 10) - 15 * 2);
        g.setColor(Color.red);
        g.drawString("Загрузка мира....                                 Загруженно:" + progressBarValue + "/" + max, (width / 12) + 8, (height / 3) + 15);
    }

    public void update() {
        instance.getWorld().getLoader().update();
        if (instance.isInit()) {
            instance.getNetManager().update();
        }
        try {
            PlayerManager pManager = TheEngine.instance.getWorld().getPlayerManager();
            for (Map.Entry<String, Player> e : pManager.getPlayers().entrySet()) {
                Player p = e.getValue();
                p.update();
            }
            TheEngine.instance.setOnlinePlayers(pManager.getPlayers().size());
        } catch (Exception e) {

        }
    }
}
