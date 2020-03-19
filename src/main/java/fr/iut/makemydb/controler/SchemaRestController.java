package fr.iut.makemydb.controler;

import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.mapper.DtoConverter;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.service.SchemaService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("/api/schema")
@RestController
@Transactional
public class SchemaRestController {
    @Autowired
    private SchemaService delegate;

    @Autowired
    private DtoConverter mapper;

    @GetMapping("/byName/{name}")
    public List<SchemaDTO> findByNameContain(@PathVariable("name") String name){
        val tmp = delegate.findByNameContain(name);
        return mapper.mapAsList(tmp, SchemaDTO.class);
    }

    @PostMapping(path = "/")
    public SchemaDTO create(@RequestBody SchemaDTO schema){
        val tmp = delegate.createSchemaEntity(schema);
        return mapper.map(tmp, SchemaDTO.class);
    }
}
