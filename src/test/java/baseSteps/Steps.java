package baseSteps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Steps {
    public void paramSendBody()throws IOException{
        JSONObject body=new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/Json/test.json"))));

        System.out.println(body.toString());//выводим то, что считали из файла .json

        body.put("name","Tomato");
        body.put("job","Eat market");

        System.out.println(body.toString());//выводим изменения

        RequestSpecification request = given();
        Response postJson=request
                .header("Content-type","application/json")
                .baseUri("https://reqres.in/api")
                .body(body.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(201)//код ответа об успешном создании ресурса
                .extract()
                .response();

        System.out.println(postJson.statusCode());//выводим статус код

        System.out.println(postJson.asString());//выводим ответ от сервера

        //Сравнениваем исходные значения с полученными в ответе с сервера
        Assertions.assertEquals((new JSONObject(postJson.getBody().asString()).get("name")),(body.get("name")),"Не соответствует");
        Assertions.assertEquals((new JSONObject(postJson.getBody().asString()).get("job")),(body.get("job")),"Не соответствует");
    }
}
