package sim.tv2.no.player;

import java.util.Comparator;

public class TopSpeedComparator implements Comparator<Player> {

	public TopSpeedComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getTopSpeed() > o2.getTopSpeed()) {
			return -1;
		} else if (o1.getTopSpeed() < o2.getTopSpeed()) {
			return 1;
		} else return 0;
	}

}
