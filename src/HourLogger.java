import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class HourLogger {

    public static void main(String[] args) {
        // Need username, password, day, start, end
        if (args.length != 5) {
            System.out.println("Invalid arguments!");
            return;
        }

        String username = args[0];
        String password = args[1];
        String day = args[2];
        String startTime = args[3];
        String endTime = args[4];

        WebDriver driver =  new ChromeDriver();
        driver.get("https://my.ece.illinois.edu/portal/Account/Login");


    }
}
