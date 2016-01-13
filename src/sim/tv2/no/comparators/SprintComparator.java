package sim.tv2.no.comparators;

import java.util.Comparator;

import sim.tv2.no.player.Player;

public class SprintComparator implements Comparator<Player> {

	public SprintComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getSprints() > o2.getSprints()) {
			return -1;
		} else if (o1.getSprints() < o2.getSprints()) {
			return 1;
		} else return 0;
	}

}
