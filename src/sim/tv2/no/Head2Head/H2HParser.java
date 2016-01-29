package sim.tv2.no.Head2Head;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.parser.Parser;
import sim.tv2.no.player.H2HPlayer;

public class H2HParser {
	
	public H2HParser() {
		
	}
	
	/**
	 * Method to crawl the Premier League pages for information about a given player
	 * @param url - the player url
	 * @param isHomePlayer - a boolean representing wheter or not the player is playing for the home team or away team
	 */
	public H2HPlayer processPlayerUrl(String url, boolean isHomePlayer) throws IOException{
		H2HPlayer player = null;
		Document doc = null;
		try {
			
			// Get the number and name of the player
			doc = Jsoup.connect(Parser.PROFILE_PAGE + url).get();
			Elements heroName = doc.getElementsByClass("hero-name");
			Elements liElements = heroName.select("ul");
			
			int playerNumber = Integer.parseInt(liElements.select("li").get(0).text());
			String playerName = liElements.select("li").get(1).text();
			
			// Get the data from the overview page
			Elements playerOverviewSection = doc.getElementsByClass("playerprofileoverview");
			Elements tableRows = playerOverviewSection.select("tr");
			double height = Double.parseDouble(tableRows.get(1).select("td").get(3).text().split(" ")[0]);
			int age = Integer.parseInt(tableRows.get(2).select("td").get(1).text());
			double weight = Double.parseDouble(tableRows.get(2).select("td").get(3).text().split(" ")[0]);
			int appearances = Integer.parseInt(tableRows.get(6).select("td").get(1).text());
			int careerGoals = Integer.parseInt(tableRows.get(7).select("td").get(1).text());
			int yellowCards = Integer.parseInt(tableRows.get(8).select("td").get(1).text());
			int redCards = Integer.parseInt(tableRows.get(9).select("td").get(1).text());
			
			// Get the data from the history page
			// Need to get hold of the urlname for the player, the format is name-name-name
			String[] splitUrl = url.split("/");
			String urlPlayerName = splitUrl[splitUrl.length - 1];
			
			doc = Jsoup.connect(Parser.CAREER_PAGE + urlPlayerName).get();
			Elements historyTable = doc.getElementsByClass("playerInfoPod");
			Elements historyTableRows = historyTable.select("tr");
			String gamesThisSeason = historyTableRows.get(1).select("td").get(1).text();

			// Get the data from the stats page
			doc = Jsoup.connect(Parser.STATS_PAGE + urlPlayerName).get();
			Element statsElement = doc.getElementById("clubsTabsAttacking");
			Elements ulStatsElements = statsElement.select("ul");
			int seasonalGoals = Integer.parseInt(ulStatsElements.get(1).select("div").get(1).text());
			int assists = Integer.parseInt(ulStatsElements.get(1).select("div").get(7).text());
			
			// Create a new player
			if(isHomePlayer) {
				player = new H2HPlayer(playerName, age, appearances, careerGoals, yellowCards, redCards, height, weight, gamesThisSeason, assists, playerNumber, seasonalGoals, true);
			} else if(!isHomePlayer) {
				player = new H2HPlayer(playerName, age, appearances, careerGoals, yellowCards, redCards, height, weight, gamesThisSeason, assists, playerNumber, seasonalGoals, false);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException("Ingen spillere med navn " + url + " tilgjengelig\nPrøv et nytt navn");
		}
		return player;
	}

}
