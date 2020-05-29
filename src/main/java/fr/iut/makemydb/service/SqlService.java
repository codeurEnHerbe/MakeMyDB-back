package fr.iut.makemydb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.model.Attribute;
import fr.iut.makemydb.model.Entity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SqlService {

    public String generateSql(SchemaEntity schema) throws JsonProcessingException {
        String sql = "";
        String data = schema.getSchemaData();
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        CollectionType collectionType = typeFactory.constructCollectionType(List.class, Entity.class);
        List<Entity> entities = mapper.readValue(data, collectionType);
        System.out.print(createRelations(entities));

        return "";
    }

    private String createRelations(List<Entity> entities){
        StringBuilder stringBuild = new StringBuilder();
        entities.forEach( entity -> {
            stringBuild.append("CREATE TABLE IF NOT EXISTS " + entity.getElement().getName() + " (" + createAttributes(entity.getElement().getAttributes()) + ")");
        });
        return stringBuild.toString();
    }

    private String createAttributes(ArrayList<Attribute> attributes) {
        StringBuilder stringBuild = new StringBuilder();
        attributes.forEach( attribute -> {
            stringBuild.append("\n\t\t" + attribute.getName() + " " + attribute.getType());
        });
        return stringBuild.toString();
    }
}
