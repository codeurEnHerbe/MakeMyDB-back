package fr.iut.makemydb.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.mapper.DtoConverter;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.service.SchemaService;
import fr.iut.makemydb.service.SqlService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/schema")
@RestController
@Transactional
public class SchemaRestController {
    @Autowired
    private SchemaService delegate;

    @Autowired
    private SqlService sqlService;

    @Autowired
    private SchemaRepository repo;

    @Autowired
    private DtoConverter mapper;

    @GetMapping("/byName/{name}")
    public List<SchemaDTO> findByNameContain(@PathVariable("name") String name){
        val tmp = delegate.findByNameContain(name);
        return mapper.mapAsList(tmp, SchemaDTO.class);
    }

    @GetMapping("/")
    public List<SchemaDTO> findAll(){
        val tmp = repo.findAll();
        return mapper.mapAsList(tmp, SchemaDTO.class);
    }

    @GetMapping("/generate")
    public String generateSql(@RequestParam("id") int id) throws JsonProcessingException {
        //Question: Peut on faire cette opérantion en passant par le model ? (this.user.schemas.find(id))
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<SchemaEntity> schema = delegate.findByUsernameAndId(user, id);
        if(schema.isPresent()) {
            return sqlService.generateSql(schema.get());
        }
         return "";
    }

    @PostMapping(path = "/")
    public SchemaDTO create(@RequestBody SchemaDTO schema){
        System.out.println(schema);
        val tmp = delegate.createSchemaEntity(schema);
        return mapper.map(tmp, SchemaDTO.class);
    }
}
