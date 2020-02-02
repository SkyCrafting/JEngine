/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient.packets;

/**
 *
 * @author SkyCrafting_
 */
public class PacketAuth {

    private String name;
    private String state;
    
    public PacketAuth() { 
    }
    
    public PacketAuth(String state) { 
        this.state = state;
    }
    
    /**
     * Пакет регистрации для юзера
     * @param state
     * @param name 
     */
    public PacketAuth(String state, String name) {
        this.state = state;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
