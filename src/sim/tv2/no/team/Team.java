package sim.tv2.no.team;

import java.util.ArrayList;
import java.util.List;

import sim.tv2.no.player.Player;

/**
 * This class represents a team for the H2H-comparison. It saves the name, the team abbreviation (for image paths) and the teamId
 * @author Sindre
 *
 */
public class Team {
	
	private String teamName;
	private String teamAbbreviation;
	private int teamId;
	private List<Player> players;
	
	/**
	 * @param teamName
	 * @param teamAbbreviation
	 * @param teamId
	 */
	public Team(String teamName, String teamAbbreviation, int teamId) {
		this.teamName = teamName;
		this.teamAbbreviation = teamAbbreviation;
		this.teamId = teamId;
		players = new ArrayList<Player>();
	}
	
	public Team(String teamName) {
		this.teamName = teamName;
		players = new ArrayList<Player>();
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName.toUpperCase();
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * @return the teamAbbreviation
	 */
	public String getTeamAbbreviation() {
		return teamAbbreviation;
	}
	/**
	 * @param teamAbbreviation the teamAbbreviation to set
	 */
	public void setTeamAbbreviation(String teamAbbreviation) {
		this.teamAbbreviation = teamAbbreviation;
	}
	/**
	 * @return the teamId
	 */
	public int getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	

}
