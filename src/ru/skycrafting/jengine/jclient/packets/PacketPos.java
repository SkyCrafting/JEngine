/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient.packets;

import ru.skycrafting.jengine.jclient.network.PlayerMP;

/**
 *
 * @author SkyCrafting_
 */
public class PacketPos {

    private PlayerMP player;

    public PlayerMP getPlayer() {
        return player;
    }

    public void setPlayer(PlayerMP player) {
        this.player = player;
    }

}
