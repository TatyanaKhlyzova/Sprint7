package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.BASE_URL;
    }
    @Test
    @DisplayName("Check receive list of orders")
    public void getListOfOrders() {

        Response response = given()
                .get(Constant.LIST_ORDERS_API);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("orders", notNullValue());

    }

}
