package com.lastlesson.controller;

import com.lastlesson.entity.User;
import com.lastlesson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, Model model ){
        if (userForm.getUsername().length() < 4 || userForm.getUsername().contains(" ")){
            model.addAttribute("nameError","Имя должно состоять из 5 или более символов и не должно содержать пробелов");
            return "registration";
        }
        if (userForm.getPassword().length() < 4 || userForm.getPassword().contains(" ")){
            model.addAttribute("uncorrectPassword", "Пароль должен состоять из 5 или более символов и не должен содержать пробелов");
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароль не совпадает с подтверждением.");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError","Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }
}
