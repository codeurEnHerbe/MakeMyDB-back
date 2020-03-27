package fr.iut.makemydb.mapper;

import fr.iut.makemydb.dto.UserCredentialsDTO;
import fr.iut.makemydb.dto.UserRegisterDTO;
import org.springframework.security.core.userdetails.User;

public class UserMapper {
    public static User userDtoToUser(UserRegisterDTO newUserDTO) {
        return new User(newUserDTO.getUsername(), newUserDTO.getPassword(), newUserDTO.getAuthority());
    }

    public static UserRegisterDTO userToUserDTO(User newUser) {
        return new UserRegisterDTO(newUser.getUsername(), newUser.getPassword(), newUser.getPassword(), newUser.getAuthorities());
    }
}
