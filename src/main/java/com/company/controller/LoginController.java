package com.company.controller;

import com.company.global.GlobalData;
import com.company.model.Role;
import com.company.model.User;
import com.company.service.RoleService;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(){
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleService.findById(2).get());
        user.setRoles(roleList);

        userService.save(user);
        request.login(user.getEmail(), password);

        return "redirect:/";
    }
}
