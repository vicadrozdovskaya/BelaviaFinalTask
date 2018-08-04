package by.htp.drozdovskaya.automation.belavia.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Ticket {
	// дату вылета, время вылета, класс перелета, стоимость билета

	private Calendar dateDepature;
	private String timeDepature;
	private String classFly;
	private double cost;

	public Ticket() {
		super();
		dateDepature = new GregorianCalendar();
	}

	public Ticket(Calendar dateDepature, String timeDepature, String classFly, double cost) {
		this.dateDepature = dateDepature;
		this.timeDepature = timeDepature;
		this.classFly = classFly;
		this.cost = cost;
	}

	public Calendar getDateDepature() {
		return dateDepature;
	}

	public void setDateDepature(Calendar dateDepature) {
		this.dateDepature = dateDepature;
	}

	public String getTimeDepature() {
		return timeDepature;
	}

	public void setTimeDepature(String timeDepature) {
		this.timeDepature = timeDepature;
	}

	public String getClassFly() {
		return classFly;
	}

	public void setClassFly(String classFly) {
		this.classFly = classFly;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classFly == null) ? 0 : classFly.hashCode());
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateDepature == null) ? 0 : dateDepature.hashCode());
		result = prime * result + ((timeDepature == null) ? 0 : timeDepature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (classFly == null) {
			if (other.classFly != null)
				return false;
		} else if (!classFly.equals(other.classFly))
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (dateDepature == null) {
			if (other.dateDepature != null)
				return false;
		} else if (!dateDepature.equals(other.dateDepature))
			return false;
		if (timeDepature == null) {
			if (other.timeDepature != null)
				return false;
		} else if (!timeDepature.equals(other.timeDepature))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("E dd MMMM");
		return "\nTicket: dateDepature=" + format.format(dateDepature.getTime()) + ", timeDepature=" + timeDepature
				+ ", classFly=" + classFly + ", cost=" + cost + " BYN";
	}

}
