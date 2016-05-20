package sim.tv2.no.formationFactory;

import java.util.ArrayList;
import java.util.List;

import sim.tv2.no.player.SpillerBorsPlayer;

public class GenericFormation {

	private List<SpillerBorsPlayer> players = new ArrayList<SpillerBorsPlayer>();
	
	public GenericFormation() {
		// TODO Auto-generated constructor stub
	}
	
	public GenericFormation(List<SpillerBorsPlayer> players) {
		this.players = players;
	}
	
	/**
	 * @return the players
	 */
	public List<SpillerBorsPlayer> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<SpillerBorsPlayer> players) {
		this.players = players;
	}
	
	/**
	 * Method to return a list of x number of players for a given place on the field
	 * @param int start the starting position for the player
	 * @param int stop the stop position for the player
	 * @return list of players
	 */
	public List<SpillerBorsPlayer> calculateNumOfPlayers(int start, int stop) {
		List<SpillerBorsPlayer> tempPlayers = new ArrayList<SpillerBorsPlayer>();
		for(int i = start; i <= stop; i++) {
			tempPlayers.add(players.get(i));
		}
		return tempPlayers;
	}
	
	public String outputPlayer(List<SpillerBorsPlayer> players) {
		String info = "";
		for(SpillerBorsPlayer player : players) {
			info += player.getDogbarText() + ", ";
		}
		return info.substring(0, info.length() - 2);
	}

}
