package framework.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {
	
	public static void waitUntil(WebDriver driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public static void waitTimeUnit(WebDriver driver, int time, TimeUnit typeTimeUnit){
	driver.manage().timeouts().implicitlyWait(time, typeTimeUnit);
	}
}
