package fr.iut.makemydb.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.mapper.DtoConverter;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.service.SchemaService;
import fr.iut.makemydb.service.SqlService;
import fr.iut.makemydb.service.UserInfosService;
import lombok.val;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserInfosService userService;

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
    public String generateSql(@RequestParam("id") int id) throws JSONException, JsonProcessingException {
        Optional<SchemaEntity> schema = userService.getCurrentUser().getSchemas()
            .stream()
            .filter( schemaEntity -> schemaEntity.getId().equals(id))
            .findAny();
        if(schema.isPresent()) {
            String yeet = sqlService.generateSql(schema.get());
            System.out.println(yeet);
            return yeet;
        }
         return "";
    }

    @PostMapping(path = "/")
    public SchemaDTO create(@RequestBody SchemaDTO schema) throws JsonProcessingException {
        System.out.println(schema);
        val tmp = delegate.createSchemaEntity(schema);
        return mapper.map(tmp, SchemaDTO.class);
    }

    @GetMapping("/load/{name}")
    public ResponseEntity<SchemaDTO> load(@PathVariable("name") String name) {
        val tmp = delegate.loadSchemaEntity(name);
        if (tmp.isPresent())
            return ResponseEntity.ok(mapper.map(tmp.get(), SchemaDTO.class));
        else
            return ResponseEntity.status(403).build();
    }

}