package fr.iut.makemydb.repository;

import fr.iut.makemydb.domain.SchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchemaRepository extends JpaRepository<SchemaEntity, Integer> {

    public SchemaEntity findByName(String name);

    public List<SchemaEntity> findAllByNameLike(String namePattern);
}
