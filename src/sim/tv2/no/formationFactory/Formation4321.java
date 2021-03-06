package sim.tv2.no.formationFactory;

import java.util.List;

import sim.tv2.no.FormationInterface.Formation;
import sim.tv2.no.exceptions.TeamNotFullException;
import sim.tv2.no.player.SpillerBorsPlayer;

public class Formation4321 extends GenericFormation implements Formation {

	public Formation4321() {
		// TODO Auto-generated constructor stub
	}

	public Formation4321(List<SpillerBorsPlayer> players) {
		super(players);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFormationText() throws TeamNotFullException {
		String info = "";
		if(super.getPlayers().size() == 11) {
			info += super.getPlayers().get(0).getDogbarText() + "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(1, 4)) + "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(5, 7)) + "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(8, 9)) + "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(10, 10));
		} else {
			throw new TeamNotFullException("Lag er ikke elleve spillere");
		}
		return info;
	}

}
