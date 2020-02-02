/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author SkyCrafting_
 */
public class Sender {

    private final DatagramSocket ds;
    private final String host;
    private final int port;

    Sender(DatagramSocket ds, String host, int port) {
        this.ds = ds;
        this.host = host;
        this.port = port;
    }

    public void sendMessage(String str) {
        try {
            byte[] data = str.getBytes();
            DatagramPacket pack = new DatagramPacket(data, data.length, InetAddress.getByName(host), port);
            ds.send(pack);
        } catch (IOException e) {}
    }
}