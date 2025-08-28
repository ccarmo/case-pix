# Pix Case

Este projeto é uma Prova de Conceito (POC) para gerenciamento de chaves Pix, desenvolvido em Java com Spring Boot.

## Estrutura do Projeto

- `src/main/java/com/pix/poc/`: Código-fonte principal
- `src/main/resources/`: Configurações e scripts de banco de dados
- `docker-compose.yml`: Orquestração de containers para infraestrutura local
- `pom.xml`: Gerenciamento de dependências com Maven
- `src/test/java/com/pix/poc/`: Testes unitários e de integração
- `README.md`: Documentação do projeto
- `.gitignore`: Arquivos e pastas a serem ignorados pelo Git
- `application.properties`: Configurações da aplicação


## Banco de Dados

- **Banco utilizado:** PostgreSQL
- Por padrão, o projeto está configurado para conectar-se a um banco PostgreSQL.
- Um container do PostgreSQL pode ser iniciado via Docker Compose.

## Como rodar

### Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose
- Git
- Postman ou qualquer cliente REST para testar a API
- IDE de sua preferência (IntelliJ, Eclipse, VSCode, etc.)
- cURL (opcional, para testes via linha de comando)
- PostgreSQL

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
- Validação de chaves Pix (CPF, CNPJ, e-mail, telefone, chave aleatória)

