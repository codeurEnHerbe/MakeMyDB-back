package fr.iut.makemydb.dto;

import lombok.Data;

@Data
public class SchemaResponseDTO {
    private int id;
    private String name;
    private String schemaData;
}
