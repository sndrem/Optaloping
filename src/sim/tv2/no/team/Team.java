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
	private int teamId, matchesWon, matchesDrawn, matchesLost;
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
	
	public String toString() {
		return this.teamName;
	}

	/**
	 * @return the matchesWon
	 */
	public int getMatchesWon() {
		return matchesWon;
	}

	/**
	 * @param matchesWon the matchesWon to set
	 */
	public void setMatchesWon(int matchesWon) {
		this.matchesWon = matchesWon;
	}

	/**
	 * @return the matchesDrawn
	 */
	public int getMatchesDrawn() {
		return matchesDrawn;
	}

	/**
	 * @param matchesDrawn the matchesDrawn to set
	 */
	public void setMatchesDrawn(int matchesDrawn) {
		this.matchesDrawn = matchesDrawn;
	}

	/**
	 * @return the matchesLost
	 */
	public int getMatchesLost() {
		return matchesLost;
	}

	/**
	 * @param matchesLost the matchesLost to set
	 */
	public void setMatchesLost(int matchesLost) {
		this.matchesLost = matchesLost;
	}
	
	

}
