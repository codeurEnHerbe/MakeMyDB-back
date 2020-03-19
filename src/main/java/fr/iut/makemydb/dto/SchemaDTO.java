package fr.iut.makemydb.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Data
public class SchemaDTO {
    private Integer id;
    private int version;
    private String name;
}
