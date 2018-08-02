package by.htp.drozdovskaya.automation.belavia;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.htp.drozdovskaya.automation.belavia.steps.StepsMainPage;


public class MainPageTest {
	
	private StepsMainPage steps;
	private final String CITY_FROM = "Ã»Õ— ";
	private final String CITY_TO = "–»√¿";

	@BeforeMethod(description = "Init browser")
	public void setUp()
	{
		steps = new StepsMainPage();
		steps.initBrowser();
	}

	@Test(description = "init from City")
	public void initCity()
	{
		steps.initFromCity(CITY_FROM,CITY_TO);
		
	
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser()
	{
		steps.closeDriver();
	}

}
