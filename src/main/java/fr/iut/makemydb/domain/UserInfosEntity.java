package fr.iut.makemydb.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserInfosEntity {

    @Id
    private String username;
    private String email;

    public UserInfosEntity(){
    }

    public UserInfosEntity(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
    }
}
