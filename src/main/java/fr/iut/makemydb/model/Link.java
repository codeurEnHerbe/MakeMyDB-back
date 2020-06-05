package fr.iut.makemydb.model;

import lombok.Data;

@Data
public class Link {
    int id;
    String entityName;
    String cardinalMin;
    String cardinalMax;

    public Link(){}
}
