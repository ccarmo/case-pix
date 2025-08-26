FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /pix

# Copia arquivos do Maven para cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline

# Copia o código-fonte
COPY src ./src

WORKDIR /pix

# Por padrão, roda a aplicação com DevTools
CMD ["./mvnw", "spring-boot:run"]
