package restAssuredTest.migrationAPI;


import org.testng.annotations.Test;
import io.restassured.response.Response;


import javax.rmi.CORBA.Util;

import static org.hamcrest.Matchers.equalTo;

public class TestProductData extends BaseTest {
    private static final String GET_PRODUCT_REQUEST_BODY = "{ \"prod_id\": 0 }";
    private static final String GET_PRODUCT_VARIANT_REQUEST_BODY  = "{ \"variant_id\": 0 }";

    @Test
    public void validateProductData() {

        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.validateProductCount(response,13210);

        response.then()
                .assertThat().body("result.status", equalTo("success"));

    }
    @Test
    public void validateResponseTime(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.checkResponseTime(response.time(), 500);

        response.then()
                .assertThat().body("result.status", equalTo("success"));
    }

    @Test
    public void duplicateData(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.checkForDuplicateEntries(response);

        response.then()
                .assertThat().body("result.status", equalTo("success"));
    }

    @Test
    public void getProductDetails(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.printProductDetails(response,1500);
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }
    @Test
    public void validProductVariantData(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT,GET_PRODUCT_VARIANT_REQUEST_BODY);
        Utils.validateProductCount(response,25215);
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }
    @Test
    public void validateResponseTimeProductVariant(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT,GET_PRODUCT_VARIANT_REQUEST_BODY);
        Utils.checkResponseTime(response.time(), 500);

        response.then()
                .assertThat().body("result.status", equalTo("success"));
    }

//    @Test
//    public void duplicateDataProductVariant2() {
//        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT, GET_PRODUCT_VARIANT_REQUEST_BODY);
//        //  Utils.checkForDuplicates(response, "id", "result.data");
//        // Check for duplicates for different fields
//        String[] fieldsToCheck = {"id", "product_number", "barcode"};
//        for (String field : fieldsToCheck) {
//            // Check for duplicates while ignoring empty barcode values
//            Utils.checkForDuplicates(response, field, "result.data");
//
//            response.then()
//                    .assertThat().body("result.status", equalTo("success"));
//        }
//
//
//    }

    @Test
    public void countDuplicateId(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT, GET_PRODUCT_VARIANT_REQUEST_BODY);
        Utils.checkForDuplicates(response, "id", "result.data");
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }
    @Test(dependsOnMethods = {"countDuplicateId"})
    public void countDuplicateProductNo(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT, GET_PRODUCT_VARIANT_REQUEST_BODY);
        Utils.checkForDuplicates(response, "product_number", "result.data");
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }

    @Test(dependsOnMethods = {"countDuplicateProductNo"})
    public void countEmptyBarcode(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT, GET_PRODUCT_VARIANT_REQUEST_BODY);

        Utils.countBarcodeFalseAndEmpty(response,"result.data");
        response.then()
                .assertThat().body("result.status", equalTo("success"));

    }

    @Test
    public void getProductVariantDetails(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT,GET_PRODUCT_VARIANT_REQUEST_BODY);
        Utils.printProductVariantDetails(response,15077,"result.data");
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }




}
