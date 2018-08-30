import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HourLogger {

    public static void main(String[] args) throws InterruptedException {
        // Need username, password, day, start, end
        if (args.length != 6) {
            System.out.println("Invalid arguments!");
            return;
        }

        String username = args[0];
        String password = args[1];
        String date = args[2];
        String startTime = args[3];
        String endTime = args[4];
        String message = args[5];

        String chromePath = System.getProperty("user.dir") + "\\chromedriver.exe";
        System.out.println(chromePath);
        System.setProperty("webdriver.chrome.driver", chromePath);

        // Initialize WebDriver and open TimeTracker URL
        WebDriver driver =  new ChromeDriver();
        driver.get("https://my.ece.illinois.edu/timetracker/");

        // Implicitly wait up to 5 seconds for WebElements to become visible when searching
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement usernameBox = driver.findElement(By.id("UserName"));
        WebElement passwordBox = driver.findElement(By.id("Password"));

        usernameBox.sendKeys(username);
        passwordBox.sendKeys(password);

        // Find and click "Sign In" button
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Find and click "Add Time" button
        driver.findElement(By.cssSelector("button[title='Click to add time for this day']")).click();

        Thread.sleep(2000);

        Select dateEntry = new Select(driver.findElement(By.id("TimeEntry_EntryDates")));
        System.out.println("Date options: " + dateEntry.getOptions().size());
        fillWorkingTime(dateEntry, date, startTime, endTime);
    }

    private static void fillWorkingTime(Select dateEntry, String date, String startTime, String endTime) {
        System.out.println(date);
        for (WebElement dayOption : dateEntry.getOptions()) {
            String value = dayOption.getAttribute("value");
            System.out.println(value);
            if (value.equals(date)) {
                dateEntry.selectByValue(value);
                break;
            }
        }
    }
}
