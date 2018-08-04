package by.htp.drozdovskaya.automation.belavia.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import by.htp.drozdovskaya.automation.belavia.entity.Ticket;

public class TicketOneWayPage extends AbstractPage {

	public TicketOneWayPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);

	}

	public List<Ticket> getAvailibleTickets() {
		List<Ticket> tickets = new ArrayList<>();

		String tempDate = driver.findElement(By.xpath("//div[@class='col-mb-7']/h3")).getText();
		Calendar date = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM");
		String time = driver.findElements(By.xpath("//div[@class='departure']/strong")).get(0).getText();
		try {
			date.setTime(format.parse(tempDate));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		List<WebElement> typesOfFly = driver.findElements(By.xpath("//div[@class='fare-avail ui-corner-all']/div/a"));
		List<WebElement> costs = driver.findElements(By.xpath("//div[@class='fare-avail ui-corner-all']/div/label"));
		for (int i = 0; i < typesOfFly.size(); i++) {
			Ticket ticket = new Ticket();
			ticket.setDateDepature(date);
			ticket.setTimeDepature(time);
			ticket.setClassFly(typesOfFly.get(i).getAttribute("innerText"));
			String costStr = costs.get(i).getText();
			costStr = costStr.replace(",", ".");
			String[] result = costStr.split(" ");
			double cost = Double.parseDouble(result[0]);
			ticket.setCost(cost);
			tickets.add(ticket);
		}
		backCalendarTariffs();
		return tickets;
	}

	public void backCalendarTariffs() {
		driver.findElement(By.xpath("//div[@class='col-mb-5 text-right']/a")).click();
	}

}
