package fr.iut.makemydb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.model.*;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SqlService {

    public String generateSql(SchemaEntity schema) throws JsonProcessingException, JSONException {
        String sql = "";
        String data = schema.getSchemaData();
        ObjectMapper mapper = new ObjectMapper();
        SchemaInfos infos = mapper.readValue(data, SchemaInfos.class);
        System.out.print(createRelations(infos));

        return "";
    }

    private String createRelations(SchemaInfos infos){
        ArrayList<Element> sqlEentities = new ArrayList<>(infos.getEntities().stream().map( entity -> entity.getElement()).collect(Collectors.toList()));
        infos.getRelations().forEach( relation -> {
            Link link1 = relation.getElement().getLinks().get(0);
            Link link2 = relation.getElement().getLinks().get(1);
            if( (link1.getCardinalMax().equals("1") && link2.getCardinalMax().equals("n")) ){
                infos.findEntityByName(link1.getEntityName()).findAttributePrimary().setReferences(infos.findEntityByName(link2.getEntityName()).findAttributePrimary()); // add attribute reference(other attribute)
            }else if( (link2.getCardinalMax().equals("1") && link1.getCardinalMax().equals("n")) ){
                infos.findEntityByName(link2.getEntityName()).findAttributePrimary().setReferences(infos.findEntityByName(link1.getEntityName()).findAttributePrimary());
            }else if( link1.getCardinalMax().equals("n") && link2.getCardinalMax().equals("n") ){
                sqlEentities.add(new Element(relation.getElement().getName(), relation.getElement().getAttributes()));
            }
        });
        StringBuilder stringBuild = new StringBuilder();
        sqlEentities.forEach( element -> {
            stringBuild.append("\nCREATE TABLE IF NOT EXISTS " + element.getName() + " (" + createAttributes(element.getAttributes()) + "\n)");
        });
        return stringBuild.toString();
    }

    private String createAttributes(ArrayList<Attribute> attributes) {
        StringBuilder stringBuild = new StringBuilder();
        attributes.forEach( attribute -> {
            stringBuild.append("\n\t\t" + attribute.getName() + " " + attribute.getType());
            if(attribute.isPrimaryKey()){
                stringBuild.append(" CONSTRAINT "+ attribute.getName() +"_pk PRIMARY KEY ("+ attribute.getName() +")");
            }
        });
        return stringBuild.toString();
    }
}
