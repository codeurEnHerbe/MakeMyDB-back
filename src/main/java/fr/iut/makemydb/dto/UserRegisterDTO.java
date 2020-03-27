package fr.iut.makemydb.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class UserRegisterDTO {

    private String username;
    private String password;
    private String passwordConfirmation;
    private Collection<SimpleGrantedAuthority> authority;

    public UserRegisterDTO(String username, String password){
        this(username, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public UserRegisterDTO(String username, String password, Collection<GrantedAuthority> authorities ){

    }

    public UserRegisterDTO(){

    }
}