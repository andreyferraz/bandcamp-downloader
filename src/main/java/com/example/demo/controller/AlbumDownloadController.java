package com.example.demo.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.AlbumDownloadService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AlbumDownloadController {

    @Autowired
    private AlbumDownloadService albumDownloadService;

    @PostMapping("/download")
    public String downloadAlbum(@RequestParam("albumUrl") String albumUrl, HttpServletResponse response, Model model) {
        try {
            File zipFile = albumDownloadService.downloadAndZipAlbum(albumUrl);

            // Definir os headers da resposta para download
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=" + zipFile.getName());

            // Enviar o arquivo para o usuário
            try (FileInputStream fis = new FileInputStream(zipFile);
                    OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    os.write(buffer, 0, length);
                }
            }

            // Apagar o arquivo zip temporário
            if (!zipFile.delete()) {
                System.err.println("Falha ao deletar o arquivo temporário: " + zipFile.getName());
            }

            return ""; // Retorna string vazia ao invés de 'null'

        } catch (Exception e) {
            model.addAttribute("message", "Erro ao baixar o álbum: " + e.getMessage());
            return "index"; // Retorna para a página inicial em caso de erro
        }
    }
}
