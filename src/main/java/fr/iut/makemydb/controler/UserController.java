package fr.iut.makemydb.controler;

import fr.iut.makemydb.dto.ChangePasswordRequestDTO;
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


}
