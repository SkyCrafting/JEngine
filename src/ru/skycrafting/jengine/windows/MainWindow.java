/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.windows;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import ru.skycrafting.jengine.InputHandler;
import ru.skycrafting.jengine.TheEngine;

/**
 *
 * @author SkyCrafting_
 */
public class MainWindow extends Canvas implements Runnable {
    
    public static final float FPS = 60;
    
    private final JFrame f;
    private final String title;
    private Thread gameThread;
    private boolean isRunning = false;
    private final InputHandler inputHandler;
    private final TheEngine instance;

    public MainWindow(String title, int width, int height, TheEngine engine) {
        this.instance = engine;
        inputHandler = new InputHandler();
        this.title = title;
        f = new JFrame(title);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(width, height));
        f.setMinimumSize(new Dimension(width, height));
        f.setMaximumSize(new Dimension(width * 2, height * 2));
        f.add(inputHandler);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                instance.getThePlayer().getChat().keysDown(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.start();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    private synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    public void run() {
        int fps = 0;
        int upd = 0;
        int updloops = 0;
        long count = 0;
        float delta = 0;
        long lastTime = System.nanoTime();
        while (isRunning) {
            long now = System.nanoTime();
            long elapsedTime = now - lastTime;
            lastTime = now;
            count += elapsedTime;
            boolean render = false;
            delta += (elapsedTime / (1000000000.0 / FPS));
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) { updloops++; } else { render = true; }
            }
            if (render) { render(); fps++;
            } else { try { Thread.sleep(1); } catch (InterruptedException e) {} }
            if (count >= 1000000000.0) {
                f.setTitle(getTitle() + "       FPS: " + fps + "       Update: " + upd + "       UpdateLoops: " + updloops);
                fps = 0; upd = 0; updloops = 0; count = 0;
            }
        }
    }

    private void update() {
        instance.getRenderManager().update();
    }

    private void render() {
        instance.getRenderManager().render();
    }

    public JFrame getFrame() {
        return f;
    }

    @Override
    public int getWidth() {
        return getFrame().getWidth();
    }

    @Override
    public int getHeight() {
        return getFrame().getHeight();
    }

    public String getTitle() {
        return title;
    }
}
