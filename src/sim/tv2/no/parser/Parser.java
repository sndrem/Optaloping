package sim.tv2.no.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.player.Player;

/*
 * Class for the parser
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Parser{
	
	private List<Player> players;
	private static final String PROFILE_PAGE = "http://www.premierleague.com/en-gb/players/profile.html/";
	private static final String CAREER_PAGE = "http://www.premierleague.com/en-gb/players/profile.career-history.html/";
	private static final String STATS_PAGE = "http://www.premierleague.com/en-gb/players/profile.statistics.html/";
	private static final String TEAM_PAGE = "http://www.premierleague.com/en-gb/players/index.html?paramSearchType=BY_CLUB&paramSeason=squad&paramClubId=";

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
	 * Method to fetch the name of the players for a given club
	 * @param club id - the id for the club
	 * @return Returns a map of the players
	 */
	public Map<String, String> fetchPlayers(int id) {
		Map<String, String> players = new HashMap<String, String>();
		try {
			Document teamPage = Jsoup.connect(this.TEAM_PAGE + id).get();
			Elements playersTable = teamPage.getElementsByClass("players-table");
			Elements tablePlayers = playersTable.select("tbody tr");
			int index = 0;
			for(Element elem : tablePlayers) {
				System.out.println(elem.text());
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method to read the team names and return a hashmap
	 * 
	 */
	public Map<String, Integer> loadTeamNames(String fileName) {
		Map<String, Integer> teams = new HashMap<String, Integer>();
		File file = new File(fileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				String[] teamInfo = line.split(";");
				String teamName = teamInfo[0];
				Integer teamIndex = Integer.parseInt(teamInfo[1]);
				teams.put(teamName, teamIndex);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return teams;
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
