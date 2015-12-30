package sim.tv2.no.player;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Player> {

	public DistanceComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getDistance() > o2.getDistance()) {
			return -1;
		} else if (o1.getDistance() < o2.getDistance()) {
			return 1;
		} else return 0;
	}

}
