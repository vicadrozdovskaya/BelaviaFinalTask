package by.htp.drozdovskaya.automation.belavia;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

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

	@Test(description = "availible tickets on one way", enabled = false)
	public void availibleTicketsOnOneWay() {		
		Calendar endDate = new GregorianCalendar();
		endDate.add(Calendar.MONTH, MONTH_RANGE);
		steps.initInformationToOneWayTrip(CITY_FROM, CITY_TO);		
		List<Ticket> tickets = steps.getAvailibleTicketsOneWay(endDate);
		Collections.sort(tickets, new TicketComporatorCost());
		System.out.println("Отсортированный по стоимости ");
		System.out.println(tickets);
		Collections.sort(tickets, new TicketComporatorDate());
		System.out.println("Отсортированный по дате вылета ");
		System.out.println(tickets);
	}
	
	@Test(description = "availible tickets on two ways")
	public void availibleTicketsOnTwoWays() {
		Calendar endDate = new GregorianCalendar();
		endDate.add(Calendar.MONTH, MONTH_RANGE);
		steps.initInformationToTwoWaysTrip(CITY_FROM, CITY_TO, endDate, MONTH_RANGE);		
		List<Ticket> ticketsFrom = steps.getAvailibleTicketsInColumn(endDate);
		List<Ticket> ticketsTo = steps.getAvailibleTicketsInRow(endDate);
		Collections.sort(ticketsFrom, new TicketComporatorCost());
		System.out.println("Рига - Минск");
		System.out.println("Отсортированный по стоимости ");
		System.out.println(ticketsFrom);
		Collections.sort(ticketsFrom, new TicketComporatorDate());
		System.out.println("Отсортированный по дате вылета ");
		System.out.println(ticketsFrom);
		System.out.println("Минск - Рига");
		Collections.sort(ticketsTo, new TicketComporatorCost());
		System.out.println("Отсортированный по стоимости ");
		System.out.println(ticketsTo);
		Collections.sort(ticketsTo, new TicketComporatorDate());
		System.out.println("Отсортированный по дате вылета ");
		System.out.println(ticketsTo);
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser() {
		steps.closeDriver();
	}
}
