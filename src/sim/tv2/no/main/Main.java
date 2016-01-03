package sim.tv2.no.main;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import sim.tv2.no.gui.Gui;
import sim.tv2.no.parser.Parser;
import sim.tv2.no.player.AvgSpeedComparator;
import sim.tv2.no.player.DistanceComparator;
import sim.tv2.no.player.Player;
import sim.tv2.no.player.SprintComparator;
import sim.tv2.no.player.TopSpeedComparator;
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
				setupActionListeners();
			}
		});
		parser = new Parser();
	}
	
	
	/*
	 * Method to assign actionlisteners to the buttons
	 */
	private void setupActionListeners() {
		Events e = new Events();
		gui.getOpenFileBtn().addActionListener(e);
		gui.getRunButton().addActionListener(e);
		gui.getCopyButton().addActionListener(e);
		gui.getOpenOptaItem().addActionListener(e);
		gui.getExitItem().addActionListener(e);
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
				gui.getStatusLabel().setText("Åpnet: " + files[0].getName());
			} else {
				gui.getStatusLabel().setText("Åpnet flere filer");
			}
			processFiles(files);
		} 
	}
	
	/*
	 * Method to process a file
	 * @params file 	the file to parse
	 * The file should have this format
	 * number name distance sprints avgSpeed topSpeed - all separated by tab (\t)
	 */
	public void processFiles(File[] files) {
		boolean problemWithFile = false;
		if(files.length > 0) {
			parser.setPlayers(new ArrayList<Player>());
			gui.getStatusPanel().setBackground(Color.GREEN);
			for(File file : files) {
				try {
					parser.parseFile(file);
				} catch (NumberFormatException ex) {
					System.out.println(ex.getMessage());
					showFileProcessError(ex, file.getName());
					problemWithFile = true;
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			showFileProcessInfo(problemWithFile);
		}
	}

	private void showFileProcessError(NumberFormatException ex, String fileName) {
		if(ex != null) {
			gui.showMessage(ex.getMessage());
		}
		gui.getStatusLabel().setText("Det var en feil med formateringen av fil: " + fileName);
		gui.getStatusLabel().setBackground(Color.RED);
		gui.getStatusPanel().setBackground(Color.RED);
		gui.getOutputPane().setText(0 + " spillere er tilgjengelig");
	}

	private void showFileProcessInfo(boolean problemWithFile) {
		if(!problemWithFile) {
			String status = gui.getStatusLabel().getText();
			status += "\n --> Filen(e) er prosessert";
			gui.getStatusLabel().setText(status);
			gui.getOutputPane().setText(parser.getSize() + " spillere er tilgjengelig");
			gui.getOutputPane().setBorder(new TitledBorder(""));
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
		
		
		boolean removeName = gui.getRemoveFirstNameCheckBox().isSelected();
		
	
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
						
					} else {
						gui.showMessage("Du prøver å vise flere spillere enn det finnes\n Du prøvde: " + numberOfPlayers + ". Det er bare " + players.size() + " spillere tilgjengelig");
						gui.getOutputPane().setBorder(new TitledBorder("Viser " + 0 + " av " + parser.getSize() + " tilgjengelige spillere"));
						gui.getNumberOfPlayersArea().setText("5");
					}
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
			} else if(e.getSource() == gui.getCopyButton()) {
				copyContent();
			} else if(e.getSource() == gui.getOpenOptaItem()) {
				gui.getStatusLabel().setText(gui.getStatusLabel().getText() + " Åpner en tab for hver kamp i Firefox. Dette kan ta litt tid");
				optaWebDriver.openOptaTabs();
			} else if(e.getSource() == gui.getExitItem()) {
				System.exit(0);
			}
		}
		
	}

}
