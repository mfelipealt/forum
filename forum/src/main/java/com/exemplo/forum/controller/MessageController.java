package com.exemplo.forum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    @GetMapping("/status")
    public String status() {
        return "Fórum está funcionando!";
    }
}
