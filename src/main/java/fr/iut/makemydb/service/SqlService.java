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
        return createRelations(infos);
    }

    private String createRelations(SchemaInfos infos){
        System.out.print(infos);
        ArrayList<Element> sqlEentities = new ArrayList<>(infos.getEntities().stream().map( entity -> entity.getElement()).collect(Collectors.toList()));
        infos.getRelations().forEach( relation -> {
            Link link1 = relation.getElement().getLinks().get(0);
            Link link2 = relation.getElement().getLinks().get(1);
            Entity e1 = infos.findEntityByName(link1.getEntityName());
            Entity e2 = infos.findEntityByName(link2.getEntityName());

            if( (link1.getCardinalMax().equals("1") && link2.getCardinalMax().equals("n")) ){
                e2.getElement().getAttributes().add(
                        new Attribute("fk_" + e1.findAttributePrimary().getName(),
                                e1.findAttributePrimary(),
                                xe1));
            }else if( (link2.getCardinalMax().equals("1") && link1.getCardinalMax().equals("n")) ){
                e1.getElement().getAttributes().add(new Attribute("fk_" + e2.findAttributePrimary().getName(), e2.findAttributePrimary(), e2));
            }else if( link1.getCardinalMax().equals("n") && link2.getCardinalMax().equals("n") ){
                ArrayList<Attribute> newAttributes = relation.getElement().getAttributes();
                Attribute fk1 = new Attribute(e1.findAttributePrimary().getName() + "_fk", e1.findAttributePrimary(), e1);
                Attribute fk2 = new Attribute(e2.findAttributePrimary().getName() + "_fk", e2.findAttributePrimary(), e2);
                newAttributes.add(infos.findEntityByName(link2.getEntityName()).findAttributePrimary());
                newAttributes.add(infos.findEntityByName(link1.getEntityName()).findAttributePrimary());
                sqlEentities.add(new Element(relation.getElement().getName(), newAttributes));
            }
        });
        StringBuilder stringBuild = new StringBuilder();
        sqlEentities.forEach( element -> {
            stringBuild.append("\nCREATE TABLE IF NOT EXISTS " + element.getName() + " (" + createAttributes(element.getAttributes()) + "\n)");
        });
        return stringBuild.toString();
    }

    private String createAttributes(ArrayList<Attribute> attributes){
        StringBuilder stringBuild = new StringBuilder();

        attributes.forEach(attribute -> {
            stringBuild.append("\n\t\t" + attribute.getName() + " " + attribute.getType());
            //if(attribute.getTypeNumber()){
              //  stringBuild.append("(" + attribute.getTypeNumber().get() + ")");
            //
            //}
            //System.out.println(attribute.getTypeNumber());
        });
        stringBuild.append("\n\t\t" + "PRIMARY KEY (");

        List<Attribute> pks = attributes.stream().filter(attr -> attr.isPrimaryKey()).collect(Collectors.toList());

        for(int i = 0; i < pks.size(); i++){
            stringBuild.append(pks.get(i).getName());
            if(i < pks.size() - 1){
                stringBuild.append(", ");
            }
        }

        stringBuild.append(")");

        attributes.forEach( attr -> {
            if(attr.getReferences() != null){
                stringBuild.append("\n\t\t" + "CONSTRAINT " + attr.getName() + "_" + attr.getReferences().getName() + "_FK REFERENCES " + attr.getForeignTable().getElement().getName() + " ("  + attr.getReferences().getName() + ") ");
            }
        });

        /*
        }*/
        return stringBuild.toString();
    }
}
