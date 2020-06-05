package fr.iut.makemydb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Attribute {
    private String name, type;

    @JsonProperty(value="isPrimary")
    private boolean isPrimaryKey;

    private boolean foreignTable, foreignAttribute;

    private Attribute references;//Foreign Key

    public Attribute(){
    }
}
