package by.htp.drozdovskaya.automation.belavia.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarOfTariffsPage extends AbstractPage {

	private WebDriverWait simpleWait;
	private String clickDate;
	private String clickVerticalDate;

	public CalendarOfTariffsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
		simpleWait = new WebDriverWait(driver, 10);
		clickDate = "";
	}

	public String getClickDate() {
		return clickDate;
	}

	public void setClickDate(String clickDate) {
		this.clickDate = clickDate;
	}

	public String getClickVerticalDate() {
		return clickVerticalDate;
	}

	public void setClickVerticalDate(String clickVerticalDate) {
		this.clickVerticalDate = clickVerticalDate;
	}

	public void clickOnTicket(String availibleDate) {
		List<WebElement> dates = driver.findElements(By.xpath("//div[@class='h-outbound hidden-xs clear']/div"));
		dates.remove(0);
		List<WebElement> tickets = driver.findElements(By.xpath("//div[@class='b-matrix clear']/div"));
		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).getAttribute("id").contains(clickDate)) {
				WebElement cost = tickets.get(i).findElement(By.className("price"));
				if (cost != null) {
					cost.click();
					simpleWait.until(ExpectedConditions.attributeToBe(cost, "class", "price selected"));
					clickOnButtonNext();
					break;
				}
			}

		}

	}

	public void clickOnTicketInRow(String availibleDate) {
		List<WebElement> dates = driver.findElements(By.xpath("//div[@class='h-outbound hidden-xs clear']/div"));
		dates.remove(0);
		List<WebElement> tickets = driver.findElements(By.xpath("//div[@class='details']/div[@class='price']"));
		Pattern pattern = Pattern.compile("(\\d{2}-?){3}");
		for (int j = 0; j < dates.size(); j++) {
			if (dates.get(j).getAttribute("id").contains(availibleDate)) {
				String[] splitedValues = availibleDate.split("_");
				if (splitedValues.length > 1) {
					availibleDate = splitedValues[1];
				}
				for (int k = 0; k < tickets.size(); k++) {
					WebElement input = tickets.get(k).findElement(By.tagName("input"));
					String date = input.getAttribute("value");
					Matcher matcher = pattern.matcher(date);
					matcher.find();
					date = matcher.group(0);
					if (date.contains(availibleDate)) {
						if (input != null) {
							tickets.get(k).click();
							simpleWait
									.until(ExpectedConditions.attributeToBe(tickets.get(k), "class", "price selected"));
							clickOnButtonNext();
							j = dates.size();
							break;
						}
					}
				}
			}
		}
	}

	public void clickOnTicketInColumn(String verticalAvailibleDate) {
		List<WebElement> dates = driver.findElements(By.xpath("//div[@class='h-outbound hidden-xs clear']/div"));
		dates.remove(0);
		List<WebElement> datesVertical = driver.findElements(By.xpath("//div[@class='h-inbound hidden-xs clear']/div"));
		dates.remove(0);
		List<WebElement> tickets = driver.findElements(By.xpath("//div[@class='details']/div/div/input"));
		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).getAttribute("id").contains(clickDate)) {
				for (int j = 0; j < datesVertical.size(); j++) {
					if (datesVertical.get(j).getAttribute("id").contains(verticalAvailibleDate)) {
						String[] splitedValues = verticalAvailibleDate.split("_");
						if (splitedValues.length > 1) {
							verticalAvailibleDate = splitedValues[1];
						}
						for (int k = 0; k < tickets.size(); k++) {
							if (tickets.get(k).getAttribute("value").contains(verticalAvailibleDate)) {
								WebElement cost = tickets.get(k).findElement(By.xpath("parent::*/parent::*"));
								if (cost != null) {
									cost.click();
									simpleWait.until(ExpectedConditions.attributeToBe(cost, "class", "price selected"));
									clickOnButtonNext();
									j = datesVertical.size();
									i = dates.size();
									break;
								}
							}
						}
					}
				}
			}

		}

	}
	public String getNextClickableDate(Calendar lastClickedDate) {
		String returnStatement = "";
		List<WebElement> dates = driver.findElements(By.xpath("//div[@class='h-outbound hidden-xs clear']/div"));
		dates.remove(0);
		List<WebElement> tickets = driver.findElements(By.xpath("//div[@class='details']"));
		for (int i = 0; i < dates.size(); i++) {
			String iterableDateValue = dates.get(i).getAttribute("id");
			String[] splitedValues = iterableDateValue.split("_");
			if (splitedValues.length > 1) {
				iterableDateValue = splitedValues[1];
			}
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			Calendar currentIterableDate = new GregorianCalendar();
			try {
				currentIterableDate.setTime(format.parse(iterableDateValue));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (currentIterableDate.after(lastClickedDate)) {
				List<WebElement> element = tickets.get(i).findElements(By.tagName("div"));
				for (int j = 0; j < element.size(); j++) {
					if (element.get(j).getAttribute("class").equals("price")) {
						returnStatement = dates.get(i).getAttribute("id");
						lastClickedDate.setTime(currentIterableDate.getTime());
						i = dates.size();
						break;
					}
				}
			}
		}
		return returnStatement;
	}

	public String getNextClickableDateInColumn(Calendar lastClickedDate) {
		String returnStatement = "";
		List<WebElement> dates = driver.findElements(By.xpath("//div[@class='h-inbound hidden-xs clear']/div"));
		dates.remove(0);
		for (int i = dates.size() - 1; i >= 0; i--) {
			String iterableDateValue = dates.get(i).getAttribute("id");
			String[] splitedValues = iterableDateValue.split("_");
			if (splitedValues.length > 1) {
				iterableDateValue = splitedValues[1];
			}
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			Calendar currentIterableDate = new GregorianCalendar();
			try {
				currentIterableDate.setTime(format.parse(iterableDateValue));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (currentIterableDate.before(lastClickedDate)) {
				List<WebElement> element = driver.findElements(By.xpath("//div[@class='details']/div[@class='price']"));
				for (int j = 0; j < element.size(); j++) {
					if (element.get(j).getAttribute("class").equals("price")) {
						WebElement elWithPrice = element.get(j).findElement(By.tagName("input"));
						if (elWithPrice.getAttribute("value").contains(iterableDateValue)) {
							returnStatement = dates.get(i).getAttribute("id");
							lastClickedDate.setTime(currentIterableDate.getTime());
							i = -1;
							break;
						}
					}
				}
			}

		}

		return returnStatement;
	}

	public void clickOnButtonNext() {
		driver.findElement(By.xpath("//div[@class='col-mb-6 text-right']/button")).click();
	}

}
