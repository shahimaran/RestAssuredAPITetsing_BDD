package restAssuredTest.migrationAPI;

import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    @BeforeClass
    public void setUp() {
        // Set up the base URL or any common configuration
        RestAssured.baseURI = Config.BASE_URL;
    }
    @AfterClass
    public void tearDown() {
        // Clean up resources if needed
    }
}
