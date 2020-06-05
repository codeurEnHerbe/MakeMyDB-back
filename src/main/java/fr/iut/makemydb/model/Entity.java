package fr.iut.makemydb.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class Entity {
    private String elementId, label;
    private Element element;
    private int x, y;

    public Entity(String elementId, String label, Element element, int x, int y) {
        this.elementId = elementId;
        this.label = label;
        this.element = element;
        this.x = x;
        this.y = y;
    }

    public Entity(){
        this.element = new Element();
    }

    public Attribute findAttributePrimary(){
        Optional<Attribute> result = this.element.getAttributes().stream()
            .filter( attr -> attr.isPrimaryKey())
            .findAny();
        if(result.isPresent()){
           return result.get();
        }
        return null;
    }
}
