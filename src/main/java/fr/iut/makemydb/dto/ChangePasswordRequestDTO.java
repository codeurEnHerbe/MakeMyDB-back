package fr.iut.makemydb.dto;

import lombok.*;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
@Data
public class ChangePasswordRequestDTO {

    private String oldPassword;
    private String newPassword;


}
