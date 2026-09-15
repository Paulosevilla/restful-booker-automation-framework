package stepDefinitions;

import constants.RestfulBookerEndpoints;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import util.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSteps {

    private Response response;
    private int bookingIdCreado;


    @When("realizo un GET al endpoint de bookings")
    public void obtenerBookings() {

        response = Request.get(
                RestfulBookerEndpoints.BOOKING_ENDPOINT
        );
    }


    @Then("verifico que el status code sea {int}")
    public void verificarStatusCode(int statusEsperado) {

        Assertions.assertEquals(
                statusEsperado,
                response.getStatusCode(),
                "El status code no coincide"
        );
    }


    @And("verifico que la respuesta sea una lista de booking IDs")
    public void verificarListaBookings() {

        List<Integer> bookingIds =
                response.jsonPath()
                        .getList("bookingid");

        Assertions.assertNotNull(
                bookingIds,
                "La lista de bookings no debe ser null"
        );
    }


    @And("verifico que el header Content-Type contenga {string}")
    public void verificarContentType(String contenidoEsperado) {

        String contentType =
                response.getHeader("Content-Type");

        Assertions.assertNotNull(
                contentType,
                "El header Content-Type no existe"
        );

        Assertions.assertTrue(
                contentType.contains(contenidoEsperado),
                "Content-Type inesperado: " + contentType
        );
    }


    @And("verifico que el tiempo de respuesta sea menor a {long} milisegundos")
    public void verificarTiempoRespuesta(long tiempoMaximo) {

        long tiempoReal =
                response.getTime();

        Assertions.assertTrue(
                tiempoReal < tiempoMaximo,
                "La respuesta tardó " +
                        tiempoReal +
                        " ms"
        );
    }


    @Given("creo una reserva temporal con nombre {string} y apellido {string}")
    public void crearReservaTemporal(
            String nombre,
            String apellido) {

        String payload = """
                {
                  "firstname": "%s",
                  "lastname": "%s",
                  "totalprice": 1500,
                  "depositpaid": true,
                  "bookingdates": {
                    "checkin": "2026-10-01",
                    "checkout": "2026-10-10"
                  },
                  "additionalneeds": "Breakfast"
                }
                """.formatted(
                nombre,
                apellido
        );

        Response respuestaCreacion =
                Request.post(
                        RestfulBookerEndpoints.BOOKING_ENDPOINT,
                        payload
                );

        Assertions.assertEquals(
                200,
                respuestaCreacion.getStatusCode(),
                "No se pudo crear la reserva temporal"
        );

        bookingIdCreado =
                respuestaCreacion
                        .jsonPath()
                        .getInt("bookingid");

        Assertions.assertTrue(
                bookingIdCreado > 0,
                "No se obtuvo un booking ID válido"
        );
    }


    @When("consulto bookings filtrando por firstname {string}")
    public void filtrarPorFirstname(String firstname) {

        Map<String, String> parametros =
                new HashMap<>();

        parametros.put(
                "firstname",
                firstname
        );

        response =
                Request.getWithParams(
                        RestfulBookerEndpoints.BOOKING_ENDPOINT,
                        parametros
                );
    }


    @When("consulto bookings filtrando por lastname {string}")
    public void filtrarPorLastname(String lastname) {

        Map<String, String> parametros =
                new HashMap<>();

        parametros.put(
                "lastname",
                lastname
        );

        response =
                Request.getWithParams(
                        RestfulBookerEndpoints.BOOKING_ENDPOINT,
                        parametros
                );
    }


    @And("verifico que el booking creado aparezca en los resultados")
    public void verificarBookingCreado() {

        List<Integer> bookingIds =
                response.jsonPath()
                        .getList("bookingid");

        Assertions.assertNotNull(
                bookingIds,
                "La respuesta no contiene una lista"
        );

        Assertions.assertTrue(
                bookingIds.contains(
                        bookingIdCreado
                ),
                "El booking " +
                        bookingIdCreado +
                        " no apareció en los resultados"
        );
    }


    @When("realizo un POST al auth con credenciales invalidas")
    public void autenticacionInvalida() {

        String payload = """
                {
                  "username": "admin",
                  "password": "password_incorrecto"
                }
                """;

        response =
                Request.post(
                        RestfulBookerEndpoints.AUTH_ENDPOINT,
                        payload
                );
    }


    @And("verifico que el campo reason sea {string}")
    public void verificarReason(
            String reasonEsperado) {

        String reasonReal =
                response.jsonPath()
                        .getString("reason");

        Assertions.assertEquals(
                reasonEsperado,
                reasonReal
        );
    }


    @When("realizo un GET al endpoint ping")
    public void verificarPing() {

        response =
                Request.get(
                        RestfulBookerEndpoints.PING_ENDPOINT
                );
    }
}
