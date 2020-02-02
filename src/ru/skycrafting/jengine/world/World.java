/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world;

import ru.skycrafting.jengine.world.Loader.WorldLoader;
import ru.skycrafting.jengine.world.Loader.tile.TileManager;
import ru.skycrafting.jengine.world.player.PlayerManager;

/**
 *
 * @author SkyCrafting_
 */
public class World{
    
    private final WorldLoader loader;
    private final TileManager tileManager;
    private final PlayerManager playerManager;
    public static int size = 1;
    
    
    public World() {
        tileManager = new TileManager();
        loader = new WorldLoader(100);
        playerManager = new PlayerManager();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public WorldLoader getLoader() {
        return loader;
    }
}