<h1>
    <img src="https://cdn.pixabay.com/animation/2022/07/29/14/39/14-39-55-151_512.gif" alt="icon" width="32" style="vertical-align: min(-5px); margin-right: 10px;">
    Spring Boot Banking API
</h1>

![build incomplete](https://img.shields.io/badge/build-incomplete-yellow)

## Overview

The **Spring Boot Banking API** is a RESTful service designed to simulate 
basic banking operations. It provides functionalities such as user 
authentication, account creation, deposits, and transfers. Built with Spring Boot, 
Swagger for documentation, JWT for security, JPA for data persistence, and PostgreSQL as the database.
**** 

### 🚀 1. Features

- ✅ **JWT Authentication**: Secure user authentication and authorization.
- 👤 **User Management**: Register and manage user accounts.
- 🏦 **Account Operations**: Create bank accounts and view balances.
- 🔄 **Transactions**: Perform deposits and transfers between accounts.
- 💰 **Loan**: Manage your loan applications and repayments

****

### 🛠️ 2. Technologies Used

- ![Jwt](https://img.shields.io/badge/Jwt-4.4.0-orange)
- ![Swagger](https://img.shields.io/badge/Swagger-2.8.6-brightgreen)
- ![Maven](https://img.shields.io/badge/Maven-3.9.9-red)
- ![Docker](https://img.shields.io/badge/Docker-28.0.1-blue)
- ![Spring Security](https://img.shields.io/badge/Spring_Security-3.4.4-brighgreen)
- ![Java](https://img.shields.io/badge/Java-17-blue)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-blue)
> InteliJIDEA
****

### 🧑‍💻 3. **Getting Started**

### 3.1 ***Prerequisites***

- **Java 17** or higher
- **PostgreSQL** database
- **Maven** installed
- **Docker** installed

### 3.2 ***installation***

3.2.1 **Clone the repository**:

```shell
git clone https://github.com/FernandoCruzCavina/spring_boot_banking_api.git
cd spring_boot_banking_api
```

3.2.2 **Configure the database**:

- Create a PostgreSQL database named ```banking```
- Update the ```application.properties``` file with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/banking
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3.2.3 **Configure the security keys**

- Access the RsaService.java file
```
..\BankUp\src\main\java\org\example\bankup\security\RsaService.java
```
- Run **once time** this file
```java
public class RsaService{
    public static void main(String[] args) {
        loadkeys(); //this method create the public key and private key
    }
}
```

3.2.4 **Run the ``docker-compose.yml``**

```shell
docker compose up -d
```

3.2.5 **Run the application**:
```shell
./mvnw spring-boot:run
```

***

### 📬 4. **API Endpoints**

#### Some Endpoints

| **Method** | **Endpoint**         | **Description**     |
|:----------:|:---------------------|:--------------------|
|    GET     | /customers/{id}      | Get customer by id  |
|    POST    | /customers/create    | Create new customer |
|    POST    | /accounts/create     | Create new account  |
|    POST    | /transactions/create | Transfer money      |
|    GET     | /loans/{id}          | Get loan by id      |


I also prepared endpoints with JSON responses for you in Postman and Swagger-UI

<div  style="display: flex; justify-content: center; gap: 50px; flex-wrap: wrap; align-items: center;">
    <div style="text-align: center">
        <img src="https://www.svgrepo.com/show/354202/postman-icon.svg" width="100">
        <p>
            <a href="https://www.postman.com/telecoms-astronaut-73012880/bankup/collection/9l1uupb/rest-api-basics-crud-test-variable?action=share&creator=37077735">
                to access all endpoint in Postman
            </a>
        </p>
    </div>
    <div style="text-align: center">
        <img src="https://help.apiary.io/images/swagger-logo.png" width="100">
        <p>
            <a>
                http://localhost:PORT/swagger-ui/index.html#/
            </a>
        </p>
    </div>
</div>


