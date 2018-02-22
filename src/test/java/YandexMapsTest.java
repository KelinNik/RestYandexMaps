import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

import static core.YandexMapsContacts.*;
import static org.hamcrest.Matchers.lessThan;

public class YandexMapsTest {

    @Test
    public void simpleSpellerApiCall() {
        RestAssured
                .given()
                .queryParam(GEOCODE, PLACE)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .get(YANDEX_MAPS_API_URI)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(Matchers.allOf(
                        Matchers.containsString("Россия")))
                .time(lessThan(20000L)); // Milliseconds
    }


    @Test
    public void spellerApiCallsWithDifferentMethods() {
        //GET
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log().everything()
                .get(YANDEX_MAPS_API_URI)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //POST
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log().everything()
                .post(YANDEX_MAPS_API_URI)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //HEAD
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log().everything()
                .head(YANDEX_MAPS_API_URI)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //OPTIONS
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log().everything()
                .options(YANDEX_MAPS_API_URI)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //PUT
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log().everything()
                .put(YANDEX_MAPS_API_URI)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //PATCH
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log()
                .everything()
                .patch(YANDEX_MAPS_API_URI)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //DELETE
        RestAssured
                .given()
                .param(GEOCODE, PLACE)
                .log()
                .everything()
                .delete(YANDEX_MAPS_API_URI).prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .statusLine("HTTP/1.1 405 Not Allowed");
    }
}

