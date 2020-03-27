package fr.iut.makemydb.controler;

import fr.iut.makemydb.dto.ChangePasswordRequestDTO;
import fr.iut.makemydb.dto.UserCredentialsDTO;
import fr.iut.makemydb.dto.UserRegisterDTO;
import fr.iut.makemydb.mapper.DtoConverter;
import fr.iut.makemydb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private JdbcUserDetailsManager jdbcUserDetailsManager;
    private PasswordEncoder passwordEncoder;

    public UserController(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequestDTO changePassword){
        jdbcUserDetailsManager.changePassword(changePassword.getOldPassword(), passwordEncoder.encode(changePassword.getNewPassword()));
    }

    @GetMapping("/all")
    public UserDetails getUser(){
        return jdbcUserDetailsManager.loadUserByUsername("admin");
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO newUserDTO){
        User tmp = UserMapper.userDtoToUser(newUserDTO);
        if(jdbcUserDetailsManager.userExists(tmp.getUsername())) {
            jdbcUserDetailsManager.createUser(tmp);
            return ResponseEntity.ok(UserMapper.userToUserDTO(tmp));
        }else{
            return ResponseEntity.status(403).build();
        }

    }

}
