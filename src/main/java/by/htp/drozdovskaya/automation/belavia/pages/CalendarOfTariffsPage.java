package by.htp.drozdovskaya.automation.belavia.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarOfTariffsPage extends AbstractPage {

	private WebDriverWait simpleWait;
	private String clickDate;

	public CalendarOfTariffsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
		simpleWait = new WebDriverWait(driver, 10);
		clickDate = "";
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

	public String getClickDate() {
		return clickDate;
	}

	public void setClickDate(String clickDate) {
		this.clickDate = clickDate;
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

	public void clickOnButtonNext() {
		driver.findElement(By.xpath("//div[@class='col-mb-6 text-right']/button")).click();
	}

}
