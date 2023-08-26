package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.RandomData.randomString;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginCourierNegativeTest {
    public String login = randomString(9);;
    public String password = randomString(12);;
    public String firstName = randomString(14);;
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

    @Test
    @DisplayName("Negative test - Check login courier in system without login")
    public void loginCourierWithoutLogin() {
        String login = "";
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API);
        response.then().assertThat()
                .statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Negative test - Check login courier in system without password")
    public void loginCourierWithoutPassword() {
        String password = "";
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API);
        response.then().assertThat()
                .statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Negative test - Check login courier in system with non-existent login")
    public void loginCourierWithNonExistentLogin() {
        String login = "dadadadQAdad";
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API);
        response.then().assertThat()
                .statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Negative test - Check login courier in system with non-existent password")
    public void loginCourierWithNonExistentPassword() {
        String password = "dadadadQAdadf";
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(Constant.LOGIN_COURIER_API);
        response.then().assertThat()
                .statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}
