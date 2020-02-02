/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.entity;

import java.awt.Graphics2D;

/**
 *
 * @author SkyCrafting_
 */
public abstract class Entity {

    public final EntityTypes type;

    protected Entity(EntityTypes type, float x, float y) {
        this.type = type;
    }
    
    public abstract void render(Graphics2D g);

}
