package fr.iut.makemydb.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.dto.SchemaResponseDTO;
import fr.iut.makemydb.dto.SchemaResponseDTOLight;
import fr.iut.makemydb.mapper.DtoConverter;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.service.SchemaService;
import fr.iut.makemydb.service.SqlService;
import fr.iut.makemydb.service.UserInfosService;
import lombok.val;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, String>> generateSql() throws JSONException, JsonProcessingException {
        Optional<SchemaEntity> schema = userService.getCurrentUser().getSchemas()
            .stream()
            .filter( schemaEntity -> schemaEntity.getId().equals(1))
            .findAny();
        if(schema.isPresent()) {
            String yeet = sqlService.generateSql(schema.get());
            System.out.println(yeet);
            return ResponseEntity.ok().body(Collections.singletonMap("response", yeet));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/")
    public ResponseEntity<SchemaDTO> save(@RequestBody SchemaDTO schema) throws JsonProcessingException {
        System.out.println(schema);
        if(userService.getCurrentUser() == null){
            return ResponseEntity.status(401).build();
        }
        val tmp = delegate.createSchemaEntity(schema);
        return ResponseEntity.ok(mapper.map(tmp, SchemaDTO.class));
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<SchemaResponseDTO> load(@PathVariable("id") int id) {
        val tmp = delegate.loadSchemaEntity(id);
        if (tmp.isPresent())
            return ResponseEntity.ok(mapper.map(tmp.get(), SchemaResponseDTO.class));
        else
            return ResponseEntity.status(403).build();
    }

    @GetMapping("/load/")
    public ResponseEntity<List<SchemaResponseDTOLight>> loadAll() {
        val tmp = delegate.loadAllSchemaEntityLight();


        if(tmp != null)
            return ResponseEntity.ok(mapper.mapAsList(tmp, SchemaResponseDTOLight.class));

        return ResponseEntity.status(403).build();
    }

}