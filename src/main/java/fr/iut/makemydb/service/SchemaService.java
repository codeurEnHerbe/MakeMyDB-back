package fr.iut.makemydb.service;

import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.domain.UserInfosEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.repository.UserInfosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<SchemaEntity> findByNameContain(String name){
        UserInfosEntity userInfos = userServ.getCurrentUser();
        return userInfos.getSchemas().stream().
                filter( schema -> schema.getName() == "%"+name+"%")
                .collect(Collectors.toList());
    }

    public SchemaEntity createSchemaEntity(SchemaDTO schema){
        UserInfosEntity userInfos = userServ.getCurrentUser();
        SchemaEntity e = new SchemaEntity();
        e.setName(schema.getName());
        e.setSchemaData(schema.getSchemaData());
        userInfos.getSchemas().add(e);
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
}
