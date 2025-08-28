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
docker compose -p case-pix up --build
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

## Endpoints da API
- Criar chave Pix: 

    - `POST /pix`
    - Resposta: `200 Created` com id da chave criada.
```bash
curl --request POST \
  --url http://localhost:8080/pix \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.4.0' \
  --data '{
    "documentNumber": "12345678909",
    "accountNumber": "0002",
    "accountType": "CORRENTE",
    "agencyNumber": "5678",
    "nameClient": "Maria",
    "lastNameClient": "Julia",
    "pixType": "CELULAR",
    "pixValue": "+1155953023291"
  }'
```

- Deletar chave Pix:

    - `Delete /pix/{id}`
    - Resposta: `200 OK` com id da chave deletada.
  
```bash
curl --request DELETE \
  --url http://localhost:8080/pix/74e7feb0-890c-45f0-95c9-a0b63d05d55c
```
- Busca chave Pix:

    - `Get /pix`
    - Resposta: `200 OK` com detalhes da chave.

```bash
curl --request GET \
  --url 'http://localhost:8080/pix?id=3ce8fe72-d6a1-4251-bf66-12ae0b801c0a&pixType=CELULAR&pixInclusionDate=28%2F08%2F2025&pixDeactivationDate=28%2F08%2F2025&agencyNumber=5672&accountNumber=1' \
```
- Atualizar chave Pix:

    - `Patch /pix`
    - Resposta: `200 OK` com detalhes da chave atualizada.
```bash
curl --request PATCH \
  --url http://localhost:8080/pix \
  --header 'Content-Type: application/json' \
  --data '{
  "id": "22222222-0000-0000-0000-000000000001",
  "accountType": "CORRENTE",
  "agencyNumber": "1234",
	"accountNumber": "0001",
  "nameClient": "Maria3",
  "lastNameClient": "Julia3"
}'
```
    

