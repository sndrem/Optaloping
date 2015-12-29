package sim.tv2.no.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import sim.tv2.no.gui.Gui;
import sim.tv2.no.parser.Parser;
import sim.tv2.no.player.Player;

/*
 * Main class for the application
 * @author Sindre Moldeklev
 * @version 0.1
 */

public class Main {
	
	private Gui gui;
	private Parser parser;

	public Main() {
		// TODO Auto-generated constructor stub
		gui = Gui.getInstance();
		setupActionListeners();
		parser = new Parser();
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		
	}
	

	private void setupActionListeners() {
		Events e = new Events();
		gui.getOpenFileBtn().addActionListener(e);
		gui.getRunButton().addActionListener(e);
	}
	
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
	
	public void processFile(File file) {
		try {
			parser.parseText(file);
			String status = gui.getStatusLabel().getText();
			status += "\n --> Filen er prosessert";
			gui.getStatusLabel().setText(status);
			gui.getOutputPane().setText(parser.getSize() + " spillere er tilgjengelig");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void calculate(int numberOfPlayers) {

		List<Player> players = parser.getPlayers();	
		Collections.sort(players);
		
		if(numberOfPlayers <= players.size() && players.size() > 0) {
			gui.getOutputPane().setBorder(new TitledBorder("Viser " + numberOfPlayers + " av " + parser.getSize() + " tilgjengelige spillere"));	
			for(int i = 0; i < numberOfPlayers; i++) {
					System.out.println(players.get(i));
					String output = gui.getOutputPane().getText();
					gui.getOutputPane().setText(output + "\n" + players.get(i).toString());
				}
				
			} else {
				JOptionPane.showMessageDialog(gui, "Du prøver å vise flere spillere enn det finnes\n Du prøvde: " + numberOfPlayers + ". Det er bare " + players.size() + " spillere tilgjengelig");
			}
		
	}
	
	private class Events implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == gui.getOpenFileBtn()) {
				// Open file
				openFile();
				gui.getRunButton().setEnabled(true);
			} else if (e.getSource() == gui.getRunButton()) {
				gui.getOutputPane().setText("");
				// Calculate the data
				try {
					int range = Integer.parseInt(gui.getNumberOfPlayersArea().getText().trim());				
					calculate(range);
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(gui, "Vennligst fyll inn et tall");
					System.out.println(ex.getMessage());
				}
				
			}
		}
		
	}

}
