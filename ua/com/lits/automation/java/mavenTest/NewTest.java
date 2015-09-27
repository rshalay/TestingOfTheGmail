package ua.com.lits.automation.java.mavenTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class NewTest {
  @Test
  public void f() {
	  
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("My first java app");		
		WebDriver driver = new ChromeDriver();		
		driver.get("https://mail.google.com");
		driver.quit();
  }

  @AfterMethod
  public void afterMethod() {
  }

}
