package core;

import beans.YandexMapsAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static core.YandexMapsContacts.GEOCODE;
import static core.YandexMapsContacts.YANDEX_MAPS_API_URI;
import static org.hamcrest.Matchers.lessThan;

public class YandexMapsApi {

    private YandexMapsApi() {
    }

    private HashMap<String, String> params = new HashMap<>();

    public static class ApiBuilder {
        YandexMapsApi spellerApi;

        private ApiBuilder(YandexMapsApi gcApi) {
            spellerApi = gcApi;
        }

        public ApiBuilder geocode(String geocode) {
            spellerApi.params.put(GEOCODE, geocode);
            return this;
        }

        public Response callApi() {
            return RestAssured.with()
                    .queryParams(spellerApi.params)
                    .log().all()
                    .get(YANDEX_MAPS_API_URI).prettyPeek();
        }
    }

    public static ApiBuilder with() {
        YandexMapsApi api = new YandexMapsApi();
        return new ApiBuilder(api);
    }

    public static List<YandexMapsAnswer> getYandexMapsAnswers(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<YandexMapsAnswer>>() {
        }.getType());
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(YANDEX_MAPS_API_URI)
                .build();
    }
}

