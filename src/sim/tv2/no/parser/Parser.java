package sim.tv2.no.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sim.tv2.no.player.Player;

/*
 * Class for the parser
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Parser {
	
	private List<Player> players;

	public Parser() {
		setPlayers(new ArrayList<Player>());
	}
		
	/*
	 * Method to parse a tab-separated text and return a list of Players
	 * @params text 	The tab-separated text of player data
	 * @return List<Player> the list of players
	 */
	public List<Player> parseText(String text) {
		
		String[] lines = text.split(System.getProperty("line.separator"));
		
		for(String line : lines) {
			String[] columns = line.split("\t");
			
			int number = Integer.parseInt(columns[0]);
			String name = columns[1];
			float distance = Float.parseFloat(columns[2]);
			int sprints = Integer.parseInt(columns[3]);
			float avgSpeed = Float.parseFloat(columns[4]);
			float topSpeed = Float.parseFloat(columns[5]);
			
			Player player = new Player(name, number, sprints, distance, avgSpeed, topSpeed);
			players.add(player);			
		}
		return players;
	}
	
	/*
	 * Method to parse a text file
	 * @params file the file to parse
	 * @return List<Player> the list of players
	 */
	public List<Player> parseText(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			
			String line;
			while((line = br.readLine()) != null) {
				String[] columns = line.split("\t");
				int number = Integer.parseInt(columns[0]);
				String name = columns[1];
				float distance = Float.parseFloat(columns[2]);
				int sprints = Integer.parseInt(columns[3]);
				float avgSpeed = Float.parseFloat(columns[4]);
				float topSpeed = Float.parseFloat(columns[5]);
				
				Player player = new Player(name, number, sprints, distance, avgSpeed, topSpeed);
				players.add(player);
			}
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		br.close();
		return players;
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
	
	public int getSize() {
		if(players != null) {
			return players.size();
		} else return 0;
	}
	
	

}
