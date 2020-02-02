/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.awt.Color;
import java.io.IOException;
import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.jclient.network.ConnectionState;
import ru.skycrafting.jengine.jclient.network.NetworkRegister;
import ru.skycrafting.jengine.jclient.network.PlayerMP;
import ru.skycrafting.jengine.jclient.packets.*;
import ru.skycrafting.jengine.world.player.Player;
import ru.skycrafting.jengine.world.player.PlayerManager;

/**
 *
 * @author SkyCrafting_
 */
public class NetworkManager extends Listener {

    private final Client client;

    private boolean isConnected = false;

    private PlayerManager playerManager;

    public NetworkManager(String playerName, String ip, int tPort, int uPort) throws IOException {
        this.client = new Client();

        /**
         * Register
         */
        new NetworkRegister().register(client);
        this.client.addListener(this);
        this.client.start();

        try {
            this.client.connect(5000, ip, tPort, uPort + 1);
        } catch (IOException e) {
            System.err.println("Connection ERROR: " + e.getMessage());
            System.exit(0);
        }

        System.err.println("Client started!");

        client.sendTCP(new PacketAuth(ConnectionState.AUTH_EXPECT.name(), playerName));
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof PacketAuth) {
            PacketAuth tempPacket = (PacketAuth) o;
            if (tempPacket.getState().equalsIgnoreCase(ConnectionState.AUTH_DONE.name())) {
                isConnected = true;
                System.out.println("Connected!");
            }
        }
        if (!isConnected) {
            return;
        }
        playerManager = TheEngine.instance.getWorld().getPlayerManager();

        if (o instanceof PacketPos) {
            PacketPos pos = (PacketPos) o;
            String name = pos.getPlayer().getName();

            if (!playerManager.getPlayers().containsKey(name)) {
                playerManager.addPlayer(name, new Player(TheEngine.instance, name, pos.getPlayer().getX(), pos.getPlayer().getY()));
            } else {
                playerManager.getPlayers().get(name).setPos(pos.getPlayer().getX(), pos.getPlayer().getY());
                playerManager.getPlayers().get(name).setDirection(Player.getDirectionByName(pos.getPlayer().getDirrection()));
            }
        }

        if (o instanceof PacketChat) {
            PacketChat chat = (PacketChat) o;
            TheEngine.instance.getThePlayer().getChat().addChatMessage(Color.yellow.getRGB(), chat.getSenderName(), chat.getMsg());
            System.out.println("[CHAT]: [" + chat.getSenderName() + "]: " + chat.getMsg());
            //    if (chat.getState().equalsIgnoreCase(ConnectionState.DISCONNECTED.name())) {
            //      playerManager.removePlayer(chat.getSenderName());
            //}
        }

        if (o instanceof PacketDisconnect) {
            PacketDisconnect disconnect = (PacketDisconnect) o;
            playerManager.removePlayer(disconnect.getPlayer().getName());
            TheEngine.instance.getThePlayer().getChat().addChatMessage(Color.red.getRGB(), disconnect.getPlayer().getName(), disconnect.getDisconnectMessage());
        }

        if (o instanceof PacketFile) {
            PacketFile f = (PacketFile) o;
            System.out.println(f.getData());
        }
    }

    public void sendChatMessage(String msg) {
        if (isConnected) {
            PacketChat chat = new PacketChat();
            chat.setSenderName(TheEngine.instance.getThePlayerName());
            chat.setMsg(msg);
            this.client.sendTCP(chat);
            if (msg.contains("GET_WORLD")) {
                PacketGet get = new PacketGet();
                get.setGetType("WORLD");
                this.client.sendTCP(get);
            }
        }
    }

    @Override
    public void disconnected(Connection c) {
        System.err.println("Server closed. Я ебал рот...");
        System.exit(0);
    }

    public void update() {
        if (!isConnected) {
            return;
        }
        PacketPos pos = new PacketPos();
        PlayerMP pMP = new PlayerMP();
        pMP.setX(TheEngine.instance.getThePlayer().getX());
        pMP.setY(TheEngine.instance.getThePlayer().getY());
        pMP.setName(TheEngine.instance.getThePlayerName());
        pMP.setDirrection(TheEngine.instance.getThePlayer().getDirection().name());

        pos.setPlayer(pMP);

        client.sendUDP(pos);
    }

}
