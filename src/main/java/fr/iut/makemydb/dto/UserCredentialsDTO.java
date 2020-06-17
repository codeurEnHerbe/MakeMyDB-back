package fr.iut.makemydb.dto;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
public class UserCredentialsDTO {

    private String username;
    private String password;

    public UserCredentialsDTO(){

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
