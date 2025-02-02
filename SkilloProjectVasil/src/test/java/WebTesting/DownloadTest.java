package WebTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class DownloadTest extends TestObject{
    @Test
    public void testDownload() throws InterruptedException {
        WebDriver driver = super.getWebDriver();
        driver.get("https://demoqa.com/upload-download");
        WebElement downloadButton = driver.findElement(By.id("downloadButton"));
        // да си добавим проверка за бутона
        downloadButton.click();

        //Verfication
        String fineName = "sampleFile.jpeg";
        File file = new File(DOWNLOAD_DIR.concat(fineName));
        Assert.assertTrue(isFileDownloaded(file), "The file is not downloaded!");
    }
    private boolean isFileDownloaded(File file) throws InterruptedException{
        int waitTime = 20;
        int counter = 0;
        while (counter < waitTime){
            if (file.exists()){
                return true;
            }
            Thread.sleep(1000);
            counter++;
        }
        return false;
    }
}
