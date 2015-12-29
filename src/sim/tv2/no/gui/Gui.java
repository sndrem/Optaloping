package sim.tv2.no.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
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
	private JButton openFileBtn, runButton;
	private JEditorPane inputPane, outputPane;
	private JTextArea numberOfPlayersArea;
	
	public Gui() {
		setupGui();
	}
	
	public static void main(String[] args) {
		new Gui();
	}

	private void setupGui() {
		this.setBounds(new Rectangle(1000,1200));
		this.setTitle("Løpestats fra Opta");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new GridLayout());
		
		openFileBtn = new JButton("Åpne tekstfil");
		openFileBtn.setToolTipText("Åpne en tab-separert tekstfil med løpedata");
		
		numberOfPlayersArea = new JTextArea();
		numberOfPlayersArea.setToolTipText("Velg antall spillere du ønsker kalkulert");	
		numberOfPlayersArea.setBorder(new TitledBorder("Velg antall spillere"));
		
		runButton = new JButton("Kjør");
		runButton.setToolTipText("Trykk her for å kalkulere spillere som har løpt mest");
		
		northPanel.add(openFileBtn);

		northPanel.add(numberOfPlayersArea);
		northPanel.add(runButton);
		this.add(northPanel, BorderLayout.NORTH);
		
		Dimension minimumSize = new Dimension(100,100);
		
		inputPane = new JEditorPane();
		inputPane.setMinimumSize(minimumSize);
		inputPane.setContentType("text/html");
		outputPane = new JEditorPane();
		outputPane.setMinimumSize(minimumSize);
		outputPane.setEditable(false);
		
		JScrollPane inputScrollPane = new JScrollPane(inputPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputScrollPane.setBorder(new TitledBorder("Lim inn data her"));
		inputScrollPane.setToolTipText("Kopier inn tab-separert data fra LM3 her");
		
		JScrollPane outputScrollPane = new JScrollPane(outputPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outputScrollPane.setBorder(new TitledBorder("Oppsummering"));
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputScrollPane, outputScrollPane);
		splitPane.setDividerLocation(450);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(splitPane, BorderLayout.CENTER);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		
		this.setVisible(true);
		
	}
	

}
