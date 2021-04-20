import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class main {
    private ChromeDriver driver;
    private WebDriverWait wait;

    @Before
    public void CreateDriver(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        // This is the only place I have the url so I decided to not use a constant.
        driver.get("https://eternalbox.dev/jukebox_index.html");
    }

    @Test
    public void VisitPage() throws InterruptedException {
        // Is the title present?
        Assert.assertEquals(driver.findElement(By.id("the-infinite-eternal-jukebox")).getText(), "The Infinite Eternal Jukebox");
        Thread.sleep(1000);

        // Let's search for my song!
        driver.findElementById("show-loader").click();
        driver.findElementById("search").sendKeys("My Dude");
        driver.findElementById("go-search").click();
        Thread.sleep(1000);
        WebElement songList = driver.findElementById("song-list");
        List<WebElement> songs = songList.findElements(By.tagName("li"));

        // Search through the list for my favorite song.
        for(WebElement song : songs){
            if(song.getText().equals("My Dude by Litany")){
                song.click();
                break;
            }
        }
        // Please wait for the song to load. We'll know when info turns to Ready!
        WebElement info = driver.findElementById("info");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("info"), "Ready!"));

        //Finally, the play button!
        driver.findElementById("go").click();

        //We'll make sure that the time properly displays a minutes 60 seconds after play.
        wait.withTimeout(Duration.ofSeconds(70)).until(ExpectedConditions.textToBePresentInElementLocated(By.id("time"), "00:01:00"));
    }

    @After
    public void Close(){
        driver.quit();
    }
}
