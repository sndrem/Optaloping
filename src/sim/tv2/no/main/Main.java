package sim.tv2.no.main;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.comparators.AvgSpeedComparator;
import sim.tv2.no.comparators.DistanceComparator;
import sim.tv2.no.comparators.SprintComparator;
import sim.tv2.no.comparators.TopSpeedComparator;
import sim.tv2.no.gui.Gui;
import sim.tv2.no.parser.Parser;
import sim.tv2.no.player.H2HPlayer;
import sim.tv2.no.player.Player;
import sim.tv2.no.team.Team;
import sim.tv2.no.webDriver.OpenOpta;

/*
 * Main class for the application
 * @author Sindre Moldeklev
 * @version 0.1
 * 
 * .replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""); kan bli brukt for å fjerne ordinals fra datoene på Physioroom
 */

public class Main {
	
	private Gui gui;
	private Parser parser;
	private OpenOpta optaWebDriver = new OpenOpta();
	private Map<String, Team> teams = new HashMap<String, Team>();
	private Map<String, String> homePlayers;
	private Map<String, String> awayPlayers;
	
	private H2HPlayer homePlayer;
	private H2HPlayer awayPlayer;
	
	public static void main(String[] args) {
		new Main();
	}
	
	/*
	 * Constructor for the main class
	 * It sets up the gui, assigns actionlisteners to the buttons and creates an instance of a parser
	 */
	public Main() {
		// TODO Auto-generated constructor stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {			
				gui = Gui.getInstance();
				parser = new Parser();
				setupTeams();
				setupActionListeners();
			}
		});
	}
	
	/**
	 * Method to initialize the teams used for H2H
	 */
	private void setupTeams() {
//		teams = parser.loadTeamNames("lagnavn/teamNames.txt");
		 teams.put("Arsenal", new Team("Arsenal", "ARS", 3));
		 teams.put("Aston Villa", new Team("Aston Villa", "AVI", 7));
		 teams.put("Bournemouth", new Team("Bournemouth", "BOU", 91));
		 teams.put("Chelsea", new Team("Chelsea", "CHE", 8));
		 teams.put("Crystal Palace", new Team("Crystal Palace", "CRY", 31));
		 teams.put("Everton", new Team("Everton", "EVE", 11));
		 teams.put("Leicester", new Team("Leicester", "LEI", 13));
		 teams.put("Liverpool", new Team("Liverpool", "LIV", 14));
		 teams.put("Man. City", new Team("Man. City", "MAC", 43));
		 teams.put("Man. United", new Team("Man. United", "MAU", 1));
		 teams.put("Newcastle", new Team("Newcastle", "NEW", 4));
		 teams.put("Norwich", new Team("Norwich", "NOR", 45));
		 teams.put("Southampton", new Team("Southampton", "SOU", 20));
		 teams.put("Stoke", new Team("Stoke", "STO", 110));
		 teams.put("Sunderland", new Team("Sunderland", "SUN", 56));
		 teams.put("Swansea", new Team("Swansea", "SWA", 80));
		 teams.put("Tottenham", new Team("Tottenham", "TOT", 6));
		 teams.put("Watford", new Team("Watford", "WAT", 57));
		 teams.put("West Bromwich", new Team("West Bromwich", "WBA", 35));
		 teams.put("West Ham", new Team("West Ham", "WHA", 21));
		for(String teamName : teams.keySet()) {
			gui.getHomeTeamNames().addItem(teamName);
		}
		
		setupPlayers(teams.get(gui.getHomeTeamNames().getItemAt(0)).getTeamId(), 0);
		
		for(String teamName : teams.keySet()) {
			gui.getAwayTeamNames().addItem(teamName);
		}
		
		setupPlayers(teams.get(gui.getAwayTeamNames().getItemAt(0)).getTeamId(), 1);
	}
	
	/**
	 * Method to load the players into the gui
	 */
	private void setupPlayers(int teamId, int typeOfTeam) {
		
		switch (typeOfTeam) {
		case 0:
			homePlayers = parser.fetchPlayers(teamId);
			gui.getHomeTeamModel().removeAllElements();
			for(String player : homePlayers.keySet()) {
				gui.getHomeTeamModel().addElement(player);
			}
			break;
		case 1:
			awayPlayers = parser.fetchPlayers(teamId);
			gui.getAwayTeamModel().removeAllElements();
			for(String player : awayPlayers.keySet()) {
				gui.getAwayTeamModel().addElement(player);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * Method to crawl the Premier League pages for information about a given player
	 * @param url - the player url
	 * @param isHomePlayer - a boolean representing wheter or not the player is playing for the home team or away team
	 */
	private void processPlayerUrl(String url, boolean isHomePlayer) {
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
				homePlayer = new H2HPlayer(playerName, age, appearances, careerGoals, yellowCards, redCards, height, weight, gamesThisSeason, assists, playerNumber, seasonalGoals);
			} else if(!isHomePlayer) {
				awayPlayer = new H2HPlayer(playerName, age, appearances, careerGoals, yellowCards, redCards, height, weight, gamesThisSeason, assists, playerNumber, seasonalGoals);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			gui.showMessage("Ingen spillere med navn " + url + " tilgjengelig\nPrøv et nytt navn");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method to show the player information for both players
	 * @param homePlayer
	 * @param awayPlayer
	 */
	private void showPlayerInfo(H2HPlayer homePlayer, H2HPlayer awayPlayer) {
		Team homeTeam = teams.get(gui.getHomeTeamNames().getSelectedItem());
		Team awayTeam = teams.get(gui.getAwayTeamNames().getSelectedItem());
		
		String info = "*SUPER S16 " + homePlayer.getName() + "\n"
				+ "VS\n"
				+ awayPlayer.getName() + "\n"
				+ "*K:\\Sporten\\Grafikk\\Engelsk fotball\\01 Premier League\\Laglogoer\\Vinkla\\" + homeTeam.getTeamAbbreviation().toUpperCase() + "_v.png\n"
				+ "*K:\\Sporten\\Grafikk\\Engelsk fotball\\01 Premier League\\Laglogoer\\Vinkla\\" + awayTeam.getTeamAbbreviation().toUpperCase() + "_h.png\n"
				+"Alder\n"
				+ homePlayer.getAge() + " år\n"
				+ awayPlayer.getAge() + " år\n"
				+"Høyde\n"
				+ convertHeight(homePlayer.getHeight()) + " cm\n"
				+ convertHeight(awayPlayer.getHeight()) + " cm\n"
				+"PL-kamper totalt\n"
				+ homePlayer.getAppearances() + "\n"
				+ awayPlayer.getAppearances() + "\n"
				+ "Mål totalt\n"
				+ homePlayer.getGoals() + "\n"
				+ awayPlayer.getGoals() + "\n"
				+ "PL-kamper 15/16\n"
				+ homePlayer.getGamesInThisSeason() + "\n"
				+ awayPlayer.getGamesInThisSeason() + "\n"
				+ "Mål 15/16\n"
				+ homePlayer.getSeasonalGoals() + "\n"
				+ awayPlayer.getSeasonalGoals() + "\n"
				+ "Assists 15/16\n"
				+ homePlayer.getAssists() + "\n"
				+ awayPlayer.getAssists() + " <00:01.10";
		gui.getOutputH2HArea().setText(info);
		
	}
	
	/**
	 * Method to convert a double for height, into an integer
	 * @param height
	 * @return an integer representing the height
	 * TODO Fikse denne metoden
	 */
	private int convertHeight(Double height) {
		String temp = "";
		temp += height;
		temp = temp.replace(".", "");
		// Hvis vi får en spiller som er feks 190 høy, så kommer det fra PL som 1.9, da må vi sjekke lengden og 
		// legge til en ekstra null for padding
		if(temp.length() <= 2) {
			temp += 0;
		}
		return Integer.parseInt(temp.replace(".", ""));
	}
	
	/*
	 * Method to assign actionlisteners to the buttons
	 */
	private void setupActionListeners() {
		Events e = new Events();
		gui.getOpenFileBtn().setAction(new OpenFileAction("Åpne tekstfil"));
		gui.getOpenFileMenuItem().setAction(new OpenFileAction("Åpne tekstfil"));
		gui.getOpenFileMenuItem().setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		gui.getRunButton().setAction(new RunAction("Kjør"));
		gui.getCopyButton().setAction(new CopyAction("Kopier til clipboard"));
		gui.getOpenOptaItem().addActionListener(e);
		gui.getExitItem().setAction(new ExitAction("Lukk"));
		gui.getExitItem().setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		gui.getRemoveFirstNameCheckBox().setAction(new RunAction("Fjern fornavn"));
		gui.getOrderCheckBox().setAction(new RunAction("Reverse"));
		gui.getCategoryDropdow().setAction(new RunAction());
		gui.getNumberOfPlayersArea().addActionListener(new RunAction());
		gui.getShowCategoryCheckBox().setAction(new RunAction("Vis kategorinavn"));
		gui.getSelectTextCheckBox().addActionListener(e);
		
		gui.getHomeTeamNames().addActionListener(e);
		gui.getAwayTeamNames().addActionListener(e);
		gui.getH2hButton().addActionListener(e);
		
		gui.getGenerateReportButton().addActionListener(new GenerateRapportAction("Full rapport"));
		
		// Key events
		gui.getOpenFileBtn().getActionMap().put("openFile", new OpenFileAction());
		gui.getOpenFileBtn().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control O"), "openFile");
		gui.getOpenFileBtn().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK), "openFile");
		
		gui.getRunButton().getActionMap().put("runCalculations", new RunAction());
		gui.getRunButton().getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "runCalculations");
		
		gui.getCopyButton().getActionMap().put("copyContent", new CopyAction());
		gui.getCopyButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), "copyContent");
		
		gui.getExitItem().getActionMap().put("exitAction", new ExitAction());
		gui.getExitItem().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl Q"), "exitAction");
	}
	
	/*
	 * Method to open a filechooser
	 */
	public void openFile() {
		JFileChooser fileChooser = gui.getFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Tekstfiler", "txt", "text");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(gui);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File[] files = fileChooser.getSelectedFiles();
			if(files.length == 1) {
				gui.getStatusTextArea().setText("Åpnet: " + files[0].getName());
			} else {
				gui.getStatusTextArea().setText("Åpnet flere filer");
			}
			processFiles(files);
			gui.getRunButton().setEnabled(true);
			gui.getCategoryDropdow().setEnabled(true);
			gui.getRemoveFirstNameCheckBox().setEnabled(true);
			gui.getOrderCheckBox().setEnabled(true);
			gui.getRunButton().requestFocus();
		} 
	}
	
	/*
	 * Method to process a file
	 * @params file 	the file to parse
	 * The file should have this format
	 * number name distance sprints avgSpeed topSpeed - all separated by tab (\t)
	 */
	public void processFiles(File[] files) {
		if(files.length > 0) {
			parser.setTeams(new ArrayList<Team>());
			gui.getStatusPanel().setBackground(Color.GREEN);
			gui.getStatusTextArea().setBackground(Color.GREEN);
			for(File file : files) {
				try {
					parser.parseFile(file);
					showFileProcessInfo(file.getName());					
				} catch (NumberFormatException ex) {
					System.out.println(ex.getMessage());
					if(files.length == 1) {
						showFileProcessError(ex, file.getName(), Color.RED);
					} else {
						showFileProcessError(ex, file.getName(), Color.YELLOW);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			gui.getOutputPane().setText(parser.getSize() + " spillere er tilgjengelig");
			gui.getOutputPane().setBorder(new TitledBorder(""));
			populateNumbersArea(parser.getSize());
			gui.getNumberOfPlayersArea().requestFocus();
		}
	}

	private void populateNumbersArea(int size) {
		// Clear items if we load a new file
		gui.getNumberOfPlayersArea().removeAllItems();
		
		for(int i = 1; i <= size; i++) {
			gui.getNumberOfPlayersArea().addItem(i);
		}
		
		// If number of players >= 5, then we default show top 5
		if(size >= 5) {
			gui.getNumberOfPlayersArea().setSelectedIndex(4);
		}
		
	}

	private void showFileProcessError(NumberFormatException ex, String fileName, Color color) {
		if(ex != null) {
			gui.showMessage(ex.getMessage());
		}
		String status = gui.getStatusTextArea().getText();
		gui.getStatusTextArea().setText("Det var en feil med formateringen av fil: " + fileName + "\n\n" + status);
		gui.getStatusTextArea().setBackground(color);
		gui.getStatusPanel().setBackground(color);
		gui.getOutputPane().setText(0 + " spillere er tilgjengelig");
	}

	private void showFileProcessInfo(String fileName) {
		String status = gui.getStatusTextArea().getText();
		status += " \n--> " + fileName + " er prosessert";
		gui.getStatusTextArea().setText(status);
	}


	/*
	 * Method to output the desired numbers of players to the gui
	 * @params numberOfPlayers		the number of players the user wants output of
	 */
	public String calculate(int numberOfPlayers, int category, boolean fullRapport) {
		// øk antall spillere med "en" fordi JComboBox teller fra 0 og det kan forvirre sluttbrukeren.
		numberOfPlayers++;
		String teamName = "";
		gui.getOutputPane().setText(teamName);
		List<Team> teams = parser.getTeams();
		
		for(int index = 0; index < teams.size(); index++) {
			Team team = teams.get(index);
			List<Player> players = team.getPlayers();
			
			teamName = gui.getOutputPane().getText();
			
			
			if(!fullRapport) {
				if(index <= 0) {
					teamName += team.getTeamName() + "\n";
				} else {
					teamName += "\n\n" + team.getTeamName() + "\n";
				}
			} else {
				if(index <= 0) {
					teamName += "\n\n" + team.getTeamName() + "\n";
				} else {
					teamName += "\n\n" + team.getTeamName() + "\n";
				}
			}
			
			gui.getOutputPane().setText(teamName);
			
			sortPlayers(players, category);
			
			if(gui.getOrderCheckBox().isSelected()) {
				Collections.reverse(players);
			}
			
			boolean removeName = gui.getRemoveFirstNameCheckBox().isSelected();		
		
			if(numberOfPlayers < 0) {
				gui.showMessage("Vennligst fyll inn et positivt tall");
				gui.getNumberOfPlayersArea().setSelectedIndex(4);
			} else {
					if(numberOfPlayers <= players.size() && players.size() > 0) {
//						gui.getOutputPane().setBorder(new TitledBorder("Viser " + numberOfPlayers + " av " + parser.getSize() + " tilgjengelige spillere"));	
						if(gui.getShowCategoryCheckBox().isSelected()) {
							switch (category) {
								case 0:
									gui.getOutputPane().setText("\nDistanse løpt\n");
									break;
								case 1:
									gui.getOutputPane().setText("\nAntall sprinter\n");
									break;
								case 2:
									gui.getOutputPane().setText("\nGjennomsnittsfart\n");
									break;
								case 3:
									gui.getOutputPane().setText("\nToppfart\n");
									break;
								default:
									break;
								}
							}
						
						
							for(int i = 0; i < numberOfPlayers; i++) {
								String output = gui.getOutputPane().getText();
								switch (category) {
								case 0:
									gui.getOutputPane().setText(output + "\n" + players.get(i).toString(removeName).trim());
									break;
								case 1:
									gui.getOutputPane().setText(output + "\n" + players.get(i).printSprints(removeName).trim());
									break;
								case 2:
									gui.getOutputPane().setText(output + "\n" + players.get(i).printAvgSpeed(removeName).trim());
									break;
								case 3:
									gui.getOutputPane().setText(output + "\n" + players.get(i).printTopSpeed(removeName).trim());
									break;
								default:
									break;
								}
							}
							
							selectAllText(gui.getSelectTextCheckBox().isSelected());
							
						} else {
							gui.showMessage("Du prøver å vise flere spillere enn det finnes\n Du prøvde: " + numberOfPlayers + ". Det er bare " + players.size() + " spillere tilgjengelig");
							gui.getOutputPane().setBorder(new TitledBorder("Viser " + 0 + " av " + parser.getSize() + " tilgjengelige spillere"));
							gui.getNumberOfPlayersArea().setSelectedIndex(4);
					}
				} 
			
			}
			return gui.getOutputPane().getText();
		} 
	
	/**
	 * Method select all the text in the gui
	 * @param checked checks wether the user wants to select all text.
	 */
	private void selectAllText(boolean checked) {
		if(checked) {
			gui.getOutputPane().requestFocusInWindow();
			gui.getOutputPane().selectAll();
		} else {
			gui.getNumberOfPlayersArea().requestFocus();
		}
	}
	
	private String convertToPlayerUrl(String name) {
		return name.replace(" ", "-").toLowerCase();
	}

	/**
	 * Method to copy the content of the outputPane to the system clipboard
	 */
	private void copyContent() {
		String content = gui.getOutputPane().getText();
		StringSelection selection = new StringSelection(content);
		Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipBoard.setContents(selection, null);
	}

	
	/**
	 * Method to sort the players
	 * @param players the players to sort
	 * @param category the category to sort based on
	 */
	private void sortPlayers(List<Player> players, int category) {
		switch (category) {
		case 0:
			Collections.sort(players, new DistanceComparator());
			break;
		case 1:
			Collections.sort(players, new SprintComparator());
			break;
		case 2:
			Collections.sort(players, new AvgSpeedComparator());
			break;
		case 3:
			Collections.sort(players, new TopSpeedComparator());
			break;
		default:
			break;
		}
	}
	
	/*
	 * Private class that implements the ActionListener interface
	 */
	private class Events implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == gui.getOpenOptaItem()) {
				gui.getStatusTextArea().setText(gui.getStatusTextArea().getText() + " Åpner en tab for hver kamp i Firefox. Dette kan ta litt tid");
				optaWebDriver.openOptaTabs();
			} else if(e.getSource() == gui.getCategoryDropdow()) {
				calculate(gui.getNumberOfPlayersArea().getSelectedIndex(), gui.getCategoryDropdow().getSelectedIndex(), false);
			} else if (e.getSource() == gui.getSelectTextCheckBox()) {
				selectAllText(gui.getSelectTextCheckBox().isSelected());
			} else if (e.getSource() == gui.getH2hButton()) {
				// Hvis brukeren ikke skriver i noen av tekstfeltene
				if(!gui.getHomeTextSearchBox().isSelected() && !gui.getAwayTexSearchCheckBox().isSelected()) {
					
					String homePlayerUrl = homePlayers.get((String) gui.getHomePlayerNames().getSelectedItem());
					if(homePlayerUrl != null) {
						processPlayerUrl(homePlayerUrl, true);
					}
					
					String awayPlayerUrl = awayPlayers.get((String) gui.getAwayPlayerNames().getSelectedItem());
					
					if(awayPlayerUrl != null) {
						processPlayerUrl(awayPlayerUrl, false);
					}
				// Hvis brukeren skriver i hjemmelaget sitt tekstfelt, men ikke bortelaget
				} else if (gui.getHomeTextSearchBox().isSelected() && !gui.getAwayTexSearchCheckBox().isSelected()) {
					String playerUrlName = convertToPlayerUrl(gui.getHomeTeamSearch().getText());
					System.out.println(playerUrlName);
					processPlayerUrl(playerUrlName, true);
					
					String awayPlayerUrl = awayPlayers.get((String) gui.getAwayPlayerNames().getSelectedItem());
					
					if(awayPlayerUrl != null) {
						processPlayerUrl(awayPlayerUrl, false);
					}
				
				// Hvis brukeren skriver i bortelaget sitt tekstfelt, men ikke i hjemmelaget
				} else if(!gui.getHomeTextSearchBox().isSelected() && gui.getAwayTexSearchCheckBox().isSelected()) {
					String playerUrlName = convertToPlayerUrl(gui.getAwayTeamSearch().getText());
					System.out.println(playerUrlName);
					processPlayerUrl(playerUrlName, false);
					
					String homePlayerUrl = homePlayers.get((String) gui.getHomePlayerNames().getSelectedItem());
					
					if(homePlayerUrl != null) {
						processPlayerUrl(homePlayerUrl, true);
					}
				// Hvis brukeren skriver i både hjemmelaget og bortelaget sitt felt
				} else if (gui.getHomeTextSearchBox().isSelected() && gui.getAwayTexSearchCheckBox().isSelected()) {
					String homePlayerUrl = convertToPlayerUrl(gui.getHomeTeamSearch().getText());
					String awayPlayerUrl = convertToPlayerUrl(gui.getAwayTeamSearch().getText());
					processPlayerUrl(homePlayerUrl, true);
					processPlayerUrl(awayPlayerUrl, false);
				}
				showPlayerInfo(homePlayer, awayPlayer);
				
				
			} else if(e.getSource() == gui.getHomeTeamNames()) {
				String teamName = (String) gui.getHomeTeamNames().getSelectedItem();
				parser.fetchPlayers(teams.get(teamName).getTeamId());
				setupPlayers(teams.get(teamName).getTeamId(), 0);
			} else if (e.getSource() == gui.getAwayTeamNames()) {
				String teamName = (String) gui.getAwayTeamNames().getSelectedItem();
				parser.fetchPlayers(teams.get(teamName).getTeamId());
				setupPlayers(teams.get(teamName).getTeamId(), 1);
			}
		}
	}
	
	private void generateRapport() {
		int categories = gui.getCategoryDropdow().getItemCount();
//		gui.getShowCategoryCheckBox().setSelected(true);
		String output = "";
		gui.getOutputPane().setText(output);
		for(int i = 0; i < categories; i++) {
			output += calculate(5, i, true);
		}
		gui.getOutputPane().setText(output);
	}
	
	/**
	 * Private class for key listeners
	 * @author Sindre Moldeklev
	 */
	private class OpenFileAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2528328845681249227L;
		
		public OpenFileAction() {
			
		}
		
		public OpenFileAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			openFile();
		}	

	}
	
	private class RunAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2699857934124111118L;

		public RunAction() {
			
		}
		
		public RunAction(String name) {
			super(name);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				calculate(gui.getNumberOfPlayersArea().getSelectedIndex(), gui.getCategoryDropdow().getSelectedIndex(), false);
			} catch(IllegalArgumentException ex) {
				System.out.println("Du har ikke lest inn en fil enda");
				System.out.println(ex.getMessage());
			}
		}
		
	}
	
	private class CopyAction extends AbstractAction {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1143123678277652149L;

		public CopyAction() {
			
		}
		
		public CopyAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			copyContent();
		}
		
	}
	
	private class ExitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3910572228923061194L;

		public ExitAction() {
			
		}
		
		public ExitAction(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			int choice = JOptionPane.showConfirmDialog(gui, "Er du sikker på at du vil stenge programmet", "Stenge programmet?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(choice == 0) {
				System.exit(0);
			}
		}
	}
	
	private class GenerateRapportAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7842770879089217159L;
		
		public GenerateRapportAction() {
			// TODO Auto-generated constructor stub
		}
		
		public GenerateRapportAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			generateRapport();
			
		}
		
	}
}
