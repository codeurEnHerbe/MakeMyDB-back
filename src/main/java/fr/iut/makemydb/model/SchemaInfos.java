package fr.iut.makemydb.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class SchemaInfos {
    ArrayList<Entity> entities;

    ArrayList<Relation> relations;

    public SchemaInfos(){
        entities = new ArrayList<>();
        relations = new ArrayList<>();
    }

    public Entity findEntityByName(String name){
        Optional<Entity> result = this.entities.stream()
                .filter( entity -> entity.getElement().getName().equals(name))
                .findAny();

        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
