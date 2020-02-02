/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.network;

import ru.skycrafting.jengine.world.player.Player;

/**
 *
 * @author SkyCrafting_
 */
public class Packet {
    
    private final Connector c;
    
    public Packet(Connector c) {
        this.c = c;
    }
    
    public void sendLogin(Player p){
        c.send("packet:" + p.getName() + ":login:" + p.getX() + ":" + p.getY() + ":");
    }

    public void sendPosition(Player p, float x, float y) {
        c.send("packet:" + p.getName() + ":pos:" + x + ":" + y + ":");
    }

    public void sendMessage(Player p, String msg) {
        c.send("packet:" + p.getName() + ":msg:" + msg.replace(":", "{u06DE}") + ":");
    }
}
