# Restful Booker Automation Framework

Framework de automatización de pruebas para la API Restful Booker.

## API bajo prueba

https://restful-booker.herokuapp.com

## Tecnologías utilizadas

- Java 17
- Maven
- REST Assured
- JUnit 5
- Cucumber
- Extent Reports

## Conceptos implementados

- REST API Testing
- Cucumber
- JUnit Assertions
- Status Code Validation
- Body Validation
- Header Validation
- Response Time Validation
- Query Parameters
- GET Requests
- POST Requests
- Extent Reports

## Escenarios automatizados

1. Obtener la lista de booking IDs.
2. Filtrar bookings por firstname.
3. Filtrar bookings por lastname.
4. Validar autenticación con credenciales inválidas.
5. Verificar disponibilidad de la API mediante Ping.

## Validaciones realizadas

Se verifican diferentes elementos de las respuestas HTTP:

- Status Code
- Response Body
- Headers
- Booking IDs
- Mensajes de respuesta
- Tiempo de respuesta

## Ejecución

Para ejecutar todas las pruebas:

```bash
mvn clean test