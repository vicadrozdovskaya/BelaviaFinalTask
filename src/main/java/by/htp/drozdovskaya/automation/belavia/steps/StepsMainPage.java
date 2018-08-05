package by.htp.drozdovskaya.automation.belavia.steps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private Calendar lastClickedDateInRow;
	private Calendar lastClickedDateInColumn;

	public void initBrowser() {
		driver = DriverSingleton.getDriver();
		lastClickedDateInRow = new GregorianCalendar();
		lastClickedDateInColumn = new GregorianCalendar();
	}

	public void closeDriver() {
		DriverSingleton.closeDriver();
	}

	public void initInformationToOneWayTrip(String cityFrom, String cityTo) {
		MainPage mainPage = new MainPage(driver);
		mainPage.openPage();
		mainPage.initFromCity(cityFrom);
		mainPage.initToCity(cityTo);
		mainPage.clickOnOneWay();
		mainPage.choiceDepartureDate();
		mainPage.clickOnFindButton();
	}

	public void initInformationToTwoWaysTrip(String cityFrom, String cityTo, Calendar endDate, int monthRange) {
		MainPage mainPage = new MainPage(driver);
		mainPage.openPage();
		mainPage.initFromCity(cityFrom);
		mainPage.initToCity(cityTo);
		mainPage.clickOnTwoWays();
		mainPage.choiceDepartureDate();
		mainPage.choiceArrivalDate(endDate, monthRange);
		mainPage.clickOnFindButton();
	}

	private void clickOnTicketAtAvailibleDate() {
		CalendarOfTariffsPage calendarTariffsPage = new CalendarOfTariffsPage(driver);
		String availibleDate = calendarTariffsPage.getNextClickableDate(lastClickedDateInRow);
		calendarTariffsPage.setClickDate(availibleDate);
		calendarTariffsPage.clickOnTicket(availibleDate);
	}

	private List<Ticket> getInformationAboutTicketsOnCurrentDepartureDate() {
		List<Ticket> tickets = new ArrayList<>();
		TicketOneWayPage ticketPage = new TicketOneWayPage(driver);
		List<Ticket> tempTickets = ticketPage.getAvailibleTicketsOnDepartureDate();
		for (Ticket t : tempTickets) {
			tickets.add(t);
		}
		ticketPage.backCalendarTariffs();
		return tickets;
	}

	private List<Ticket> getInformationAboutTicketsOnCurrentArrivalDate() {
		List<Ticket> tickets = new ArrayList<>();
		TicketOneWayPage ticketPage = new TicketOneWayPage(driver);
		tickets.addAll(ticketPage.getAvailibleTicketsOnArrivalDate());
		ticketPage.backCalendarTariffs();
		return tickets;
	}

	public List<Ticket> getAvailibleTicketsOneWay(Calendar endDate) {
		List<Ticket> tickets = new ArrayList<>();
		lastClickedDateInRow.add(Calendar.DATE, -1);
		setTimeToBeginnigOfDay(lastClickedDateInRow);
		while (lastClickedDateInRow.before(endDate)) {
			if (driver.getCurrentUrl().contains("https://booking.belavia.by/Select/Calendar")) {
				clickOnTicketAtAvailibleDate();
				tickets.addAll(getInformationAboutTicketsOnCurrentDepartureDate());
			} else {
				tickets.addAll(getInformationAboutTicketsOnCurrentDepartureDate());
			}
		}
		return tickets;
	}

	public List<Ticket> getAvailibleTicketsInRow(Calendar endDate) {
		List<Ticket> tickets = new ArrayList<>();
		lastClickedDateInRow.setTime(new Date());
		setTimeToBeginnigOfDay(lastClickedDateInRow);
		lastClickedDateInRow.add(Calendar.DATE, -1);
		CalendarOfTariffsPage calendarTariffsPage = new CalendarOfTariffsPage(driver);
		String verticalAvailibleDate = calendarTariffsPage.getNextClickableDateInColumn(lastClickedDateInColumn);
		Calendar horizontalLimit = new GregorianCalendar();
		horizontalLimit.setTime(endDate.getTime());
		horizontalLimit.add(Calendar.DATE, -1);
		setTimeToBeginnigOfDay(horizontalLimit);
		while (lastClickedDateInRow.before(horizontalLimit)) {
			String horizontalAvailibleDate = calendarTariffsPage.getNextClickableDate(lastClickedDateInRow);
			System.out.println("availibleDate " + horizontalAvailibleDate);
			calendarTariffsPage.setClickDate(verticalAvailibleDate);
			calendarTariffsPage.setClickVerticalDate(horizontalAvailibleDate);
			calendarTariffsPage.clickOnTicketInRow(horizontalAvailibleDate);
			tickets.addAll(getInformationAboutTicketsOnCurrentDepartureDate());
			setTimeToBeginnigOfDay(lastClickedDateInRow);
		}
		return tickets;
	}

	public List<Ticket> getAvailibleTicketsInColumn(Calendar endDate) {
		List<Ticket> tickets = new ArrayList<>();
		lastClickedDateInColumn.setTime(endDate.getTime());
		setTimeToBeginnigOfDay(lastClickedDateInColumn);
		lastClickedDateInColumn.add(Calendar.DATE, 1);
		CalendarOfTariffsPage calendarTariffsPage = new CalendarOfTariffsPage(driver);
		String availibleDate = calendarTariffsPage.getNextClickableDate(lastClickedDateInRow);
		Calendar verticalLimit = new GregorianCalendar();
		verticalLimit.setTime(lastClickedDateInRow.getTime());
		verticalLimit.add(Calendar.DATE, 1);
		setTimeToBeginnigOfDay(verticalLimit);
		while (lastClickedDateInColumn.compareTo(verticalLimit) != 0) {
			String verticalAvailibleDate = calendarTariffsPage.getNextClickableDateInColumn(lastClickedDateInColumn);
			System.out.println("verticalAvailibleDate " + verticalAvailibleDate);
			calendarTariffsPage.setClickDate(availibleDate);
			calendarTariffsPage.setClickVerticalDate(verticalAvailibleDate);
			calendarTariffsPage.clickOnTicketInColumn(verticalAvailibleDate);
			tickets.addAll(getInformationAboutTicketsOnCurrentArrivalDate());
			setTimeToBeginnigOfDay(lastClickedDateInColumn);
		}
		return tickets;
	}

	private void setTimeToBeginnigOfDay(Calendar date) {
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.HOUR_OF_DAY, 0);
	}

}
