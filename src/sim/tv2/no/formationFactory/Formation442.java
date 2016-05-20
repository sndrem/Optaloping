package sim.tv2.no.formationFactory;

import java.util.List;

import sim.tv2.no.FormationInterface.Formation;
import sim.tv2.no.exceptions.TeamNotFullException;
import sim.tv2.no.player.SpillerBorsPlayer;

public class Formation442 extends GenericFormation implements Formation{
	
	
	public Formation442(List<SpillerBorsPlayer> players) {
		super(players); 
	}
	
	public Formation442() {
		
	}

	@Override
	public String getFormationText() throws TeamNotFullException {
		String info = "";
		if(getPlayers().size() == 11) {
			SpillerBorsPlayer keeper = getPlayers().get(0);
			
			List<SpillerBorsPlayer> defenders = super.calculateNumOfPlayers(1, 4);
			List<SpillerBorsPlayer> middle = super.calculateNumOfPlayers(5, 8);
			List<SpillerBorsPlayer> attackers = super.calculateNumOfPlayers(9, 10);			
			
			info += keeper.getDogbarText() + "; ";
			info += super.outputPlayer(defenders);
			info += "; ";
			info += super.outputPlayer(middle);
			info += "; ";
			info += super.outputPlayer(attackers);
			
			
			
		} else {
			throw new TeamNotFullException("Laget trenger 11 spillere");
		}
		return info;
	}
	


	
	
	

}
