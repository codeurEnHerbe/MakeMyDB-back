package fr.iut.makemydb.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class UserInfosEntity {

    @Id
    private String username;
    private String email;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<SchemaEntity> schemas;

    public UserInfosEntity(){
    }

    public UserInfosEntity(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
    }
}
