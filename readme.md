# Bandcamp Downloader

## Descrição
Este projeto é uma aplicação Spring Boot que permite aos usuários baixar álbuns do Bandcamp. A aplicação faz scraping das páginas dos álbuns, obtendo as músicas e a capa, e compacta tudo em um arquivo ZIP.

## Tecnologias Utilizadas
- **Java**: 17
- **Spring Boot**: 3.3.4
- **Thymeleaf**: Para renderização de views
- **Jsoup**: Para scraping de HTML
- **JSON**: Para manipulação de dados em formato JSON

## Funcionalidades
- Download de todas as faixas de um álbum do Bandcamp.
- Compactação das faixas e da capa do álbum em um arquivo ZIP.
- Interface web simples para entrada da URL do álbum.

## Pré-requisitos
- JDK 17
- Maven

## Como Executar
1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>

2. Navegue até o diretório do projeto:
   ```bash
   cd bandcamp-downloader

3. Execute o projeto com Maven:
   ```bash
   mvn spring-boot:run

4. Acesse a aplicação em seu navegador:
   ```bash
   http://localhost:8080

## Uso
- 1. Insira a URL do álbum do Bandcamp que você deseja baixar.
- 2. Clique no botão para iniciar o download.
- 3. O arquivo ZIP será baixado contendo todas as faixas e a capa do álbum.    

## Estrutura do Projeto
- src/main/java/com/bandcamp: Contém a lógica da aplicação.
- - AlbumDownloadService.java: Serviço responsável por baixar e compactar os álbuns.
- - AlbumDownloadController.java: Controlador para gerenciar as requisições da interface.
- - AlbumDownloadViewController.java: Controlador para exibir a página de download.
- src/main/resources/templates: Contém as views Thymeleaf.
- src/main/resources/static: Contém arquivos estáticos (CSS, JavaScript).

## Contato
Para mais informações, você pode me contatar em [andrey.developer@hotmail.com].