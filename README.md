# Pix Case

Este projeto é uma Prova de Conceito (POC) para gerenciamento de chaves Pix, desenvolvido em Java com Spring Boot.

## Estrutura do Projeto

- `src/main/java/com/pix/poc/`: Código-fonte principal
- `src/test/java/com/pix/poc/`: Testes unitários e de integração
- `src/main/resources/`: Configurações e scripts de banco de dados
- `docker-compose.yml`: Orquestração de containers para infraestrutura local

## Banco de Dados

- **Banco utilizado:** PostgreSQL
- Por padrão, o projeto está configurado para conectar-se a um banco PostgreSQL.
- Um container do PostgreSQL pode ser iniciado via Docker Compose.

## Como rodar

### Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose

### Build do projeto

```sh
mvn clean install
```

### Subir infraestrutura (banco de dados) com Docker Compose

```sh
docker-compose up --build
```

### Executar aplicação via Maven

```sh
mvn spring-boot:run
```

### Executar testes

```sh
mvn test
```

## Funcionalidades

- Cadastro, consulta, atualização e remoção de chaves Pix
- Validações de CPF, CNPJ, e-mail e telefone
- API RESTful

