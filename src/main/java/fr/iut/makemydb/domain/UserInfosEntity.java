package fr.iut.makemydb.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
public class UserInfosEntity {

    @Id
    private String username;
    private String email;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<SchemaEntity> schemas;

    public UserInfosEntity(){
    }

    public UserInfosEntity(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
    }

    public void addSchema(SchemaEntity e) {
        Set<SchemaEntity> schemas = this.getSchemas();
        schemas.add(e);
        this.setSchemas(schemas);
    }
}
