/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient.packets;

import ru.skycrafting.jengine.jclient.network.PlayerMP;

/**
 *
 * @author SkyCrafting_
 */
public class PacketDisconnect {

    private PlayerMP player;
    private String disconnectMessage;
    private String color;

    public PlayerMP getPlayer() {
        return player;
    }

    public void setPlayer(PlayerMP player) {
        this.player = player;
    }

    public void setDisconnectMessage(String disconnectMessage) {
        this.disconnectMessage = disconnectMessage;
    }

    public String getDisconnectMessage() {
        return disconnectMessage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
