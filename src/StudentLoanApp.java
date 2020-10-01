/**
	* Class Name:StudentLoanApp.java
	* Purpose: To develop an app that calculates student loan with a GUI
	* @author:Zitong Wang, 0975104
	* Date:    Jul 18, 2020
*/


import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;



public class StudentLoanApp extends JFrame {
	
	//Declare variables
	private ArrayList <Student> students  = new ArrayList<Student>();
	private JLabel welcomeMsgLabel, indexLabel, inputLabel, surnameLabel, studentIDLabel, middleNameLabel, firstNameLabel, aptNumberLabel, streetNumberLabel, streetNameLabel,
					cityLabel, provinceLabel, postalCodeLabel, CSLLabel, OSLLabel,
					surnameLabelDisplay, studentIDLabelDisplay, middleNameLabelDisplay, firstNameLabelDisplay, aptNumberLabelDisplay, streetNumberLabelDisplay, streetNameLabelDisplay,
					cityLabelDisplay, provinceLabelDisplay, postalCodeLabelDisplay, CSLLabelDisplay, OSLLabelDisplay, amortizationJLabel;
	private JPanel card1LeftPanel, card1RightPanel, inputPanel, card1ButtonPanel, card2LeftPanel, card2RightPanel, card2FormPanel, card2ButtonPanel, cards, card1, card2;
	private JTextField surnameTextField, studentIDTextField, middleNameTextField, firstNameTextField, aptNumberTextField, streetNumberTextField, streetNameTextField,
						cityTextField, postalCodeTextField, 
						surnameTextFieldDisplay, studentIDTextFieldDisplay, middleNameTextFieldDisplay, firstNameTextFieldDisplay, aptNumberTextFieldDisplay, streetNumberTextFieldDisplay, streetNameTextFieldDisplay,
						cityTextFieldDisplay, provinceTextFieldDisplay, postalCodeTextFieldDisplay, CSLTextFieldDisplay, OSLTextFieldDisplay;
	private JButton submitButton, clearFormButton, goToCard2Button, goToCard1Button, calculateButton, removeButton;
	private BasicArrowButton previousButton, nextButton;
	private JSpinner amortizationSpinner, OSLSpinner, CSLSpinner;
	private CardLayout cl;
	private JSlider rateSlider;
	private JTextArea outputForm;
	private int currentIndex = 0;
	private int amortization = 60;
	private double prime = 4.25;
	private double OSLPayment, CSLPayment;
	private String provinces[] = {"Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Northwest Territories", "Nova Scotia", "Nunavut", 
			"Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon Territory"};
	private JComboBox<String> provincesComboBox;
	
	//Defines fonts and colors
	private Font buttonFont = new Font("Apple Casual", Font.BOLD, 15);
	private Font labelFont = new Font("Helvetica", Font.PLAIN, 15);
	private Font titleFont = new Font("Helvetica", Font.BOLD, 20);
	private Font msgFont = new Font("Helvetica", Font.BOLD, 20);
	private Font defaultFont = new Font("Tahoma",Font.PLAIN, 13);
	private Color fanshaweRed = new Color(228, 31, 32);
	private Color fanshaweGrey = new Color(53, 48, 29);
	private Color fanshaweYellow = new Color(255,209,27);
	
	
	  
	
	public StudentLoanApp()  {
		
		//Call to superclass constructor
		super("Zitong Wang 0975104");
		
		//Set up methods for the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(850,550);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		
		//Build a card panel that contains two cards; add to frame
		buildCardsPanel();
		add(cards, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/*Method Name: buildCardPanel
	*Purpose: To build a panel that contains two cards: one contains input form and one contains calculation form
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCardsPanel() {
		cards = new JPanel(new CardLayout());
		cl = (CardLayout) (cards.getLayout());
		buildCard1Panel();
		cards.add(card1, "Main Window");
		buildCard2Panel();
		cards.add(card2, "Database Window");
	}
	
	/*Method Name: buildCard1Panel
	*Purpose: To build the panel that holds the input card, which belongs to the cardsPanel
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard1Panel() {
		card1 = new JPanel();
		card1.setLayout(new GridLayout(1,2));
		buildCard1LeftPanel();
		buildCard1RightPanel();
		card1.add(card1LeftPanel);
		card1.add(card1RightPanel);   
	}
	
	/*Method Name: buildCard1LeftPanel
	*Purpose: To build the panel that occupies the left of card1, which shows fanshawe logo and welcome message
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard1LeftPanel(){
		
		card1LeftPanel = new JPanel();
		card1LeftPanel.setLayout(new BorderLayout());;
		card1LeftPanel.setBackground(fanshaweRed);
		welcomeMsgLabel = new JLabel("This is Zitong's Student Loan Calculator", SwingConstants.CENTER);
		welcomeMsgLabel.setForeground(Color.WHITE);
		welcomeMsgLabel.setFont(titleFont);
		
		//Try to import the logo image from bin folder, and puts it to the center of Card1LeftPanel if successful
		//Picture accessed from https://i.pinimg.com/originals/56/60/a1/5660a153d31bace9a5f163882e7359d0.png
		//Copyrights belong to the uploader or Fanshawe College
		BufferedImage fanshaweLogo;
		try {
			fanshaweLogo = ImageIO.read(new File("fanshaweLogo.png"));
			JLabel picLabel = new JLabel(new ImageIcon(fanshaweLogo));
			card1LeftPanel.add(picLabel,BorderLayout.CENTER);
		} 
		catch (IOException e) {
			System.out.println("Logo file import failed");
		}
		card1LeftPanel.add(welcomeMsgLabel, BorderLayout.NORTH);
	}
	
	/*Method Name: buildCard1RightPanel
	*Purpose: To build the panel that occupies the right of card1, which hosts a Label at the top, 
	*the inputPanel that contains textfields in the center, and the buttonPanel that contains buttons at the bottom
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard1RightPanel() {
		card1RightPanel = new JPanel();
		card1RightPanel.setLayout(new BorderLayout());
		inputLabel = new JLabel("To start, enter your information below:", SwingConstants.CENTER);
		inputLabel.setFont(msgFont);
		buildInputPanel();
		buildcard1ButtonPanel();
		card1RightPanel.add(inputLabel,BorderLayout.NORTH);
		card1RightPanel.add(inputPanel, BorderLayout.CENTER);
		card1RightPanel.add(card1ButtonPanel, BorderLayout.SOUTH);
		card1RightPanel.setBackground(Color.WHITE);
	}
	
	/*Method Name: buildInputPanel
	*Purpose: To build the panel that occupies the center of Card1RightPanel, which hosts textfields and their labels
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildInputPanel() {
		
		//Initiate labels and textfields
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(12, 2, 5, 5));
		studentIDLabel = new JLabel("Student ID:", SwingConstants.CENTER);
		studentIDLabel.setFont(labelFont);
		surnameLabel = new JLabel("Surname:", SwingConstants.CENTER);
		surnameLabel.setFont(labelFont);
		firstNameLabel = new JLabel("First Name:", SwingConstants.CENTER);
		firstNameLabel.setFont(labelFont);	
		middleNameLabel = new JLabel("Middle Name:", SwingConstants.CENTER);
		middleNameLabel.setFont(labelFont);
		aptNumberLabel = new JLabel("Apartment Number:", SwingConstants.CENTER);
		aptNumberLabel.setFont(labelFont);
		streetNumberLabel = new JLabel("Street Number:", SwingConstants.CENTER);
		streetNumberLabel.setFont(labelFont);
		streetNameLabel = new JLabel("Street Name:", SwingConstants.CENTER);
		streetNameLabel.setFont(labelFont);
		cityLabel = new JLabel("City:", SwingConstants.CENTER);
		cityLabel.setFont(labelFont);
		provinceLabel = new JLabel("Province:", SwingConstants.CENTER);
		provinceLabel.setFont(labelFont);
		postalCodeLabel = new JLabel("Postal Code:", SwingConstants.CENTER);
		postalCodeLabel.setFont(labelFont);
		CSLLabel = new JLabel("Canada Student Loan ($):", SwingConstants.CENTER);
		CSLLabel.setFont(labelFont);
		OSLLabel = new JLabel("Ontario Student Loan ($):", SwingConstants.CENTER);
		OSLLabel.setFont(labelFont);
		surnameTextField = new JTextField();
		studentIDTextField = new JTextField();
		firstNameTextField = new JTextField();
		middleNameTextField = new JTextField();
		aptNumberTextField = new JTextField();
		streetNumberTextField = new JTextField();
		streetNameTextField = new JTextField();
		cityTextField = new JTextField("London");
		postalCodeTextField = new JTextField();
		provincesComboBox = new JComboBox<>(provinces);
		provincesComboBox.setSelectedIndex(8);
		provincesComboBox.setBackground(Color.WHITE);
		provincesComboBox.setFont(defaultFont);
		
		//Set formats for Jspinners that accepts CSL/OSL amounts to take in double values only. Negative values are allowed so NegativeValueExceptions could be thrown and tested
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		format.setRoundingMode(RoundingMode.HALF_UP);
		NumberFormatter numFormatter = new NumberFormatter(format);
		numFormatter.setValueClass(Double.class);
		numFormatter.setMinimum(-100000000.00);
		numFormatter.setMaximum(Double.MAX_VALUE);
		numFormatter.setAllowsInvalid(false);
		DefaultFormatterFactory factory = new DefaultFormatterFactory(numFormatter);
		CSLSpinner = new JSpinner();
		CSLSpinner.setModel(new SpinnerNumberModel(0.0, -10000000.00, Double.MAX_VALUE, 500));
		JFormattedTextField CSLFormattedTextField = ((JSpinner.DefaultEditor) CSLSpinner.getEditor()).getTextField();
		CSLFormattedTextField.setFormatterFactory(factory);
		CSLFormattedTextField.setHorizontalAlignment(JTextField.LEFT);
		CSLFormattedTextField.setFont(defaultFont);
		OSLSpinner = new JSpinner();
		OSLSpinner.setModel(new SpinnerNumberModel(0.0, -10000000.00, Double.MAX_VALUE, 500));
		JFormattedTextField OSLFormattedTextField = ((JSpinner.DefaultEditor) OSLSpinner.getEditor()).getTextField();
		OSLFormattedTextField.setFormatterFactory(factory);
		OSLFormattedTextField.setHorizontalAlignment(JTextField.LEFT);
		OSLFormattedTextField.setFont(defaultFont);
		
		//Add the labels and textfields to the panel
		inputPanel.add(studentIDLabel);
		inputPanel.add(studentIDTextField);
		
		inputPanel.add(surnameLabel);
		inputPanel.add(surnameTextField);
		
		inputPanel.add(firstNameLabel);
		inputPanel.add(firstNameTextField);
		
		inputPanel.add(middleNameLabel);
		inputPanel.add(middleNameTextField);
		
		inputPanel.add(aptNumberLabel);
		inputPanel.add(aptNumberTextField);
		
		inputPanel.add(streetNumberLabel);
		inputPanel.add(streetNumberTextField);
		
		inputPanel.add(streetNameLabel);
		inputPanel.add(streetNameTextField);
		
		inputPanel.add(cityLabel);
		inputPanel.add(cityTextField);
				
		inputPanel.add(provinceLabel);
		inputPanel.add(provincesComboBox);
		
		inputPanel.add(postalCodeLabel);
		inputPanel.add(postalCodeTextField);
		
		inputPanel.add(CSLLabel);
		inputPanel.add(CSLSpinner);
		
		inputPanel.add(OSLLabel);
		inputPanel.add(OSLSpinner);
		
		//Set background and padding for the panel
		inputPanel.setBackground(Color.WHITE);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
	

	/*Method Name: buildcard1ButtonPanel
	*Purpose: To build the panel that occupies the bottom of Card1RightPanel, which hosts buttons for submission, go to the other card, and clear form
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildcard1ButtonPanel() {
		card1ButtonPanel = new JPanel();
		card1ButtonPanel.setLayout(new GridLayout(3,1,10,10));
		
		submitButton = new JButton("Submit");
		clearFormButton = new JButton("Clear Form");
		goToCard2Button = new JButton("Calculate Repayment");
		
		//Register buttons with listener
		submitButton.addActionListener(new buttonListener());
		clearFormButton.addActionListener(new buttonListener());
		goToCard2Button.addActionListener(new buttonListener());
		
		//Formats the buttons with styles
		submitButton.setForeground(Color.WHITE);
		submitButton.setBackground(fanshaweRed);
		submitButton.setFont(buttonFont);
		clearFormButton.setForeground(fanshaweGrey);
		clearFormButton.setBackground(fanshaweYellow);
		clearFormButton.setFont(buttonFont);
		goToCard2Button.setForeground(Color.WHITE);
		goToCard2Button.setBackground(fanshaweRed);
		goToCard2Button.setFont(buttonFont);
		card1ButtonPanel.add(submitButton);
		card1ButtonPanel.add(goToCard2Button);
		card1ButtonPanel.add(clearFormButton);
		card1ButtonPanel.setBackground(Color.WHITE);
		card1ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
	
	/*Method Name: buildCard2Panel
	*Purpose: To build the panel that holds the calculation card, which belongs to the cardsPanel
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard2Panel() {
		card2 = new JPanel();
		card2.setLayout(new GridLayout(1,2));
		buildCard2LeftPanel();
		buildCard2RightPanel();
		card2.add(card2LeftPanel);
		card2.add(card2RightPanel);
	}
	

	/*Method Name: buildCard2LeftPanel
	*Purpose: To build the panel that occupies the left of card2, which shows a text area for results output, a slider for prime rates selection,
	*a spinner for amortization period input and a calculate button
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard2LeftPanel() {
		//Initialize panel and set formatting 
		card2LeftPanel = new JPanel();
		card2LeftPanel.setLayout(new GridBagLayout());
		card2LeftPanel.setBackground(Color.WHITE);
		card2LeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		GridBagConstraints c = new GridBagConstraints();
		
		//Initialize the text area and add it to the gridbag so that it starts from the top
		outputForm = new JTextArea(14, 0);
		outputForm.setFont(buttonFont);
		outputForm.setEditable(false);
		outputForm.setText("Welcome!\n\n\nTo start, select your prime rate and amortization period\n\n\nClick Calculate and your calculation will show up here\n\n\ud83d\udc3b\uD83D\uDE00\uD83D\uDE00\ud83d\udc3b");
		outputForm.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 0, 3, 0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.ipady = 100;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 0;
		c.weightx = 1;
		card2LeftPanel.add(outputForm, c);
		
		//Add the slider after the text area for prime rate selection. The actual value is 100 times greater than labeled value (900 vs 9.00)
		//interest rates are forced to snap to ticks which are in quarter percents
		rateSlider = new JSlider(JSlider.HORIZONTAL, 0, 900, 425);
		rateSlider.setMajorTickSpacing(50);
		rateSlider.setMinorTickSpacing(25);
		rateSlider.setBackground(Color.WHITE);
		rateSlider.setSnapToTicks(true);
	    Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
	    labelTable.put( 0, new JLabel("0.00") );
	    labelTable.put(300, new JLabel("3.00") );
	    labelTable.put(450, new JLabel("Prime Rate") );
	    labelTable.put(600, new JLabel("6.00") );
	    labelTable.put(900, new JLabel("9.00") );
	    rateSlider.setLabelTable(labelTable);
		rateSlider.setPaintTicks(true);
		rateSlider.setPaintLabels(true);
		rateSlider.addChangeListener(new changeHandler());
		c.ipady = 1;
		c.gridy = 1;
		card2LeftPanel.add(rateSlider,c);
		
		//Add label for amortization period after slider and occupies only 1 gridwidth
		amortizationJLabel = new JLabel("Amortization period in months: ", SwingConstants.CENTER);
		amortizationJLabel.setFont(labelFont);
		c.anchor = GridBagConstraints.PAGE_END;
		c.ipady = 2;
		c.gridy = 2;
		c.weightx = 0.2;
		c.gridwidth = 1;
		card2LeftPanel.add(amortizationJLabel, c);
		
		//Add the spinner right to the label. Sets its text field to take in positive ints only starting from 1
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter numberFormatter = new NumberFormatter(format);
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(1);
		numberFormatter.setMaximum(1000);
		numberFormatter.setAllowsInvalid(false);
		DefaultFormatterFactory factory = new DefaultFormatterFactory(numberFormatter);
		amortizationSpinner = new JSpinner();
		amortizationSpinner.addChangeListener(new changeHandler());
		amortizationSpinner.setModel(new SpinnerNumberModel(60, 1, Integer.MAX_VALUE, 1));
		JFormattedTextField ftf = ((JSpinner.DefaultEditor) amortizationSpinner.getEditor()).getTextField();
		ftf.setFormatterFactory(factory);
		c.gridx = 1;
		c.weightx = 0.8;
		c.weighty = 1;
		card2LeftPanel.add(amortizationSpinner, c);
		
		//Add the calculation button at the bottom
		calculateButton = new JButton(String.format("Calculate at %.2f%% for %d months", prime, amortization));
		calculateButton.setForeground(Color.WHITE);
		calculateButton.setBackground(fanshaweRed);
		calculateButton.setFont(buttonFont); 
		c.ipady = 1;
		c.gridwidth = 2;
		c.gridy = 3;
		c.gridx = 0;
		c.weightx = 0;
		card2LeftPanel.add(calculateButton, c);
	}
	
	/*Method Name: buildCard2RightPanel
	*Purpose: To build the panel that occupies the right of card2, which hosts a record counter label at the top, 
	*the Card2FormPanel that display record details in the center, and the card2ButtonPanel that contains buttons at the bottom
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard2RightPanel() {
		
		//Initialize the panel and add the record counter label to the top
		card2RightPanel = new JPanel();
		card2RightPanel.setLayout(new BorderLayout());
		card2RightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		indexLabel = new JLabel("", SwingConstants.CENTER);
		indexLabel.setFont(msgFont);
		
		//Add the other two panels to the center and bottom
		buildCard2FormPanel();
		buildcard2ButtonPanel();
		card2RightPanel.add(indexLabel,BorderLayout.NORTH);
		card2RightPanel.add(card2FormPanel, BorderLayout.CENTER);
		card2RightPanel.add(card2ButtonPanel,BorderLayout.SOUTH);
		card2RightPanel.setBackground(Color.WHITE);
	}
	
	/*Method Name: buildCard2FormPanel
	*Purpose: To build the panel that occupies the center of Card2RightPanel, which hosts textfields and their labels that display record details
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildCard2FormPanel(){
		
		//Initialize the panel
		card2FormPanel = new JPanel();
		card2FormPanel.setLayout(new GridLayout(12, 2, 5, 5));
		card2FormPanel.setBackground(Color.WHITE);
		card2FormPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//Initialize the labels
		studentIDLabelDisplay = new JLabel("Student ID:", SwingConstants.CENTER);
		studentIDLabelDisplay.setFont(labelFont);
		surnameLabelDisplay  = new JLabel("Surname:", SwingConstants.CENTER);
		surnameLabelDisplay.setFont(labelFont);
		firstNameLabelDisplay  = new JLabel("First Name:", SwingConstants.CENTER);
		firstNameLabelDisplay.setFont(labelFont);	
		middleNameLabelDisplay  = new JLabel("Middle Name:", SwingConstants.CENTER);
		middleNameLabelDisplay .setFont(labelFont);
		aptNumberLabelDisplay  = new JLabel("Apartment Number:", SwingConstants.CENTER);
		aptNumberLabelDisplay.setFont(labelFont);
		streetNumberLabelDisplay  = new JLabel("Street Number:", SwingConstants.CENTER);
		streetNumberLabelDisplay .setFont(labelFont);
		streetNameLabelDisplay  = new JLabel("Street Name:", SwingConstants.CENTER);
		streetNameLabelDisplay .setFont(labelFont);
		cityLabelDisplay  = new JLabel("City:", SwingConstants.CENTER);
		cityLabelDisplay.setFont(labelFont);
		provinceLabelDisplay = new JLabel("Province:", SwingConstants.CENTER);
		provinceLabelDisplay.setFont(labelFont);
		postalCodeLabelDisplay  = new JLabel("Postal Code:", SwingConstants.CENTER);
		postalCodeLabelDisplay .setFont(labelFont);
		CSLLabelDisplay  = new JLabel("Canada Student Loan ($):", SwingConstants.CENTER);
		CSLLabelDisplay.setFont(labelFont);
		OSLLabelDisplay  = new JLabel("Ontario Student Loan ($):", SwingConstants.CENTER);
		OSLLabelDisplay.setFont(labelFont);
		
		//Initialize the text fields
		studentIDTextFieldDisplay = new JTextField();
		studentIDTextFieldDisplay.setBackground(Color.WHITE);
		studentIDTextFieldDisplay.setEditable(false);
		surnameTextFieldDisplay = new JTextField();
		surnameTextFieldDisplay.setBackground(Color.WHITE);
		surnameTextFieldDisplay.setEditable(false);
		firstNameTextFieldDisplay = new JTextField();
		firstNameTextFieldDisplay.setBackground(Color.WHITE);
		firstNameTextFieldDisplay.setEditable(false);
		middleNameTextFieldDisplay = new JTextField();
		middleNameTextFieldDisplay.setBackground(Color.WHITE);
		middleNameTextFieldDisplay.setEditable(false);
		aptNumberTextFieldDisplay = new JTextField();
		aptNumberTextFieldDisplay.setBackground(Color.WHITE);
		aptNumberTextFieldDisplay.setEditable(false);
		streetNumberTextFieldDisplay = new JTextField();
		streetNumberTextFieldDisplay.setBackground(Color.WHITE);
		streetNumberTextFieldDisplay.setEditable(false);
		streetNameTextFieldDisplay = new JTextField();
		streetNameTextFieldDisplay.setBackground(Color.WHITE);
		streetNameTextFieldDisplay.setEditable(false);
		cityTextFieldDisplay = new JTextField();
		cityTextFieldDisplay.setBackground(Color.WHITE);
		cityTextFieldDisplay .setEditable(false);
		provinceTextFieldDisplay = new JTextField();
		provinceTextFieldDisplay.setBackground(Color.WHITE);
		provinceTextFieldDisplay.setEditable(false);
		postalCodeTextFieldDisplay = new JTextField();
		postalCodeTextFieldDisplay.setBackground(Color.WHITE);
		postalCodeTextFieldDisplay.setEditable(false);
		CSLTextFieldDisplay = new JTextField();
		CSLTextFieldDisplay.setBackground(Color.WHITE);
		CSLTextFieldDisplay.setEditable(false);
		OSLTextFieldDisplay = new JTextField();
		OSLTextFieldDisplay.setBackground(Color.WHITE);
		OSLTextFieldDisplay.setEditable(false);
		
		//Add the labels and textfields to the panel in pairs
		card2FormPanel.add(studentIDLabelDisplay);
		card2FormPanel.add(studentIDTextFieldDisplay);
		card2FormPanel.add(surnameLabelDisplay);
		card2FormPanel.add(surnameTextFieldDisplay);
		card2FormPanel.add(firstNameLabelDisplay);
		card2FormPanel.add(firstNameTextFieldDisplay);
		card2FormPanel.add(middleNameLabelDisplay);
		card2FormPanel.add(middleNameTextFieldDisplay);
		card2FormPanel.add(aptNumberLabelDisplay);
		card2FormPanel.add(aptNumberTextFieldDisplay);
		card2FormPanel.add(streetNumberLabelDisplay);
		card2FormPanel.add(streetNumberTextFieldDisplay);
		card2FormPanel.add(streetNameLabelDisplay);
		card2FormPanel.add(streetNameTextFieldDisplay);
		card2FormPanel.add(cityLabelDisplay);
		card2FormPanel.add(cityTextFieldDisplay);		
		card2FormPanel.add(provinceLabelDisplay);
		card2FormPanel.add(provinceTextFieldDisplay);
		card2FormPanel.add(postalCodeLabelDisplay);
		card2FormPanel.add(postalCodeTextFieldDisplay);
		card2FormPanel.add(CSLLabelDisplay);
		card2FormPanel.add(CSLTextFieldDisplay);
		card2FormPanel.add(OSLLabelDisplay);
		card2FormPanel.add(OSLTextFieldDisplay);
	}
	
	/*Method Name: buildcard2ButtonPanel
	*Purpose: To build the panel that occupies the bottom of Card2RightPanel, which hosts buttons for going to the previous or next record, and the button for going back to card1
	*Accepts: N/A
	*Returns: N/A
	*/
	public void buildcard2ButtonPanel() {
		
		//Initialize the panel
		card2ButtonPanel = new JPanel();
		card2ButtonPanel.setLayout(new GridBagLayout());
		card2ButtonPanel.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		
		//Add the previous button
		previousButton = new BasicArrowButton(BasicArrowButton.WEST, fanshaweRed, Color.WHITE, Color.WHITE, Color.WHITE);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.insets = new Insets(3, 0, 3, 0);
		c.ipady = 15;
		c.gridx = 0;
		c.gridy = 0;
		card2ButtonPanel.add(previousButton, c);
		
		//Add the next button besides the previous button
		nextButton = new BasicArrowButton(BasicArrowButton.EAST, fanshaweRed, Color.WHITE, Color.WHITE, Color.WHITE);
		c.gridx = 1;
		card2ButtonPanel.add(nextButton, c);
		
		//Add the button for going back to card1 after the previous/next buttons
		goToCard1Button = new JButton("Insert New Record");
		goToCard1Button.setForeground(Color.WHITE);
		goToCard1Button.setBackground(fanshaweRed);
		goToCard1Button.setFont(buttonFont);
		c.gridwidth = 2;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		card2ButtonPanel.add(goToCard1Button, c);
		
		//Add the button for deleting current selected record
		removeButton = new JButton("DELETE This Record");
		removeButton.setForeground(fanshaweGrey);
		removeButton.setBackground(fanshaweYellow);
		removeButton.setFont(buttonFont);
		c.gridy = 2;
		card2ButtonPanel.add(removeButton,c);
		
		//Register the buttons with listener
		previousButton.addActionListener(new buttonListener());
		nextButton.addActionListener(new buttonListener());
		calculateButton.addActionListener(new buttonListener());
		goToCard1Button.addActionListener(new buttonListener());
		removeButton.addActionListener(new buttonListener());
	}
	
	/*Method Name: updateCalculationForm
	*Purpose: Function that will be called to update the card2FormPanel when previous/next button is clicked
	*Accepts: N/A
	*Returns: N/A
	*/
	private void updateCalculationForm() {
		
		//If showing the first record or last record already, pop up a message box and then do nothing
		if(currentIndex < 0) {
			JOptionPane.showMessageDialog(null, "Error: Already displaying the first record");
			currentIndex = 0;
		}
		else if (currentIndex > students.size() - 1) {
			JOptionPane.showMessageDialog(null, "Error: Already displaying the last record");
			currentIndex = students.size() - 1; 
		}
		else {
			studentIDTextFieldDisplay.setText(students.get(currentIndex).getStudentID());
			surnameTextFieldDisplay.setText(students.get(currentIndex).getSurname());
			middleNameTextFieldDisplay.setText(students.get(currentIndex).getMiddleName());
			firstNameTextFieldDisplay.setText(students.get(currentIndex).getFirstName());
			aptNumberTextFieldDisplay.setText(students.get(currentIndex).getAptNumber());
			streetNumberTextFieldDisplay.setText(students.get(currentIndex).getStreetNumber());
			streetNameTextFieldDisplay.setText(students.get(currentIndex).getStreetName());
			cityTextFieldDisplay.setText(students.get(currentIndex).getCity());
			provinceTextFieldDisplay.setText(students.get(currentIndex).getProvince());
			postalCodeTextFieldDisplay.setText(students.get(currentIndex).getPostalCode());
			CSLTextFieldDisplay.setText(String.format("%.2f", students.get(currentIndex).getCslLoanAmount()));
			OSLTextFieldDisplay.setText(String.format("%.2f", students.get(currentIndex).getOslLoanAmount()));
		}
	}
	

	/**
	 * Class Name:		buttonListener 
	 * Purpose:			An inner class which implements ActionListener and ZW_LoanInterface
	 * Coder:			Zitong Wang
	 * Date:			Jul 18, 2020
	 */
	private class buttonListener implements ActionListener, ZW_LoanInterface{
		
		/*Method Name: calculateLoanPayment
		*Purpose: Implemented method from ZW_LoanInterface. Calculates monthly pay
		*Accepts: double principal, double primeRate, int amortization
		*Returns: Double monthly pay
		*/
		public double calculateLoanPayment(double principal, double primeRate, int amortization) {
			double monthlyRate = primeRate * ANNUAL_RATE_TO_MONTHLY_RATE;
			double monthlyPayment =  principal * monthlyRate * Math.pow((1 + monthlyRate), amortization) / (Math.pow((1 + monthlyRate), amortization) - 1);
			
			//Results are rounded to two decimals 
			return (int)((monthlyPayment + 0.005) * 100)/100.0;
		}
		
		/*Method Name: actionPerformed
		*Purpose: Handles event when buttons are clicked; throws exception for negative loan inputs.
		*Accepts: ActionEvent e
		*Returns: N/A
		*/
		public void actionPerformed(ActionEvent e) {
			boolean isValidStudentID = true;
			
			//Handles events when submit button is clicked
			if(e.getActionCommand().equals("Submit")) {
				String studentIDInput = studentIDTextField.getText();
				
				//Validates student id for 7 digit number
				if(studentIDInput.isBlank()) {
					isValidStudentID = false;
					JOptionPane.showMessageDialog(null, "Error: Student ID is a required field");
				}
				else if(studentIDInput.length() != 7) {
					
					isValidStudentID = false;
					JOptionPane.showMessageDialog(null, "Error: Please enter 7 digits Student ID");
				}
				else {
					for(int i = 0; i < studentIDInput.length(); ++i) {
						if(!Character.isDigit(studentIDInput.charAt(i))) {
							isValidStudentID = false;
							JOptionPane.showMessageDialog(null, "Error: Please enter 7 digits Student ID");
							break;
						}
					}
				}
				
				//Validates loan amounts entered provided student id is valid
				if(isValidStudentID) {
					double CSL = (double) CSLSpinner.getValue();
					double OSL = (double) OSLSpinner.getValue();
					try {
						numberChecker(CSL);
						numberChecker(OSL);
						Student newRecord = new Student(studentIDTextField.getText(), surnameTextField.getText(), middleNameTextField.getText(), firstNameTextField.getText(), 
								aptNumberTextField.getText(), streetNumberTextField.getText(), streetNameTextField.getText(), cityTextField.getText(), (String)provincesComboBox.getSelectedItem(),
								postalCodeTextField.getText(), CSL, OSL);
						students.add(newRecord);
						JOptionPane.showMessageDialog(null, "Record added successfully");
					}
					catch (ZW_NegativeValueException ex) {
						JOptionPane.showMessageDialog(null, "Error: Loan Amounts cannot be negative; they will be converted to positive numbers");
						if(CSL < 0) {
							CSLSpinner.setValue(- CSL);
						}
						if(OSL < 0) {
							OSLSpinner.setValue(- OSL);
						}
						Student newRecord = new Student(studentIDTextField.getText(), surnameTextField.getText(), middleNameTextField.getText(), firstNameTextField.getText(), 
								aptNumberTextField.getText(), streetNumberTextField.getText(), streetNameTextField.getText(), cityTextField.getText(), (String)provincesComboBox.getSelectedItem(),
								postalCodeTextField.getText(),(double) CSLSpinner.getValue(),(double) OSLSpinner.getValue());
						students.add(newRecord);
						JOptionPane.showMessageDialog(null, "Record added successfully");
					}
				}
				
			}
			
			//Handles events when clear form button is clicked
			if(e.getActionCommand().equals("Clear Form")) {
				surnameTextField.setText("");
				studentIDTextField .setText("");
				firstNameTextField.setText("");
				middleNameTextField.setText("");
				aptNumberTextField.setText("");
				streetNumberTextField.setText("");
				streetNameTextField.setText("");
				cityTextField.setText("");
				provincesComboBox.setSelectedIndex(8);
				postalCodeTextField.setText("");
				CSLSpinner.setValue(0);
				OSLSpinner.setValue(0);
			}
			
			//Handles events when Calculate Repayment button is clicked, which takes user to card2, and initializes card2
			if(e.getActionCommand().equals("Calculate Repayment")) {
				if(students.size()!=0) {
			        cl.show(cards, "Database Window");
			        currentIndex = 0;
			        indexLabel.setText(String.format("Showing record      %d    /    %d", currentIndex + 1, students.size()));
			        updateCalculationForm();
			        outputForm.setText(String.format("Welcome, %s!\n\n\nTo start, select your prime rate and amortization period\n\n\nClick Calculate and your calculation will show up here\n\n\ud83d\udc3b\uD83D\uDE00\uD83D\uDE00\ud83d\udc3b", 
			        		(students.get(currentIndex).getFirstName().isBlank()) ? "Anonymous user" : students.get(currentIndex).getFirstName()));
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: To start, enter a student's information");
				}
   
			}
			
			//Handles events when Insert New Record button is clicked, which takes user to card1
			if(e.getActionCommand().equals("Insert New Record")) {
				cl.show(cards, "Main Window");
			}
			
			//Handles events when previousButton button is clicked, which update the card2FormPanel with the previous record
			if(e.getSource() == previousButton) {
				currentIndex--;
				updateCalculationForm();
				indexLabel.setText(String.format("Showing record      %d    /    %d", currentIndex +1 , students.size()));
				outputForm.setText(String.format("Welcome, %s!\n\n\nTo start, select your prime rate and amortization period\n\n\nClick Calculate and your calculation will show up here\n\n\ud83d\udc3b\uD83D\uDE00\uD83D\uDE00\ud83d\udc3b",
						(students.get(currentIndex).getFirstName().isBlank()) ? "Anonymous user" : students.get(currentIndex).getFirstName()));
			}
			
			//Handles events when nextButton button is clicked, which update the card2FormPanel with the next record
			if(e.getSource() == nextButton) {
				currentIndex++;
				updateCalculationForm();
				indexLabel.setText(String.format("Showing record      %d    /    %d", currentIndex +1 , students.size()));
				outputForm.setText(String.format("Welcome, %s!\n\n\nTo start, select your prime rate and amortization period\n\n\nClick Calculate and your calculation will show up here\n\n\ud83d\udc3b\uD83D\uDE00\uD83D\uDE00\ud83d\udc3b", 
						(students.get(currentIndex).getFirstName().isBlank()) ? "Anonymous user" : students.get(currentIndex).getFirstName()));
			}
			
			//Handles events when calculateButton button is clicked, which calculates the monthly loan amounts and outputs to the text area
			if(e.getSource() == calculateButton) {
				OSLPayment = calculateLoanPayment(students.get(currentIndex).getOslLoanAmount(), prime + 1.0, amortization);
				CSLPayment = calculateLoanPayment(students.get(currentIndex).getCslLoanAmount(), prime + 2.5, amortization);
				outputForm.setText(String.format("%s\n\nPrime rate is %.2f%%\n\nAmortization period is %d months\n\nCSL rate: %.2f%%\nCSL payment: $%.2f per month\n\nOSL rate: %.2f%%\nOSL payment: $%.2f per month\n\nTotal payment: $%.2f per month",
						students.get(currentIndex).toString(), prime, amortization, prime + 2.5, CSLPayment, prime + 1.0, OSLPayment, OSLPayment + CSLPayment));
			}
			
			//Handles events when removeButton is clicked, which deletes the current showing record
			if(e.getSource() == removeButton) {
				students.remove(currentIndex);
				if(currentIndex > students.size() - 1) {
					currentIndex--;
				}
				if(students.size() == 0) {
					JOptionPane.showMessageDialog(null, "Database now empty. To start, enter a student's information");
					cl.show(cards, "Main Window");
				}
				else {
					updateCalculationForm();
					indexLabel.setText(String.format("Showing record      %d    /    %d", currentIndex +1 , students.size()));
					outputForm.setText(String.format("Welcome, %s!\n\n\nTo start, select your prime rate and amortization period\n\n\nClick Calculate and your calculation will show up here\n\n\ud83d\udc3b\uD83D\uDE00\uD83D\uDE00\ud83d\udc3b", 
							(students.get(currentIndex).getFirstName().isBlank()) ? "Anonymous user" : students.get(currentIndex).getFirstName()));
				}
			}
		}
	}
	
	/**
	 * Class Name:		changeHandler
	 * Purpose:			An inner class which implements ChangeListener
	 * Coder:			Zitong Wang
	 * Date:			Jul 18, 2020
	 */
	private class changeHandler implements ChangeListener{
		
		/*Method Name: stateChanged
		*Purpose: Handles event when the state of spinner or slider is changed
		*Accepts: ChangeEvent e
		*Returns: N/A
		*/
		public void stateChanged(ChangeEvent e) {
			
			//Update the prime value and the calculateButton text when the slider is changed. Actually value is 100 times less than the labeled value
			if(e.getSource() == rateSlider) {
				JSlider source = (JSlider) e.getSource();
				prime = source.getValue()/100.0;
				calculateButton.setText(String.format("Calculate at %.2f%% for %d months", prime, amortization));
			}
			
			//Update the amortization value and the calculateButton text when the spinner is changed
			if(e.getSource() == amortizationSpinner) {
				amortization = (int) amortizationSpinner.getValue();
				calculateButton.setText(String.format("Calculate at %.2f%% for %d months", prime, amortization));
			}
		}
	}
	
	/*Method Name: numberChecker
	*Purpose: A helper method that throws a ZW_NegativeValueException if the parameter is negative
	*Accepts: double num
	*Returns: N/A
	*/
	private void numberChecker(double num) throws ZW_NegativeValueException {
		if(num < 0) {
			throw new ZW_NegativeValueException();
		}
	}

	//Add an embedded main method for testing
	public static void main(String[] args) 
	{	
		//Creates an instance of the frame
		 new StudentLoanApp();
	}//End of main method
}//End of class
