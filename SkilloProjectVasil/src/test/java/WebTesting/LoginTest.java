package WebTesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import factory.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class LoginTest extends TestObject{
    private boolean userReg = false;

//    @BeforeMethod(alwaysRun = true)
//    public void beforeTest(){
//        WebDriverManager.edgedriver().setup();
//        webDriver = new EdgeDriver();
//        //WebDriverManager.chromedriver().setup();
//        //WebDriverManager.chromedriver().clearDriverCache().setup();
//        //webDriver = new ChromeDriver();
//        webDriver.manage().window().maximize();
//        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
//        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    //}

//    @AfterMethod(alwaysRun = true)
//    public void afterTest(){
//        if (webDriver != null) {
//            webDriver.close();
//        }
//        if(userReg == true){
//            //DB call for detele user with id .....
//            // Automation-[10 random numbers]-[10 random chars]-test@domain.com
//        }
   // }

    @DataProvider(name="getUser")
    public Object[][] getUsers(){
        return new Object[][]{
                {"Vasil2","Proba71", "5469"},
        };
    }

    @Test(dataProvider = "getUser")
    public void loginTest(String username, String password, String userId){

        HomePage homePage = new HomePage(webDriver);
        Header header = new Header(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        ProfilePage profilePage = new ProfilePage(webDriver);

        homePage.navigateTo();
        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");

        header.clickLogin();

        Assert.assertTrue(loginPage.isUrlLoaded(), "Current page is not Login");

        loginPage.fillInUserName(username);
        loginPage.fillInPassword(password);
        loginPage.checkRememberMe();

        Assert.assertTrue(loginPage.isCheckedRememberMe(), "Remember me checkbox is not checked.");

        loginPage.clickSignIn();
        header.clickProfile();

        //String userProfileURL = "http://training.skillo-bg.com:4200/users/" + userId;
        //wait.until(ExpectedConditions.urlToBe(userProfileURL));
        Assert.assertTrue(profilePage.isUrlLoaded(userId), "Current page in not profile page for " + userId + " user");

        //profilePage.isUrlLoaded();
        //profilePage.isUrlLoaded("5511");
        //Assert.assertEquals(webDriver.getCurrentUrl(),"http://training.skillo-bg.com:4200/users/" + userId);

        Assert.assertTrue(profilePage.isUrlLoaded(), "Current page is not profile page");
    }

}
