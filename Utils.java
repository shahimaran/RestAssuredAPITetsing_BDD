package restAssuredTest.migrationAPI;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;


public class Utils {

    /************************* Print the Product Details *************************************/
    public static void printProductDetails(Response response, int productIdToValidate) {
        List<Map<String, Object>> data = response.jsonPath().getList("result.data");
        boolean productFound = false;

        for (Map<String, Object> item : data) {
            Integer id = (Integer) item.get("id");
            if (id.equals(productIdToValidate)) {
                productFound = true;
                System.out.println("Product details for ID " + productIdToValidate + ":");
                System.out.println("ID: " + item.get("id"));
                System.out.println("Product Number: " + item.get("product_number"));
                System.out.println("Name: " + item.get("name"));
                System.out.println("Arabic Name: " + item.get("name_ar"));
                System.out.println("Description: " + item.get("desc"));
                System.out.println("Arabic Description: " + item.get("desc_ar"));
                System.out.println("Brand ID: " + item.get("brand_id"));
                System.out.println("Product Type ID: " + item.get("product_type_id"));
                System.out.println("SKU ID: " + item.get("sku_id"));
                System.out.println("Sell Online: " + item.get("sell_online"));
                System.out.println("Sell POS: " + item.get("sell_pos"));
                System.out.println("Status ID: " + item.get("status_id"));
                System.out.println("Is Approved: " + item.get("is_approved"));
                System.out.println("UOM ID: " + item.get("uom_id"));
                break;
            }
        }

        if (!productFound) {
            throw new RuntimeException("Product with ID " + productIdToValidate + " not found in the response.");
        }
    }

    public static void printProductVariantDetails(Response response, int productId,String arrayResponse) {
        // Extract the data array from the response
        List<Map<String, Object>> data = response.jsonPath().getList(arrayResponse);

        // Find and print the details for the specific product ID
        boolean productFound = false;
        for (Map<String, Object> item : data) {
            Integer id = (Integer) item.get("id");
            if (id != null && id == productId) {
                System.out.println("Product details for ID " + productId + ":");
                System.out.println("ID: " + item.get("id"));
                System.out.println("Product ID: " + item.get("product_id"));
                System.out.println("Name: " + item.get("name"));
                System.out.println("Name (AR): " + item.get("name_ar"));
                System.out.println("Description: " + item.get("desc"));
                System.out.println("Description (AR): " + item.get("desc_ar"));
                System.out.println("Barcode: " + item.get("barcode"));
                System.out.println("Alternate Barcode: " + item.get("alternate_barcode"));
                System.out.println("Sell Online: " + item.get("sell_online"));
                System.out.println("Sell POS: " + item.get("sell_pos"));
                System.out.println("UOM ID: " + item.get("uom_id"));
                System.out.println("-------------------------------");
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            System.out.println("No product found with ID " + productId);
        }
    }
    public static void printMainCategoryDetails(Response response, int productId,String arrayResponse) {
        // Extract the data array from the response
        List<Map<String, Object>> data = response.jsonPath().getList(arrayResponse);

        // Find and print the details for the specific product ID
        boolean productFound = false;
        for (Map<String, Object> item : data) {
            Integer id = (Integer) item.get("id");
            if (id != null && id == productId) {
                System.out.println("Product details for ID " + productId + ":");
                System.out.println("ID: " + item.get("id"));
                System.out.println("Name: " + item.get("name"));
                System.out.println("Name (AR): " + item.get("name_ar"));
                System.out.println("Description: " + item.get("description"));
                System.out.println("Description (AR): " + item.get("description_ar"));
                System.out.println("Image URL: " + item.get("image_url"));
                System.out.println("Business Commodity: " + item.get("business_commodity_id"));
                System.out.println("Sell Online: " + item.get("sell_online"));
                System.out.println("Sell POS: " + item.get("sell_pos"));
                System.out.println("UOM ID: " + item.get("uom_id"));
                System.out.println("-------------------------------");
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            System.out.println("No product found with ID " + productId);
        }
    }
    /*****************Response time exceeds the maximum allowed time **********************************
     *
     * @param responseTime The actual response time in milliseconds.
     * @param maxResponseTimeInMillis The maximum allowed response time in milliseconds.
     */
    public static void checkResponseTime(long responseTime, long maxResponseTimeInMillis) {
        System.out.println("Response time exceeded the maximum limit of " + maxResponseTimeInMillis + " milliseconds." +"\n"+ " Measured time: " + responseTime + " milliseconds.");
        if (responseTime > maxResponseTimeInMillis) {
            throw new RuntimeException("Response time exceeded the maximum limit of " + maxResponseTimeInMillis +  "\n"  +" milliseconds. Measured time: " + responseTime + " milliseconds.");
        }
    }

    /************** Validates the count of products in the API response *******************************/

    public static void validateProductCount(Response response, int expectedCount) {
        int numberOfElements = response.jsonPath().getList("result.data").size();
        System.out.println("Actual number of products: "+ numberOfElements);
        if (numberOfElements != expectedCount) {
            throw new RuntimeException("Actual number of elements in the data array: " + numberOfElements +"\n" +". Expected number of elements in the data array: " + expectedCount + ".");
        }
    }
    /**************** Validates duplicate entry in the API response *************************************/
    public static void checkForDuplicateEntries(Response response) {
    List<Map<String, Object>> data = response.jsonPath().getList("result.data");

    // Maps to track duplicate counts and their full details
    Map<Integer, Integer> idCountMap = new HashMap<>();
    Map<String, Integer> nameCountMap = new HashMap<>();
    Map<String, Integer> productNumberCountMap = new HashMap<>();

    Map<Integer, Map<String, Object>> idMap = new HashMap<>();
    Map<String, Map<String, Object>> nameMap = new HashMap<>();
    Map<String, Map<String, Object>> productNumberMap = new HashMap<>();

    // Iterate over the data to check for duplicates
        for (Map<String, Object> item : data) {
        Object idObject = item.get("id");
        Object productNumberObject = item.get("product_number");
        Object nameObject = item.get("name");

        Integer id = (idObject instanceof Integer) ? (Integer) idObject : null;
        String productNumber = (productNumberObject instanceof String) ? (String) productNumberObject : null;
        String name = (nameObject instanceof String) ? (String) nameObject : null;

        // Count duplicates for IDs
        if (id != null) {
            idCountMap.put(id, idCountMap.getOrDefault(id, 0) + 1);
            idMap.put(id, item);
        }

        // Count duplicates for names
        if (name != null) {
            nameCountMap.put(name, nameCountMap.getOrDefault(name, 0) + 1);
            nameMap.put(name, item);
        }

        // Count duplicates for product numbers
        if (productNumber != null) {
            productNumberCountMap.put(productNumber, productNumberCountMap.getOrDefault(productNumber, 0) + 1);
            productNumberMap.put(productNumber, item);
        }
    }

    // Report duplicate IDs
    StringBuilder sb = new StringBuilder();
        int totalDuplicateIdCount = 0;

        if (!idCountMap.isEmpty()) {
        sb.append("Duplicate IDs found:\n");
        for (Map.Entry<Integer, Integer> entry : idCountMap.entrySet()) {
            Integer id = entry.getKey();
            Integer count = entry.getValue();
            if (count > 1) {
                totalDuplicateIdCount += count;
                Map<String, Object> details = idMap.get(id);
                sb.append(String.format("ID: %d, Count: %d%nDetails: %s%n", id, count, details));
            }
        }
        sb.append("--------------------------------------------------------------\n");
    }

    // Report duplicate names
        if (!nameCountMap.isEmpty()) {
        sb.append("Duplicate names found:\n");
        for (Map.Entry<String, Integer> entry : nameCountMap.entrySet()) {
            String name = entry.getKey();
            Integer count = entry.getValue();
            if (count > 1) {
                totalDuplicateIdCount += count;
                Map<String, Object> details = nameMap.get(name);
                sb.append(String.format("Name: %s, Count: %d%nDetails: %s%n", name, count, details));
            }
        }
        sb.append("------------------------------------------------------\n");
    }

    // Report duplicate product numbers
        if (!productNumberCountMap.isEmpty()) {
        sb.append("Duplicate product numbers found:\n");
        for (Map.Entry<String, Integer> entry : productNumberCountMap.entrySet()) {
            String productNumber = entry.getKey();
            Integer count = entry.getValue();
            if (count > 1) {
                totalDuplicateIdCount += count;
                Map<String, Object> details = productNumberMap.get(productNumber);
                sb.append(String.format("Product Number: %s, Count: %d%nDetails: %s%n", productNumber, count, details));
            }
        }
        sb.append("-----------------------------------------------------------\n");
    }
        // Display total number of elements with duplicate IDs
        if (totalDuplicateIdCount > 0) {
            sb.append(String.format("Total number of elements with duplicate IDs: %d%n", totalDuplicateIdCount));
        }

        if (sb.length()>0) {
        throw new RuntimeException(sb.toString());
    }
}


    /***********   Sends a POST request to the given endpoint and returns the response. *********************
     *
     * @param endpoint The API endpoint to send the request to.
     * @param requestBody The request body to send in the POST request.
     * @return The response from the API.
     */
    public static Response sendPostRequest(String endpoint, String requestBody) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .extract().response();
    }
    /**
     * Checks for duplicates based on id, product_id, and barcode in the API response and counts duplicates.
     *
     * @param response The API response to check for duplicates.
     */
    public static void DuplicateEntriesProductVariant(Response response) {
        List<Map<String, Object>> data = response.jsonPath().getList("result.data");

        // Maps to track counts of ids, product_ids, and barcodes
        Map<Integer, Integer> idCountMap = new HashMap<>();
        Map<String, Integer> productIdCountMap = new HashMap<>();
        Map<String, Integer> barcodeCountMap = new HashMap<>();

        // Maps to store details for duplicates
        Map<Integer, Map<String, Object>> idMap = new HashMap<>();
        Map<String, Map<String, Object>> productIdMap = new HashMap<>();
        Map<String, Map<String, Object>> barcodeMap = new HashMap<>();

        // Iterate over the data to check for duplicates
        for (Map<String, Object> item : data) {
            Object idObject = item.get("id");
            Object productIdObject = item.get("product_id");
            Object barcodeObject = item.get("barcode");

            Integer id = (idObject instanceof Integer) ? (Integer) idObject : null;
            String productId = (productIdObject instanceof String) ? (String) productIdObject : null;
            String barcode = (barcodeObject instanceof String) ? (String) barcodeObject : null;

            // Count duplicates for IDs
            if (id != null) {
                idCountMap.put(id, idCountMap.getOrDefault(id, 0) + 1);
                idMap.put(id, item);
            }

            // Count duplicates for product_ids
            if (productId != null) {
                productIdCountMap.put(productId, productIdCountMap.getOrDefault(productId, 0) + 1);
                productIdMap.put(productId, item);
            }

            // Count duplicates for barcodes
            if (barcode != null) {
                barcodeCountMap.put(barcode, barcodeCountMap.getOrDefault(barcode, 0) + 1);
                barcodeMap.put(barcode, item);
            }
        }

        // Report and count duplicate IDs
        StringBuilder sb = new StringBuilder();
        int totalDuplicateIdCount = 0;
        int totalDuplicateProductIdCount = 0;
        int totalDuplicateBarcodeCount = 0;

        if (!idCountMap.isEmpty()) {
            sb.append("Duplicate IDs found:\n");
            for (Map.Entry<Integer, Integer> entry : idCountMap.entrySet()) {
                Integer id = entry.getKey();
                Integer count = entry.getValue();
                if (count > 1) {
                    totalDuplicateIdCount += count;
                    Map<String, Object> details = idMap.get(id);
                    sb.append(String.format("ID: %d, Count: %d%nDetails: %s%n", id, count, details));
                }
            }
            sb.append("-----------------------------\n");
        }

        // Report and count duplicate product_ids
        if (!productIdCountMap.isEmpty()) {
            sb.append("Duplicate product_ids found:\n");
            for (Map.Entry<String, Integer> entry : productIdCountMap.entrySet()) {
                String productId = entry.getKey();
                Integer count = entry.getValue();
                if (count > 1) {
                    totalDuplicateProductIdCount += count;
                    Map<String, Object> details = productIdMap.get(productId);
                    sb.append(String.format("Product ID: %s, Count: %d%nDetails: %s%n", productId, count, details));
                }
            }
            sb.append("-----------------------------\n");
        }

        // Report and count duplicate barcodes
        if (!barcodeCountMap.isEmpty()) {
            sb.append("Duplicate barcodes found:\n");
            for (Map.Entry<String, Integer> entry : barcodeCountMap.entrySet()) {
                String barcode = entry.getKey();
                Integer count = entry.getValue();
                if (count > 1) {
                    totalDuplicateBarcodeCount += count;
                    Map<String, Object> details = barcodeMap.get(barcode);
                    sb.append(String.format("Barcode: %s, Count: %d%nDetails: %s%n", barcode, count, details));
                }
            }
            sb.append("-----------------------------\n");
        }

        // Display total counts
        if (totalDuplicateIdCount > 0 || totalDuplicateProductIdCount > 0 || totalDuplicateBarcodeCount > 0) {
            sb.append(String.format("Total number of elements with duplicate IDs: %d%n", totalDuplicateIdCount));
            sb.append(String.format("Total number of elements with duplicate product_ids: %d%n", totalDuplicateProductIdCount));
            sb.append(String.format("Total number of elements with duplicate barcodes: %d%n", totalDuplicateBarcodeCount));
            throw new RuntimeException(sb.toString());
        }
    }

    /**
     * Checks for duplicates based on the specified field and displays details.
     * @param response The API response to check for duplicates.
     * @param fieldName The field name to check for duplicates (e.g., "id", "product_number", "barcode").
     * @param arrayResponse The JSON path to the array of objects in the response.
     */
    public static void checkForDuplicates(Response response, String fieldName, String arrayResponse) {
        // Extract the data array from the response
        List<Map<String, Object>> data = response.jsonPath().getList(arrayResponse);

        // Filter out items where field value is null or empty
        List<Map<String, Object>> filteredData = data.stream()
                .filter(item -> item.get(fieldName) != null && !item.get(fieldName).toString().trim().isEmpty())
                .collect(Collectors.toList());

        // Maps to track counts of the specified field
        Map<Object, Integer> fieldCountMap = new HashMap<>();
        // Maps to store details for duplicates
        Map<Object, Map<String, Object>> fieldMap = new HashMap<>();

        // Iterate over the filtered data to check for duplicates
        for (Map<String, Object> item : filteredData) {
            Object fieldValue = item.get(fieldName);

            if (fieldValue != null) {
                fieldCountMap.put(fieldValue, fieldCountMap.getOrDefault(fieldValue, 0) + 1);
                // Store details of the first occurrence
                if (fieldCountMap.get(fieldValue) == 1) {
                    fieldMap.put(fieldValue, item);
                }
            }
        }

        // Report duplicates
        StringBuilder sb = new StringBuilder();
        int totalDuplicateCount = 0;

        if (!fieldCountMap.isEmpty()) {
            sb.append("Duplicate ").append(fieldName).append("s found:\n");
            for (Map.Entry<Object, Integer> entry : fieldCountMap.entrySet()) {
                Object value = entry.getKey();
                Integer count = entry.getValue();
                if (count > 1) {
                    totalDuplicateCount += count;
                    Map<String, Object> details = fieldMap.get(value);
                    sb.append(String.format("%s: %s, Count: %d%nDetails: %s%n", fieldName, value, count, details));
                }
            }
            sb.append("-----------------------------\n");
        }

        // Display total count of duplicates
        if (totalDuplicateCount > 0) {
            sb.append(String.format("Total number of duplicate %ss: %d%n", fieldName, totalDuplicateCount));
            throw new RuntimeException(sb.toString());
        }
    }
/**************************** Count for barcode empty & barcode as false *********************/
    public static void countBarcodeFalseAndEmpty(Response response, String arrayResponse) {
        // Extract the data array from the response
        List<Map<String, Object>> data = response.jsonPath().getList(arrayResponse);

        // Count occurrences of false or empty barcode values
        long countFalse = data.stream()
                .map(item -> item.get("barcode"))
                .filter(value -> value instanceof Boolean && !(Boolean) value) // false values
                .count();

        long countEmpty = data.stream()
                .map(item -> item.get("barcode"))
                .filter(value -> value == null || value.toString().trim().isEmpty()) // empty or null values
                .count();

        // Print the counts
        System.out.println("Count of false barcodes: " + countFalse);
        System.out.println("Count of empty barcodes: " + countEmpty);
    }




}



