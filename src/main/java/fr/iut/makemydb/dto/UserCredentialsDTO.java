package fr.iut.makemydb.dto;

import lombok.Data;

@Data
public class UserCredentialsDTO {

    private String username;
    private String password;

    public UserCredentialsDTO(){

    }
}
