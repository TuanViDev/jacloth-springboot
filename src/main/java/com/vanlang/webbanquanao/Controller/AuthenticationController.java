package com.vanlang.webbanquanao.Controller;

import com.vanlang.webbanquanao.Model.User;
import com.vanlang.webbanquanao.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegister()
    {
        return "/authentication/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        User user = userService.findUserByUsername(username);
        if (user != null) return "redirect:/register?usernameExist";

        ResponseEntity<String> response = registerAPI(username, password);
        if (response.getStatusCode() == HttpStatus.OK)
        {
            return "redirect:/login?registeredSuccess";
        }
        else{
            return "redirect:/register?error";
        }
    }

    @GetMapping("/login")
    public String showLogin()
    {
        return "/authentication/login";
    }



    //////////////   API     /////////////////
    @PostMapping("/api/guest/register")
    public ResponseEntity<String> registerAPI(String username, String password)
    {
        User newUser = new User();
        newUser.setUsername(username);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(password));
        newUser.setRole("USER");
        userService.saveUser(newUser);

        if (newUser.getId() >= 0)
        {
            return ResponseEntity.ok().body("Register successful");
        }
        else
        {
            return ResponseEntity.badRequest().body("Register unsuccessful");
        }
    }


}
