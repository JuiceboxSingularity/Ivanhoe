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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Base64;
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
import javax.swing.JOptionPane;
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
import javax.swing.Timer;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Game;

public class View extends JFrame {
	private Image images;
	private JPanel panel;
	private GridBagConstraints c;

	private final int WINDOW_WIDTH = 1024;
	private final int WINDOW_HEIGHT = 800;
	private JPanel sidebarOptions;
	private JPanel bottombar;
	private DrawPanel playArea;
	
	private JLayeredPane[] playerArea = new JLayeredPane[6];

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
	private JButton startGameButton;
	private JButton startButton;
	private JButton endButton;
	private JButton widthdrawButton;
	
	private JTextArea textArea;
	
	private static Color purple = new Color(115,20,130);
	private static Color red = new Color(150,20,20);
	private static Color yellow = new Color(165,160,20);
	private static Color green = new Color(30,150,20);
	private static Color blue = new Color(25,20,155);

	private Model model;
	
	
	SocketChannel channel;	
	Selector selector;
	boolean connected;
	int port = 1001;
	
	CharBuffer charBuffer = CharBuffer.allocate(8192);
	ByteBuffer byteBuffer = ByteBuffer.allocate(8192);

	Charset charset = Charset.forName("US-ASCII");
	CharsetEncoder encoder = charset.newEncoder();
	CharsetDecoder decoder = charset.newDecoder();
	
	Base64.Encoder b64Encoder = Base64.getEncoder();
	Base64.Decoder b64Decoder = Base64.getDecoder();
	
	public View(Model tempmodel) {
		tourColor = Color.black;
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
		panel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		sidebarOptions = new JPanel(new GridBagLayout());
		bottombar = new JPanel();
		playArea = new DrawPanel();
		playArea.setLayout(new GridLayout(2,3));
		
		for (int x = 0; x < 6; x++){
			playerArea[x] = new JLayeredPane();
		}
		
		playerYou = new JPanel(new GridLayout(2,5));
		playerOne = new JPanel(new GridLayout(2,5));
		playerTwo = new JPanel(new GridLayout(2,5));
		playerThree = new JPanel(new GridLayout(2,5));
		playerFour = new JPanel(new GridLayout(2,5));
		
		JLabel tempLabel;
		connectButton = new JButton("Connect");
		button = new JButton("Refresh");
		startButton = new JButton("Start Turn");
		endButton = new JButton("End Turn");
		widthdrawButton = new JButton("Widthdraw");
		startGameButton = new JButton("Start Game");

		connectBox = new JTextField(10);
		nameBox = new JTextField(10);
		
		sidebarOptions.setBackground(Color.darkGray);

		bottombar.setBackground(new Color(115, 60, 20));

		playerYou.setBackground(new Color(190, 110, 30));
		playerYou.setBorder(BorderFactory.createTitledBorder("You"));

		panel.setBackground(Color.gray);

		
		c = new GridBagConstraints();
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridheight = 1;
		c.gridwidth = 1;
		//c.anchor = GridBagConstraints.PAGE_END;
		
		c.gridx = 0;
		c.gridy = 3;
		connectButton.addActionListener(new ConnectButtonActionListener());
		sidebarOptions.add(connectButton, c);
		
		c.gridx = 0;
		c.gridy = 4;
		button.addActionListener(new ButtonActionListener());
		sidebarOptions.add(button, c);
		
		c.gridx = 0;
		c.gridy = 5;
		widthdrawButton.addActionListener(new WidthdrawButtonActionListener());
		sidebarOptions.add(widthdrawButton, c);
		
		c.gridx = 1;
		c.gridy = 3;
		startButton.addActionListener(new StartButtonActionListener());
		sidebarOptions.add(startButton, c);
		
		c.gridx = 1;
		c.gridy = 4;
		endButton.addActionListener(new EndButtonActionListener());
		sidebarOptions.add(endButton, c);
		
		c.gridx = 1;
		c.gridy = 5;
		startGameButton.addActionListener(new StartGameButtonActionListener());
		sidebarOptions.add(startGameButton, c);
		
		textArea = new JTextArea(25, 20);
		textArea.setEditable(false);
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.ipady = 25;
		sidebarOptions.add(textArea, c);
				
		c = new GridBagConstraints();
		c.gridheight = 1;
		c.gridwidth = 1;
		
		tempLabel = new JLabel();
		tempLabel.setText("Server");
		tempLabel.setForeground(yellow);
		c.gridx = 0;
		c.gridy = 1;
		sidebarOptions.add(tempLabel, c);

		tempLabel = new JLabel();
		tempLabel.setText("Name");
		tempLabel.setForeground(yellow);
		c.gridx = 0;
		c.gridy = 2;
		sidebarOptions.add(tempLabel, c);

		
		c.gridx = 1;
		c.gridy = 1;
		connectBox.setText("127.0.0.1");
		sidebarOptions.add(connectBox, c);
		
		c.gridx = 1;
		c.gridy = 2;
		sidebarOptions.add(nameBox, c);
		
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.7;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 4;
		//playArea.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.4), (int) (panel.getHeight() * 0.7)));
		panel.add(playArea, c);
		
		c.weightx = 0.2;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		//playerYou.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.2), (int) (panel.getHeight() * 0.3)));
		panel.add(playerYou, c);
		//
		c.weightx = 0.6;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 2;
		bottombar.setBorder(BorderFactory.createTitledBorder("Hand"));
		bottombar.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.6), (int) (panel.getHeight() * 0.3)));
		panel.add(bottombar, c);
		
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		sidebarOptions.setPreferredSize(new Dimension(250,0));
		panel.add(sidebarOptions, c);
		
		playerArea[0].setBorder(BorderFactory.createTitledBorder(null,"Player 1", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.add(playerArea[0]);
		playerArea[1].setBorder(BorderFactory.createTitledBorder(null,"Player 2", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.add(playerArea[1]);
		playerArea[2].setBorder(BorderFactory.createTitledBorder(null,"Player 3", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.add(playerArea[2]);
		playerArea[3].setBorder(BorderFactory.createTitledBorder(null,"Player 4", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.add(playerArea[3]);
		
		//otherArea.setBorder(BorderFactory.createTitledBorder(null,"You", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white));
		//playArea.add(otherArea);
		playerArea[5].setBorder(BorderFactory.createTitledBorder(null,"You", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.add(playerArea[5]);
		
		playerArea[4].setBorder(BorderFactory.createTitledBorder(null,"Player 5", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), tourColor));
		playArea.add(playerArea[4]);
		
		
				
		//playerArea.setVisible(true);
		
		add(panel);
		setTitle("Ivanhoe Game");
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
					   
		new Timer(100, new NetworkActionListener()).start();
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
		
		
		
		for (int x = 0; x < model.game.getPlayers().size();x++){
			playerArea[x].removeAll();
			playerArea[x].addMouseListener(new targetPlayerListener(x));
			java.util.List<Card> played = model.getPlayerArea(x);
			if (x == model.playerNum){
				
				playerArea[5].removeAll();
				//otherArea.addMouseListener(new targetPlayerListener(x));
			}
			for (int i = 0; i < played.size(); i++) {
				tempLabel = new JLabel(new ImageIcon(images.getCards(played.get(i))));
				tempLabel.setSize(new Dimension(images.getCards(played.get(i)).getWidth(),images.getCards(played.get(i)).getHeight()));
				tempLabel.addMouseListener(new cardInPlayListener(played.get(i),x));
				
				
				if (x == model.playerNum){ 
					playerArea[5].add(tempLabel, new Integer(i));
					Border border = BorderFactory.createTitledBorder(null,"You (Player "+(model.playerNum+1)+")", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.white);
					playerArea[5].setBorder(border);
				} else {
					playerArea[x].add(tempLabel, new Integer(i));
					
				}
				
				tempLabel.setLocation((int)tempLabel.getLocation().getX()+5+10*i,(int)tempLabel.getLocation().getY()+15+15*i);
				//System.out.println(i);
			}
		}
		revalidate();
		repaint();
	}

	public void loadTokens() {
		if (true){
			//return;
		}
		//System.out.println("TOKENS");
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
		if (model.getPlayerTokens(0).contains("P")||true) {
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
		if (model.getPlayerTokens(1).contains("P")||true) {
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
		/*
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

	public CardColor chooseColor() {
		java.util.List<CardColor> options = new ArrayList<CardColor>();
		for (CardColor c : CardColor.values()) {
			if ((!c.equals(CardColor.None)) && (!c.equals(CardColor.White))) {
				options.add(c);
			}
		}
		Object[] oparr = options.toArray();
		int n = JOptionPane.showOptionDialog(this,
				"Please choose a color ",
						"Color selector",
						JOptionPane.CLOSED_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						oparr,
						oparr[0]);
		
		return options.get(n);
	}
	
	public void update() {
		loadHand();
		loadPlayArea();
		loadTokens();
	}
	
	//Will need to be changed
	public void useCard(int x) {
		String message;
		
		Game game = model.game;
		//model.getHand().remove(0);
		//model.setHand(model.getHand().remove(0));
		System.out.println(x);
		if (model.game.getCurrentPlayer().getId() == (model.playerNum+1)){
			game.validatePlay(game.getPlayers().get(model.playerNum).getHand().get(x));
			//game.performPlay(x);
			message = "play:"+x;
			sendString(message);
		} else {
			System.out.println("NOT YOUR TURN");
		}
	}
	
	//Will need to be changed
	public void selectPlayedCard(Card card, int player) {
		System.out.println("Card: " + card.getCardName());
		System.out.println("Player: " + player);
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
	
	class NetworkActionListener implements ActionListener {
		int bytes;
		String string;
		Game game;
		public void actionPerformed(ActionEvent e) {
			if (connected){
				try {
					byteBuffer.clear();
					bytes = channel.read(byteBuffer);
					byteBuffer.flip();
				} catch (IOException e1) {
					connected = false;
					
				}
				if (bytes == 0){

				} else if (bytes > 0){
					System.out.println("\n\nSTUFF RECEIVED: "+bytes);
					
					charBuffer.clear();
					decoder.decode(byteBuffer,charBuffer,false);
					charBuffer.flip();
					string = charBuffer.toString();
					//SERVER READ KEEP SELECTING IF THESE BUFFERS NOT CLEARED; SOME SORT OF LAZY READ?
					byteBuffer.clear();
					charBuffer.clear();
					
					System.out.println(string);
					System.out.println(string.length());
					
					String[] parts = string.split(":");
					switch(parts[0]){
					case "state":
						game = decodeGameState(parts[1]);
						System.out.println("RECEIVED GAME STATE");
						
						
						model.setGame(game);
						
						if (game.getCurrentPlayer().getId() != model.lastPlayerNum){
							model.lastPlayerNum = game.getCurrentPlayer().getId();
							textAppend("PLAYER " + model.lastPlayerNum + " TURN\n");
						}
						
						redraw();
						break;
					case "player":
						model.setPlayer(Integer.parseInt(parts[1]));
						System.out.println("RECEIVED PLAYER NUMBER: " + model.playerNum);
						textAppend("YOU ARE PLAYER: " + (model.playerNum+1)+"\n");
						break;
					case "msg":
						
						textAppend(parts[1]+"\n");
						break;
					}
				} else {
					//EOF -1
				}
			}
		}
	}
	
	public void redraw(){
		System.out.println("REFRESH");
		loadHand();
		loadPlayArea();
		loadTokens();
	}
	
	public Game decodeGameState(String message){
		Game game;
		byte[] object;
		object = b64Decoder.decode(message);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(object);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(bais);
			game = (Game) ois.readObject();
			return game;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	public void sendString(String message){
		charBuffer.clear();
		charBuffer.put(message);
		charBuffer.flip();
		
		try {
			encoder.reset();
			channel.write(encoder.encode(charBuffer));
			encoder.reset();
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class StartGameButtonActionListener implements ActionListener {
		String message;
		public void actionPerformed(ActionEvent e) {
			message = "startGame:";
			sendString(message);
		}
	}
	
	class StartButtonActionListener implements ActionListener {
		String message;
		public void actionPerformed(ActionEvent e) {
			message = "start:";
			sendString(message);
		}
	}

	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			redraw();
			//chooseTourColor();
			//connect();
		}
	}
	
	class ConnectButtonActionListener implements ActionListener {
		String message;
		public void actionPerformed(ActionEvent e) {
			try {
				channel = SocketChannel.open();
				channel.configureBlocking(false);
				channel.connect(new InetSocketAddress(connectBox.getText(), port));
			
				selector = Selector.open();
				channel.register(selector,SelectionKey.OP_CONNECT/*|SelectionKey.OP_READ*/);
				
				selector.select(500); //CONNECT TIMEOUT
				if (!channel.finishConnect()) {
					throw new SocketTimeoutException();
				} else {
					message = "join:"+nameBox.getText();
					sendString(message);
					
					textAppend("CONNECTED\n");
					connected = true;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class EndButtonActionListener implements ActionListener {
		String message;
		public void actionPerformed(ActionEvent e) {
			message = "end:";
			sendString(message);
		}	
	}
	
	class WidthdrawButtonActionListener implements ActionListener {
		String message;
		public void actionPerformed(ActionEvent e) {
			message = "widthdraw:";
			sendString(message);
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
				//g.drawImage(images.getBackgroundPic(this.getWidth(),this.getHeight()), 0, 0, null);
			}
		}
	}
}