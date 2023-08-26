package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        List<String> colorGrey = new ArrayList<>();
        colorGrey.add("GREY");
        List<String> colorBlack = new ArrayList<>();
        colorBlack.add("BLACK");
        List<String> colorGreyAndBlack = new ArrayList<>();
        colorGreyAndBlack.add("GREY");
        colorGreyAndBlack.add("BLACK");
        List<String> withourColor = new ArrayList<>();

        return new Object[][] {
                { "Kirill", "Popov", "Lenina, 145 apt.", "4", "+7 876 355 35 35", 5, "2024-06-06", "I want it", colorGrey},
                { "Varvara", "Bob", "Professor Popov, 12 apt.", "7", "+7 800 359 35 35", 4, "2024-08-12", "No comments", colorBlack},
                { "Mat", "Volkov", "Konoha, 1 apt.", "9", "+7 800 355 35 99", 6, "2023-10-06", "Don't be late", colorGreyAndBlack},
                { "Oleg", "Petrov", "Lenina, 11 apt.", "3", "+7 456 355 77 99", 8, "2023-10-25", "", withourColor},
        };
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.BASE_URL;
    }

    @Test
    @DisplayName("Check create new order with different color (grey;black)")
    @Description("Use different options: gray color; black color; both colors; no color indication")
    public void createNewOrder() {
        CreateOrderTest order = new CreateOrderTest(firstName,  lastName,  address,  metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(Constant.CREATE_ORDER_API);
        response.then().assertThat()
                .statusCode(201);
        response.then().assertThat().body("track", notNullValue());
    }

}
