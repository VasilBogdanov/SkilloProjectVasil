package WebTesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestObject {
    public static final String TEST_RESOURCES_DIR = "src\\test\\resources\\";
    public static final String DOWNLOAD_DIR = TEST_RESOURCES_DIR.concat("download\\");
    public static final String SCREENSHOTS_DIR = TEST_RESOURCES_DIR.concat("screenshots\\");
    public static final String REPORTS_DIR = TEST_RESOURCES_DIR.concat("reports\\");
    public WebDriver webDriver;
    @BeforeSuite
    protected final void setupTestSuite() throws IOException {
        cleanDirectory(SCREENSHOTS_DIR);
        cleanDirectory(REPORTS_DIR);
        WebDriverManager.edgedriver().setup();
    }
    @BeforeMethod
    protected final void setUpTest(){
        this.webDriver = new EdgeDriver(configEdgeOptions());
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @AfterMethod
    protected final void tearDownTest(ITestResult testResult){
        takeScreenshot(testResult);
        quitDriver();
    }
    @AfterSuite
    public void deleteDownloadFiles() throws IOException{
        cleanDirectory(DOWNLOAD_DIR);
    }
    private void quitDriver() {
        if (this.webDriver != null){
            this.webDriver.quit();
        }
    }
    protected WebDriver getWebDriver(){
        return webDriver;
    }
    private EdgeOptions configEdgeOptions(){
        //Create path and setting for download folder
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory",
                System.getProperty("user.dir").concat("\\").concat(DOWNLOAD_DIR));

        EdgeOptions edgeOptions = new EdgeOptions();
        //Set new default download folder
        edgeOptions.setExperimentalOption("prefs", prefs);
        //Force the download to be automatic
        edgeOptions.addArguments("disable-popup-blocking");
        return edgeOptions;
    }
    private void cleanDirectory(String directoryPath) throws IOException{
        File directory = new File(directoryPath);
        Assert.assertTrue(directory.isDirectory(), "Invalid directory!");

        FileUtils.cleanDirectory(directory);
        String[] fileList = directory.list();
        if (fileList != null && fileList.length == 0){
            System.out.printf("All files are deleted in Directory: %s%n", directoryPath);
        }else {
            System.out.printf("Unable to delete the files in Directory: %s%n", directoryPath);
        }
    }
    private void takeScreenshot(ITestResult testResult){
        if(ITestResult.FAILURE == testResult.getStatus()){
            try{
                TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
                File screeshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName();
                FileUtils.copyFile(screeshot, new File(SCREENSHOTS_DIR.concat(testName).concat(".jpg")));
            }catch (IOException e){
                System.out.println("Unable to create a screenshot file: " + e.getMessage());
            }
        }
    }
}
