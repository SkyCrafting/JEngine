/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.Loader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.skycrafting.jengine.TheEngine;
import ru.skycrafting.jengine.graphics.IRender;
import ru.skycrafting.jengine.world.Loader.tile.Tile;
import ru.skycrafting.jengine.world.player.PlayerCamera;

/**
 *
 * @author SkyCrafting_
 */
public class WorldLoader implements IRender {

    private int worldSize = 10;
    private byte[][] world;
    private Random r = new Random();
    protected TheEngine instance;

    public byte[][] getWorld() {
        return world;
    }

    public void setWorld(byte[][] world) {
        this.world = world;
        worldSize = world.length;
    }

    public int getTileSizeByID(int id) {
        int temp = 0;
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                int idd = getWorld()[y][x];
                if (idd == id) {
                    temp++;
                }
            }
        }
        return temp;
    }

    public WorldLoader(int size) {
        this.worldSize = size;
        this.world = new byte[size][size];
        this.instance = TheEngine.instance;
        /* for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                world[y][x] = (byte) instance.getRandomNumber(1, 10);
            }
        }

        for (int offset = 1; offset < 2; offset++) {
            for (int y = 0; y < world.length; y++) {
                for (int x = 0; x < world[y].length; x++) {
                    if (x < offset) {
                        world[y][x] = 0;
                    }
                    if (x < offset) {
                        world[y][x] = 0;
                    }

                    if (x > offset) {
                        world[worldSize - offset][x] = 0;
                    }
                    if (x > offset) {
                        world[y][worldSize - offset] = 0;
                    }
                }
            }
        }*/
    }

    public void load(String worldName) {
        world = loadWorld(worldName);
        worldSize = world.length;
        System.out.println("World Loaded!");

        //System.out.println("!!!!!!!!!!");
        saveWorld(world, "mudak111");
        TheEngine.instance.getRenderManager().setMaxApple(getTileSizeByID(3));
    }

    private String[] tempArray;

    public void saveWorld(byte[][] world, String worldName) {
        File worldDir = new File(instance.getEngineDirectory() + File.separator + worldName);
        JSONObject jo = new JSONObject();
        jo.put("worldSize", world.length);
        jo.put("worldName", worldName);
        File regionDir = new File(worldDir + File.separator + "world_data");
        if (!regionDir.exists()) {
            regionDir.mkdirs();
        }
        System.out.println(worldName);
        String temp = "";
        for (int y = 0; y < world.length; y++) {
            for (int i = 1; i < world.length; i++) {
                if (y == i * 10) {
                    temp += "--";
                }
            }
            for (int x = 0; x < world.length; x++) {
                temp += world[y][x] + "-" + y + "-" + x + "~";
            }
        }

        tempArray = temp.split("--");
        System.out.println("Total files: " + tempArray.length);
        jo.put("rgFilesCount", tempArray.length);
        ArrayList<String> regions = new ArrayList<>();
        new Thread(() -> {
            try {
                for (int i = 0; i < tempArray.length; i++) {
                    File f = new File(regionDir + File.separator + "region_" + i + ".jengine.json");
                    FileWriter writer = new FileWriter(f);
                    writer.write(tempArray[i]);
                    writer.flush();
                    System.out.println("Write: " + "region_" + i + ".jengine.json   " + f.length() / 1024 + "Kb");
                    regions.add("region_" + i + ".jengine.json");
                }
                jo.put("regions", regions);
                saveWorldConfig(worldDir, jo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("World Saved!");
    }

    private void saveWorldConfig(File worldDir, JSONObject jo) {
        try {
            File worldConfig = new File(worldDir + File.separator + "world.jengine.json");
            FileWriter writer = new FileWriter(worldConfig);
            writer.write(jo.toString(4));
            writer.flush();
        } catch (IOException | JSONException e) {
        }
    }

    private byte[][] loadWorld(String worldName) {
        JSONObject config = new JSONObject();
        try {
            File worldDir = new File(instance.getEngineDirectory() + File.separator + worldName);
            FileReader reader = new FileReader(worldDir + File.separator + "world.jengine.json");
            BufferedReader br = new BufferedReader(reader);
            String temp;
            String readed = "";
            while ((temp = br.readLine()) != null) {
                readed += temp;
            }
            config = new JSONObject(readed);
        } catch (IOException | JSONException e) {
        }

        JSONArray rgFiles = config.getJSONArray("regions");
        int worldSize = Integer.parseInt(config.get("worldSize").toString());

        String temp = "";
        try {
            for (int id = 0; id < rgFiles.length(); id++) {
                File f = new File(instance.getEngineDirectory() + File.separator + worldName + File.separator + "world_data" + File.separator + rgFiles.getString(id));
                String str = reader(f);
                temp += str;
            }
        } catch (Exception e) {
        }

        String[] block = temp.split("~");
        byte[][] resultWorld = new byte[worldSize][worldSize];

        for (int i = 0; i < block.length; i++) {
            resultWorld[Integer.parseInt(block[i].split("-")[1])][Integer.parseInt(block[i].split("-")[2])] = (byte) Integer.parseInt(block[i].split("-")[0]);
        }
        return resultWorld;
    }

    private String reader(File fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NULL!";
    }

    public Tile getTileByPos(int x, int y) {
        if ((x >= 0 && x <= worldSize - 1) && (y >= 0 && y <= worldSize - 1)) {
            Tile t = instance.getWorld().getTileManager().getTileById(world[y][x]);
            return t;
        }
        return TheEngine.instance.getWorld().getTileManager().getTileById(0);
    }

    private Tile tempTile;

    @Override
    public void render(Graphics g) {
        int tileScale = instance.getWorld().getTileManager().getSCALE_TILE_SIZE();
        PlayerCamera cam = instance.getCamera();

        /* <-- Оптимизация - наше всё--> */
        int xStart = (int) Math.max(0, cam.getxOffSet() / tileScale);
        int xEnd = (int) Math.min(world.length, (cam.getxOffSet() + (instance.getWindow().getWidth() + tileScale)) / tileScale);

        int yStart = (int) Math.max(0, cam.getyOffSet() / tileScale);
        int yEnd = (int) Math.min(world.length, (cam.getyOffSet() + instance.getWindow().getHeight()) / tileScale);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                tempTile = instance.getWorld().getTileManager().getTileById(world[y][x]);
                tempTile.render((Graphics2D) g,
                        (int) (x * tileScale - cam.getxOffSet()),
                        (int) (y * tileScale - cam.getyOffSet()));
            }
        }
    }

    @Override
    public void update() {

    }

    public int getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(int worldSize) {
        this.worldSize = worldSize;
    }
}
