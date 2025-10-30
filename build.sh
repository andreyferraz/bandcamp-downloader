#!/bin/bash

# Script para compilar o Bandcamp Downloader com Java 17
# Uso: ./build.sh

set -e

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Compilando Bandcamp Downloader ===${NC}"

# Verificar se JAVA_HOME está configurado para Java 17
if [ -n "$JAVA_HOME" ]; then
    JAVA_VERSION=$("$JAVA_HOME/bin/java" -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" != "17" ]; then
        echo -e "${YELLOW}JAVA_HOME está configurado, mas não aponta para Java 17.${NC}"
        echo -e "${YELLOW}Tentando encontrar Java 17...${NC}"
        unset JAVA_HOME
    fi
fi

# Se JAVA_HOME não estiver configurado ou não for Java 17, tentar encontrar Java 17
if [ -z "$JAVA_HOME" ]; then
    # Verificar localizações comuns do Java no macOS
    JAVA17_PATHS=(
        "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
        "/Library/Java/JavaVirtualMachines/adoptopenjdk-17.jdk/Contents/Home"
        "/Library/Java/JavaVirtualMachines/amazon-corretto-17.jdk/Contents/Home"
        "/Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home"
        "$HOME/.sdkman/candidates/java/17.0"*
    )
    
    # Também usar /usr/libexec/java_home se disponível
    if [ -x "/usr/libexec/java_home" ]; then
        JAVA_HOME_FOUND=$(/usr/libexec/java_home -v 17 2>/dev/null || echo "")
        if [ -n "$JAVA_HOME_FOUND" ]; then
            export JAVA_HOME="$JAVA_HOME_FOUND"
            echo -e "${GREEN}Java 17 encontrado em: $JAVA_HOME${NC}"
        fi
    fi
    
    # Se ainda não encontrou, verificar os caminhos comuns
    if [ -z "$JAVA_HOME" ]; then
        for path in "${JAVA17_PATHS[@]}"; do
            # Expandir wildcards
            for expanded_path in $path; do
                if [ -d "$expanded_path" ] && [ -x "$expanded_path/bin/java" ]; then
                    export JAVA_HOME="$expanded_path"
                    echo -e "${GREEN}Java 17 encontrado em: $JAVA_HOME${NC}"
                    break 2
                fi
            done
        done
    fi
fi

# Verificar se conseguiu encontrar Java 17
if [ -z "$JAVA_HOME" ]; then
    echo -e "${RED}Erro: Java 17 não encontrado!${NC}"
    echo -e "${YELLOW}Por favor, instale o Java 17 ou configure a variável JAVA_HOME.${NC}"
    echo ""
    echo "Para instalar o Java 17 no macOS, você pode usar:"
    echo "  - Homebrew: brew install openjdk@17"
    echo "  - SDKMAN: sdk install java 17-tem"
    echo "  - Baixar de: https://adoptium.net/temurin/releases/?version=17"
    exit 1
fi

# Verificar a versão do Java
JAVA_VERSION=$("$JAVA_HOME/bin/java" -version 2>&1 | head -n 1)
echo -e "${GREEN}Usando: $JAVA_VERSION${NC}"
echo ""

# Exportar JAVA_HOME para o Maven wrapper usar
export JAVA_HOME

# Compilar o projeto
echo -e "${GREEN}Compilando projeto...${NC}"
./mvnw clean package -DskipTests

echo ""
echo -e "${GREEN}✓ Compilação concluída com sucesso!${NC}"
echo -e "${YELLOW}Para rodar o projeto, use: ./run.sh${NC}"
