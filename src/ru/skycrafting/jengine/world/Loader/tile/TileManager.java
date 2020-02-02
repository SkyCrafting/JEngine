/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.Loader.tile;

import java.util.HashMap;
import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.graphics.TextureAtlas;

/**
 *
 * @author SkyCrafting_
 */
public class TileManager {

    private final Tile voidTile, grassTile, dirtTile, appleTile;
    private final TextureAtlas atlas = TheEngine.instance.getAtlas();
    private final int TILE_SCALE = 16;
    private final int TILE_ZOOM = 2;
    
    private final int SCALE_TILE_SIZE = TILE_SCALE * TILE_ZOOM;
    
    
    private final HashMap<Integer, Tile> TileList;

    public int getTileScale() {
        return TILE_SCALE;
    }
    
    public TileManager() {
        TileList = new HashMap<>();
        voidTile = new Tile(false, "void", atlas.cutTile(6 * TILE_SCALE, 0 * TILE_SCALE, 16, 16), TILE_ZOOM);
     //   grassTile = new Tile(true, "grass", atlas.cutTile(0 * TILE_SCALE, 0 * TILE_SCALE, 16, 16), TILE_ZOOM);
        grassTile = new Tile(false, "grass", atlas.cutTile(4 * TILE_SCALE, 0 * TILE_SCALE, 16, 16), TILE_ZOOM);
        dirtTile = new Tile(false, "dirt", atlas.cutTile(4 * TILE_SCALE, 0 * TILE_SCALE, 16, 16), TILE_ZOOM);
        appleTile = new Tile(false, "apple", atlas.cutTile(3 * TILE_SCALE, 0 * TILE_SCALE, 16, 16), TILE_ZOOM);
        
        for (int i = 4; i < 100; i++) {
            TileList.put(i, new Tile(false, "dirt_" + i, atlas.cutTile(4 * TILE_SCALE, 0 * TILE_SCALE, 16, 16), TILE_ZOOM));
        }
        
        
        TileList.put(0, voidTile);
        TileList.put(1, grassTile);
        TileList.put(2, dirtTile);
        TileList.put(3, appleTile);
    }

    public int getSCALE_TILE_SIZE() {
        return SCALE_TILE_SIZE;
    }

    public Tile getTileById(int id) {
        return TileList.get(id);
    }

}
