package sim.tv2.no.player;

import java.text.DecimalFormat;
import java.util.Comparator;

/*
 * This class represents a player
 * @author Sindre Moldeklev
 * @version 0.1
 */
public class Player implements Comparator<Player>, Comparable<Player> {
	
	private String name;
	private int number, sprints;
	private double distance, avgSpeed, topSpeed;
	private DecimalFormat df;
	
	/*
	 * Constructor the player class
	 * @params name		the name of the player
	 * @params number	the number of the player
	 * @params sprints	number of sprints for the player
	 * @params distance	the distance ran for the player
	 * @params avgSpeed	the average speed for the player
	 * @params topSpeed	the top speed for the player
	 */
	public Player(String name, int number, int sprints, double distance,
			double avgSpeed, double topSpeed) {
		this.name = name;
		this.number = number;
		this.sprints = sprints;
		this.distance = distance;
		this.avgSpeed = avgSpeed;
		this.topSpeed = topSpeed;
		df = new DecimalFormat("##.##");
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the sprints
	 */
	public int getSprints() {
		return sprints;
	}

	/**
	 * @param sprints the sprints to set
	 */
	public void setSprints(int sprints) {
		this.sprints = sprints;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the avgSpeed
	 */
	public double getAvgSpeed() {
		return avgSpeed;
	}

	/**
	 * @param avgSpeed the avgSpeed to set
	 */
	public void setAvgSpeed(double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	/**
	 * @return the topSpeed
	 */
	public double getTopSpeed() {
		return topSpeed;
	}

	/**
	 * @param topSpeed the topSpeed to set
	 */
	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;
	}
	
	/**
	 * Method to print information for the player based on top speed
	 */
	public String printTopSpeed(boolean deleteFirstName) {
		if(!deleteFirstName) {
			return df.format(this.topSpeed) + " km/t. - " + this.getName();
		} else {
			return df.format(this.topSpeed) + " km/t. - " + removeFirstName(this.name);
		}
	}
	
	private String removeFirstName(String name) {
		String[] wholeName = name.split(" ");
		String restOfName = "";
		if(wholeName.length == 1) {
			return name;
		} else {
			for(int i=1; i < wholeName.length; i++) {
				restOfName += " " + wholeName[i];
			}
		}
		return restOfName.trim();
	}

	/**
	 * Method to print information for the player based on avgSpeed
	 */
	public String printAvgSpeed(boolean deleteFirstName) {
		if(!deleteFirstName) {
			return df.format(this.avgSpeed) + " km/t. - " + this.getName();
		} else {
			return df.format(this.avgSpeed) + " km/t. - " + removeFirstName(this.name);
		}
	}
	
	/**
	 * Method to print information for the player based on sprints
	 */
	public String printSprints(boolean deleteFirstName) {
		if(!deleteFirstName) {
			return this.sprints + " - " + this.name;
		} else {
			return this.sprints + " - " + removeFirstName(this.name);
		}
	}
		
	public String toString(boolean deleteFirstName) {
		if(!deleteFirstName) {
			return df.format(this.distance) + " km. - " + this.name;
		} else {
			return df.format(this.distance) + " km. - " + removeFirstName(this.name);
		}
	}
	

	@Override
	public int compareTo(Player otherPlayer) {
		Player player2 = otherPlayer;
		
		if(this.distance > player2.getDistance()) {
			return -1;
		} else if (this.distance < player2.getDistance()) {
			return 1;
		} else return 0;
	}

	@Override
	public int compare(Player o1, Player o2) {
		return 0;
	}
}
