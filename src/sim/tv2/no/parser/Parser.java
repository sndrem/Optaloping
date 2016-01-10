package sim.tv2.no.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import sim.tv2.no.player.Player;

/*
 * Class for the parser
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Parser{
	
	private List<Player> players;

	public Parser() {
		setPlayers(new ArrayList<Player>());
	}
		
	
	/*
	 * Method to parse a text file
	 * @params file the file to parse
	 * @return List<Player> the list of players
	 */
	public List<Player> parseFile(File file) throws IOException, NumberFormatException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int lineNumber = 1;
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
				lineNumber++;
			}
			
		} catch(NumberFormatException e) {
			System.out.println(e.getMessage());
			br.close();
			throw new NumberFormatException("Feil format på " + file.getName() + ". Dobbelsjekk tekstfilen og prøv igjen. Sjekk linje: " + lineNumber);
		} catch(IOException e) {
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
	
	/*
	 * Method to get the size of the list of players
	 * @return int the size of the list of players
	 */
	public int getSize() {
		if(players != null) {
			return players.size();
		} else return 0;
	}

}
