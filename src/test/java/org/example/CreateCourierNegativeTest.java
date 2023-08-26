package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.RandomData.randomString;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierNegativeTest {
    public String login = randomString(8);;
    public String password = randomString(13);;
    public String firstName = randomString(10);;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.BASE_URL;
    }
    @Test
    @DisplayName("Negative test - Check create new courier without login")
    public void createCourierWithoutLogin() {
        String login = "";
        CreateCourier courier = new CreateCourier(login, password, firstName);
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constant.CREATE_COURIER_API);
        response.then().assertThat()
                .statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Negative test - Check create new courier without password")
    public void createCourierWithoutPassword() {
        String password = "";
        CreateCourier courier = new CreateCourier(login, password, firstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constant.CREATE_COURIER_API);
        response.then().assertThat()
                .statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    // Тест ниже упадет в ошибку, поскольку в документации указан другой текст сообщения в теле ответа
    @Test
    @DisplayName("Negative test - Check create double courier")
    public void createDoubleCourier() {
        CreateCourier courier = new CreateCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constant.CREATE_COURIER_API);
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constant.CREATE_COURIER_API);
        response.then().assertThat()
                .statusCode(409);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется"));
    }
}
