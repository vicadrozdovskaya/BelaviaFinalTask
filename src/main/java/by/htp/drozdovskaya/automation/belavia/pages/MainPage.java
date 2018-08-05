package by.htp.drozdovskaya.automation.belavia.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

	public void initFromCity(String city) {
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

	public void initToCity(String city) {
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

	public void clickOnOneWay() {
		driver.findElement(By.xpath("//label[@for='JourneySpan_Ow']")).click();
	}

	public void clickOnTwoWays() {
		driver.findElement(By.xpath("//label[@for='JourneySpan_Rt']")).click();
	}

	public void choiceDepartureDate() {
		driver.findElements(By.xpath("//a/i[@class='icon-calendar']")).get(0).click();
		List<WebElement> calendar = driver.findElements(By.xpath("//td/a"));
		Calendar today = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("dd");
		String date = format.format(today.getTime());
		String day = String.valueOf(Integer.parseInt(date));
		simpleWait.until(ExpectedConditions.attributeToBe(By.xpath(
				"//div[@class='wrapper ui-trigger-input ui-date-input']/span[@data-valmsg-for='DepartureDate_Datepicker']"),
				"class", "field-validation-valid"));
		for (WebElement we : calendar) {
			if (we.getText().contains(day)) {
				we.click();
				break;
			}
		}

	}

	public void choiceArrivalDate(Calendar endDate, int monthRange) {
		for (int i = 0; i < monthRange - 2; i++) {
			driver.findElement(By.xpath("//a/i[@class='icon-right-open']")).click();
		}
		List<WebElement> calendar = driver.findElements(By.xpath(
				"//div[@class='ui-datepicker-group ui-datepicker-group-last']/table[@class='ui-datepicker-calendar']/tbody/tr/td/a"));
		simpleWait.until(ExpectedConditions.attributeToBe(By.xpath(
				"//div[@class='wrapper ui-trigger-input ui-date-input']/span[@data-valmsg-for='ReturnDate_Datepicker']"),
				"class", "field-validation-valid"));
		SimpleDateFormat format = new SimpleDateFormat("dd");
		String date = format.format(endDate.getTime());
		String day = String.valueOf(Integer.parseInt(date));
		for (WebElement we : calendar) {
			if (we.getText().contains(day)) {
				we.click();
				break;
			}
		}
	}

	public void clickOnFindButton() {
		driver.findElements(By.xpath("//div[@class='col-mb-12 col-4 col-offset-8']/button")).get(0).click();
	}

	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

}
