package fr.iut.makemydb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Attribute {
    private String name, type;

    @JsonProperty(value="isPrimary")
    private boolean isPrimaryKey;

    @JsonIgnore
    private Entity foreignTable;

    @JsonIgnore
    private Attribute references;//Foreign Key

    public Attribute(){
    }

    //FK oriented constructor
    public Attribute(String name, Attribute references, Entity foreignTable){
        this.name = name;
        this.references = references;
        this.foreignTable = foreignTable;
        this.type = foreignTable.findAttributePrimary().getType();
    }
}
