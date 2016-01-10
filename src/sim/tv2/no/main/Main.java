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
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
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
		gui.getRunButton().setAction(new RunAction("Kjør"));
		gui.getCopyButton().addActionListener(e);
		gui.getOpenOptaItem().addActionListener(e);
		gui.getExitItem().addActionListener(e);
		gui.getRemoveFirstNameCheckBox().setAction(new RunAction("Fjern fornavn"));
		gui.getOrderCheckBox().setAction(new RunAction("Reverse"));
		gui.getCategoryDropdow().addActionListener(e);
		
		// Key events
		gui.getOpenFileBtn().getActionMap().put("openFile", new OpenFileAction());
		gui.getOpenFileBtn().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control O"), "openFile");
		gui.getOpenFileBtn().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "openFile");
		
		gui.getRunButton().getActionMap().put("runCalculations", new RunAction());
		gui.getRunButton().getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "runCalculations");
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
		}
	}

	private void populateNumbersArea(int size) {
		// Clear items if we load a new file
		gui.getNumberOfPlayersArea().removeAllItems();
		
		for(int i = 1; i <= size; i++) {
			gui.getNumberOfPlayersArea().addItem(i);
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
						gui.getNumberOfPlayersArea().setSelectedIndex(4);
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
			} else if (e.getSource() == gui.getRunButton()) {
				// Calculate the data
				try {
					calculate(gui.getNumberOfPlayersArea().getSelectedIndex(), gui.getCategoryDropdow().getSelectedIndex());
				} catch(NumberFormatException ex) {
					gui.showMessage("Vennligst fyll inn et tall");
					System.out.println(ex.getMessage());
				}
			} else if(e.getSource() == gui.getCopyButton()) {
				copyContent();
			} else if(e.getSource() == gui.getOpenOptaItem()) {
				gui.getStatusTextArea().setText(gui.getStatusTextArea().getText() + " Åpner en tab for hver kamp i Firefox. Dette kan ta litt tid");
				optaWebDriver.openOptaTabs();
			} else if(e.getSource() == gui.getExitItem()) {
				System.exit(0);
			} else if(e.getSource() == gui.getCategoryDropdow()) {
				int selected = gui.getCategoryDropdow().getSelectedIndex();
				try {
					calculate(gui.getNumberOfPlayersArea().getSelectedIndex(), selected);
				} catch(NumberFormatException ex) {
					gui.showMessage("Vennligst fyll inn et tall");
					System.out.println(ex.getMessage());
				}
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
			} catch(NumberFormatException ex) {
				gui.showMessage("Vennligst fyll inn et tall");
				System.out.println(ex.getMessage());
			}
			
		}
		
	}


		

}
