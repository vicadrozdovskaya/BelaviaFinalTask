package by.htp.drozdovskaya.automation.belavia.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends AbstractPage {

	private final String BASE_URL = "https://belavia.by/";
	private WebDriverWait simpleWait;

	public MainPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
		simpleWait = new WebDriverWait(driver, 10);
	}

	public void clickOnFromCity(String city) {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElements(By.xpath("//div[@class='wrapper ui-trigger-input']/a")).get(0))
				.click().perform();

		driver.findElement(By.xpath("//input[@id='OriginLocation_Combobox']")).sendKeys(city);
		simpleWait.until(ExpectedConditions
				.textToBePresentInElementValue(By.xpath("//input[@id='OriginLocation_Combobox']"), city));
		simpleWait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//ul[@id='ui-id-2']/li"), 1));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.querySelector(\"ul#ui-id-2 li.ui-menu-item a\").click()");
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		simpleWait.until(ExpectedConditions
				.textToBePresentInElementValue(By.xpath("//input[@id='OriginLocation_Combobox']"), "Минск (MSQ), BY"));

	}

	public void clickOnToCity(String city) {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElements(By.xpath("//div[@class='wrapper ui-trigger-input']/a")).get(1))
				.click().perform();

		driver.findElement(By.xpath("//input[@id='DestinationLocation_Combobox']")).sendKeys(city);
		simpleWait.until(ExpectedConditions
				.textToBePresentInElementValue(By.xpath("//input[@id='DestinationLocation_Combobox']"), city));
		simpleWait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//ul[@id='ui-id-3']/li"), 1));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.querySelector(\"ul#ui-id-3 li.ui-menu-item a\").click()");
		simpleWait.until(ExpectedConditions.textToBePresentInElementValue(
				By.xpath("//input[@id='DestinationLocation_Combobox']"), "Рига (RIX), LV"));

	}

	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

}
