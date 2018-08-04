package by.htp.drozdovskaya.automation.belavia;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.htp.drozdovskaya.automation.belavia.entity.Ticket;
import by.htp.drozdovskaya.automation.belavia.entity.comparator.TicketComporatorCost;
import by.htp.drozdovskaya.automation.belavia.entity.comparator.TicketComporatorDate;
import by.htp.drozdovskaya.automation.belavia.steps.StepsMainPage;

public class MainPageTest {

	private StepsMainPage steps;
	private final String CITY_FROM = "МИНСК";
	private final String CITY_TO = "РИГА";
	private final static int MONTH_RANGE = 3;

	@BeforeMethod(description = "Init browser")
	public void setUp() {
		steps = new StepsMainPage();
		steps.initBrowser();
	}

	@Test(description = "init from City")
	public void initCity() {
		Calendar endDate = new GregorianCalendar();
		endDate.add(Calendar.MONTH, MONTH_RANGE);
		steps.initDateAndDestination(CITY_FROM, CITY_TO);
		List<Ticket> tickets = steps.getAvailibleTickets(endDate);
		Collections.sort(tickets, new TicketComporatorCost());
		System.out.println("Отсортированный по стоимости ");
		System.out.println(tickets);
		Collections.sort(tickets, new TicketComporatorDate());
		System.out.println("Отсортированный по дате вылета ");
		System.out.println(tickets);
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser() {
		steps.closeDriver();
	}

}
