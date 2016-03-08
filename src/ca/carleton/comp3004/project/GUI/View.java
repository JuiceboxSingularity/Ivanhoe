package ca.carleton.comp3004.project.GUI;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter.FilterBypass;

import ca.carleton.comp3004.project.gameobjects.Card;

public class View extends JFrame {
	private Image images;
	private JPanel panel;
	private GridBagConstraints c;

	private JPanel sidebar;
	private JPanel sidebarOptions;
	private JPanel bottombar;
	private DrawPanel playArea;
	
	private JLayeredPane[] playerArea = new JLayeredPane[5];

	private JPanel otherArea;
	private JPanel playerYou;
	private JPanel playerOne;
	private JPanel playerTwo;
	private JPanel playerThree;
	private JPanel playerFour;
	private JPanel playZone;
	private JPanel playZone1;
	private JPanel playZone2;
	private JPanel playZone3;
	private JPanel playZone4;
	
	
	private JTextField connectBox;
	private JTextField nameBox;
	private Color tourColor;
	
	private JButton button;
	private JButton connectButton;
	
	private JTextArea textArea;
	
	private static Color purple = new Color(115,20,130);
	private static Color red = new Color(150,20,20);
	private static Color yellow = new Color(165,160,20);
	private static Color green = new Color(30,150,20);
	private static Color blue = new Color(25,20,155);

	private Model model;

	//private int testval = 0;

	public View(Model tempmodel) {
		tourColor = Color.white;
		images = new Image();
		initUI();
		model = tempmodel;
	}
	
	private void textAppend(String string){
		int max = 4;
		int lines = textArea.getLineCount();
		
		if (lines > max) {
			int linesToRemove = lines - max -1;
			int lengthToRemove;
			try {
				lengthToRemove = textArea.getLineStartOffset(linesToRemove);
				textArea.replaceRange("", 0, lengthToRemove);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		textArea.append(string);
	}

	private void initUI() {
		panel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();

		sidebar = new JPanel(new GridLayout(6,0));
		//sidebar = new JPanel();
		sidebarOptions = new JPanel(new GridBagLayout());
		bottombar = new JPanel();
		playArea = new DrawPanel();
		playArea.setLayout(new GridLayout(2,3));
		
		for (int x = 0; x < 5; x++){
			playerArea[x] = new JLayeredPane();
		}
		
		otherArea = new JPanel(new GridLayout(2,5));
		playerYou = new JPanel(new GridLayout(2,5));
		playerOne = new JPanel(new GridLayout(2,5));
		playerTwo = new JPanel(new GridLayout(2,5));
		playerThree = new JPanel(new GridLayout(2,5));
		playerFour = new JPanel(new GridLayout(2,5));
		
		JLabel tempLabel;
		connectButton = new JButton("Connect");
		button = new JButton("Refresh");
		connectBox = new JTextField(10);
		nameBox = new JTextField(10);
		
		
		
		
		otherArea.setOpaque(false);
		
		sidebar.setBackground(Color.gray);

		sidebarOptions.setBackground(Color.darkGray);

		bottombar.setBackground(new Color(115, 60, 20));

		playerYou.setBackground(new Color(190, 110, 30));
		playerYou.setBorder(BorderFactory.createTitledBorder("You"));
		playerOne.setBackground(new Color(190, 110, 30));
		playerOne.setBorder(BorderFactory.createTitledBorder("Player"));
		playerTwo.setBackground(new Color(245, 205, 135));
		playerTwo.setBorder(BorderFactory.createTitledBorder("Player"));
		playerThree.setBackground(new Color(245, 205, 135));
		playerThree.setBorder(BorderFactory.createTitledBorder("Player"));
		playerFour.setBackground(new Color(190, 110, 30));
		playerFour.setBorder(BorderFactory.createTitledBorder("Player"));
		
		playArea.paintComponent(images.getBackgroundPic().getGraphics());

		panel.setBackground(Color.gray);

		c.weightx = 10;
		c.weighty = 10;
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		connectBox.setText("127.0.0.1");
		sidebarOptions.add(connectBox, c);
		
		c.weightx = 10;
		c.weighty = 10;
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		sidebarOptions.add(nameBox, c);
		
		
		connectButton.addActionListener(new ConnectButtonActionListener());
		c.weightx = 0.1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		sidebarOptions.add(connectButton, c);
		
		button.addActionListener(new ButtonActionListener());
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		sidebarOptions.add(button, c);
		
		tempLabel = new JLabel();
		tempLabel.setText("Server");
		tempLabel.setForeground(yellow);
		c.weightx = 0.1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		sidebarOptions.add(tempLabel, c);
		
		tempLabel = new JLabel();
		tempLabel.setText("Name");
		tempLabel.setForeground(yellow);
		c.weightx = 0.1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		sidebarOptions.add(tempLabel, c);
		
		textArea = new JTextArea(6, 20);
		//((AbstractDocument) textArea.getDocument()).setDocumentFilter(new ConsoleDocument(textArea, 3));
		textArea.setEditable(false);
		c.weightx = 1.0;
		c.weighty = 2.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
		sidebarOptions.add(textArea, c);
				
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.4;
		c.weighty = 0.7;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 1;
		playArea.setBorder(BorderFactory.createTitledBorder(null,"Play Area", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.4), (int) (panel.getHeight() * 0.7)));
		panel.add(playArea, c);
		c.weightx = 0.2;
		c.weighty = 0.7;
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 1;
		sidebar.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.7)));
		panel.add(sidebar, c);
		//
		c.weightx = 0.2;
		c.weighty = 0.3;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		playerYou.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.3)));
		panel.add(playerYou, c);
		//
		c.weightx = 0.6;
		c.weighty = 0.3;
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 2;
		bottombar.setBorder(BorderFactory.createTitledBorder("Hand"));
		bottombar.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.6), (int) (panel.getHeight() * 0.3)));
		panel.add(bottombar, c);
		c.weightx = 0.2;
		c.weighty = 0.3;
		c.gridx = 3;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		sidebarOptions.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.3)));
		panel.add(sidebarOptions, c);
		c.weightx = 0.2;
		c.weighty = 0.35;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		playerOne.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.35)));
		panel.add(playerOne, c);
		c.weightx = 0.2;
		c.weighty = 0.35;
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		playerTwo.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.35)));
		panel.add(playerTwo, c);
		c.weightx = 0.2;
		c.weighty = 0.35;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		playerThree.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.35)));
		panel.add(playerThree, c);
		c.weightx = 0.2;
		c.weighty = 0.35;
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		playerFour.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.35)));
		panel.add(playerFour, c);
		
		otherArea.setBorder(BorderFactory.createTitledBorder(null,"Other Area", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		playArea.add(otherArea);
		playerArea[1].setBorder(BorderFactory.createTitledBorder(null,"Player 1", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		playArea.add(playerArea[1]);
		playerArea[2].setBorder(BorderFactory.createTitledBorder(null,"Player 2", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		playArea.add(playerArea[2]);
		playerArea[3].setBorder(BorderFactory.createTitledBorder(null,"Player 3", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		playArea.add(playerArea[3]);
		playerArea[0].setBorder(BorderFactory.createTitledBorder(null,"You", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		playArea.add(playerArea[0]);
		playerArea[4].setBorder(BorderFactory.createTitledBorder(null,"Player 4", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		playArea.add(playerArea[4]);
		
		//playerArea.setVisible(true);
		
		add(panel);
		setTitle("Ivanhoe Game");
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void loadHand() {
		JButton tempButton;
		java.util.List<Card> hand = model.getHand();
		bottombar.removeAll();
		for (int i = 0; i < hand.size(); i++) {
			//System.out.println("GETTING HAND: "+i);
			tempButton = new JButton(new ImageIcon(images.getCards(hand.get(i))));
			int temp = 100;
			if (bottombar.getWidth() / model.getHandSize() < temp) {
				temp = bottombar.getWidth() / model.getHandSize();
			}
			tempButton.setPreferredSize(new Dimension((int) (temp), 160));
			tempButton.addActionListener(new HandActionListener(i));
			bottombar.add(tempButton, c);
		}
		//System.out.println("DONE GETTING HAND");
		revalidate();
		repaint();
	}

	public void loadPlayArea() {
		JLabel tempLabel;
		
		
		
		for (int x = 0; x < 5;x++){
			playerArea[x].removeAll();
			playerArea[x].addMouseListener(new targetPlayerListener(x));
			java.util.List<Card> played = model.getPlayerArea(x);
			
			for (int i = 0; i < played.size(); i++) {
				tempLabel = new JLabel(new ImageIcon(images.getCards(played.get(i))));
				tempLabel.setSize(new Dimension(images.getCards(played.get(i)).getWidth(),images.getCards(played.get(i)).getHeight()));
				//NEW
				tempLabel.addMouseListener(new cardInPlayListener(played.get(i),x));
				playerArea[x].add(tempLabel, new Integer(i));
				tempLabel.setLocation((int)tempLabel.getLocation().getX()+5+10*i,(int)tempLabel.getLocation().getY()+15+15*i);
				//System.out.println(i);
			}
		}
		revalidate();
		repaint();
	}

	public void loadTokens() {
		JLabel templabel;
		JPanel tempPanel;
		//YOU
		//otherArea.getComponentCount()
		//tempPanel = new JPanel();
		playerYou.removeAll();
		templabel = new JLabel();
		tempPanel = new JPanel(new GridLayout(1,5));
		tempPanel.setOpaque(false);
		templabel.setOpaque(true);
		if (model.getPlayerTokens(0).contains("P")) {
			templabel.setBackground(purple);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(0).contains("R")) {
			templabel.setBackground(red);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(0).contains("B")) {
			templabel.setBackground(blue);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(0).contains("Y")) {
			templabel.setBackground(yellow);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(0).contains("G")) {
			templabel.setBackground(green);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		playerYou.add(tempPanel);
		/*
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		
		if (model.getPlayerSpecials().contains("Shield")) {
			templabel = new JLabel(new ImageIcon(images.resize(images.getCards(14), (int)playerYou.getWidth()/5, (int)playerYou.getHeight()/2)));
			//NEW
			templabel.addMouseListener(new cardInPlayListener((int)14,0));
			tempPanel.add(templabel);
		}
		if (model.getPlayerSpecials().contains("Stunned")) {
			templabel = new JLabel(new ImageIcon(images.resize(images.getCards(15), (int)playerYou.getWidth()/5, (int)playerYou.getHeight()/2)));
			//NEW
			templabel.addMouseListener(new cardInPlayListener((int)15,0));
			tempPanel.add(templabel);
		}
		if (model.getPlayerSpecials().contains("Ivanhoe")) {
			templabel = new JLabel(new ImageIcon(images.resize(images.getCards(16), (int)playerYou.getWidth()/5, (int)playerYou.getHeight()/2)));
			//NEW
			templabel.addMouseListener(new cardInPlayListener((int)16,0));
			tempPanel.add(templabel);
			
		}
		
		playerYou.add(tempPanel);
		*/
		
		//playerArea1.
		playerOne.removeAll();
		templabel = new JLabel();
		tempPanel = new JPanel(new GridLayout(1,5));
		tempPanel.setOpaque(false);
		templabel.setOpaque(true);
		if (model.getPlayerTokens(1).contains("P")) {
			templabel.setBackground(purple);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(1).contains("R")) {
			templabel.setBackground(red);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(1).contains("B")) {
			templabel.setBackground(blue);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(1).contains("Y")) {
			templabel.setBackground(yellow);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(1).contains("G")) {
			templabel.setBackground(green);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		playerOne.add(tempPanel);
		
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		
		
		playerOne.add(tempPanel);

		//playerArea2.]
		playerTwo.removeAll();
		templabel = new JLabel();
		templabel.setOpaque(true);
		tempPanel = new JPanel(new GridLayout(1,5));
		tempPanel.setOpaque(false);
		if (model.getPlayerTokens(2).contains("P")) {
			templabel.setBackground(purple);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(2).contains("R")) {
			templabel.setBackground(red);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(2).contains("B")) {
			templabel.setBackground(blue);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(2).contains("Y")) {
			templabel.setBackground(yellow);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(2).contains("G")) {
			templabel.setBackground(green);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		playerTwo.add(tempPanel);
		
		/*
		//playerArea3.
		playerThree.removeAll();
		templabel = new JLabel();
		templabel.setOpaque(true);
		tempPanel = new JPanel(new GridLayout(1,5));
		tempPanel.setOpaque(false);
		if (model.getPlayerTokens(3).contains("P")) {
			templabel.setBackground(purple);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(3).contains("R")) {
			templabel.setBackground(red);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(3).contains("B")) {
			templabel.setBackground(blue);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(4).contains("Y")) {
			templabel.setBackground(yellow);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(3).contains("G")) {
			templabel.setBackground(green);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		playerThree.add(tempPanel);
		
		
		
		//playerArea4.
		playerFour.removeAll();
		templabel = new JLabel();
		templabel.setOpaque(true);
		tempPanel = new JPanel(new GridLayout(1,5));
		tempPanel.setOpaque(false);
		if (model.getPlayerTokens(4).contains("P")) {
			templabel.setBackground(purple);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(4).contains("R")) {
			templabel.setBackground(red);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(4).contains("B")) {
			templabel.setBackground(blue);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(4).contains("Y")) {
			templabel.setBackground(yellow);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		templabel = new JLabel();
		templabel.setOpaque(true);
		if (model.getPlayerTokens(4).contains("G")) {
			templabel.setBackground(green);
			tempPanel.add(templabel);
		} else {
			templabel.setOpaque(false);
			tempPanel.add(templabel);
		}
		playerFour.add(tempPanel);
		*/
	}

	public void update() {
		loadHand();
		loadPlayArea();
		loadTokens();
	}
	
	public void chooseTourColor() {
		//sidebarOptions
		sidebar.removeAll();
		
		JLabel tempLabel;
		tempLabel = new JLabel("Pick a color:");
		sidebar.add(tempLabel);
		
		JButton tempButton;
		
		tempButton = new JButton();
		tempButton.setBackground(purple);
		tempButton.addActionListener(new ColorChooserListener(purple));
		//tempButton.setSize(new Dimension(50,50));
		sidebar.add(tempButton);
		
		tempButton = new JButton();
		tempButton.setBackground(red);
		tempButton.addActionListener(new ColorChooserListener(red));
		//tempButton.setSize(new Dimension(50,50));
		sidebar.add(tempButton);
		
		tempButton = new JButton();
		tempButton.setBackground(blue);
		tempButton.addActionListener(new ColorChooserListener(blue));
		//tempButton.setSize(new Dimension(50,50));
		sidebar.add(tempButton);
		
		tempButton = new JButton();
		tempButton.setBackground(yellow);
		tempButton.addActionListener(new ColorChooserListener(yellow));
		//tempButton.setSize(new Dimension(50,50));
		sidebar.add(tempButton);
		
		tempButton = new JButton();
		tempButton.setBackground(green);
		tempButton.addActionListener(new ColorChooserListener(green));
		//tempButton.setSize(new Dimension(50,50));
		sidebar.add(tempButton);
		
		revalidate();
		repaint();
	}
	
	//Will need to be changed
	public void useCard(int x) {
		//model.getHand().remove(0);
		//model.setHand(model.getHand().remove(0));
		System.out.println(x);
	}
	
	//Will need to be changed
	public void selectPlayedCard(Card card, int player) {
		System.out.println("Card: " + card.getCardName());
		System.out.println("Player: " + player);
	}

	//Will need to be changed
	public void selectColor(Color col) {
		System.out.println("Chosen" + col);
		tourColor = col;
		playArea.setBorder(BorderFactory.createTitledBorder(null,"Play Area", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		sidebar.removeAll();
		revalidate();
		repaint();
	}
	
	//Will need to be changed
	public void targetPlayer(int player) {
		System.out.println("Target Player: " + player);
	}
	
	//Will need to be changed
	public void connect() {
		//do stuff using connectBox input
	}
	
	//Will need to be changed
	public JButton getButton() {
		return button;
	}

	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			textAppend("WHAT\n");
			loadHand();
			loadPlayArea();
			loadTokens();
			chooseTourColor();
			//connect();
		}
	}
	
	class ConnectButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}


	private class HandActionListener implements ActionListener {
		private int card;

		public HandActionListener(int card) {
			this.card = card;
		}

		public void actionPerformed(ActionEvent e) {
			useCard(card);
		}
	}
	
	private class ColorChooserListener implements ActionListener {
		private Color col;

		public ColorChooserListener(Color col) {
			this.col = col;
		}

		public void actionPerformed(ActionEvent e) {
			selectColor(col);
		}
	}
	
	private class cardInPlayListener implements MouseListener {
		private Card card;
		private int player;

		public cardInPlayListener(Card card, int player) {
			this.card = card;
			this.player = player;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			selectPlayedCard(card,player);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}//*/

	private class targetPlayerListener implements MouseListener {
		//private int card;
		private int player;

		public targetPlayerListener(int player) {
			//this.card = card;
			this.player = player;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			targetPlayer(player);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}//*/
	
	public class DrawPanel extends JPanel {
		// @Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			if (this.getWidth() > 0 && this.getHeight() > 0) {
				g.drawImage(images.getBackgroundPic(this.getWidth(),this.getHeight()), 0, 0, null);
			}
		}
	}
}