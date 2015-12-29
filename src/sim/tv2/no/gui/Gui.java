package sim.tv2.no.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

/*
 * Class for the graphical user interface 
 * @Author: Sindre Moldeklev
 * @version 0.1
 */

public class Gui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7443633601733752828L;
	private JButton openFileBtn, runButton;
	private JEditorPane inputPane, outputPane;
	private JTextArea numberOfPlayersArea;
	private static Gui instance = null;
	private final JFileChooser fileChooser = new JFileChooser(".");
	private JLabel statusLabel;
	
	private Gui() {
		setupGui();
	}
	
	public static Gui getInstance() {
		if(instance == null) {
			instance = new Gui();
		}
		return instance;
	}
	

	private void setupGui() {
		this.setBounds(new Rectangle(600,600));
		this.setTitle("Løpestats fra Opta");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new GridLayout());
		
		openFileBtn = new JButton("Åpne tekstfil");
		openFileBtn.setToolTipText("Åpne en tab-separert tekstfil med løpedata");
		
		numberOfPlayersArea = new JTextArea("5");
		numberOfPlayersArea.setToolTipText("Velg antall spillere du ønsker kalkulert");	
		numberOfPlayersArea.setBorder(new TitledBorder("Velg antall spillere"));
		
		runButton = new JButton("Kjør");
		runButton.setEnabled(false);
		runButton.setToolTipText("Trykk her for å kalkulere spillere som har løpt mest");
		
		northPanel.add(openFileBtn);

		northPanel.add(numberOfPlayersArea);
		northPanel.add(runButton);
		this.add(northPanel, BorderLayout.NORTH);
		
		Dimension minimumSize = new Dimension(100,100);
		
		inputPane = new JEditorPane();
		inputPane.setMinimumSize(minimumSize);
		inputPane.setContentType("text/html");
		inputPane.setEnabled(false);
		outputPane = new JEditorPane();
		outputPane.setMinimumSize(minimumSize);
		outputPane.setEditable(false);
		
		JScrollPane inputScrollPane = new JScrollPane(inputPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputScrollPane.setBorder(new TitledBorder("Lim inn data her"));
		inputScrollPane.setToolTipText("Kopier inn tab-separert data fra LM3 her");
		inputScrollPane.setEnabled(false);
	
		
		JScrollPane outputScrollPane = new JScrollPane(outputPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outputScrollPane.setBorder(new TitledBorder("Oppsummering"));
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputScrollPane, outputScrollPane);
		splitPane.setDividerLocation(450);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(outputScrollPane, BorderLayout.CENTER);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		JPanel statusPanel = new JPanel();
		statusLabel = new JLabel("Status:");
		statusPanel.add(statusLabel);
		
		this.add(statusPanel, BorderLayout.SOUTH);
		
		
		this.setVisible(true);
		
	}

	/**
	 * @return the openFileBtn
	 */
	public JButton getOpenFileBtn() {
		return openFileBtn;
	}

	/**
	 * @param openFileBtn the openFileBtn to set
	 */
	public void setOpenFileBtn(JButton openFileBtn) {
		this.openFileBtn = openFileBtn;
	}

	/**
	 * @return the runButton
	 */
	public JButton getRunButton() {
		return runButton;
	}

	/**
	 * @param runButton the runButton to set
	 */
	public void setRunButton(JButton runButton) {
		this.runButton = runButton;
	}

	/**
	 * @return the inputPane
	 */
	public JEditorPane getInputPane() {
		return inputPane;
	}

	/**
	 * @param inputPane the inputPane to set
	 */
	public void setInputPane(JEditorPane inputPane) {
		this.inputPane = inputPane;
	}

	/**
	 * @return the outputPane
	 */
	public JEditorPane getOutputPane() {
		return outputPane;
	}

	/**
	 * @param outputPane the outputPane to set
	 */
	public void setOutputPane(JEditorPane outputPane) {
		this.outputPane = outputPane;
	}

	/**
	 * @return the numberOfPlayersArea
	 */
	public JTextArea getNumberOfPlayersArea() {
		return numberOfPlayersArea;
	}

	/**
	 * @param numberOfPlayersArea the numberOfPlayersArea to set
	 */
	public void setNumberOfPlayersArea(JTextArea numberOfPlayersArea) {
		this.numberOfPlayersArea = numberOfPlayersArea;
	}

	/**
	 * @return the fileChooser
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	/**
	 * @return the statusLabel
	 */
	public JLabel getStatusLabel() {
		return statusLabel;
	}

	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}
	

}
