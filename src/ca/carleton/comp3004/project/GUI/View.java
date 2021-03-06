package ca.carleton.comp3004.project.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
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
import java.util.LinkedList;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
import ca.carleton.comp3004.project.gameobjects.Card.CardType;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;

public class View extends JFrame {
	int WIDTH = 1024;
	int HEIGHT = 768;
	int SIDE_WIDTH = 250;
	
	private Image images;
	private JPanel panel;
	private GridBagConstraints c;

	private JPanel sidebarOptions;
	private JPanel bottombar;
	private DrawPanel playArea;
	
	private JLayeredPane[] playerAreaPane = new JLayeredPane[6];
	private JPanel[] playerArea = new JPanel[6];
	private JPanel[] tokenArea = new JPanel[6];
	private JPanel[] statusArea = new JPanel[6];
	
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
	
	CharBuffer charBuffer = CharBuffer.allocate(16384);
	ByteBuffer byteBuffer = ByteBuffer.allocate(16384);

	Charset charset = Charset.forName("US-ASCII");
	CharsetEncoder encoder = charset.newEncoder();
	CharsetDecoder decoder = charset.newDecoder();
	
	Base64.Encoder b64Encoder = Base64.getEncoder();
	Base64.Decoder b64Decoder = Base64.getDecoder();
	
	static String path = System.getProperty("user.dir");
	String soundShuffle = "/sounds/cockatrice/shuffle.wav";
	String soundDraw = "/sounds/cockatrice/draw.wav";
	String soundPlay = "/sounds/cockatrice/playCard.wav";
	String soundPass = "/sounds/cockatrice/Passturn.wav";
	String soundVictory = "/sounds/cockatrice/victory.wav";
	public View(Model tempmodel) {
		tourColor = Color.black;
		images = new Image();
		initUI();
		model = tempmodel;
		playSound(soundShuffle);
	}
	
	private void textAppend(String string){
		int max = 24;
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
	
	class lab extends JLabel {
		boolean on;
		
		public lab(){
			on = false;
		}
		
		public lab(boolean bool){
			on = bool;
		}
		
		public void paintComponent(Graphics g) {
			
			int size = this.getWidth();
			if (size > this.getHeight()) size = this.getHeight();
			int s = 4;
			
	        super.paintComponent(g);       
	        Graphics2D g2d = (Graphics2D) g;
	        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHints(rh);
	       
	        g.setColor(this.getForeground());
	        g2d.setStroke(new BasicStroke(3));
	        if (on){
	        	g2d.fillOval(s/2,s/2,size-s,size-s);
	        } else {
	        	g2d.drawOval(s/2,s/2,size-s,size-s);
			}
	    }  
		
	}
	
	private void initToken(JPanel panel){
		lab tempLabel;
		
		tempLabel = new lab();
		tempLabel.setForeground(blue);
		tempLabel.setOpaque(true);
		panel.add(tempLabel);
		
		tempLabel = new lab();
		tempLabel.setForeground(green);
		tempLabel.setOpaque(true);
		panel.add(tempLabel);
		
		tempLabel = new lab();
		tempLabel.setForeground(yellow);
		tempLabel.setOpaque(true);
		panel.add(tempLabel);
		
		tempLabel = new lab();
		tempLabel.setForeground(red);
		tempLabel.setOpaque(true);
		panel.add(tempLabel);
		
		tempLabel = new lab();
		tempLabel.setForeground(purple);
		tempLabel.setOpaque(true);
		panel.add(tempLabel);
	}

	private void initUI() {
		
		setSize(WIDTH,HEIGHT);
		
		panel = new JPanel(new GridBagLayout());
		
		sidebarOptions = new JPanel(new GridBagLayout());
		bottombar = new JPanel();
		playArea = new DrawPanel();
		playArea.setLayout(new GridLayout(2,3));
		
		JLabel tempLabel;
		
		for (int x = 0; x < 6; x++){
			playerArea[x] = new JPanel();
			playerArea[x].setLayout(new BorderLayout(0,0));
			playerAreaPane[x] = new JLayeredPane();
			tokenArea[x] =  new JPanel(new GridLayout(1,5));
			
			initToken(tokenArea[x]);
			
			
			/*
			tempLabel = new lab();
			tempLabel.setForeground(red);
			tempLabel.setOpaque(true);
			
			statusArea[x].add(tempLabel);
			*/
			statusArea[x] = new JPanel(new GridLayout(2,1));
			
			tempLabel = new JLabel(new ImageIcon(images.getStatusPic(1)));
			statusArea[x].add(tempLabel);
			tempLabel = new JLabel(new ImageIcon(images.getStatusPic(0)));
			statusArea[x].add(tempLabel);
			
			
			playerAreaPane[x].setPreferredSize(new Dimension(140,100));
			playerArea[x].add(playerAreaPane[x],BorderLayout.CENTER);
			
			tokenArea[x].setPreferredSize(new Dimension((WIDTH-SIDE_WIDTH)/3-100, (WIDTH-SIDE_WIDTH)/15));
			statusArea[x].setPreferredSize(new Dimension(32,32));
			
			playerArea[x].add(statusArea[x],BorderLayout.LINE_START);
			playerArea[x].add(tokenArea[x],BorderLayout.PAGE_END);
		}
		
		
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
		
		textArea = new JTextArea(20, 20);
		textArea.setEditable(false);
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
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
		//
		c.weightx = 0.6;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 2;
		bottombar.setBorder(BorderFactory.createTitledBorder("Hand"));
		//.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.6), (int) (panel.getHeight() * 0.3)));
		bottombar.setPreferredSize(new Dimension(250,250));
		panel.add(bottombar, c);
		
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		sidebarOptions.setPreferredSize(new Dimension(SIDE_WIDTH,0));
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
		
		
		
		add(panel);
		setTitle("Ivanhoe Game");
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
					   
		new Timer(100, new NetworkActionListener()).start();
	}

	public void loadHand() {
		JButton tempButton;
		
		java.util.List<Card> hand = model.getHand();
		//java.util.List<Card> hand = new LinkedList<Card>();
		//hand.add(new Card(Card.CardType.Color,Card.CardColor.Red,4,"red"));
		bottombar.removeAll();
		for (int i = 0; i < hand.size(); i++) {
			//System.out.println("GETTING HAND: "+i);
			tempButton = new JButton(new ImageIcon(images.getCards(hand.get(i))));
			int temp = 100;
			
			if (bottombar.getWidth() / hand.size() < temp) {
				temp = bottombar.getWidth() / hand.size();
			}
			
			tempButton.setPreferredSize(new Dimension((int) (temp), 160));
			tempButton.addActionListener(new HandActionListener(i));
			if (model.game != null) {
				if (model.game.validatePlay(hand.get(i))) tempButton.setEnabled(true);
				else tempButton.setEnabled(false);
			}
			bottombar.add(tempButton, c);
		}
		//System.out.println("DONE GETTING HAND");
		revalidate();
		repaint();
	}

	public void loadStatus() {
		int x1;
		JLabel tempLabel;
		statusArea[5].removeAll();
		System.out.println("HERE???");
		for (int x = 0; x <= 4;x++){
			statusArea[x].removeAll();			
			if (x  < model.game.getPlayers().size()){
				x1 = x;
				if (x == model.playerNum){
					x1 = 5;
				}
				if (model.game.getPlayers().get(x).isShielded()){
					tempLabel = new JLabel(new ImageIcon(images.getStatusPic(1)));
					statusArea[x1].add(tempLabel);
				}
				if (model.game.getPlayers().get(x).isStunned()){
					tempLabel = new JLabel(new ImageIcon(images.getStatusPic(1)));
					statusArea[x1].add(tempLabel);
				}
				
			}
			
		}
		
		revalidate();
		repaint();
	}
	
	public void loadPlayArea() {
		JLabel tempLabel;
		JPanel tempPanel;
		
		playerAreaPane[5].removeAll();
		for (int x = 0; x < model.game.getPlayers().size();x++){
		//for (int x = 0; x < 3;x++){
			System.out.println("REMOVE ALLLLLLLL P: " + x);
			playerAreaPane[x].removeAll();
			playerAreaPane[x].addMouseListener(new targetPlayerListener(x));
			
			//DEBUG
			java.util.List<Card> played = model.getPlayerArea(x);
			//java.util.List<Card> played = new LinkedList<Card>();
			//played.add(new Card(Card.CardType.Color,Card.CardColor.Red,4,"red"));
			//played.add(new Card(Card.CardType.Color,Card.CardColor.Red,1,"green"));
			
			for (int i = 0; i < played.size(); i++) {
				tempLabel = new JLabel(new ImageIcon(images.getCards(played.get(i))));
				tempLabel.setSize(new Dimension(images.getCards(played.get(i)).getWidth(),images.getCards(played.get(i)).getHeight()));
				tempLabel.addMouseListener(new cardInPlayListener(played.get(i),x));
				
				
				
				if (x == model.playerNum){ 
					playerAreaPane[5].add(tempLabel, new Integer(i));
					//Border border = BorderFactory.createTitledBorder(null,"You (Player "+(model.playerNum+1)+")", TitledBorder.LEFT, TitledBorder.TOP, BorderFactory.createTitledBorder("test").getTitleFont(), Color.black);
					//playerAreaPane[5].setBorder(border);
				} else {
					playerAreaPane[x].add(tempLabel, new Integer(i));
				}
				
				tempLabel.setLocation((int)tempLabel.getLocation().getX()+5+10*i,(int)tempLabel.getLocation().getY()+15+15*i);
				//System.out.println(i);
				
				
			}
			
			
			//otherArea.addMouseListener(new targetPlayerListener(x));
			
			
		}

		revalidate();
		repaint();
	}

	public void loadTokens() {

		JLabel templabel;
		JPanel tempPanel;
		JLabel tempLabel;
		int x1;
		
		tokenArea[5].removeAll();
		for (int x = 0; x <= 4;x++){
			tokenArea[x].removeAll();
			if (x  < model.game.getPlayers().size()){
				x1 = x;
				if (x == model.playerNum){
					x1 = 5;
				}
				
				tempLabel = new lab(model.getPlayerTokens(x).contains("B"));
				tempLabel.setForeground(blue);
				tempLabel.setOpaque(true);
				tempLabel.addMouseListener(new tokenPickListerner(Card.CardColor.Blue));
				tokenArea[x1].add(tempLabel);
				
				tempLabel = new lab(model.getPlayerTokens(x).contains("G"));
				tempLabel.setForeground(green);
				tempLabel.setOpaque(true);
				tempLabel.addMouseListener(new tokenPickListerner(Card.CardColor.Green));
				tokenArea[x1].add(tempLabel);
				
				tempLabel = new lab(model.getPlayerTokens(x).contains("Y"));
				tempLabel.setForeground(yellow);
				tempLabel.setOpaque(true);
				tempLabel.addMouseListener(new tokenPickListerner(Card.CardColor.Yellow));
				tokenArea[x1].add(tempLabel);
				
				tempLabel = new lab(model.getPlayerTokens(x).contains("R"));
				tempLabel.setForeground(red);
				tempLabel.setOpaque(true);
				tempLabel.addMouseListener(new tokenPickListerner(Card.CardColor.Red));
				tokenArea[x1].add(tempLabel);
				
				tempLabel = new lab(model.getPlayerTokens(x).contains("P"));
				tempLabel.setForeground(purple);
				tempLabel.setOpaque(true);
				tempLabel.addMouseListener(new tokenPickListerner(Card.CardColor.Purple));
				tokenArea[x1].add(tempLabel);
			}
		}
		revalidate();
		repaint();
		
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
	
	public int choosePlayer() {
		java.util.List<Integer> options = new ArrayList<Integer>();
		for (Player p : model.game.getPlayers()) {
				options.add(p.getId());
		}
		Object[] oparr = options.toArray();
		int n = JOptionPane.showOptionDialog(this,
				"Please choose a player ",
						"Player selector",
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
		loadStatus();
		//THE BUTTON, NOT USED
	}
	
	//Will need to be changed
	public void useCard(int x) {
		String message;
		
		Game game = model.game;
		//model.getHand().remove(0);
		//model.setHand(model.getHand().remove(0));
		System.out.println(x);
		if (game.purple == true){
			System.out.println("CHOOSE A COLOR FIRST\n");
			return;
		}
		if (game.getPlayers().get(model.playerNum).getHand().get(x).getCardName().equals("Ivanhoe")) {
			message = "playIvanhoe:"+model.playerNum;
			sendString(message);
		}
		else if (model.game.getCurrentPlayer().getId() == (model.playerNum+1) /*||true*/){
			Card card = game.getPlayers().get(model.playerNum).getHand().get(x);
			
			if (card.getCardType() == CardType.Action){
				if (card.targetColor){
					message = "targetColor:";
					message += chooseColor().ordinal();
					sendString(message);
				}
				if (card.targetPlayer){
					message = "targetPlayer:";
					message += choosePlayer();
					sendString(message);
				}
				message = "play:"+x;
				sendString(message);
			} else if (card.getCardType() == CardType.Color){
				message = "play:"+x;
				sendString(message);
			} else if (card.getCardType() == CardType.Supporter) {
				message = "play:"+x;
				sendString(message);
			}
			
			//game.validatePlay(game.getPlayers().get(model.playerNum).getHand().get(x));
			//game.performPlay(x);
			
		} else {
			//System.out.println("NOT YOUR TURN");
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
	
	public void process(String string){
		Game game;
		
		String[] parts = string.split(":");
		switch(parts[0]){
		case "state":
			//int size = Integer.parseInt( parts[1];
			
			game = decodeGameState(parts[1]);
			System.out.println("RECEIVED GAME STATE");
			
			
			model.setGame(game);
			
			if (game.getCurrentPlayer().getId() != model.lastPlayerNum){
				model.lastPlayerNum = game.getCurrentPlayer().getId();
				textAppend("PLAYER " + model.lastPlayerNum + " TURN\n");
			}
			if (game.purple){
				if (model.game.getCurrentPlayer().getId() == (model.playerNum+1)){
					textAppend("PLEASE CHOOSE A COLOR\n");
					//choosePlayer();
					System.out.println("PICKED A COLORRR");
					String message;
					message = "purple:";
					message += chooseColor().ordinal();
					sendString(message);
					System.out.println(message);
				}
				
				
			}
			if (game.won){
				playSound(soundVictory);
				JOptionPane.showMessageDialog(null, "PLAYER " + game.getCurrentPlayer().getId() + " HAS WON");
				textAppend("TOURNAMENT OVER\n");
				textAppend("PLAYER " + game.getCurrentPlayer().getId() + " HAS WON\n");
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
		
	}
	
	class NetworkActionListener implements ActionListener {
		long size;
		int bytes,read,back;
		String string;
		
		ByteBuffer socketBuffer = ByteBuffer.allocate(16384);
		ByteBuffer msgBuffer = ByteBuffer.allocate(16384);
		
		boolean waiting = false, oversized = false;
		
		boolean d = false;
		
		public void actionPerformed(ActionEvent e) {
			if (connected){
				
				socketBuffer.clear();
					
				try {
					bytes = channel.read(socketBuffer);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("DISCONNECTED");
					connected = false;
					e1.printStackTrace();
				}
				
				if (bytes > 0 || oversized){
					oversized = false;
					socketBuffer.flip();
					if (!waiting && !oversized){
						read = bytes;
						size = socketBuffer.getLong();
						if (d) System.out.println("SIZE HEADER: "+size);
						msgBuffer.clear();
						//msgBuffer.limit(new Long(size).intValue());
					} else {
						read += bytes;	
					}
					
					System.out.println("STUFF RECEIVED: "+bytes);
					msgBuffer.put(socketBuffer);
					
					if (read < (size+8)){
						waiting = true;
						
						return;
					} else if (read > (size+8)){
						msgBuffer.limit(new Long(size).intValue());
						back = (int) (read - (size+8));
						if (d) System.out.println("RECEIVED TOO MUCH: " + back);
						socketBuffer.position(socketBuffer.limit()-back);
						size = socketBuffer.getLong();
						if (d) System.out.println("PLUS SIZE HEADER: "+size);
						oversized = true;
					} else {
						waiting = false;
					}
				
				} else if (bytes == 0){
					return;	
				} else {
					System.out.println("DISCONNECTED?");
					return;
				}
				
				msgBuffer.flip();
				charBuffer.clear();
				decoder.reset();
				decoder.decode(msgBuffer,charBuffer,true);
				charBuffer.flip();
				
				string = charBuffer.toString();
				//SERVER READ KEEP SELECTING IF THESE BUFFERS NOT CLEARED; SOME SORT OF LAZY READ?
				byteBuffer.clear();
				charBuffer.clear();
					
				System.out.println(string);
				System.out.println(string.length());
					
				process(string);
				
				if (oversized == true){
					if (d) System.out.println("OVERSIZED");
					waiting = true;
					read = back;
					msgBuffer.clear();
					msgBuffer.put(socketBuffer);
				}
			
			
			}
		}
	}
	
	public void redraw(){
		System.out.println("REFRESH");

		loadHand();
		loadPlayArea();
		loadTokens();
		loadStatus();
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
			writeData(channel,encoder.encode(charBuffer));
			encoder.reset();
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeData(SocketChannel socket,ByteBuffer byteBuffer){
		ByteBuffer size = ByteBuffer.allocate(16384);
		size.putLong(byteBuffer.limit());
		size.put(byteBuffer);
		size.flip();
		
		byteBuffer.rewind();
		
		System.out.println("SIZE: " + size.limit());
		//System.out.println("SIZE 1:" + byteBuffer.position());
		try {
			socket.write(size);
			//System.out.println("SIZE 2:" + byteBuffer.limit());
		} catch (IOException e) {
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
			playSound(soundDraw);
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
			playSound(soundPass);
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
			playSound(soundPlay);
			useCard(card);
		}
	}

	private class tokenPickListerner implements MouseListener {
		private Card.CardColor color;

		public tokenPickListerner(Card.CardColor col) {
			this.color = col;
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("PICKED A COLORRR");
			String message;
			message = "purple:";
			message += this.color.ordinal();
			sendString(message);
			System.out.println(message);
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
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
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
	
	public static synchronized void playSound(final String fileName)
    {
        // Note: use .wav files            
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path + fileName));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
                }
            }
        }).start();
    }
}