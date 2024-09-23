package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumDownloadViewController {

    @GetMapping("/")
    public String showDownloadPage(Model model) {
        return "index"; // Certifique-se de que o nome do arquivo HTML é 'index.html'
    }
    
}

