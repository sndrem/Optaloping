package sim.tv2.no.player;

public class SpillerBorsPlayer extends Player {
	
	private int score;

	public SpillerBorsPlayer(String name, int number, int sprints,
			double distance, double avgSpeed, double topSpeed, String team,
			boolean homeTeam, int score) {
		super(name, number, sprints, distance, avgSpeed, topSpeed, team,
				homeTeam);
		this.setScore(score);
		// TODO Auto-generated constructor stub
	}

	public SpillerBorsPlayer(String name, int score) {
		super(name);
		this.setScore(score);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return super.getName();
	}
	
	public String getDogbarText() {
		return super.getName() + " - " + this.score;
	}

}
