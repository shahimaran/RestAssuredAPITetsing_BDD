package restAssuredTest.migrationAPI;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import restAssuredTest.migrationAPI.Utils.ExtentReportManager;
import restAssuredTest.migrationAPI.Utils.Utils;
import static org.hamcrest.Matchers.equalTo;

public class TestProductData extends BaseTest {
    private static final String GET_PRODUCT_REQUEST_BODY = "{ \"prod_id\": 0 }";
    private static final String GET_PRODUCT_VARIANT_REQUEST_BODY  = "{ \"variant_id\": 0 }";
    private static final String GET_MAIN_CATEGORY_REQUEST_BODY  = "{\"main_category_id\": 0}";
    private static final String GET_SUB_CATEGORY_REQUEST_BODY  = "{\"sub_category_id\": 0}";
    private static final String GET_BUSINESS_COMMODITY_REQUEST_BODY  = "{\"business_commodity_id\": 0}";
    private static final String GET_PRODUCT_IMAGE_REQUEST_BODY  = "{\"variant_id\": 0}";
    private static final String GET_PRODUCT_MULTI_CATEGORY_REQUEST_BODY  = "{\"product_id\": 0}";
    private static final String GET_PRODUCT_UOM_REQUEST_BODY  = "{\"uom_id\": 0}";





    @Test
    public void validateProductData() {

        logger.info("Starting test: Count the Number of the product in the payload");
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        if (response != null) {
            logger.info("Received response successfully.");
        } else {
            logger.error("Failed to receive response.");
        }
        Utils.validateProductCount(response,13275);

        response.then()
                .assertThat().body("result.status", equalTo("success"));
        logger.pass("Test validateProductData passed successfully.");

    }

    @Test
    public void validateResponseTime(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.checkResponseTime(response.time(), 500);

        response.then()
                .assertThat().body("result.status", equalTo("success"));
    }

    @Test
    public void duplicateProductData(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.checkForDuplicateEntries(response);

        response.then()
                .assertThat().body("result.status", equalTo("success"));
    }

    @Test
    public void getProductDetails(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.printProductDetails(response,6141);
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }
    @Test
    public void validProductVariantData(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_VARIANT_DATA_ENDPOINT,GET_PRODUCT_VARIANT_REQUEST_BODY);
        Utils.validateProductCount(response,15078);
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

    @Test
    public void getMainCategoryData(){
        Response response = Utils.sendPostRequest(Config.GET_MAIN_CATEGORY_DATA_ENDPOINT,GET_MAIN_CATEGORY_REQUEST_BODY);
        Utils.checkResponseTime(200,200);
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }
    @Test
    public void checkDuplicateMainCategory(){
        Response response = Utils.sendPostRequest(Config.GET_MAIN_CATEGORY_DATA_ENDPOINT,GET_MAIN_CATEGORY_REQUEST_BODY);
        Utils.checkForDuplicates(response,"id","result.data");
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }

    @Test
    public void countMainCategory(){
        Response response = Utils.sendPostRequest(Config.GET_MAIN_CATEGORY_DATA_ENDPOINT,GET_MAIN_CATEGORY_REQUEST_BODY);
        Utils.validateProductCount(response,73);

    }
    @Test
    public void mainCategoryDetails(){
        Response response = Utils.sendPostRequest(Config.GET_MAIN_CATEGORY_DATA_ENDPOINT,GET_MAIN_CATEGORY_REQUEST_BODY);
        Utils.printMainCategoryDetails(response,18,"result.data");

    }
    @Test
    public void productDetailsWithLogs(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_DATA_ENDPOINT,GET_PRODUCT_REQUEST_BODY);
        Utils.productDetailsLogs(response,6160);
        response.then()
                .assertThat().body("result.status", equalTo("success"));


    }
    @Test
    public void countBusinessCommodityCategory(){
        Response response = Utils.sendPostRequest(Config.GET_BUSINESS_COMMODITY_DATA_ENDPOINT,GET_BUSINESS_COMMODITY_REQUEST_BODY);
        Utils.validateProductCount(response,73);

    }
    @Test
    public void countProductImageCategory(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_IMAGE_DATA_ENDPOINT,GET_PRODUCT_IMAGE_REQUEST_BODY);
        Utils.validateProductCount(response,73);

    }
    @Test
    public void countSubCategory(){
        Response response = Utils.sendPostRequest(Config.GET_SUB_CATEGORY_DATA_ENDPOINT,GET_SUB_CATEGORY_REQUEST_BODY);
        Utils.validateProductCount(response,73);

    }
    @Test
    public void countProductUOM(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_UOM_DATA_ENDPOINT,GET_PRODUCT_UOM_REQUEST_BODY);
        Utils.validateProductCount(response,73);

    }
    @Test
    public void countMultiCategoryImage(){
        Response response = Utils.sendPostRequest(Config.GET_PRODUCT_MULTI_CATEGORY_DATA_ENDPOINT,GET_PRODUCT_MULTI_CATEGORY_REQUEST_BODY);
        Utils.validateProductCount(response,73);

    }







}
