package fr.iut.makemydb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.domain.UserInfosEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.repository.UserInfosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchemaService {


    @Autowired
    private SchemaRepository repository;

    @Autowired
    private UserInfosRepository userRepo;

    @Autowired
    private UserInfosService userServ;

    @Autowired
    private ObjectMapper mapper;

    public List<SchemaEntity> findByNameContain(String name){
        UserInfosEntity userInfos = userServ.getCurrentUser();
        return userInfos.getSchemas().stream().
                filter( schema -> schema.getName() == "%"+name+"%")
                .collect(Collectors.toList());
    }

    public SchemaEntity createSchemaEntity(SchemaDTO schema) throws JsonProcessingException {
        UserInfosEntity userInfos = userServ.getCurrentUser();
        SchemaEntity e = new SchemaEntity();
        List<SchemaEntity> listSchema = userInfos.getSchemas();

        Optional<SchemaEntity> schemaEntity = listSchema.stream()
                .filter(userSchema -> userSchema.getName().equals(schema.getName()))
                .findAny();

        e.setName(schema.getName());
        e.setSchemaData(mapper.writeValueAsString(schema.getSchemaData()));
        if (schemaEntity.isPresent()) {
            listSchema.remove(schemaEntity.get());
        }
        listSchema.add(e);
        e.setUser(userInfos);
        return e;
    }

    public Optional<SchemaEntity> findById(int id){
        UserInfosEntity userInfos = userServ.getCurrentUser();
        List<SchemaEntity> schemas = userInfos.getSchemas();
        Optional<SchemaEntity> result = schemas.stream()
                .filter( schema -> schema.getId() == id)
                .findAny();

        return result;
    }

    public Optional<SchemaEntity> loadSchemaEntity(String name) {
        UserInfosEntity user = userServ.getCurrentUser();

        return user.getSchemas()
                .stream()
                .filter(schema -> schema.getName().equals(name))
                .findAny();
    }

    public List<SchemaEntity> loadAllSchemaEntity() {
        UserInfosEntity user = userServ.getCurrentUser();

        return user.getSchemas();
    }
}
