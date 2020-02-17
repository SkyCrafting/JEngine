/*
 * ���� �� ����, ������� ����� �������� �����, �� ��� ����� �� �����!
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
     * ����� ����������� ��� �����
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
