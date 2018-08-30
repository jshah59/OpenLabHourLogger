import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class HourLogger {

    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        // Need username, password, date, start, end, message
        if (args.length != 6) {
            System.out.println("Please use the following format: ");
            System.out.println("    java -jar HourLogger.jar [username][password][M/DD/YYYY][start][end][message]");
            return;
        }

        // Import arguments
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
        driver =  new ChromeDriver();
        driver.get("https://my.ece.illinois.edu/timetracker/");

        // Implicitly wait up to 5 seconds for WebElements to become visible when searching
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Log into TimeTracker interface
        login(username, password);

        Thread.sleep(2000);     // Allow dates to populate in dropdown

        // Populate fields in pop-up GUI
        fillWorkingTime(date, startTime, endTime, message);

        // Safely exit Chrome
        cleanUp();
    }

    private static void login(String username, String password) {
        // Find username + password boxes and enter login credentials
        WebElement usernameBox = driver.findElement(By.id("UserName"));
        WebElement passwordBox = driver.findElement(By.id("Password"));

        usernameBox.sendKeys(username);
        passwordBox.sendKeys(password);

        // Find and click "Sign In" button
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Find and click "Add Time" button
        driver.findElement(By.cssSelector("button[title='Click to add time for this day']")).click();
    }

    private static void fillWorkingTime(String date, String startTime, String endTime, String message) {
        // Select date of working hours
        Select dateEntry = new Select(driver.findElement(By.id("TimeEntry_EntryDates")));
        dateEntry.selectByValue(date);

        // Populate time fields
        WebElement startTimeBox = driver.findElement(By.id("TimeEntry_StartDateTime"));
        WebElement endTimeBox = driver.findElement(By.id("TimeEntry_EndDateTime"));
        WebElement workDescriptionBox =  driver.findElement(By.id("TimeEntry_LogDTO_Note"));
        WebElement submissionButton = driver.findElement(By.cssSelector("button[type='submit']"));

        startTimeBox.sendKeys(startTime);
        endTimeBox.sendKeys(endTime);
        workDescriptionBox.sendKeys(message);

        // Submit working hours
        submissionButton.click();
    }

    private static void cleanUp() {
        driver.quit();
    }
}
