package fr.iut.makemydb.controler;

import fr.iut.makemydb.dto.ChangePasswordRequestDTO;
import fr.iut.makemydb.dto.UserRegisterDTO;
import fr.iut.makemydb.mapper.UserMapper;
import fr.iut.makemydb.service.UserInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserInfosService userInfosService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder, UserInfosService userInfosService){
        this.passwordEncoder = passwordEncoder;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.userInfosService = userInfosService;
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequestDTO changePassword){
        jdbcUserDetailsManager.changePassword(changePassword.getOldPassword(), passwordEncoder.encode(changePassword.getNewPassword()));
    }

    @GetMapping("/all")
    public UserDetails getUser(){
        return jdbcUserDetailsManager.loadUserByUsername("admin");
    }

    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestParam("username") String username){
        return jdbcUserDetailsManager.userExists(username);
    }

    @GetMapping("/check-email")
    public Boolean checkEmail(@RequestParam("email") String email) {
        return this.userInfosService.checkEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO newUserDTO){
        System.out.println(newUserDTO);
        newUserDTO.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        User tmp = UserMapper.userDtoToUser(newUserDTO);
        if(jdbcUserDetailsManager.userExists(tmp.getUsername())) {
            return ResponseEntity.status(403).build();
        }else{
            jdbcUserDetailsManager.createUser(tmp);
            userInfosService.saveUserInfos(newUserDTO);
            return ResponseEntity.ok(UserMapper.userToUserDTO(tmp));

        }

    }

    @GetMapping("/me")
    public UserDetails getInfos(){
        return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @DeleteMapping("/{username}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity deleteUser(@PathVariable("username") String username){
        jdbcUserDetailsManager.deleteUser(username);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}