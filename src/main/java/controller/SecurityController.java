package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("")
    public String helloWorld(){
        return "Hello World";
    }


    @GetMapping("/protected")
    public String test(){
        return "Hello World Y";
    }
}
