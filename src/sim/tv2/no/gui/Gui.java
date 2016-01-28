package sim.tv2.no.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
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
	private JCheckBox selectTextCheckBox;
	private JTabbedPane tabbedPane;
	private JPanel optaRunningPanel;
	private JPanel head2headPanel;
	private JTextArea outputH2HArea;
	private JComboBox<String> homeTeamNames;
	private JComboBox<String> homePlayerNames;
	private JComboBox<String> awayTeamDropBox;
	private JComboBox<String> awayTeamNames;
	private DefaultComboBoxModel<String> homeTeamModel, awayTeamModel;
	private JButton h2hButton;
	private JTextArea homeTeamSearch;
	private JCheckBox textSearchCheckBox;
	private JCheckBox awayTexSearchCheckBox;
	private JTextArea awayTeamSearch;
	private JButton generateReportButton;
	private JMenuItem createFilesMenuItem;
	private JMenuItem openDir;
	
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
		this.setTitle("Statistikk og H2H");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setFocusable(true);
		
		createMenuBar();	
		
		setOptaRunningPanel(new JPanel(new BorderLayout()));
		
		createOptaRunningPanel();
		createHead2HeadPanel();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Løping", getOptaRunningPanel());
		tabbedPane.add("H2H", getHead2headPanel());
		
		
		this.add(tabbedPane);
		
		this.setVisible(true);
		
	}
	
	private void createHead2HeadPanel() {
		head2headPanel = new JPanel(new BorderLayout());
		
		setHomeTeamModel(new DefaultComboBoxModel<String>());
		setAwayTeamModel(new DefaultComboBoxModel<String>());

		JPanel homeTeamPanel = new JPanel(new BorderLayout());
		homeTeamPanel.add(createHomeTeamDropBoxes(), BorderLayout.NORTH);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel awayTeamPanel = new JPanel(new BorderLayout());
		awayTeamPanel.add(createAwayTeamDropBoxes(), BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		setH2hButton(new JButton("Hent spillere"));
		homeTextSearchCheckBox(new JCheckBox("Hjemmelag"));
		buttonPanel.add(getHomeTextSearchBox());
		buttonPanel.add(getH2hButton());
		setAwayTexSearchCheckBox(new JCheckBox("Bortelag"));
		buttonPanel.add(getAwayTexSearchCheckBox());
		centerPanel.add(buttonPanel, BorderLayout.NORTH);
		
		setOutputH2HArea(new JTextArea(30,10));
		getOutputH2HArea().setBorder(BorderFactory.createTitledBorder("iNews-script"));
		getOutputH2HArea().setWrapStyleWord(true);
		getOutputH2HArea().setLineWrap(true);
		
		setHomeTeamSearch(new JTextArea());
		getHomeTeamSearch().setWrapStyleWord(true);
		getHomeTeamSearch().setLineWrap(true);
		getHomeTeamSearch().setBorder(BorderFactory.createTitledBorder("Søk med tekst"));
		homeTeamPanel.add(getHomeTeamSearch(), BorderLayout.CENTER);
		
		setAwayTeamSearch(new JTextArea());
		getAwayTeamSearch().setWrapStyleWord(true);
		getAwayTeamSearch().setLineWrap(true);
		getAwayTeamSearch().setBorder(BorderFactory.createTitledBorder("Søk med tekst"));
		awayTeamPanel.add(getAwayTeamSearch(), BorderLayout.CENTER);
		
		centerPanel.add(getOutputH2HArea(), BorderLayout.CENTER);
		
		head2headPanel.add(homeTeamPanel, BorderLayout.WEST);
		head2headPanel.add(centerPanel, BorderLayout.CENTER);
		head2headPanel.add(awayTeamPanel, BorderLayout.EAST);
		
		
	}

	private JPanel createHomeTeamDropBoxes() {
		setHomeTeamDropBoxes(new JComboBox<String>());
		setHomePlayerNames(new JComboBox<String>());
		getHomePlayerNames().setModel(homeTeamModel);
		getHomeTeamNames().setBorder(new TitledBorder("Velg lag"));
		getHomePlayerNames().setBorder(new TitledBorder("Velg spiller"));
		JPanel homeDropBoxPanel = new JPanel(new GridLayout(2,1));
		homeDropBoxPanel.add(getHomeTeamNames());
		homeDropBoxPanel.add(getHomePlayerNames());
		return homeDropBoxPanel;
	}
	
	private JPanel createAwayTeamDropBoxes() {
		setAwayTeamNames(new JComboBox<String>());
		setAwayPlayerNames(new JComboBox<String>());
		getAwayPlayerNames().setModel(awayTeamModel);
		getAwayPlayerNames().setBorder(new TitledBorder("Velg lag"));
		getAwayTeamNames().setBorder(new TitledBorder("Velg spiller"));
		JPanel awayDropBoxPanel = new JPanel(new GridLayout(2,1));
		awayDropBoxPanel.add(getAwayTeamNames());
		awayDropBoxPanel.add(getAwayPlayerNames());
		return awayDropBoxPanel;
	}

	private void createOptaRunningPanel() {
		// Create the top-panel. This panel holds the open button, input field for the range of players and the run button
		JPanel northPanel = new JPanel(new BorderLayout());
		
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
		
		setSelectTextCheckBox(new JCheckBox("Marker tekst"));
		getSelectTextCheckBox().setFocusable(true);
		
		setGenerateReportButton(new JButton("Full rapport"));
		
		
		
		numberOfPlayersArea = new JComboBox<Integer>();
		numberOfPlayersArea.setToolTipText("Velg antall spillere du ønsker kalkulert");	
		numberOfPlayersArea.setBorder(new TitledBorder("Velg antall spillere"));
		//TODO Endre fra TextArea til JComboBox for antall spillere basert på antall spillere lastet inn
		
		runButton = new JButton("Kjør");
		runButton.setEnabled(false);
		runButton.setToolTipText("Trykk her for å kalkulere spillere som har løpt mest");
		
		setCopyButton(new JButton("Kopier til clipboard"));
		
		
		JToolBar dropDownToolBar = new JToolBar();
		dropDownToolBar.add(getCategoryDropdow());
		dropDownToolBar.add(getNumberOfPlayersArea());
		
		
		
		JToolBar checkBoxToolBar = new JToolBar();
		checkBoxToolBar.add(getOrderCheckBox());
		checkBoxToolBar.add(getRemoveFirstNameCheckBox());
//		checkBoxToolBar.add(getShowCategoryCheckBox());
		checkBoxToolBar.add(getSelectTextCheckBox());
		checkBoxToolBar.add(getGenerateReportButton());
		

		northPanel.add(dropDownToolBar, BorderLayout.NORTH);
		northPanel.add(checkBoxToolBar, BorderLayout.SOUTH);

		
		
		Dimension minimumSize = new Dimension(100,100);
		
		// Create the ouput-pane where the players will be printed
		outputPane = new JEditorPane();
		outputPane.setMinimumSize(minimumSize);
		outputPane.setEditable(false);
		
		JScrollPane outputScrollPane = new JScrollPane(outputPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outputScrollPane.setBorder(new TitledBorder("Oppsummering"));
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(outputScrollPane, BorderLayout.CENTER);
		
		setStatusPanel(new JPanel());
		setStatusTextArea(new JTextArea("Status:"));
		getStatusPanel().add(getStatusTextArea());
		getStatusTextArea().setEditable(false);
		
		getOptaRunningPanel().add(northPanel, BorderLayout.NORTH);
		getOptaRunningPanel().add(centerPanel, BorderLayout.CENTER);
		getOptaRunningPanel().add(getStatusPanel(), BorderLayout.SOUTH);
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Meny");
		JMenu toolsMenu = new JMenu("Verktøy");
		menuBar.add(menu);
		menuBar.add(toolsMenu);
		
		setOpenOptaItem(new JMenuItem("Åpne kamper i Firefox"));
		getOpenOptaItem().setToolTipText("Åpner en tab for hver kamp i Firefox. Dette kan ta litt tid");
		toolsMenu.add(getOpenOptaItem());
		
		setCreateFilesMenuItem(new JMenuItem("Opprett .txt-filer"));
		getCreateFilesMenuItem().setToolTipText("Oppretter .txt-filer basert på kampene");
		toolsMenu.add(getCreateFilesMenuItem());
		
		setOpenFileMenuItem(new JMenuItem("Åpne tekstfil"));
		getOpenFileMenuItem().setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		menu.add(getOpenFileMenuItem());
		
		setOpenDir(new JMenuItem("Last mappe"));
		menu.add(getOpenDir());
		
		setExitItem(new JMenuItem("Lukk"));
		menu.add(getExitItem());
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Method to present a file chooser to the user
	 * @return String - the directory the user chooses
	 */
	public String showFileChooser() {
		JFileChooser chooser = new JFileChooser("");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		int choice = chooser.showSaveDialog(this);
		if(choice == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getParent();
		} else {
			return null;
		}
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

	/**
	 * @return the selectTextCheckBox
	 */
	public JCheckBox getSelectTextCheckBox() {
		return selectTextCheckBox;
	}

	/**
	 * @param selectTextCheckBox the selectTextCheckBox to set
	 */
	public void setSelectTextCheckBox(JCheckBox selectTextCheckBox) {
		this.selectTextCheckBox = selectTextCheckBox;
	}

	/**
	 * @return the optaRunningPanel
	 */
	public JPanel getOptaRunningPanel() {
		return optaRunningPanel;
	}

	/**
	 * @param optaRunningPanel the optaRunningPanel to set
	 */
	public void setOptaRunningPanel(JPanel optaRunningPanel) {
		this.optaRunningPanel = optaRunningPanel;
	}

	/**
	 * @return the head2headPanel
	 */
	public JPanel getHead2headPanel() {
		return head2headPanel;
	}

	/**
	 * @param head2headPanel the head2headPanel to set
	 */
	public void setHead2headPanel(JPanel head2headPanel) {
		this.head2headPanel = head2headPanel;
	}

	/**
	 * @return the outputH2HArea
	 */
	public JTextArea getOutputH2HArea() {
		return outputH2HArea;
	}

	/**
	 * @param outputH2HArea the outputH2HArea to set
	 */
	public void setOutputH2HArea(JTextArea outputH2HArea) {
		this.outputH2HArea = outputH2HArea;
	}

	/**
	 * @return the teamNames
	 */
	public JComboBox<String> getHomeTeamNames() {
		return homeTeamNames;
	}

	/**
	 * @param teamNames the teamNames to set
	 */
	public void setHomeTeamDropBoxes(JComboBox<String> teamNames) {
		this.homeTeamNames = teamNames;
	}

	/**
	 * @return the playerNames
	 */
	public JComboBox<String> getHomePlayerNames() {
		return homePlayerNames;
	}

	/**
	 * @param playerNames the playerNames to set
	 */
	public void setHomePlayerNames(JComboBox<String> playerNames) {
		this.homePlayerNames = playerNames;
	}

	/**
	 * @return the awayTeamDropBox
	 */
	public JComboBox<String> getAwayPlayerNames() {
		return awayTeamDropBox;
	}

	/**
	 * @param awayTeamDropBox the awayTeamDropBox to set
	 */
	public void setAwayPlayerNames(JComboBox<String> awayTeamDropBox) {
		this.awayTeamDropBox = awayTeamDropBox;
	}

	/**
	 * @return the awayTeamNames
	 */
	public JComboBox<String> getAwayTeamNames() {
		return awayTeamNames;
	}

	/**
	 * @param awayTeamNames the awayTeamNames to set
	 */
	public void setAwayTeamNames(JComboBox<String> awayTeamNames) {
		this.awayTeamNames = awayTeamNames;
	}

	/**
	 * @return the homeTeamModel
	 */
	public DefaultComboBoxModel<String> getHomeTeamModel() {
		return homeTeamModel;
	}

	/**
	 * @param homeTeamModel the homeTeamModel to set
	 */
	public void setHomeTeamModel(DefaultComboBoxModel<String> homeTeamModel) {
		this.homeTeamModel = homeTeamModel;
	}

	/**
	 * @return the awayTeamModel
	 */
	public DefaultComboBoxModel<String> getAwayTeamModel() {
		return awayTeamModel;
	}

	/**
	 * @param awayTeamModel the awayTeamModel to set
	 */
	public void setAwayTeamModel(DefaultComboBoxModel<String> awayTeamModel) {
		this.awayTeamModel = awayTeamModel;
	}

	/**
	 * @return the h2hButton
	 */
	public JButton getH2hButton() {
		return h2hButton;
	}

	/**
	 * @param h2hButton the h2hButton to set
	 */
	public void setH2hButton(JButton h2hButton) {
		this.h2hButton = h2hButton;
	}

	/**
	 * @return the textSearchCheckBox
	 */
	public JCheckBox getHomeTextSearchBox() {
		return textSearchCheckBox;
	}

	/**
	 * @param textSearchCheckBox the textSearchCheckBox to set
	 */
	public void homeTextSearchCheckBox(JCheckBox textSearchCheckBox) {
		this.textSearchCheckBox = textSearchCheckBox;
	}

	/**
	 * @return the awayTexSearchCheckBox
	 */
	public JCheckBox getAwayTexSearchCheckBox() {
		return awayTexSearchCheckBox;
	}

	/**
	 * @param awayTexSearchCheckBox the awayTexSearchCheckBox to set
	 */
	public void setAwayTexSearchCheckBox(JCheckBox awayTexSearchCheckBox) {
		this.awayTexSearchCheckBox = awayTexSearchCheckBox;
	}

	/**
	 * @return the homeTeamSearch
	 */
	public JTextArea getHomeTeamSearch() {
		return homeTeamSearch;
	}

	/**
	 * @param homeTeamSearch the homeTeamSearch to set
	 */
	public void setHomeTeamSearch(JTextArea homeTeamSearch) {
		this.homeTeamSearch = homeTeamSearch;
	}

	/**
	 * @return the awayTeamSearch
	 */
	public JTextArea getAwayTeamSearch() {
		return awayTeamSearch;
	}

	/**
	 * @param awayTeamSearch the awayTeamSearch to set
	 */
	public void setAwayTeamSearch(JTextArea awayTeamSearch) {
		this.awayTeamSearch = awayTeamSearch;
	}

	/**
	 * @return the generateReportButton
	 */
	public JButton getGenerateReportButton() {
		return generateReportButton;
	}

	/**
	 * @param generateReportButton the generateReportButton to set
	 */
	public void setGenerateReportButton(JButton generateReportButton) {
		this.generateReportButton = generateReportButton;
	}

	/**
	 * @return the createFilesMenuItem
	 */
	public JMenuItem getCreateFilesMenuItem() {
		return createFilesMenuItem;
	}

	/**
	 * @param createFilesMenuItem the createFilesMenuItem to set
	 */
	public void setCreateFilesMenuItem(JMenuItem createFilesMenuItem) {
		this.createFilesMenuItem = createFilesMenuItem;
	}

	/**
	 * @return the openDir
	 */
	public JMenuItem getOpenDir() {
		return openDir;
	}

	/**
	 * @param openDir the openDir to set
	 */
	public void setOpenDir(JMenuItem openDir) {
		this.openDir = openDir;
	}
}
