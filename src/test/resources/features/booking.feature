Feature: Automatizacion API Restful Booker

  Scenario: Obtener la lista de booking IDs
    When realizo un GET al endpoint de bookings
    Then verifico que el status code sea 200
    And verifico que la respuesta sea una lista de booking IDs
    And verifico que el header Content-Type contenga "application/json"
    And verifico que el tiempo de respuesta sea menor a 5000 milisegundos


  Scenario: Filtrar bookings por firstname
    Given creo una reserva temporal con nombre "PauloQA" y apellido "ProyectoFinal"
    When consulto bookings filtrando por firstname "PauloQA"
    Then verifico que el status code sea 200
    And verifico que el booking creado aparezca en los resultados


  Scenario: Filtrar bookings por lastname
    Given creo una reserva temporal con nombre "AutomationQA" y apellido "SevillaFinal"
    When consulto bookings filtrando por lastname "SevillaFinal"
    Then verifico que el status code sea 200
    And verifico que el booking creado aparezca en los resultados


  Scenario: Autenticacion con credenciales invalidas
    When realizo un POST al auth con credenciales invalidas
    Then verifico que el status code sea 200
    And verifico que el campo reason sea "Bad credentials"


  Scenario: Verificar disponibilidad de la API con ping
    When realizo un GET al endpoint ping
    Then verifico que el status code sea 201
    And verifico que el tiempo de respuesta sea menor a 5000 milisegundos