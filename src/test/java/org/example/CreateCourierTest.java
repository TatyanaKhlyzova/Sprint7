package org.example;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.RandomData.randomString;
import static org.hamcrest.CoreMatchers.equalTo;


public class CreateCourierTest {
    public String login = randomString(10);
    public String password = randomString(15);
    public String firstName = randomString(12);
    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.BASE_URL;
    }
    @After
    public void cleanAfterTest() {
        LoginCourier loginCourier = new LoginCourier (login, password);
        CourierId idCourier = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API)
                .body().as(CourierId.class);
        given()
                .delete("/api/v1/courier/{:id}", idCourier.getId());
    }

    @Test
    @DisplayName("Check status code when create new courier")
    @Description("Check and receive status code 201")
    public void createNewCourierStatusCode() {
        CreateCourier courier = new CreateCourier(login, password, firstName);
            given()
                 .header("Content-type", "application/json")
                 .and()
                 .body(courier)
                 .when()
                 .post(Constant.CREATE_COURIER_API)
                 .then().statusCode(201);
    }
    @Test
    @DisplayName("Check body response when create new courier")
    @Description("Check and receive body response - ок")
    public void createNewCourierBodyResponse() {
        CreateCourier courier = new CreateCourier(login, password, firstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constant.CREATE_COURIER_API);
    response.then().assertThat().body("ok", equalTo(true));
    }

}


