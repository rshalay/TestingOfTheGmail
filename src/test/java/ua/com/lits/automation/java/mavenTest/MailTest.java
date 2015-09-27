package ua.com.lits.automation.java.mavenTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.driver.Wait;
import framework.driver.WebDriverFactory;


public class MailTest {
	protected WebDriver driver;
	protected WebElement webElementInput;
	protected WebElement webElementButtom;

	@BeforeMethod
	@Parameters({ "browserName" })
	public void setUp(String browserName) throws Exception {
		driver = WebDriverFactory.getInstance(browserName);
		Wait.waitTimeUnit(driver, 90, TimeUnit.SECONDS);
		driver.get("https://mail.google.com");
		Wait.waitUntil(driver);
		driver.manage().window().maximize();
		Wait.waitUntil(driver);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("r.test231@gmail.com");
		Wait.waitUntil(driver);
		driver.findElement(By.xpath("//input[@type='submit'][ancestor::div]")).click();
		Wait.waitUntil(driver);
		driver.findElement(By.xpath(".//input[@id='Passwd']")).clear();
		Wait.waitUntil(driver);
		driver.findElement(By.xpath(".//input[@id='Passwd']")).sendKeys("test231test231");
		Wait.waitUntil(driver);
		driver.findElement(By.xpath(".//*[@id='signIn']")).click();
		Wait.waitUntil(driver);
		webElementInput = driver.findElement(By.xpath(".//input[@id='gbqfq']"));
		webElementButtom = driver.findElement(By.xpath(".//button[@id='gbqfb']"));
		
	}



	@Test
	@Parameters({ "subjectText" })
	public void checkingMailOnAvailabilityLetterTest(String subjectText) throws InterruptedException {
		Wait.waitUntil(driver);
		webElementInput.sendKeys(subjectText);
		Wait.waitUntil(driver);
		webElementButtom.click();
		Wait.waitUntil(driver);
		List<WebElement> tableRows = driver.findElements(By.xpath(".//div [@gh]//td [@tabindex]"));
		Assert.assertEquals(
				tableRows.get(0).findElement(By.xpath(".//*[contains(text(),'Hello User')]")).getText().toString(),
				"Hello User");
	}

	@Test
	@Parameters({ "subjectText" })
	public void checkingMailForAbsenceLetterTest(String subjectText) throws InterruptedException {
		Wait.waitUntil(driver);
		webElementInput.sendKeys(subjectText + "123213");
		webElementButtom.click();
		Wait.waitUntil(driver);
		Assert.assertTrue(
				driver.findElement(By.xpath(".//div[@id=':2']//div[@class='BltHke nH oy8Mbf' and @role='main']"))
						.getAttribute("role").equals("main"));
	}
	
	@AfterMethod
	public void tearDouwn() throws Exception {
		if (driver != null)
			WebDriverFactory.killDriverInstance();
	}
}
