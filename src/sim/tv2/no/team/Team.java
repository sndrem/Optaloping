package sim.tv2.no.team;

public class Team {
	
	private String teamName;
	private String teamAbbreviation;
	private int teamId;
	/**
	 * @param teamName
	 * @param teamAbbreviation
	 * @param teamId
	 */
	public Team(String teamName, String teamAbbreviation, int teamId) {
		this.teamName = teamName;
		this.teamAbbreviation = teamAbbreviation;
		this.teamId = teamId;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
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
	
	

}
