package io.petstore.tests;

import io.petstore.testData.PetsPOJO;
import io.petstore.utils.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class pet {

    private static int existingPetId;

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = ConfigurationReader.get("baseURL");

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test(priority = 1)
    public void addANewPet(){
        String endpoint = "/pet";

        PetsPOJO data = new PetsPOJO();
        existingPetId=data.getId();

        given()
                .body(data)
                .when()
                .post(endpoint)
                .then()
                .statusCode(200)
                .body("id", equalTo(data.getId()))
                .body("name", equalTo(data.getName()));

    }

    @Test(priority = 2)
    public void findPetById(){
        String endpoint = "/pet/{petId}";

        InputStream petJsonSchema=getClass().getClassLoader().getResourceAsStream("petJsonSchema.json");
        assert petJsonSchema != null;

        if(existingPetId==0){
            addANewPet();
        }

        given()
                .pathParam("petId",existingPetId)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchema(petJsonSchema))
                .body("id", equalTo(existingPetId));

    }

    @Test(priority = 3)
    public void updateAnExistingPet(){
        String endpoint = "/pet";

        PetsPOJO data = new PetsPOJO();

        if(existingPetId==0){
            addANewPet();
        }

        data.setId(existingPetId);

        given()
                .body(data)
                .when()
                .put(endpoint)
                .then()
                .statusCode(200)
                .body("id", equalTo(existingPetId))
                .body("name", equalTo(data.getName()))
                .time(lessThan(2000L));
    }

    @Test(priority = 4)
    public void deleteAPet(){
        String endpoint = "/pet/{petId}";

        if(existingPetId==0){
            addANewPet();
        }

        given()
                .header("api_key",ConfigurationReader.get("api_key"))
                .pathParam("petId",existingPetId)
                .when()
                .delete(endpoint)
                .then()
                .statusCode(200);
    }

    @Test(priority = 5)
    public void deleteTheDeletedPet(){
        String endpoint = "/pet/{petId}";

        if(existingPetId==0){
            deleteAPet();
        }

        given()
                .header("api_key", ConfigurationReader.get("api_key"))
                .pathParam("petId",existingPetId)
                .when()
                .delete(endpoint)
                .then()
                .statusCode(404);

    }

    @Test(priority = 6)
    public void addANewPetWithoutMandatoryField(){
        PetsPOJO invalidData = new PetsPOJO();

        //name is mandatory field, it assigned to empty
        invalidData.setName("");

        String endpoint = "/pet";

        given()
                .body(invalidData)
                .when()
                .post(endpoint)
                .then()
                .statusCode(400);

    }
}
