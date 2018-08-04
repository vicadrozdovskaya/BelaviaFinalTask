package by.htp.drozdovskaya.automation.belavia.steps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.WebDriver;

import by.htp.drozdovskaya.automation.belavia.driver.DriverSingleton;
import by.htp.drozdovskaya.automation.belavia.entity.Ticket;
import by.htp.drozdovskaya.automation.belavia.pages.CalendarOfTariffsPage;
import by.htp.drozdovskaya.automation.belavia.pages.MainPage;
import by.htp.drozdovskaya.automation.belavia.pages.TicketOneWayPage;

public class StepsMainPage {

	private WebDriver driver;
	private Calendar lastClickedDate;

	public void initBrowser() {
		driver = DriverSingleton.getDriver();
		lastClickedDate = new GregorianCalendar();
	}

	public void closeDriver() {
		DriverSingleton.closeDriver();
	}

	public void initDateAndDestination(String cityFrom, String cityTo) {
		MainPage mainPage = new MainPage(driver);
		mainPage.openPage();
		mainPage.initFromCity(cityFrom);
		mainPage.initToCity(cityTo);
		mainPage.clickOnOneWay();
		mainPage.choiceDepartureDate();
		mainPage.clickOnFindButton();
	}

	public void clickOnTicketAtAvailibleDate() {
		CalendarOfTariffsPage calendarTariffsPage = new CalendarOfTariffsPage(driver);
		String availibleDate = calendarTariffsPage.getNextClickableDate(lastClickedDate);
		calendarTariffsPage.setClickDate(availibleDate);
		calendarTariffsPage.clickOnTicket(availibleDate);
	}

	public List<Ticket> getInformationAboutTicketsOnCurrentDate() {
		List<Ticket> tickets = new ArrayList<>();
		TicketOneWayPage ticketPage = new TicketOneWayPage(driver);
		List<Ticket> tempTickets = ticketPage.getAvailibleTickets();
		for (Ticket t : tempTickets) {
			tickets.add(t);
		}
		return tickets;
	}

	public List<Ticket> getAvailibleTickets(Calendar endDate) {
		List<Ticket> tickets = new ArrayList<>();
		lastClickedDate.add(Calendar.DATE, -1);
		System.out.println(endDate.getTime());
		while (lastClickedDate.before(endDate)) {
			if (driver.getCurrentUrl().contains("https://booking.belavia.by/Select/Calendar")) {
				clickOnTicketAtAvailibleDate();
				tickets.addAll(getInformationAboutTicketsOnCurrentDate());
			} else {
				tickets.addAll(getInformationAboutTicketsOnCurrentDate());
			}
		}
		return tickets;
	}

}
