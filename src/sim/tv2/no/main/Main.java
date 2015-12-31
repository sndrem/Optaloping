package sim.tv2.no.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;

import sim.tv2.no.gui.Gui;
import sim.tv2.no.parser.Parser;
import sim.tv2.no.player.AvgSpeedComparator;
import sim.tv2.no.player.DistanceComparator;
import sim.tv2.no.player.Player;
import sim.tv2.no.player.SprintComparator;
import sim.tv2.no.player.TopSpeedComparator;

/*
 * Main class for the application
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Main {
	
	private Gui gui;
	private Parser parser;
	
	public static void main(String[] args) {
		new Main();
	}
	
	/*
	 * Constructor for the main class
	 * It sets up the gui, assigns actionlisteners to the buttons and creates an instance of a parser
	 */
	public Main() {
		// TODO Auto-generated constructor stub
		gui = Gui.getInstance();
		setupActionListeners();
		parser = new Parser();
	}
	
	
	/*
	 * Method to assign actionlisteners to the buttons
	 */
	private void setupActionListeners() {
		Events e = new Events();
		gui.getOpenFileBtn().addActionListener(e);
		gui.getRunButton().addActionListener(e);
	}
	
	/*
	 * Method to open a filechooser
	 */
	public void openFile() {
		JFileChooser fileChooser = gui.getFileChooser();
		int returnVal = fileChooser.showOpenDialog(gui);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			gui.getStatusLabel().setText("Åpnet: " + file.getName());
			processFile(file);
		} else {
			
		}
	}
	
	/*
	 * Method to process a file
	 * @params file 	the file to parse
	 * The file should have this format
	 * number name distance sprints avgSpeed topSpeed - all separated by tab (\t)
	 */
	public void processFile(File file) {
		try {
			parser.setPlayers(new ArrayList<Player>());
			parser.parseFile(file);
			gui.getStatusPanel().setBackground(Color.GREEN);
			String status = gui.getStatusLabel().getText();
			status += "\n --> Filen er prosessert";
			gui.getStatusLabel().setText(status);
			gui.getOutputPane().setText(parser.getSize() + " spillere er tilgjengelig");
			System.out.println(parser.getSize());
			gui.getOutputPane().setBorder(new TitledBorder(""));
		} catch (NumberFormatException ex) {
			System.out.println(ex.getMessage());
			gui.showMessage(ex.getMessage());
			gui.getStatusLabel().setText("Det var en feil med formateringen av filen");
			gui.getStatusLabel().setBackground(Color.RED);
			gui.getStatusPanel().setBackground(Color.RED);
			gui.getOutputPane().setText(0 + " spillere er tilgjengelig");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * Method to output the desired numbers of players to the gui
	 * @params numberOfPlayers		the number of players the user wants output of
	 */
	public void calculate(int numberOfPlayers) {
		gui.getOutputPane().setText("");
		List<Player> players = parser.getPlayers();	
		
		int category = gui.getCategoryDropdow().getSelectedIndex();
		sortPlayers(players, category);
		
		if(gui.getOrderCheckBox().isSelected()) {
			Collections.reverse(players);
		} 
		
		if(numberOfPlayers < 0) {
			gui.showMessage("Vennligst fyll inn et positivt tall");
			gui.getNumberOfPlayersArea().setText("5");
		} else {
		
			if(numberOfPlayers <= players.size() && players.size() > 0) {
				gui.getOutputPane().setBorder(new TitledBorder("Viser " + numberOfPlayers + " av " + parser.getSize() + " tilgjengelige spillere"));	
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
				for(int i = 0; i < numberOfPlayers; i++) {
						String output = gui.getOutputPane().getText();
						switch (category) {
						case 0:
							gui.getOutputPane().setText(output + "\n" + players.get(i).toString());
							break;
						case 1:
							gui.getOutputPane().setText(output + "\n" + players.get(i).printSprints());
							break;
						case 2:
							gui.getOutputPane().setText(output + "\n" + players.get(i).printAvgSpeed());
							break;
						case 3:
							gui.getOutputPane().setText(output + "\n" + players.get(i).printTopSpeed());
							break;
						default:
							break;
						}
					}
					
				} else {
					gui.showMessage("Du prøver å vise flere spillere enn det finnes\n Du prøvde: " + numberOfPlayers + ". Det er bare " + players.size() + " spillere tilgjengelig");
					gui.getOutputPane().setBorder(new TitledBorder("Viser " + 0 + " av " + parser.getSize() + " tilgjengelige spillere"));
				}
		} 
	}
	
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
			if(e.getSource() == gui.getOpenFileBtn()) {
				// Open file
				openFile();
				gui.getRunButton().setEnabled(true);
			} else if (e.getSource() == gui.getRunButton()) {
				// Calculate the data
				try {
					int range = Integer.parseInt(gui.getNumberOfPlayersArea().getText().trim());				
					calculate(range);
				} catch(NumberFormatException ex) {
					gui.showMessage("Vennligst fyll inn et tall");
					System.out.println(ex.getMessage());
				}
			}
		}
		
	}

}
