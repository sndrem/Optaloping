package sim.tv2.no.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;


/*
 * Class for the graphical user interface 
 * @Author: Sindre Moldeklev
 * @version 0.1
 */

public class Gui extends JFrame {
	
	private static final long serialVersionUID = -7443633601733752828L;
	private JButton openFileBtn, runButton;
	private JEditorPane inputPane, outputPane;
	private JComboBox<Integer> numberOfPlayersArea;
	private static Gui instance = null;
	private final JFileChooser fileChooser = new JFileChooser(".");
	private JTextArea statusTextArea;
	private JComboBox<String> categoryDropdow;
	private JPanel statusPanel;
	private JCheckBox orderCheckBox;
	private JButton copyButton;
	private JMenuItem openOptaItem;
	private JCheckBox removeFirstNameCheckBox;
	private JMenuItem exitItem;
	private JMenuItem openFileMenuItem;
	private JCheckBox showCategoryCheckBox;
	
	private Gui() {
		setupGui();
	}
	
	public static Gui getInstance() {
		if(instance == null) {
			instance = new Gui();
		}
		return instance;
	}
	
	/*
	 * Method to setup the gui
	 */
	private void setupGui() {
		/*
		 * Set bounds, title, place the program in the center of the screen and set default close operation
		 */
		this.setBounds(new Rectangle(800,800));
		this.setTitle("Løpestats fra Opta");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setFocusable(true);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Meny");
		JMenu toolsMenu = new JMenu("Verktøy");
		menuBar.add(menu);
		menuBar.add(toolsMenu);
		
		setOpenOptaItem(new JMenuItem("Åpne kamper i Firefox"));
		getOpenOptaItem().setToolTipText("Åpner en tab for hver kamp i Firefox. Dette kan ta litt tid");
		toolsMenu.add(getOpenOptaItem());
		
		setOpenFileMenuItem(new JMenuItem("Åpne tekstfil"));
		getOpenFileMenuItem().setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		menu.add(getOpenFileMenuItem());
		
		setExitItem(new JMenuItem("Lukk"));
		menu.add(getExitItem());
		
		this.setJMenuBar(menuBar);

				
		// Set layout for the gui
		this.setLayout(new BorderLayout());
		
		// Create the top-panel. This panel holds the open button, input field for the range of players and the run button
		JPanel northPanel = new JPanel(new GridLayout(2,2));
		
		openFileBtn = new JButton("Åpne tekstfil");
		openFileBtn.setToolTipText("Åpne en tab-separert tekstfil med løpedata");
		openFileBtn.requestFocus(true);
		
		String[] categories = {"Distanse", "Sprinter", "Gjennomsnittsfart", "Toppfart"};
		
		setCategoryDropdow(new JComboBox<String>(categories));
		getCategoryDropdow().setEnabled(false);
		getCategoryDropdow().setFocusable(true);
		getCategoryDropdow().setBorder(new TitledBorder("Velg en kategori"));
		
		setOrderCheckBox(new JCheckBox("Reverse"));
		getOrderCheckBox().setFocusable(true);
		
		setRemoveFirstNameCheckBox(new JCheckBox("Fjern fornavn"));
		getRemoveFirstNameCheckBox().setFocusable(true);
		getRemoveFirstNameCheckBox().setSelected(true);
		
		setShowCategoryCheckBox(new JCheckBox("Vis kategori"));
		getShowCategoryCheckBox().setFocusable(true);
		
		
		numberOfPlayersArea = new JComboBox<Integer>();
		numberOfPlayersArea.setToolTipText("Velg antall spillere du ønsker kalkulert");	
		numberOfPlayersArea.setBorder(new TitledBorder("Velg antall spillere"));
		//TODO Endre fra TextArea til JComboBox for antall spillere basert på antall spillere lastet inn
		
		runButton = new JButton("Kjør");
		runButton.setEnabled(false);
		runButton.setToolTipText("Trykk her for å kalkulere spillere som har løpt mest");
		
		setCopyButton(new JButton("Kopier til clipboard"));
		
//		northPanel.add(openFileBtn);
		northPanel.add(getCategoryDropdow());
		northPanel.add(numberOfPlayersArea);
		northPanel.add(getOrderCheckBox());
		northPanel.add(getRemoveFirstNameCheckBox());
		northPanel.add(getShowCategoryCheckBox());

//		northPanel.add(runButton);
//		northPanel.add(getCopyButton());
		this.add(northPanel, BorderLayout.NORTH);
		
		Dimension minimumSize = new Dimension(100,100);
		
		// Create the ouput-pane where the players will be printed
		outputPane = new JEditorPane();
		outputPane.setMinimumSize(minimumSize);
		outputPane.setEditable(false);
		
		JScrollPane outputScrollPane = new JScrollPane(outputPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outputScrollPane.setBorder(new TitledBorder("Oppsummering"));
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(outputScrollPane, BorderLayout.CENTER);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
	
		setStatusPanel(new JPanel());
		setStatusTextArea(new JTextArea("Status:"));
		getStatusPanel().add(getStatusTextArea());
		getStatusTextArea().setEditable(false);
		
		this.add(getStatusPanel(), BorderLayout.SOUTH);
		
		
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
	public JComboBox<Integer> getNumberOfPlayersArea() {
		return numberOfPlayersArea;
	}

	/**
	 * @param numberOfPlayersArea the numberOfPlayersArea to set
	 */
	public void setNumberOfPlayersArea(JComboBox<Integer> numberOfPlayersArea) {
		this.numberOfPlayersArea = numberOfPlayersArea;
	}

	/**
	 * @return the fileChooser
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	
	/*
	 * Method to show a dialog box
	 * @params info the info you want to display
	 */
	public void showMessage(String info) {
		JOptionPane.showMessageDialog(this, info, "Obs", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return the categoryDropdow
	 */
	public JComboBox<String> getCategoryDropdow() {
		return categoryDropdow;
	}

	/**
	 * @param categoryDropdow the categoryDropdow to set
	 */
	public void setCategoryDropdow(JComboBox<String> categoryDropdow) {
		this.categoryDropdow = categoryDropdow;
	}

	/**
	 * @return the statusPanel
	 */
	public JPanel getStatusPanel() {
		return statusPanel;
	}

	/**
	 * @param statusPanel the statusPanel to set
	 */
	public void setStatusPanel(JPanel statusPanel) {
		this.statusPanel = statusPanel;
	}

	/**
	 * @return the orderCheckBox
	 */
	public JCheckBox getOrderCheckBox() {
		return orderCheckBox;
	}

	/**
	 * @param orderCheckBox the orderCheckBox to set
	 */
	public void setOrderCheckBox(JCheckBox orderCheckBox) {
		this.orderCheckBox = orderCheckBox;
	}

	/**
	 * @return the copyButton
	 */
	public JButton getCopyButton() {
		return copyButton;
	}

	/**
	 * @param copyButton the copyButton to set
	 */
	public void setCopyButton(JButton copyButton) {
		this.copyButton = copyButton;
	}

	/**
	 * @return the openOptaItem
	 */
	public JMenuItem getOpenOptaItem() {
		return openOptaItem;
	}

	/**
	 * @param openOptaItem the openOptaItem to set
	 */
	public void setOpenOptaItem(JMenuItem openOptaItem) {
		this.openOptaItem = openOptaItem;
	}

	/**
	 * @return the removeFirstNameCheckBox
	 */
	public JCheckBox getRemoveFirstNameCheckBox() {
		return removeFirstNameCheckBox;
	}

	/**
	 * @param removeFirstNameCheckBox the removeFirstNameCheckBox to set
	 */
	public void setRemoveFirstNameCheckBox(JCheckBox removeFirstNameCheckBox) {
		this.removeFirstNameCheckBox = removeFirstNameCheckBox;
	}

	/**
	 * @return the exitItem
	 */
	public JMenuItem getExitItem() {
		return exitItem;
	}

	/**
	 * @param exitItem the exitItem to set
	 */
	public void setExitItem(JMenuItem exitItem) {
		this.exitItem = exitItem;
	}

	/**
	 * @return the statusTextArea
	 */
	public JTextArea getStatusTextArea() {
		return statusTextArea;
	}

	/**
	 * @param statusTextArea the statusTextArea to set
	 */
	public void setStatusTextArea(JTextArea statusTextArea) {
		this.statusTextArea = statusTextArea;
	}

	/**
	 * @return the openFileMenuItem
	 */
	public JMenuItem getOpenFileMenuItem() {
		return openFileMenuItem;
	}

	/**
	 * @param openFileMenuItem the openFileMenuItem to set
	 */
	public void setOpenFileMenuItem(JMenuItem openFileMenuItem) {
		this.openFileMenuItem = openFileMenuItem;
	}

	/**
	 * @return the showCategoryCheckBox
	 */
	public JCheckBox getShowCategoryCheckBox() {
		return showCategoryCheckBox;
	}

	/**
	 * @param showCategoryCheckBox the showCategoryCheckBox to set
	 */
	public void setShowCategoryCheckBox(JCheckBox showCategoryCheckBox) {
		this.showCategoryCheckBox = showCategoryCheckBox;
	}

}
