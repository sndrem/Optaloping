package sim.tv2.no.match;

import java.util.HashMap;
import java.util.Map;

public class Match {
	
	private String homeTeam, awayTeam;
	private Map<String, String> teamAbbreviations;

	/**
	 * @param homeTeam
	 * @param awayTeam
	 */
	public Match(String homeTeam, String awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		setTeamAbbreviations(new HashMap<String, String>());
		teamAbbreviations.put("Leicester", "LEI");
		teamAbbreviations.put("Man. City", "MAC");
		teamAbbreviations.put("Arsenal", "ARS");
		teamAbbreviations.put("Tottenham", "TOT");
		teamAbbreviations.put("Man. United", "MAU");
		teamAbbreviations.put("West Ham", "WHU");
		teamAbbreviations.put("Liverpool", "LIV");
		teamAbbreviations.put("Southampton", "SOU");
		teamAbbreviations.put("Stoke", "STO");
		teamAbbreviations.put("Watford", "WAT");
		teamAbbreviations.put("Crystal Palace", "CRY");
		teamAbbreviations.put("Everton", "EVE");
		teamAbbreviations.put("Chelsea", "CHE");
		teamAbbreviations.put("West Bromwich", "WBA");
		teamAbbreviations.put("Swansea", "SWA");
		teamAbbreviations.put("Bournemouth", "BOU");
		teamAbbreviations.put("Norwich", "NOR");
		teamAbbreviations.put("Newcastle", "NEW");
		teamAbbreviations.put("Sunderland", "SUN");
		teamAbbreviations.put("Aston Villa", "AVI");
	}
	
	public String getAbbreviation(String teamName) {
		return teamAbbreviations.get(teamName);
	}
	
	public String getAbbreviatedFileName() {
		return getAbbreviation(this.homeTeam).toLowerCase() + "VS" + getAbbreviation(this.awayTeam).toLowerCase() + ".txt";
	}

	/**
	 * @return the homeTeam
	 */
	public String getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @param homeTeam the homeTeam to set
	 */
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public String getAwayTeam() {
		return awayTeam;
	}

	/**
	 * @param awayTeam the awayTeam to set
	 */
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	
	public String toString() {
		return this.homeTeam + " VS " + this.awayTeam;
	}

	/**
	 * @return the teamAbbreviations
	 */
	public Map<String, String> getTeamAbbreviations() {
		return teamAbbreviations;
	}

	/**
	 * @param teamAbbreviations the teamAbbreviations to set
	 */
	public void setTeamAbbreviations(Map<String, String> teamAbbreviations) {
		this.teamAbbreviations = teamAbbreviations;
	}
	
	

}
