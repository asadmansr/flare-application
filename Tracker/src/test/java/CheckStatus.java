import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class CheckStatus {
    WebDriver webDriver;
    WebElement webElement;
    boolean bookAvailable = false;
    final String Book_URL = "http://catalogue.mcmaster.ca/catalogue/Search/Results?lookfor=Concurrency%3A++State%2C+Models%2C+%26+Java+Programs&type=AllFields&submit=FIND";
    //final String Book_URL = "http://catalogue.mcmaster.ca/catalogue/Search/Results?lookfor=concurrency&type=AllFields&submit=FIND";

    @Before
    public void initTestSet() {
        webDriver = new ChromeDriver();
        webDriver.get(Book_URL);
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }


    @org.junit.Test
    public void getBookStatus(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        webElement = webDriver.findElement(By.cssSelector("*[class^='callnumAndLocation']"));
        List<WebElement> tableRows = webElement.findElements(By.tagName("tr"));

        String tableCheck = tableRows.get(1).getText();
        String tableArray[] = tableCheck.split(" ");
        int N = tableArray.length;
        if ((tableArray[N-1].contains("Available"))){
            bookAvailable = true;
        } else {

            webDriver.quit();
        }

        Assert.assertTrue("Book Available: ",bookAvailable);
    }
}
