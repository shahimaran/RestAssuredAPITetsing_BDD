package restAssuredTest.migrationAPI;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sun.javafx.collections.MappingChange;
import com.sun.xml.xsom.impl.scd.Iterators;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class Product extends BaseTest {

    private String productUrl = "https://partners.myginne.com/Mig/GetProductData";
    private String productRequestBody = "{\n" +
            "    \"prod_id\": 0\n" +
            "}";

    @Test
    public void getProductData() {

        given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post(productUrl)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .assertThat().body("result.status", equalTo("success"))// Asserts that the status field is "success"
                .assertThat().body("result.data.size", equalTo(1000))
                .log()
                .all();
    }

    @Test
    public void getProductCount() {

        // Send POST request and capture the response
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post(productUrl)
                .then()
                .statusCode(200) // Assert that the status code is 200
                .statusLine("HTTP/1.1 200 OK") // Assert that the status line is correct
                .extract().response(); // Extract the response

        // Print the number of elements in the data array
        int numberOfElements = response.jsonPath().getList("result.data").size();
        System.out.println("Number of elements in the data array: " + numberOfElements);

        // Validate the response
        response.then()
                .body("result.status", equalTo("success")) // Assert that the status field is "success"
                .body("result.data.size()", equalTo(20)); // Assert that the size of the data array is 20
    }

    @Test
    public void getProductNumber() {     // Send POST request and capture the response
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post(productUrl)
                .then()
                .statusCode(200) // Assert that the status code is 200
                .statusLine("HTTP/1.1 200 OK") // Assert that the status line is correct
                .extract().response(); // Extract the response


        // Print the number of elements in the data array
        int numberOfElements = response.jsonPath().getList("result.data").size();

        System.out.println("Number of elements in the data array: " + numberOfElements);

        int dataCount = 13204;  //Number of records in the array list
        //Check if the no. of product is not equals to the dataCount
        if (numberOfElements != dataCount) {
            throw new RuntimeException("Actual number of elements in the data array: " + numberOfElements + " Expected number of elements in the data array: " + dataCount);
        }


        // Validate the response
        response.then()
                .assertThat().body("result.status", equalTo("success")) // Assert that the status field is "success"
                .assertThat().body("result.data.size()", equalTo(dataCount)); // Assert that the size of the data array is 20

    }

    @Test
    public void getProductResponseTime() {     // Send POST request and capture the response
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post(productUrl)
                .then()
                .statusCode(200) // Assert that the status code is 200
                .statusLine("HTTP/1.1 200 OK") // Assert that the status line is correct
                .extract().response(); // Extract the response

        //Measure the response time
        long responseTime = response.getTime();

        // Print the number of elements in the data array
        int numberOfElements = response.jsonPath().getList("result.data").size();

        System.out.println("Number of elements in the data array: " + numberOfElements);
        System.out.println("Measured response time in milliseconds: " + responseTime);

        // Validate the response time
        long maxResponseTimeInMillis = 5000; // 5 seconds in milliseconds

        int dataCount = 13204;  //Number of records in the array list
        //Check if the no. of product is not equals to the dataCount
        if (numberOfElements != dataCount) {
            throw new RuntimeException("Actual number of elements in the data array: " + numberOfElements + " Expected number of elements in the data array: " + dataCount);
        }

        // Check if the response time exceeds the maximum allowed
        if (responseTime > maxResponseTimeInMillis) {
            // Instead of throwing AssertionError, directly throw a RuntimeException
            throw new RuntimeException("Response time exceeded the maximum limit of 5 seconds. Measured time: " + responseTime + " milliseconds.");
        }

        // Validate the response
        response.then()
                .assertThat().body("result.status", equalTo("success")) // Assert that the status field is "success"
                .assertThat().body("result.data.size()", equalTo(dataCount)); // Assert that the size of the data array is 20

    }

    @Test
    public void getDuplicateEntry() {     // Send POST request and capture the response
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post(productUrl)
                .then()
                .statusCode(200) // Assert that the status code is 200
                .statusLine("HTTP/1.1 200 OK") // Assert that the status line is correct
                .extract().response(); // Extract the response

        //Measure the response time
        long responseTime = response.getTime();

        // Print the number of elements in the data array
        int numberOfElements = response.jsonPath().getList("result.data").size();

        System.out.println("Number of elements in the data array: " + numberOfElements);
        System.out.println("Measured response time in milliseconds: " + responseTime);

        //Extract the data array from the response
        List<Map<String, Object>> data = response.jsonPath().getList("result.data");

        // Sets to track duplicates
        Set<String> uniqueNames = new HashSet<>();
        Set<Integer> uniqueIds = new HashSet<>();

        // Iterate over the data to check for duplicates
        for (Map<String, Object> item : data) {
            String name = (String) item.get("name");
            int Id = (int) item.get("id");

            // Check for duplicate names
            if (!uniqueNames.add(name)) {
                throw new RuntimeException("Duplicate entry found for name: " + name);
            }

            // Check for duplicate IDs
            if (!uniqueIds.add(Id)) {
                throw new RuntimeException("Duplicate entry found for ID: " + Id);
            }

            // Validate the response
            response.then()
                    .assertThat().body("result.status", equalTo("success")) // Assert that the status field is "success"
                    .assertThat().body("result.data.size()", equalTo(13204)); // Assert that the size of the data array is 20

        }

    }

    @Test
    public void verifyProduct() {     // Send POST request and capture the response
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post(productUrl)
                .then()
                .statusCode(200) // Assert that the status code is 200
                .statusLine("HTTP/1.1 200 OK") // Assert that the status line is correct
                .extract().response(); // Extract the response

        //Measure the response time
        long responseTime = response.getTime();
        // Print the number of elements in the data array
        int numberOfElements = response.jsonPath().getList("result.data").size();

        System.out.println("Number of elements in the data array: " + numberOfElements);
        System.out.println("Measured response time in milliseconds: " + responseTime);

        //Extract the data array from the response
        List<Map<String, Object>> data = response.jsonPath().getList("result.data");

        // Specify the product id to validate
        int productIdToValidate = 6142;
        String expectedName = "7 Stars Rolling Cigarette Paper";
        int expectedBrandId = 830;

        // Flag to check if the product with the given ID is found
        boolean productFound = false;

        // Iterate over the data to find and validate the specific product
        for (Map<String, Object> item : data) {
            Integer id = (Integer) item.get("id");
            String name = (String) item.get("name");
            Integer brandId = (Integer) item.get("brand_id");

            if (id.equals(productIdToValidate)) {
                productFound = true;
                // Validate the name and brand_id
                if (!name.equals(expectedName)) {
                    throw new RuntimeException("For product ID " + productIdToValidate + ", expected name: " + expectedName + ", but found: " + name);
                }
                if (!brandId.equals(expectedBrandId)) {
                    throw new RuntimeException("For product ID " + productIdToValidate + ", expected brand ID: " + expectedBrandId + ", but found: " + brandId);
                }
                // Exit the loop once the product is found and validated
                break;
            }
        }
        if (!productFound) {
            throw new RuntimeException("Product with ID " + productIdToValidate + " not found in the response.");
        }
        // Validate the response
        response.then()
                .assertThat().body("result.status", equalTo("success")); // Assert that the status field is "success"

    }


    @Test
    public void validateProductProperties() {

        // Send POST request and capture the response
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequestBody)
                .when()
                .post("/GetProductData")
                .then()
                .statusCode(200) // Assert that the status code is 200
                .statusLine("HTTP/1.1 200 OK") // Assert that the status line is correct
                .extract().response(); // Extract the response
        

        // Print product details for a specific product ID
        int productIdToValidate = 6142;
        Utils.printProductDetails(response, productIdToValidate);


        response.then()
                .body("result.status", equalTo("success")); // Assert that the status field is "success"
    }


}