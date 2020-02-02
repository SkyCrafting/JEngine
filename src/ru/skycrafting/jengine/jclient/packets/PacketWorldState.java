/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient.packets;

import ru.skycrafting.jengine.jclient.network.PlayerMP;

/**
 *
 * @author SkyCrafting_
 */
public class PacketWorldState {

    private PlayerMP p;

    public PlayerMP getP() {
        return p;
    }

    public void setP(PlayerMP p) {
        this.p = p;
    }
}
