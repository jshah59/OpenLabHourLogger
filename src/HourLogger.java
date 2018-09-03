import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class HourLogger {

    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        String username;
        String password;
        String date;
        String startTime;
        String endTime;
        String message;
        // Need username, password, date, start, end, message

        // Default message case
        if (args.length == 5) {
            username = args[0];
            password = args[1];
            date = args[2];
            startTime = args[3];
            endTime = args[4];
            message = "Lab monitor";
        } else if (args.length == 6) {
            username = args[0];
            password = args[1];
            date = args[2];
            startTime = args[3];
            endTime = args[4];
            message = args[5];
        } else {
            System.out.println("Please use the following format: ");
            System.out.println("    java -jar HourLogger.jar [username][password][M/DD/YYYY][start][end][message]");
            return;
        }

        String chromePath = System.getProperty("user.dir") + "\\chromedriver.exe";
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

        Thread.sleep(1000);     // Allow time to submit timesheet data

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
