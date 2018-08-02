package by.htp.drozdovskaya.automation.belavia.steps;

import org.openqa.selenium.WebDriver;

import by.htp.drozdovskaya.automation.belavia.driver.DriverSingleton;
import by.htp.drozdovskaya.automation.belavia.pages.MainPage;

public class StepsMainPage {

	private WebDriver driver;

	public void initBrowser() {
		driver = DriverSingleton.getDriver();
	}

	public void closeDriver() {
		DriverSingleton.closeDriver();
	}

	public void initFromCity(String cityFrom, String cityTo) {
		MainPage mainPage = new MainPage(driver);
		mainPage.openPage();
		mainPage.clickOnFromCity(cityFrom);
		mainPage.clickOnToCity(cityTo);
	}

}
