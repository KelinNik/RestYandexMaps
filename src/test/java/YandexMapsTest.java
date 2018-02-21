import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;

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
                        Matchers.stringContainsInOrder(Arrays.asList("Россия"))))
                // Matchers.containsString("\"code\":1")))
                //  .contentType(ContentType.JSON)
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

    }
}
