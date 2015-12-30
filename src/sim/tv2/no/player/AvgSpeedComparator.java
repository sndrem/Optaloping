package sim.tv2.no.player;

import java.util.Comparator;

public class AvgSpeedComparator implements Comparator<Player> {

	public AvgSpeedComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getAvgSpeed() > o2.getAvgSpeed()) {
			return -1;
		} else if (o1.getAvgSpeed() < o2.getAvgSpeed()) {
			return 1;
		} else return 0;
	}

}
