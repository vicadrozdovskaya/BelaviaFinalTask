package by.htp.drozdovskaya.automation.belavia.entity.comparator;

import java.util.Comparator;

import by.htp.drozdovskaya.automation.belavia.entity.Ticket;

public class TicketComporatorCost implements Comparator<Ticket> {

	@Override
	public int compare(Ticket ticket1, Ticket ticket2) {
		if (ticket1.getCost() > ticket2.getCost())
			return 1;
		else if (ticket1.getCost() < ticket2.getCost())
			return -1;
		else
			return 0;
	}

}
