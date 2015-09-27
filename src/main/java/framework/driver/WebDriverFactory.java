package framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
	private static final String CHROME = "chrome";
	private static final String FIREFOX = "firefox";
	private static final String IE = "ie";
	// private static final String SAFARI = "safari";
	// private static final String OPERA = "opera";

	private static WebDriver driver;
	private static DesiredCapabilities dc;

	private WebDriverFactory() {
	}

	public static WebDriver getInstance(String browser) throws Exception {
		if (driver == null) {
			if (CHROME.equals(browser)) {

				setChromeDriver();

				ChromeOptions options = new ChromeOptions();
				options.addArguments("text-type");
				dc = DesiredCapabilities.chrome();
				dc.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(dc);

			} else if (IE.equals(browser)) {

				setIEDriver();
				
				DesiredCapabilities ieCapabilities=DesiredCapabilities.internetExplorer();
			    ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			    ieCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
			    driver = new InternetExplorerDriver(ieCapabilities);    				

			} else if (FIREFOX.equals(browser)) {
				FirefoxProfile fp = new FirefoxProfile();
				dc = DesiredCapabilities.firefox();
				dc.setCapability(FirefoxDriver.PROFILE, fp);
				driver = new FirefoxDriver(dc);
			} else
				throw new RuntimeException("Invalid browser property set in configuration file");

		}

		return driver;

	}

	public static void killDriverInstance() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	private static void setChromeDriver() throws Exception {
		String osName = System.getProperty("os.name").toLowerCase();
		StringBuffer chromeBinaryPath = new StringBuffer("src/main/resources/drivers/");
		if (osName.startsWith("win")) {
			chromeBinaryPath.append("chrome-win/chromedriver.exe");
		} else if (osName.startsWith("lin")) {
			chromeBinaryPath.append("chrome-lin/chromedriver");
		} else if (osName.startsWith("mac")) {
			chromeBinaryPath.append("chrome-mac/chromedriver");
		} else
			throw new RuntimeException("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.chrome.driver", chromeBinaryPath.toString());

	}
	
	private static void setIEDriver() throws Exception {
			 String osName = String.valueOf(System.getProperty("os.arch"));
			StringBuffer ieBinaryPath = new StringBuffer("src/main/resources/drivers/");
		if (osName.startsWith("x86")) {
			ieBinaryPath.append("ie-32/IEDriverServer.exe");
		} else if (osName.startsWith("x64")) {
			ieBinaryPath.append("ie-64/IEDriverServer.exe");
		} else
			throw new RuntimeException("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.ie.driver", ieBinaryPath.toString());
	}
}
