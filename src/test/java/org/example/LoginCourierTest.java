package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.RandomData.randomString;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    public String login = randomString(9);;
    public String password = randomString(12);;
    public String firstName = randomString(14);;
    LoginCourier loginCourier = new LoginCourier (login, password);
    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.BASE_URL;
    }
    @Before
    public void createNewCourier(){
        CreateCourier courier = new CreateCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post(Constant.CREATE_COURIER_API);
    }
    @After
    public void cleanAfterTest() {

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
    @DisplayName("Check login courier in system and receive status code 200 and body response not null")
    public void loginCourierInSystem() {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("id", notNullValue());


    }
    @Test
    @DisplayName("Check login courier in system and return id not null")
    public void loginCourierInSystemAndReturnId() {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("id", notNullValue());

    }

 }

