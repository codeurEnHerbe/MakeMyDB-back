package fr.iut.makemydb.model;


import lombok.Data;

import java.util.ArrayList;

@Data
public class Element {

    private String name;
    private ArrayList<Attribute> attributes;

    public Element(){
        this.attributes = new ArrayList<>();
    }

    public Element(String name, ArrayList<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
    }
}
