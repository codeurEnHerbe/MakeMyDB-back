package fr.iut.makemydb.model;

import lombok.Data;

@Data
public class Relation {
    private String elementId, label;
    private ElementRelation element;
    private int x, y;

    public Relation(){
    }
}
