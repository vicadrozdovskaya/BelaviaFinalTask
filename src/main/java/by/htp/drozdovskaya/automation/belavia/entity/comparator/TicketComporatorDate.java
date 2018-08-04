package by.htp.drozdovskaya.automation.belavia.entity.comparator;

import java.util.Comparator;

import by.htp.drozdovskaya.automation.belavia.entity.Ticket;

public class TicketComporatorDate implements Comparator<Ticket>{

	@Override
	public int compare(Ticket ticket1, Ticket ticket2) {
		return ticket1.getDateDepature().compareTo(ticket2.getDateDepature());
	}

}
