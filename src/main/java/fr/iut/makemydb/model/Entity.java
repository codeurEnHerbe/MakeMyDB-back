package fr.iut.makemydb.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Entity {
    private String elementId, label;
    private Element element;
    private int x, y;

    public Entity(){
        this.element = new Element();
    }
}
