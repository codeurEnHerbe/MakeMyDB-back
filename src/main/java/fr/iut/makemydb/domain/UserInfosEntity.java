package fr.iut.makemydb.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
public class UserInfosEntity {

    @Id
    private String username;
    private String email;

    @OneToMany
    private Set<SchemaEntity> schemas;

    public UserInfosEntity(){
    }

    public UserInfosEntity(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
    }
}
