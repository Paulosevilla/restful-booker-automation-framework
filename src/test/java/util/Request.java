package util;

import constants.RestfulBookerEndpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class Request {

    private static void configurarBaseUrl() {
        RestAssured.baseURI =
                RestfulBookerEndpoints.BASE_URL;
    }

    // GET normal
    public static Response get(String endpoint) {

        configurarBaseUrl();

        return RestAssured
                .given()
                .when()
                .get(endpoint);
    }

    // GET con parámetros/filtros
    public static Response getWithParams(
            String endpoint,
            Map<String, String> parametros) {

        configurarBaseUrl();

        return RestAssured
                .given()
                .queryParams(parametros)
                .when()
                .get(endpoint);
    }

    // GET por ID
    public static Response getById(
            String endpoint,
            String id) {

        configurarBaseUrl();

        return RestAssured
                .given()
                .pathParam("id", id)
                .when()
                .get(endpoint);
    }

    // POST
    public static Response post(
            String endpoint,
            String payload) {

        configurarBaseUrl();

        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(payload)
                .when()
                .post(endpoint);
    }

    // PUT - actualización completa
    public static Response put(
            String endpoint,
            String id,
            String payload,
            String token) {

        configurarBaseUrl();

        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .cookie("token", token)
                .pathParam("id", id)
                .body(payload)
                .when()
                .put(endpoint);
    }

    // PATCH - actualización parcial
    public static Response patch(
            String endpoint,
            String id,
            String payload,
            String token) {

        configurarBaseUrl();

        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .cookie("token", token)
                .pathParam("id", id)
                .body(payload)
                .when()
                .patch(endpoint);
    }
}