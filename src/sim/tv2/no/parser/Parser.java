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

import sim.tv2.no.match.Match;
import sim.tv2.no.player.Player;
import sim.tv2.no.team.Team;

/*
 * Class for the parser
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Parser{
	
	private List<Team> teams;
	public static final String PROFILE_PAGE = "http://www.premierleague.com/en-gb/players/profile.html/";
	public static final String CAREER_PAGE = "http://www.premierleague.com/en-gb/players/profile.career-history.html/";
	public static final String STATS_PAGE = "http://www.premierleague.com/en-gb/players/profile.statistics.html/";
	public static final String TEAM_PAGE = "http://www.premierleague.com/en-gb/players/index.html?paramSearchType=BY_CLUB&paramSeason=squad&paramClubId=";
	public static final String PREMIER_LEAGUE = "http://www.premierleague.com/";
	private static final String ALT_OM_FOTBALL_PL = "http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=230&useFullUrl=false";

	public Parser() {
		setTeams(new ArrayList<Team>());
	}
		
	
	/*
	 * Method to parse a text file
	 * @params file the file to parse
	 * @return List<Player> the list of players
	 */
	public List<Team> parseFile(File file) throws IOException, NumberFormatException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		int lineNumber = 1;
		List<Player> teamPlayers = new ArrayList<Player>();
		boolean homeOrAway = false;
		try {
			String line;
			String teamName = "";
			while((line = br.readLine()) != null) {
				if(line.toLowerCase().startsWith("home")) {
					teamName = line.split(":")[1];
					homeOrAway = true;
					continue;
				} else if(line.toLowerCase().startsWith("away")) {
					teamName = line.split(":")[1];
					homeOrAway = false;
					continue;
				}
				
				String[] columns = line.split("\t");
				int number = Integer.parseInt(columns[0]);
				String name = columns[1];
				float distance = Float.parseFloat(columns[2]);
				int sprints = Integer.parseInt(columns[3]);
				float avgSpeed = Float.parseFloat(columns[4]);
				float topSpeed = Float.parseFloat(columns[5]);
				
				Player player = new Player(name, number, sprints, distance, avgSpeed, topSpeed, teamName, homeOrAway);
				teamPlayers.add(player);
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
		
		dividePlayers(teamPlayers);
		
		return getTeams();
	}
	
	private List<Team> dividePlayers(List<Player> players) {
		List<Player> homeTeamPlayers = new ArrayList<Player>();
		List<Player> awayTeamPlayers = new ArrayList<Player>();
		for(Player pl : players) {
			if(pl.isHomePlayer()) {
				homeTeamPlayers.add(pl);
			} else if(!pl.isHomePlayer()) {
				awayTeamPlayers.add(pl);
			}
		}
		if(homeTeamPlayers.size() > 0) {
			Team homeTeam = new Team(homeTeamPlayers.get(0).getTeam());
			
			homeTeam.getPlayers().addAll(homeTeamPlayers);
			getTeams().add(homeTeam);
		}
		
		if(awayTeamPlayers.size() > 0) {
			Team awayTeam = new Team(awayTeamPlayers.get(0).getTeam());
			awayTeam.getPlayers().addAll(awayTeamPlayers);
			getTeams().add(awayTeam);
		}
		
		return getTeams();
	}
	
	/**
	 * Method to fetch the name of the players for a given club
	 * @param club id - the id for the club
	 * @return Returns a map of the players
	 */
	public Map<String, String> fetchPlayers(int id) {
		Map<String, String> players = new HashMap<String, String>();
		try {
					
				Document teamPage = Jsoup.connect(Parser.TEAM_PAGE + id).get();
				Elements playersTable = teamPage.getElementsByClass("players-table");
				Elements tablePlayers = playersTable.select("tbody tr");
				for(Element tableRow : tablePlayers) {
					Elements tableData = tableRow.select("td");
					String playerName = tableData.get(0).text();
					String playerUrl = tableData.get(0).select("a").attr("href");
					players.put(playerName, playerUrl);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return players;
	}
	
	/**
	 * Method to read the team names and return a hashmap
	 * @param fileName the fileName of the file
	 * 
	 */
	public Map<String, Team> loadTeamNames(String fileName) {
		Map<String, Team> teams = new HashMap<String, Team>();
		File file = new File(fileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				String[] teamInfo = line.split(";");
				String teamName = teamInfo[0];
				Integer teamIndex = Integer.parseInt(teamInfo[1]);
				String abbreviation = teamInfo[2];
				Team team = new Team(teamName, abbreviation, teamIndex);
				teams.put(teamName, team);
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
	
	public List<Match> getNextMatches() {
		List<Match> matches = new ArrayList<Match>();
		try {
			Document doc = Jsoup.connect(Parser.ALT_OM_FOTBALL_PL).get();
			Element nextMatchSection = doc.getElementById("sd_fixtures_table_next");
			Elements nextMatchRows = nextMatchSection.select("tr");
			for(Element row : nextMatchRows) {
				String homeTeam = row.getElementsByClass("sd_fixtures_home").text();
				String awayTeam = row.getElementsByClass("sd_fixtures_away").text();
				Match match = new Match(homeTeam, awayTeam);
				matches.add(match);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matches;
	}
	
	public int getSize() {
		if(this.teams != null) {
			int size = 0;
			for(Team t : teams) {
				for(Player p : t.getPlayers()) {
					size++;
				}
			}
			return size;
		}
		return 0;
	}


	/**
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}


	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
}
