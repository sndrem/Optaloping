package sim.tv2.no.comparators;

import java.util.Comparator;

import sim.tv2.no.player.Player;

public class NumberComparator implements Comparator<Player> {

	public NumberComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getNumber() > o2.getNumber()) {
			return 1;
		} else if (o1.getNumber() < o2.getNumber()) {
			return -1;
		} else return 0;
	}

}
