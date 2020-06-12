package fr.iut.makemydb.dto;

import lombok.Data;

@Data
public class SchemaResponseDTOLight {
        private int id;
        private String name;

    public SchemaResponseDTOLight(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
