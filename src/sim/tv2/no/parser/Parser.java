package sim.tv2.no.parser;

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
		setPlayers(new ArrayList<>());
	}
	
	public static void main(String[] args) {
		Parser p = new Parser();
		String testString = "2	Joel Ward	9.80	48	6.15	29.23\n" +
				"4	Brede Hangeland	9.79	19	6.12	31.95\n" +
				"6	Scott Dann	9.23	29	5.79	30.42\n" +
				"7	Yohan Cabaye	11.28	53	7.09	29.76\n" +
				"8	Patrick Bamford	5.52	29	6.71	31.66\n" +
				"11	Wilfried Zaha	10.45	63	6.56	34.33\n" +
				"13	Wayne Hennessey	4.84	5	3.04	28.42\n" +
				"14	Lee Chung-yong	2.89	20	7.20	29.37\n" +
				"15	Mile Jedinak	11.17	41	7.01	28.27\n" +
				"18	James McArthur	9.67	55	7.64	31.90\n" +
				"22	Jordon Mutch	2.38	15	7.26	31.12\n" +
				"23	Pape Souaré	10.02	62	6.30	32.48\n" +
				"29	Marouane Chamakh	5.36	35	6.87	29.94\n" +
				"42	Jason Puncheon	8.56	52	7.17	29.45\n";
		List<Player> play = p.parseText(testString);
		
		for(Player pl : play) {
			System.out.println(pl);
		}
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
