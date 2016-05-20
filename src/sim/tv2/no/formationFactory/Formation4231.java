package sim.tv2.no.formationFactory;

import java.util.List;

import sim.tv2.no.FormationInterface.Formation;
import sim.tv2.no.exceptions.TeamNotFullException;
import sim.tv2.no.player.SpillerBorsPlayer;

public class Formation4231 extends GenericFormation implements Formation {
	
	public Formation4231(List<SpillerBorsPlayer> players) {
		super(players);
	}
	
	public Formation4231(){	}
	
	@Override
	public String getFormationText() throws TeamNotFullException {
		String info = "";
		if(getPlayers().size() == 11) {
			SpillerBorsPlayer keeper = getPlayers().get(0);
			
			info += keeper.getDogbarText() + "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(1, 4));
			info += "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(5, 6));
			info += "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(7, 9));
			info += "; ";
			info += super.outputPlayer(super.calculateNumOfPlayers(10, 10));
						
		} else {
			throw new TeamNotFullException("Laget trenger 11 spillere");
		}
		return info;
	}

}
