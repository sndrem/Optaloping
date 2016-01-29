package sim.tv2.no.player;

/**
 * This class represents a H2H-player
 * @author Sindre
 *
 */
public class H2HPlayer extends Player {

	private int age;
	private int appearances;
	private int careerGoals;
	private int yellowCards;
	private int redCards;
	private double height;
	private double weight;
	private String gamesThisSeason;
	private int assists;
	private int number;
	private int seasonalGoals;
	private boolean homePlayer;
	
	
	public H2HPlayer(String name, int age, int appearances,
			int careerGoals, int yellowCards, int redCards, double height,
			double weight, String gamesInThisSeason, int assists, int number, int seasonalGoals, boolean homePlayer) {
		super(name);
		this.age = age;
		this.appearances = appearances;
		this.careerGoals = careerGoals;
		this.yellowCards = yellowCards;
		this.redCards = redCards;
		this.height = height;
		this.weight = weight;
		this.gamesThisSeason = gamesInThisSeason;
		this.assists = assists;
		this.number = number;
		this.setSeasonalGoals(seasonalGoals);
		this.setHomePlayer(homePlayer);
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the appearances
	 */
	public int getAppearances() {
		return appearances;
	}

	/**
	 * @param appearances the appearances to set
	 */
	public void setAppearances(int appearances) {
		this.appearances = appearances;
	}

	/**
	 * @return the goals
	 */
	public int getGoals() {
		return careerGoals;
	}

	/**
	 * @param goals the goals to set
	 */
	public void setGoals(int goals) {
		this.careerGoals = goals;
	}

	/**
	 * @return the yellowCards
	 */
	public int getYellowCards() {
		return yellowCards;
	}

	/**
	 * @param yellowCards the yellowCards to set
	 */
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}

	/**
	 * @return the redCards
	 */
	public int getRedCards() {
		return redCards;
	}

	/**
	 * @param redCards the redCards to set
	 */
	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the gamesInThisSeason
	 */
	public String getGamesInThisSeason() {
		return gamesThisSeason;
	}

	/**
	 * @param gamesInThisSeason the gamesInThisSeason to set
	 */
	public void setGamesInThisSeason(String gamesInThisSeason) {
		this.gamesThisSeason = gamesInThisSeason;
	}

	/**
	 * @return the assists
	 */
	public int getAssists() {
		return assists;
	}

	/**
	 * @param assists the assists to set
	 */
	public void setAssists(int assists) {
		this.assists = assists;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the seasonalGoals
	 */
	public int getSeasonalGoals() {
		return seasonalGoals;
	}

	/**
	 * @param seasonalGoals the seasonalGoals to set
	 */
	public void setSeasonalGoals(int seasonalGoals) {
		this.seasonalGoals = seasonalGoals;
	}
	
	public String toString() {
		return this.getName() + " har spilt " + this.getAppearances() + " kamper i Premier League. Han har scoret " + this.getGoals() + " mål i PL. I år har han scoret " + this.getSeasonalGoals() + " mål og gitt assists"
				+ " " + this.getAssists() + " ganger.";
		

	}

	/**
	 * @return the homePlayer
	 */
	public boolean isHomePlayer() {
		return homePlayer;
	}

	/**
	 * @param homePlayer the homePlayer to set
	 */
	public void setHomePlayer(boolean homePlayer) {
		this.homePlayer = homePlayer;
	}
	
	

}
