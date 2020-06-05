package fr.iut.makemydb.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ElementRelation extends Element {

    public ArrayList<Link> links;
    public ElementRelation(){
        super();
        links = new ArrayList<>();
    }
}
