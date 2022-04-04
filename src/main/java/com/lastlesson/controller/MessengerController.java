package com.lastlesson.controller;

import com.lastlesson.entity.Message;
import com.lastlesson.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MessengerController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messenger")
    public String messenger(Model model, @RequestParam(name = "name", required = false) String name) {
        model.addAttribute("newMessage", new Message());
        if (name == null || name.isEmpty()) {
            model.addAttribute("allMessages", messageRepository.findAll());
        } else {
            model.addAttribute("allMessages", messageRepository.findByUsername(name));
        }
        return "messenger";
    }

    @PostMapping("/messenger/update")
    public String addMessage(@ModelAttribute("newMessage") Message newMessage, Model model, HttpServletRequest httpServletRequest) {
        if (newMessage.getMessage().length() < 1) {
            model.addAttribute("messageError", "Сообщение не должно быть пустым.");
            return "redirect:/messenger";
        }
        newMessage.setUsername(httpServletRequest.getRemoteUser());
        messageRepository.save(newMessage);
        return "redirect:/messenger";
    }


}
