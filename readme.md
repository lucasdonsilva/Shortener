# Shortener.

O projeto consiste em encurtar uma url gerando um alias randomico.

## Link da aplicação rodando no heroku

https://shortener-lucas.herokuapp.com/

## Tecnologias utilizadas.

- Java 11 - Escolhido por ser uma das ultimas versões do java com diversas otimizações.
- Spring Boot, Data, Web, Cache e Actuator. - Escolhido por ser um framework completo e um dos mais utilizado pelos desenvolvedores.
- Junit 5. - Escolhido por ter diversos métodos e tratamentos com alguns recursos que foram implementados no java 8.
- Mockito. - Escolhido por ser um dos frameworks mais utilizado para simular comportamentos em objetos.
- Mongodb. - Escolhida por ser uma excelente base para fazer um find por id indexado além de sua fácil escalabilidade horizontal.
- Lombok. - Escolhido para retirar a verbosidade dos pojos.
- Mapstruct. - Escolhido para fazer "de/para" de objetos, é o mais otimizado da comunidade.

## Execução dos testes

Rodar os comandos abaixo na raiz do projeto.

**Unitários**  

    ./mvnw test

**Integração** 

    ./mvnw integration-test

## Build do projeto

Para gerar o artefato do projeto, execute o comando a baixo na raiz do projeto.

    ./mvnw package -Dsurefire.skip=true

## Rodando a aplicação
    
Para rodar o projeto localmente, é necessário ter o mongo instalado ou subir ele com o docker-compose.
    
    docker-compose up -d
    
Assim que o mongo estiver de pé, basta alterar a string de conexão no application-dev.yaml e passar -Denv=dev como variavel.
 
## Endpoints disponíveis.

### Cadastrar uma url (POST): http://localhost:8080/shorteners

**JSON:**

```
{
    "url": "http://www.google.com.br"
}
```

**Status:** 201 -> Criado com sucesso.

**Status:** 400 -> Url nula ou vazia.

**Status:** 422 -> Url enviada mas fora de padrão.

**Response data:** 

```
Header: Location = http://localhost:8080/shorteners/abcdef
```

### Redirecionar uma url (GET): http://localhost:8080/shorteners/{alias}

**Status:** 302 -> Redireciona para a url cadastrada no alias.

**Status:** 404 -> Alias não encontrado.


### Método GET: http://localhost:8080/shorteners/top10

**Status:** 200 -> Retorna um array vazio ou top 10 de urls mais acessadas.

**Response data:** 

```
[
    {
        "url": "http://www.google.com.br",
        "alias": "google",
        "access": 10
    },
    {
        "url": "http://www.facebook.com.br",
        "alias": "face",
        "access": 5
    },
    {
        "url": "http://www.g1.com.br",
        "alias": "g1",
        "access": 2
    }
]
```

## Monitoramento

Acessar o endopoint abaixo para ver a saúde da aplicação e suas dependencias.

- http://localhost:8080/health