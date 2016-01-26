package sim.tv2.no.match;


/**
 * This class represents a match
 * @author Sindre
 *
 */
public class Match {
	
	private String homeTeam, awayTeam;

	/**
	 * @param homeTeam
	 * @param awayTeam
	 */
	public Match(String homeTeam, String awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
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

}
