package fr.iut.makemydb.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class UserRegisterDTO {


    private String email;
    private String username;
    private String password;
    private String passwordConfirmation;

    private Collection<GrantedAuthority> authority =  Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

    public UserRegisterDTO(String username, String password, String passwordConfirmation){
        this(username, password, passwordConfirmation, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public UserRegisterDTO(String username, String password, String passwordConfirmation, Collection<GrantedAuthority> authorities ){
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.authority = authorities;
    }

    public UserRegisterDTO(){}


}