package ru.skycrafting.jengine;

import ru.skycrafting.jengine.graphics.TextureAtlas;
import ru.skycrafting.jengine.network.Connector;
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
    private Player thePlayer;
    private String thePlayerName;
    private Connector network;
    private boolean initAllPlayers = false;
    private int onlinePlayers = 0;

    public Player getThePlayer() {
        return thePlayer;
    }

    public TheEngine() {
        /* <-- Init Main --> */
        TheEngine.instance = this;
        atlas = new TextureAtlas("Atlas.png");
        renderManager = new RenderManager(instance);
        world = new World();
        window = new MainWindow("TheEngine ", 780, 480, instance);
        camera = new PlayerCamera(0, 0);

        /* <-- Init Input --> */
        handler = getWindow().getInputHandler();

        /* <-- Init Player --> */
        initPlayer();

    }

    private boolean init = false;

    private void initPlayer() {
        int playerX = 63, playerY = 60;
        thePlayerName = "Mudak_" + getRandomNumber(1, 5000);
        thePlayer = new Player(instance, thePlayerName, playerX, playerY);
        getWorld().getPlayerManager().addPlayer(thePlayerName, thePlayer);
        network = new Connector(instance, "192.168.100.3", 25565, thePlayerName);
        network.getPacketSender().sendLogin(thePlayer);
        init = true;
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

    public Connector getNetwork() {
        return network;
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

    public boolean isInitAllPlayers() {
        return initAllPlayers;
    }

    public void setInitAllPlayers(boolean initAllPlayers) {
        this.initAllPlayers = initAllPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }
}
