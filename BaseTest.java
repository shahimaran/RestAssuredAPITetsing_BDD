package restAssuredTest.migrationAPI;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.restassured.RestAssured;
import org.testng.annotations.*;
import restAssuredTest.migrationAPI.Utils.ExtentReportManager;
import restAssuredTest.migrationAPI.Utils.Utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.lang.reflect.Method;

public class BaseTest {
    private static ExtentReports report;
    protected ExtentTest logger;


    @BeforeSuite
    public void setUpReport() {
        // Initialize ExtentReports
        report = ExtentReportManager.getReportInstance("UAT API Migration Automation Results", "All Headlines UI Test Report");    }
    @BeforeMethod
    public void startTest(Method method) {
        // Create a new test for each method
        logger = report.createTest(method.getName());
        Utils.setExtentTest(logger); // Set the ExtentTest instance for Utils
    }

    @BeforeClass
    public void setUp() {
        // Set up the base URL or any common configuration
        RestAssured.baseURI = Config.BASE_URL;
    }
   /* @AfterClass
    public void tearDown() {
        // Clean up resources if needed
    }*/
    @AfterSuite
    public void tearDown() {
        // Flush the reports
        report.flush();
    }
    /*@AfterMethod
    public void flushReport(){
        report.flush();
    }*/
}
