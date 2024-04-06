package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ProfilePage {
    @FindBy(xpath = "//*[@class='profile-image-source']")
    private WebElement profilePicture;
    @FindBy(xpath = "//i[@class='fas fa-user-edit ng-star-inserted']")
    // For Chrome browser xPath is different //i[@class='fas fa-user-edit']
    private WebElement profileEdit;
    @FindBy(xpath = "//textarea[@class='form-control ng-untouched ng-pristine ng-valid']")
    private WebElement fieldPublicInfo;
    public static final String PAGE_URL = "http://training.skillo-bg.com:4200/users/";
    private final WebDriver webDriver;
    public ProfilePage(WebDriver driver){
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean isUrlLoaded(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.urlContains(PAGE_URL));
    }
    public boolean isUrlLoaded(String userId){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.urlToBe(PAGE_URL+userId));
    }
    public void clickProfilePicture() {
        profilePicture.click();
    }
    public void uploadProfilePicture(File file){
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='file']")));
        webDriver.switchTo().activeElement();
        WebElement uploadFile = webDriver.findElement(By.xpath("//input[@type='file']"));
        uploadFile.sendKeys(file.getAbsolutePath());
    }
    public void clickProfileEdit() {
        profileEdit.click();
    }
    public void setFieldPublicInfo(){
        String publicInfo = fieldPublicInfo.getText();
        System.out.println("proba");
        fieldPublicInfo.click();

    }

}
