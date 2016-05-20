package sim.tv2.no.FormationInterface;

import java.util.List;

import sim.tv2.no.exceptions.TeamNotFullException;
import sim.tv2.no.player.SpillerBorsPlayer;

public interface Formation {
	
	public String getFormationText() throws TeamNotFullException;
	public void setPlayers(List<SpillerBorsPlayer> players);
	
}
