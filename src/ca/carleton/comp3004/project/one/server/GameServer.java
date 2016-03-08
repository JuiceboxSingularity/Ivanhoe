package ca.carleton.comp3004.project.one.server;

import java.net.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Base64;
import java.util.Iterator;
import java.util.Set;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;
import ca.carleton.comp3004.project.networking.NetworkCallbackInterface;
import ca.carleton.comp3004.project.networking.Server;

public class GameServer extends Thread {
	
	class ServerImpl implements NetworkCallbackInterface{
		
		SelectableChannel select;
		String string;
		SocketChannel client;
		
		CharBuffer charBuffer = CharBuffer.allocate(8192);
		ByteBuffer byteBuffer = ByteBuffer.allocate(8192);

		Charset charset = Charset.forName("US-ASCII");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();
		
		Base64.Encoder b64Encoder = Base64.getEncoder();
		Base64.Decoder b64Decoder = Base64.getDecoder();
		
		Game game;
		
		int bytes;
		int numPlayers = 0;
		Player[] players = new Player[6]; //5+1
		boolean started = false;
		
		public void read(SelectionKey key){
			client = (SocketChannel)key.channel();
			try {
				bytes = client.read(byteBuffer);
				if (bytes == -1){
					System.out.println("DISCONNECTED");
					byteBuffer.clear();
					client.close();
					key.cancel();
					return;
				}
				byteBuffer.flip();
				decoder.decode(byteBuffer,charBuffer,false);
				charBuffer.flip();
				string = charBuffer.toString();
				//SERVER READ KEEP SELECTING IF THESE BUFFERS NOT CLEARED; SOME SORT OF LAZY READ?
				byteBuffer.clear();
				charBuffer.clear();
				
				string = string.replaceAll("[\\r\\n]+\\s", ""); //PUTTY TESTING
				
				System.out.println(string);
				System.out.println(string.length());
				
				String[] parts = string.split(":");
				
				
				switch(parts[0]){
				case "join":
					System.out.println("ATTEMPT TO JOIN");
					//TODO : GAME ALREADY STARTED CHECK
					if (numPlayers>=5){
						System.out.println("TOO MANY");
					} else {
						numPlayers++;
						players[numPlayers] = new Player(parts[1],numPlayers);
						System.out.println("JOINED: PLAYER " + parts[1] + " " + numPlayers);
					}
					break;
				case "start":
					System.out.println("ATTEMPT TO START");
					if (numPlayers>=2){
						System.out.println("STARTING GAME: " + numPlayers + " PLAYERS");
						started = true;
						game = new Game(numPlayers);
						for (int x = 1; x < numPlayers; x++){ //start from one, maybe change later
							game.addPlayer(players[x]);
						}
						
						game.dealTokens();
						game.getDeck().shuffle();
						game.dealDeck();
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream (baos);
						oos.writeObject(game);
						oos.flush();
						string = b64Encoder.encodeToString(baos.toByteArray());
						System.out.println(string);
						
						byte[] object;
						object = b64Decoder.decode(string);
						
						ByteArrayInputStream bais = new ByteArrayInputStream(object);
						ObjectInputStream ois = new ObjectInputStream(bais);
						game = (Game) ois.readObject();
						System.out.println(game.getCurrentPlayer());
						
						System.out.println(string.length());
						charBuffer.clear();
						charBuffer.append(string);
		        		charBuffer.flip();
		        		
						encoder.encode(charBuffer,byteBuffer,false);
						byteBuffer.flip();
						client.write(byteBuffer);
						byteBuffer.clear();	
						charBuffer.clear();
						
						//TODO: SEND GAME STATE
						
					} else {
						System.out.println("NOT ENOUGH PLAYERS");
					}
					break;
				case "play":
					System.out.println("ATTEMPT TO PLAY CARD");
					if (started == true){
						
					} else {
						System.out.println("GAME NOT STARTED");
					}
					
				}
				
				/*
				
				if (game.validatePlay(game.getCurrentPlayer().getHand().get(index))) {
					CardColor color = game.getCurrentPlayer().viewCard(index).getCardColor();
					game.performPlay(index);
				
					//Assert that the tournament color is what the player played
					assertEquals(game.getTournamentColor(), color);
					System.out.println("Started " + color.toString() + " Tournament!");
				} else {
					fail("Did not receive a colored card");
				}
				
				// Test playing a supporter card
				game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
				if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
					game.performPlay(game.getCurrentPlayer().getHand().size()-1);
				} else {
					fail("Could not play a supporter card!!");
				}
				
				//Test playing two maidens in one tournament
				game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
				if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
					fail("Should not be able to play two maidens!!");
				} 
				
				------------------------------------
				
				game.endTurn();
				game.startTurn();
				
				//Test player ending turn without playing a larger total
				assertFalse(game.endTurn());
				
				//Withdraw other two players
				assertFalse(game.withdrawPlayer());
				game.startTurn();
				//This returns true if the tournament has ended
				assertTrue(game.withdrawPlayer());
				
				//Check that the player indeed wins a token of the tournament color
				CardColor tournamentColor = game.getTournamentColor();
				game.endTournament();
				assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);
				System.out.println(game.getCurrentPlayer().getPlayerName() + " Has Won The " + tournamentColor.toString() + " Tournament!!");
				//Make sure a player cannot win more than one token of the same color
				game.endTournament();
				assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);
				
				
				game.startTurn();
				*/
				

			} catch (Exception e){
				e.printStackTrace();
			}
		}
		public void accept(){}
		public void write(){}
		public void connect(){}
	}
	
	Server server;
	
	public GameServer(){
		ServerImpl impl = new ServerImpl();
		System.out.println("STARTING GAME SERVER");
		server = new Server("0.0.0.0",1001, impl);
		try {
			server.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		GameServer game = new GameServer();		

	}
	
}
