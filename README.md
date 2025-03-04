# Auth-Service
The Auth Service will be the first service for the [AsianWearApp](https://working-honey-2ac.notion.site/Dobara-Phir-Se-Project-184d21524dbe800fa1b3e6538595d852) it uses when accessing the app. It will verify the user from the database (RDS). If the user is not verified, there is an option to join or exit the app. 

Reference to the documentation on Notion: [https://www.notion.so/Auth-Service-189d21524dbe808c8e7de5d932989a4c](https://working-honey-2ac.notion.site/Auth-Service-189d21524dbe808c8e7de5d932989a4c)

## Architecture Overview

* **Spring Boot** application - written in **Java** which contains API endpoints serving JSON responses, hosted as a **Docker** container.
* User database stored in local **PostgreSQL** - contains the user details information.
* **Flyway** to run the migraton scripts - creates the table and updates to add in Primary/Foreign Keys and constraints. 

### **Prerequisites**
- [Java 17 +](https://www.oracle.com/uk/java/technologies/downloads/)
- [Maven 3.6+](https://maven.apache.org/install.html)
- [PostgreSQL 13+](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/downloads)


## Building the application

To build the application:

```bash
./mvnw clean install
```

## Usage/Examples

### **Creating a New User**

#### POST /auth/register
  - Example:
```bash    
curl -X POST http://localhost:8081/auth/register \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "lastName": "Smith",
  "email": "newUser@email.com",
  "password": "Password123!"
}'
```
- Request Body:

```json
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "newUser@email.com",
  "password": "Password123!"
}
```
- Response 201:
```json
{
  "userId": "af7c1fe6-d669-414e-b066-e9733f0de7a8",
  "message": "User registered successfully!"
}
```
