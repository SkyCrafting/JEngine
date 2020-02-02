/*
 * ≈сли вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Base64;
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
    private String clientName;
    private TheEngine instance;
    private Packet packet;

    public Connector(TheEngine instance, String host, int port, String name) {
        this.instance = instance;
        packet = new Packet(this);
        byte[] inputData = new byte[256];
        try {
            ds = new DatagramSocket(); // random free port
            this.host = host;
            this.port = port;
            this.clientName = name;
            new Thread(() -> {
                System.out.println("«апущен поток приема сообщений...");
                try {
                    while (true) {
                        DatagramPacket inputPacket = new DatagramPacket(inputData, inputData.length);
                        ds.receive(inputPacket);
                        String inputMsg = Decode(new String(inputPacket.getData(), 0, inputPacket.getLength()));
                        System.out.println("Server: " + inputMsg);
                        handler(inputMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void send(String packet) {
        Sender sender = new Sender(ds, host, port);
        sender.sendMessage(Encode(packet).getBytes());
        //  sender.sendMessage(packet.getBytes());
    }

    public Packet getPacketSender() {
        return packet;
    }

    public void handler(String inputMessage) {
        try {
            if (inputMessage.split(":").length <= 2 || !inputMessage.split(":")[0].contains("packet")) {
                return;
            }
            PlayerManager playerManager = TheEngine.instance.getWorld().getPlayerManager();

            String senderName = inputMessage.split(":")[1];
            String packetType = inputMessage.split(":")[2];
            switch (packetType) {
                case "OnlinePlayers": {
                    int online = Integer.parseInt(inputMessage.split(":")[3]);
                    instance.setOnlinePlayers(online);
                    break;
                }
                case "pos": {
                    System.out.println("POS!!!!!!!");
                    float clientPosX = Float.parseFloat(inputMessage.split(":")[3]);
                    float clientPosY = Float.parseFloat(inputMessage.split(":")[4]);

                    if (!playerManager.getPlayers().containsKey(senderName)) {
                        playerManager.addPlayer(senderName, new Player(instance, senderName, clientPosX, clientPosY));
                    } else {
                        playerManager.getPlayers().get(senderName).setPos(clientPosX, clientPosY);
                    }
                    break;
                }
                case "msg": {
                    String msg = inputMessage.split(":")[3];
                    instance.getThePlayer().getChat().addChatMessage(senderName, msg);
                    System.out.println(senderName + " " + msg);
                    break;
                }
            }
            System.out.println("«агружено игроков: " + playerManager.getPlayers().size());
            if(playerManager.getPlayers().size() == instance.getOnlinePlayers()){
                instance.setInitAllPlayers(true);
            }
        } catch (Exception e) {
        }
    }

    //packet:mudak:pos~53.0~32.3~
    private String Decode(String packet) {
        try {
            return new String(Base64.getDecoder().decode(packet));
        } catch (Exception e) {
            return "";
        }
    }

    private String Encode(String packet) {
        try {
            String str = new String(Base64.getEncoder().encode(packet.getBytes()));
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }
}
