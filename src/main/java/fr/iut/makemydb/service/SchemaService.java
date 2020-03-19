package fr.iut.makemydb.service;

import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.repository.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaService {

    @Autowired
    private SchemaRepository repository;

    public List<SchemaEntity> findByNameContain(String name){
        return repository.findAllByNameLike("%"+name+"%");
    }

    public SchemaEntity createSchemaEntity(SchemaDTO schema){
        SchemaEntity e = new SchemaEntity();
        e.setName(schema.getName());
        e = repository.save(e);
        return e;
    }
}
