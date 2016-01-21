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
import java.util.List;
import java.util.Map;

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
import sim.tv2.no.player.Player;
import sim.tv2.no.webDriver.OpenOpta;

/*
 * Main class for the application
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Main {
	
	private Gui gui;
	private Parser parser;
	private OpenOpta optaWebDriver = new OpenOpta();
	private Map<String, Integer> teams = new HashMap<String, Integer>();
	private Map<String, String> homePlayers;
	private Map<String, String> awayPlayers;
	
	
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
		teams = parser.loadTeamNames(".teamNames.txt");
		for(String teamName : teams.keySet()) {
			gui.getHomeTeamNames().addItem(teamName);
		}
		
		setupPlayers(teams.get(gui.getHomeTeamNames().getItemAt(0)), 0);
		
		for(String teamName : teams.keySet()) {
			gui.getAwayTeamNames().addItem(teamName);
		}
		
		setupPlayers(teams.get(gui.getAwayTeamNames().getItemAt(0)), 1);
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
				System.out.println(player);
				gui.getHomeTeamModel().addElement(player);
			}
			break;
		case 1:
			awayPlayers = parser.fetchPlayers(teamId);
			gui.getAwayTeamModel().removeAllElements();
			for(String player : homePlayers.keySet()) {
				System.out.println(player);
				gui.getAwayTeamModel().addElement(player);
			}
			break;
		default:
			break;
		}
	}
	
	private void processPlayerUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(Parser.PREMIER_LEAGUE + url).get();
			Elements heroName = doc.getElementsByClass("hero-name");
			Elements liElements = heroName.select("ul");
			
			int playerNumber = Integer.parseInt(liElements.select("li").get(0).text());
			String playerName = liElements.select("li").get(1).text();
			
				
			Elements playerOverviewSection = doc.getElementsByClass("playerprofileoverview");
			Elements tableRows = playerOverviewSection.select("tr");
			double height = Double.parseDouble(tableRows.get(1).select("td").get(3).text().split(" ")[0]);
			int age = Integer.parseInt(tableRows.get(2).select("td").get(1).text());
			double weight = Double.parseDouble(tableRows.get(2).select("td").get(3).text().split(" ")[0]);
			int appearances = Integer.parseInt(tableRows.get(6).select("td").get(1).text());
			int goals = Integer.parseInt(tableRows.get(7).select("td").get(1).text());
			int yellowCards = Integer.parseInt(tableRows.get(8).select("td").get(1).text());
			int redCards = Integer.parseInt(tableRows.get(9).select("td").get(1).text());
			System.out.println(appearances);
//			System.out.println(playerOverviewSection.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		gui.getHomePlayerNames().addActionListener(e);
		gui.getAwayPlayerNames().addActionListener(e);
		
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
			parser.setPlayers(new ArrayList<Player>());
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
	public void calculate(int numberOfPlayers, int category) {
		// øk antall spillere med "en" fordi JComboBox teller fra 0 og det kan forvirre sluttbrukeren.
		numberOfPlayers++;
		gui.getOutputPane().setText("");
		List<Player> players = parser.getPlayers();	
		
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
					gui.getOutputPane().setBorder(new TitledBorder("Viser " + numberOfPlayers + " av " + parser.getSize() + " tilgjengelige spillere"));	
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
				calculate(gui.getNumberOfPlayersArea().getSelectedIndex(), gui.getCategoryDropdow().getSelectedIndex());
			} else if (e.getSource() == gui.getSelectTextCheckBox()) {
				selectAllText(gui.getSelectTextCheckBox().isSelected());
			} else if(e.getSource() == gui.getHomeTeamNames()) {
				String teamName = (String) gui.getHomeTeamNames().getSelectedItem();
				parser.fetchPlayers(teams.get(teamName));
				setupPlayers(teams.get(teamName), 0);
			} else if (e.getSource() == gui.getAwayTeamNames()) {
				String teamName = (String) gui.getAwayTeamNames().getSelectedItem();
				parser.fetchPlayers(teams.get(teamName));
				setupPlayers(teams.get(teamName), 1);
			} else if(e.getSource() == gui.getHomePlayerNames()) {
				String homePlayerUrl = homePlayers.get((String) gui.getHomePlayerNames().getSelectedItem());
				System.out.println(homePlayerUrl);
				if(homePlayerUrl != null) {
					processPlayerUrl(homePlayerUrl);
				}
			} else if (e.getSource() == gui.getAwayPlayerNames()) {
				System.out.println("bortelag yeeah");
			}
		}
		
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
				calculate(gui.getNumberOfPlayersArea().getSelectedIndex(), gui.getCategoryDropdow().getSelectedIndex());
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
}
