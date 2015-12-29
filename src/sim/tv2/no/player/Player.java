package sim.tv2.no.player;

import java.text.DecimalFormat;

/*
 * This class represents a player
 * @author Sindre Moldeklev
 * @version 0.1
 */
public class Player {
	
	private String name;
	private int number, sprints;
	private double distance, avgSpeed, topSpeed;

	

	public Player(String name, int number, int sprints, double distance,
			double avgSpeed, double topSpeed) {
		this.name = name;
		this.number = number;
		this.sprints = sprints;
		this.distance = distance;
		this.avgSpeed = avgSpeed;
		this.topSpeed = topSpeed;
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
	
	public String toString() {
		DecimalFormat df = new DecimalFormat("##.##");
		return df.format(this.distance) + " km. - " + this.name;
	}
}
