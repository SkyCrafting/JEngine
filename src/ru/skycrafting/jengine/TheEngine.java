package ru.skycrafting.jengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.skycrafting.jengine.graphics.TextureAtlas;
import ru.skycrafting.jengine.jclient.NetworkManager;
import ru.skycrafting.jengine.windows.MainWindow;
import ru.skycrafting.jengine.world.World;
import ru.skycrafting.jengine.world.player.Player;
import ru.skycrafting.jengine.world.player.PlayerCamera;

/**
 *
 * @author SkyCrafting_
 */
public class TheEngine {

    public static TheEngine instance;
    private final MainWindow window;
    private final RenderManager renderManager;
    private final TextureAtlas atlas;
    private final World world;
    private final PlayerCamera camera;
    private final InputHandler handler;

    /* <-- Networking --> */
    private int onlinePlayers = 0;
    private boolean isConnected = false;
    private String thePlayerName;
    private Player thePlayer;

    private NetworkManager netManager;

    /* <-- Config --> */
    private JSONObject mainConfig;
    private String textureAtlas;
    private String engineDirectory;

    public Player getThePlayer() {
        return thePlayer;
    }

    public TheEngine() {
        Config();
        /* <-- Init Main --> */
        TheEngine.instance = this;
        atlas = new TextureAtlas(textureAtlas);
        renderManager = new RenderManager(instance);
        world = new World();
        window = new MainWindow("TheEngine ", 780, 480, instance);
        camera = new PlayerCamera(0, 0);

        /* <-- Init Input --> */
        handler = getWindow().getInputHandler();

        /* <-- Init Player --> */
        initPlayer();
    }

    private void Config() {
        String dir = "C:\\Users\\SkyCrafting_\\Desktop\\NETBEANS\\JEngine\\res";
        try {
            File config = new File(dir + File.separator + "Config.json");

            if (!config.exists()) {
                FileWriter writer = new FileWriter(config);

                JSONObject jo = new JSONObject();
                jo.put("engineDirectory", dir);
                jo.put("textureAtlasFile", "Atlas.png");
                jo.put("worldsFolder", "worlds");

                ArrayList<String> list = new ArrayList<>();
                JSONArray ja = new JSONArray(list);
                jo.put("worlds", ja);
                writer.write(jo.toString(4));
                writer.flush();
                textureAtlas = jo.get("textureAtlasFile").toString();
                engineDirectory = mainConfig.get("engineDirectory").toString();
            } else {
                FileReader reader = new FileReader(config);
                BufferedReader br = new BufferedReader(reader);
                String temp = "";

                String readed = "";
                while ((temp = br.readLine()) != null) {
                    readed += temp;
                }
                mainConfig = new JSONObject(readed);
                textureAtlas = mainConfig.get("textureAtlasFile").toString();
                engineDirectory = mainConfig.get("engineDirectory").toString();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            File config = new File(engineDirectory + File.separator + "Config.json");
            FileWriter writer = new FileWriter(config);
            writer.write(getMainConfig().toString(4));
            writer.flush();
        } catch (IOException ex) {
        }
    }

    private boolean init = false;

    private void initPlayer() {
        int playerX = 103, playerY = 96;
        thePlayerName = "Mudak_" + getRandomNumber(1, 5000);
        thePlayer = new Player(instance, thePlayerName, playerX, playerY);
        getWorld().getPlayerManager().addPlayer(thePlayerName, thePlayer);
        
        
        String ip = /*"45.87.2.141";*/ "192.168.100.3";
        int port = 25565;
        
        try {
            netManager = new NetworkManager(thePlayerName, ip, port, port);
        } catch (IOException ex) {
            System.err.println("Connection error: " + ex.getMessage());
            return;
        }
        
        loadWorld(ip, port);
        
        init = true;
    }

    private void loadWorld(String ip, int port) {
        JSONArray ja = getMainConfig().getJSONArray("worlds");
        for (int i = 0; i < ja.length(); i++) {
            String[] j = ja.getString(i).split("~");
            if (j[1].contains(ip + "_" + port)) {
                //network.getPacketSender().sendLogin(thePlayer, false);
                init = true;
                System.out.println("World is exists");
                getWorld().getLoader().load(j[0]);
                return;
            }
        }
 
    }

    public boolean isInit() {
        return init;
    }

    public String getThePlayerName() {
        return thePlayerName;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public PlayerCamera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public MainWindow getWindow() {
        return window;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public InputHandler getHandler() {
        return handler;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public JSONObject getMainConfig() {
        return mainConfig;
    }

    public String getEngineDirectory() {
        return engineDirectory;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public NetworkManager getNetManager() {
        return netManager;
    }
}
