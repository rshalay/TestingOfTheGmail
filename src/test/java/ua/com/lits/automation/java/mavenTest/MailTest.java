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
		Wait.waitTimeUnit(driver, 30, TimeUnit.SECONDS);
		driver.get("https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/#identifier");
		Wait.waitUntil(driver, ".//*");
		driver.manage().window().maximize();
		Wait.waitUntil(driver, "//input[@type='email']");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("r.test231@gmail.com");
		Wait.waitUntil(driver, "//input[@type='submit'][ancestor::div]");
		driver.findElement(By.xpath("//input[@type='submit'][ancestor::div]")).click();
		Wait.waitUntil(driver, ".//input[@id='Passwd']");
		driver.findElement(By.xpath(".//input[@id='Passwd']")).clear();
		Wait.waitUntil(driver, ".//input[@id='Passwd']");
		driver.findElement(By.xpath(".//input[@id='Passwd']")).sendKeys("test231test231");
		Wait.waitUntil(driver, ".//*[@id='signIn']");
		driver.findElement(By.xpath(".//*[@id='signIn']")).click();
		webElementInput = driver.findElement(By.xpath(".//input[@id='gbqfq']"));
		webElementButtom = driver.findElement(By.xpath(".//button[@id='gbqfb']"));
		Wait.waitUntil(driver, ".//input[@id='gbqfq']");
		
	}



	@Test
	@Parameters({ "subjectText" })
	public void checkingMailOnAvailabilityLetterTest(String subjectText) throws InterruptedException {
		webElementInput.sendKeys(subjectText);
		webElementButtom.click();
		Wait.waitUntil(driver, ".//*[contains(text(), '')]");
		List<WebElement> tableRows = driver.findElements(By.xpath(".//div [@gh]//td [@tabindex]"));
		Assert.assertEquals(
				tableRows.get(0).findElement(By.xpath(".//*[contains(text(),'Hello User')]")).getText().toString(),
				"Hello User");
	}

	@Test
	@Parameters({ "subjectText" })
	public void checkingMailForAbsenceLetterTest(String subjectText) throws InterruptedException {
		webElementInput.sendKeys(subjectText + "123213");
		webElementButtom.click();
		Wait.waitUntil(driver, ".//div[@id=':2']//div[@class='BltHke nH oy8Mbf' and @role='main']");
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
