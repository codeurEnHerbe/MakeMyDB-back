package fr.iut.makemydb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.domain.UserInfosEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.dto.SchemaResponseDTOLight;
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
        repository.saveAndFlush(e);
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

    public Optional<SchemaEntity> loadSchemaEntity(int id) {
        UserInfosEntity user = userServ.getCurrentUser();

        return user.getSchemas()
                .stream()
                .filter(schema -> schema.getId() == id)
                .findAny();
    }

    public List<SchemaResponseDTOLight> loadAllSchemaEntityLight() {
        UserInfosEntity user = userServ.getCurrentUser();

        if(user == null){
            return null;
        }

        if(user.getSchemas() == null){
            return new ArrayList<SchemaResponseDTOLight>();
        }

        return user.getSchemas()
                .stream()
                .map(res -> new SchemaResponseDTOLight(res.getId(), res.getName()))
                .collect(Collectors.toList());
    }

    public boolean deleteSchemaEntity(int id) {
        UserInfosEntity user = userServ.getCurrentUser();

        return user.getSchemas().remove(user.getSchemas()
                .stream()
                .filter(schema -> schema.getId() == id)
                .findAny()
                .get());
    }
}
