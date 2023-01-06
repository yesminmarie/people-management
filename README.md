<h1 align="center">:rocket: People Management :rocket:</h1>

<p>This API allows you to manage people. 
In this project you can add people and addresses to them. 
You can also specify which is the main address of a person. 
</p>

<p>You can use the Swagger to manipulate the endpoints:
<a href = "http://localhost:8080/swagger-ui/index.html#/">
localhost:8080/swagger-ui.html</a> </p>

<h1 id="technologies">:rocket: Technologies</h1>

<p>It was used these technologies in this project.</p>

- [Java 17](https://www.oracle.com/java/)
- [Spring Boot 3.0.1](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Lombok](https://projectlombok.org/)
- [H2](https://www.h2database.com/html/main.html)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)
- [Swagger](https://swagger.io/)
- [Maven](https://maven.apache.org/)

<h1 id="how-to-run">:computer: How to run the application</h1>

<h2>Prerequisites</h2>

<p>You will need these tools installed in your machine:</p>

- Must have [Git](https://git-scm.com/ "Git") installed
- At least have Java 17 installed
- Must have Maven installed

```bash
# Clone this repository
git clone https://github.com/yesminmarie/people-management

# Go into the folder of the project
cd people-management

# execute the project
./mvn spring-boot:run
```
<h2> Endpoints: </h2>

<h3> Persons: </h3>

- POST - localhost:8080/persons (Add a new person)

<p><strong>Example:</strong></p>

```bash
{
    "name": "Maria",
    "birthDate": "11/11/1990"
}
```

- GET - localhost:8080/persons/{id} (Get the person by id)

- GET - localhost:8080/persons (Get all persons using pagination)
<p><strong> Example: <a href = "http://localhost:8080/persons?page=0&size=2">
localhost:8080/persons?page=0&size=2 </a> </strong></p>

- PUT - localhost:8080/persons/{id} (update a specific person)
<p><strong>Example:</strong></p>

```bash
{
    "name": "Ana",
    "birthDate": "11/11/2001"
}
```

<h3> Addresses: </h3>

- GET - localhost:8080/addresses/{idPerson} (Get the addresses of a specific person, passing the person id)

- POST - localhost:8080/addresses (Save a new address to a specific person)
<p><strong>Example:</strong></p>

```bash
{
    "street": "Rua Teste",
    "zipCode": "123456789",
    "number": 123,
    "city": "São Paulo",
    "idPerson": 1,
    "main": true
}
```

- PATCH - localhost:8080/addresses/{idAdress} (update the main address of a person, passing the address id that will be the main)

<h4>Made with ❤️ by Yesmin Marie</h4>