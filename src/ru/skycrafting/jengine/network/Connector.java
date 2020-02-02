/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.world.player.Player;
import ru.skycrafting.jengine.world.player.PlayerManager;

/**
 *
 * @author SkyCrafting_
 */
public class Connector {

    private DatagramSocket ds;
    private String host;
    private int port;
    private String name;
    private TheEngine instance;

    public Connector(TheEngine instance, String host, int port, String name) {
        this.instance = instance;
        try {
            ds = new DatagramSocket(); // random free port
            this.host = host;
            this.port = port;
            this.name = name;
            new Thread(() -> {
                System.out.println("Запущен поток приема сообщений...");
                try {
                    while (true) {
                        DatagramPacket pack = new DatagramPacket(new byte[1024], 1024);
                        ds.receive(pack);
                        System.out.println("Принято сообщение: " + new String(pack.getData()));
                        handler(pack.getData());
                    }
                } catch (IOException e) { e.printStackTrace(); }
            }).start();

        } catch (SocketException e) { e.printStackTrace(); }
    }

    public void send(String str) {
        Sender sender = new Sender(ds, host, port);
        sender.sendMessage(name + ":" + str);
    }

    public void handler(byte[] bytes) {
        String packet = new String(bytes);
        
        String name = str.split(":")[0];
        String pos = str.split(":")[1];

        float x = Float.parseFloat(pos.split("~")[0]);
        float y = Float.parseFloat(pos.split("~")[1]);

        PlayerManager playerManager = TheEngine.instance.getWorld().getPlayerManager();

        if (!playerManager.getPlayers().containsKey(name)) {
            playerManager.addPlayer(name, new Player(instance, name, x, y));
        } else {
            playerManager.getPlayers().get(name).setPos(x, y);
        }
        
        System.out.println("Загружено игроков: " + playerManager.getPlayers().size());
    }
}
