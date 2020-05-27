package fr.iut.makemydb.service;

import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.domain.UserInfosEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.repository.UserInfosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchemaService {

    @Autowired
    private SchemaRepository repository;

    @Autowired
    private UserInfosRepository userRepo;

    public List<SchemaEntity> findByNameContain(String name){
        return repository.findAllByNameLike("%"+name+"%");
    }

    public SchemaEntity createSchemaEntity(SchemaDTO schema){
        UserDetails userDet = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SchemaEntity e = new SchemaEntity();
        e.setName(schema.getName());
        e.setSchemaData(schema.getSchemaData());
        Optional<UserInfosEntity> foundUser = this.userRepo.findByUsername(userDet.getUsername());
        if(foundUser.isPresent())  {
            e.setUser(foundUser.get());
        }
        e = repository.save(e);
        return e;
    }
}
