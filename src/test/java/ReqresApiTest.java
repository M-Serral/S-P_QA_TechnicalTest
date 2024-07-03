import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetListUsers() {
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()))
                .extract()
                .response();

        // Print the response
        String responseString = response.asString();
        System.out.println("Response for GET /users?page=2: " + responseString);

        // Extract the object of "id=11"
        int userId = response.jsonPath().getInt("data.find { it.id == 11 }.id");
        System.out.println("Extracted User ID: " + userId);
    }

    @Test
    public void testGetSingleUser() {
        Response response = given()
                .pathParam("id", 11)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(11))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .extract()
                .response();

        // Print the response
        String responseString = response.asString();
        System.out.println("Response for GET /users/11: " + responseString);
    }

    @Test
    public void testPostCreateUser() {
        String requestBody = "{\"name\": \"Peter\", \"job\": \"Sales\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Peter"))
                .body("job", equalTo("Sales"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .extract()
                .response();

        // Print the response
        String responseString = response.asString();
        System.out.println("Response for POST /users: " + responseString);
    }

    @Test
    public void testPostRegisterUnsuccessful() {
        String requestBody = "{\"email\": \"sydney@fife\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"))
                .extract()
                .response();

        // Print the response
        String responseString = response.asString();
        System.out.println("Response for POST /register: " + responseString);
    }

    @Test
    public void testInteractWithMultipleEndpoints() {
        // First request to get user list and extract user with id=11
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Print the response
        String responseString = response.asString();
        System.out.println("Response for GET /users?page=2: " + responseString);

        int userId = response.jsonPath().getInt("data.find { it.id == 11 }.id");
        String email = response.jsonPath().getString("data.find { it.id == 11 }.email");
        String firstName = response.jsonPath().getString("data.find { it.id == 11 }.first_name");
        String lastName = response.jsonPath().getString("data.find { it.id == 11 }.last_name");

        // Second request to get single user
        Response singleUserResponse = given()
                .pathParam("id", 11)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Print the response
        String singleUserResponseString = singleUserResponse.asString();
        System.out.println("Response for GET /users/11: " + singleUserResponseString);

        // Validate the data
        singleUserResponse.then()
                .body("data.id", equalTo(userId))
                .body("data.email", equalTo(email))
                .body("data.first_name", equalTo(firstName))
                .body("data.last_name", equalTo(lastName));
    }
}
