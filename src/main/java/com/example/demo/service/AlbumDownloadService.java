package com.example.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AlbumDownloadService {

    // Método para baixar e zipar o álbum
    public File downloadAndZipAlbum(String albumUrl) throws IOException {
        try {
            // Verificar se o domínio da URL é bandcamp.com
            if (!albumUrl.contains("bandcamp.com")) {
                throw new IllegalArgumentException("URL não é uma página válida do Bandcamp.");
            }

            // Fazendo o scraping da página do Bandcamp
            Document doc = Jsoup.connect(albumUrl).get();

            // Exibindo o título da página para debug
            System.out.println("Título da página: " + doc.title());

            // Pegar o título do álbum para nomear o arquivo zip
            String albumTitle = doc.title().replaceAll("[^a-zA-Z0-9]", "_");
            File zipFile = new File(albumTitle + ".zip");

            // Criar o arquivo zip e baixar o conteúdo
            try (FileOutputStream fos = new FileOutputStream(zipFile);
                    ZipOutputStream zipOut = new ZipOutputStream(fos)) {

                // Buscar o script com o JSON das faixas
                Element scriptElement = doc.selectFirst("script[data-tralbum]");
                if (scriptElement != null) {
                    String jsonData = scriptElement.attr("data-tralbum");
                    JSONObject jsonObject = new JSONObject(jsonData);

                    // Acessar o array de faixas no JSON
                    JSONArray trackArray = jsonObject.getJSONArray("trackinfo");
                    for (int i = 0; i < trackArray.length(); i++) {
                        JSONObject track = trackArray.getJSONObject(i);
                        JSONObject fileObject = track.optJSONObject("file");
                        String trackUrl = fileObject != null ? fileObject.optString("mp3-128") : null;
                    
                        if (trackUrl != null && !trackUrl.isEmpty()) {
                            System.out.println("URL da faixa: " + trackUrl);
                            String trackName = (i + 1) + "_" + track.optString("title").replaceAll("[^a-zA-Z0-9]", "_") + ".mp3";
                            downloadAndAddToZip(trackUrl, zipOut, trackName);
                        } else {
                            System.err.println("URL da faixa não encontrada ou inválida para a faixa: " + track.optString("title"));
                        }
                    }
                    
                    
                }

                // Pegar a capa do álbum (ajustado para o id "tralbumArt")
                Element coverElement = doc.selectFirst("#tralbumArt img");
                if (coverElement != null) {
                    String coverUrl = coverElement.attr("src");
                    // Corrigir URL se necessário (para URLs relativas)
                    if (!coverUrl.startsWith("http")) {
                        coverUrl = "https://bandcamp.com" + coverUrl;
                    }
                    System.out.println("URL da capa: " + coverUrl); // Debug
                    downloadAndAddToZip(coverUrl, zipOut, "cover.jpg");
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw e; // Propaga a exceção
            }

            return zipFile;

        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            throw e;
        } catch (IOException e) {
            System.err.println("Falha ao acessar o site: " + e.getMessage());
            throw e;
        }
    }

    // Método auxiliar para baixar um arquivo e adicionar ao zip com nome
    // especificado
    private void downloadAndAddToZip(String fileUrl, ZipOutputStream zipOut, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream inputStream = url.openStream()) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                zipOut.write(buffer, 0, length);
            }

            zipOut.closeEntry();
        }
    }
}
