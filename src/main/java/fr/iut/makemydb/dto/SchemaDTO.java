package fr.iut.makemydb.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Data
public class SchemaDTO {
    private String name;
    private String schemaData;
}
