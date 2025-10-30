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
- Maven (incluído via Maven Wrapper - `mvnw`)

## Como Executar

### Opção 1: Usando o script (Recomendado)
O projeto inclui scripts que detectam automaticamente o Java 17 instalado no seu sistema:

1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd bandcamp-downloader
   ```

3. Execute o script de execução:
   ```bash
   ./run.sh
   ```

4. Acesse a aplicação em seu navegador:
   ```
   http://localhost:8080
   ```

**Scripts disponíveis:**
- `./run.sh` - Compila (se necessário) e executa o projeto
- `./build.sh` - Apenas compila o projeto

Os scripts tentarão encontrar automaticamente o Java 17 nas seguintes localizações:
- `/Library/Java/JavaVirtualMachines/` (instalações padrão no macOS)
- `~/.sdkman/candidates/java/` (SDKMAN)
- Usando `/usr/libexec/java_home -v 17`

### Opção 2: Execução manual
Se você já tem o `JAVA_HOME` configurado para Java 17:

1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd bandcamp-downloader
   ```

3. Execute o projeto com Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a aplicação em seu navegador:
   ```
   http://localhost:8080
   ```

### Instalando Java 17 (se necessário)
Se você não tem o Java 17 instalado, você pode instalá-lo usando:

**Homebrew:**
```bash
brew install openjdk@17
```

**SDKMAN:**
```bash
sdk install java 17-tem
```

Ou baixe diretamente de: https://adoptium.net/temurin/releases/?version=17

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