package by.htp.drozdovskaya.automation.belavia.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public enum DriverSingleton {

	INSTANCE;
	private static WebDriver driver;
	private final static String CHROME = "webdriver.chrome.driver";
	private final static String CHROME_PATH = "C:\\Drivers\\chromedriver\\chromedriver.exe";

	DriverSingleton(){
		
	}
	public static WebDriver getDriver() {
		if (null == driver) {
			System.setProperty(CHROME, CHROME_PATH);
			driver = new ChromeDriver();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}

		return driver;
	}

	public static void closeDriver() {
		driver.quit();
		driver = null;
	}
}
