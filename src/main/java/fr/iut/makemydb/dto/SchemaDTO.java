package fr.iut.makemydb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.iut.makemydb.model.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class SchemaDTO {
    private int id;
    private String name;
    private SchemaDataDTO schemaData;

    @Data
    public static class SchemaDataDTO{

        List<EntityDTO> entities;
        List<RelationDTO> relations;

    }
    @Data
    public static class EntityDTO{
        private String elementId;
        private ElementDTO element;
        private int x, y;

    }
    @Data
    public static class RelationDTO{
        private String elementId;
        private ElementRelationDTO element;
        private int x, y;

    }
    @Data
    public static class ElementDTO{
        private String name;
        private ArrayList<AttributeDTO> attributes;

    }
    @Data
    public static class AttributeDTO{
        private String name, type;

        @JsonProperty(value="isPrimary")
        private boolean isPrimary;
    }
    @Data
    public static class ElementRelationDTO{
        public ArrayList<LinkDTO> links;

    }
    @Data
    public static class LinkDTO{
        int id;
        String entityName;
        String cardinalMin;
        String cardinalMax;

    }
}
