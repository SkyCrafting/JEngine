/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.player;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SkyCrafting_
 */
public class PlayerManager {

    private HashMap<String, Player> players;
    
    public PlayerManager() {
        players = new HashMap<>();
    }
    
    public void addPlayer(String playerName, Player p){
        players.put(playerName, p);
    }
    
    public void removePlayer(String playerName){
        players.remove(playerName);
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }
    
    public Player getPlayer(String playerName){
        for(Map.Entry<String, Player> e : players.entrySet()){
            String name = e.getKey();
            if(name.contains(playerName)){
                return e.getValue();
            }
        }
        return null;
    }
    

}
