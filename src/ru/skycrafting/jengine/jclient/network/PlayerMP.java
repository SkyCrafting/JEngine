/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient.network;

/**
 *
 * @author SkyCrafting_
 */
public class PlayerMP {

    private float x, y;
    private String name;
    private int connectionId = 0;
    private String state;
    private String Dirrection = "UP";
    private String worldState = "NOT_DOWNLOADED";
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "X=" + getX() + " Y=" + getY() + " CS=" + getState() + " NAME=" + name + " CI=" + connectionId;
    }

    public String getDirrection() {
        return Dirrection;
    }

    public String getWorldState() {
        return worldState;
    }

    public void setWorldState(String worldState) {
        this.worldState = worldState;
    }

    public void setDirrection(String Dirrection) {
        this.Dirrection = Dirrection;
    }
}
