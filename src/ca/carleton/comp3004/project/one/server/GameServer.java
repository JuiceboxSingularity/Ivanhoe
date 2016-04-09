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
		
		CharBuffer charBuffer = CharBuffer.allocate(16384);
		ByteBuffer byteBuffer = ByteBuffer.allocate(16384);

		Charset charset = Charset.forName("US-ASCII");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();
		
		Base64.Encoder b64Encoder = Base64.getEncoder();
		Base64.Decoder b64Decoder = Base64.getDecoder();
		
		Game game;
		
		int bytes;
		int numPlayers = 0;
		int index;
		
		String message;
		
		Player[] players = new Player[6]; //5+1 TODO: THIS NEEDS TO BE REDONE
		SocketChannel[] playerSockets = new SocketChannel[5]; 
		
		boolean started = false;
		
		public void sendGame(Game game){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream (baos);
				oos.writeObject(game);
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			string = b64Encoder.encodeToString(baos.toByteArray());
			System.out.println(string);
			System.out.println("PLAYER TURN " + game.getCurrentPlayer().getId());
			
			System.out.println(string.length()+"state:".length());
			
			charBuffer.clear();
			
			charBuffer.append("state:");
			charBuffer.append(string);
    		charBuffer.flip();
    		
    		
    		byteBuffer.clear();
    		encoder.reset();
			encoder.encode(charBuffer,byteBuffer,true);
			
			byteBuffer.flip();
			writeAll(byteBuffer);
		}
				
		public void writeAll(ByteBuffer byteBuffer){
			//System.out.println("SENDING TO ALL: " + byteBuffer.remaining());
			for (int x = 0; x<numPlayers; x++){
				writeData(playerSockets[x],byteBuffer);
			}
		}
		
		public void writeString(SocketChannel socket,String string){
			charBuffer.clear();
			charBuffer.put(string);
			charBuffer.flip();
			
			byteBuffer.clear();
			encoder.reset();
			encoder.encode(charBuffer,byteBuffer,true);
			byteBuffer.flip();
			charBuffer.clear();
			
			writeData(socket,byteBuffer);
		}
		
		public void writeData(SocketChannel socket,ByteBuffer byteBuffer){
			ByteBuffer size = ByteBuffer.allocate(Long.BYTES);
			size.putLong(byteBuffer.limit());
			size.flip();
			
			byteBuffer.rewind();
			
			System.out.println("SIZE: " + byteBuffer.limit());
			//System.out.println("SIZE 1:" + byteBuffer.position());
			try {
				socket.write(size);
				//Thread.sleep(200);
				socket.write(byteBuffer);
				//System.out.println("SIZE 2:" + byteBuffer.limit());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		public void read(SelectionKey key){
			client = (SocketChannel)key.channel();
			try {
				try {
					byteBuffer.clear();
					bytes = client.read(byteBuffer);
					if (bytes == -1){
						throw new IOException();
					}
				} catch (IOException e){
					System.out.println("DISCONNECTED");
					byteBuffer.clear();
					client.close();
					key.cancel();
					return;
					//TODO: REMOVE PLAYER SOCKET IF THEY DISCONNECT
				}
				byteBuffer.flip();
				charBuffer.clear();
				decoder.decode(byteBuffer,charBuffer,false);
				charBuffer.flip();
				string = charBuffer.toString();
				//SERVER READ KEEP SELECTING IF THESE BUFFERS NOT CLEARED; SOME SORT OF LAZY READ?
				byteBuffer.clear();
				charBuffer.clear();
				
				string = string.replaceAll("[\\r\\n]+\\s", ""); //PUTTY TESTING
				
				System.out.println("\nNEW MESSAGE");
				System.out.println(string);
				System.out.println(string.length());
				
				String[] parts = string.split(":");
				
				
				switch(parts[0]){
					case "join":
						System.out.println("ATTEMPT TO JOIN");
						if (started == true){
							System.out.println("GAME ALREADY STARTED");
							break;
						}
						if (numPlayers>=5){
							System.out.println("TOO MANY");
						} else {
							if (parts.length != 2) {
								System.out.println("MALFORMED JOIN");
								break;
							}
							numPlayers++;
							playerSockets[numPlayers-1] = client;
							players[numPlayers] = new Player(parts[1],numPlayers);
							System.out.println("JOINED: PLAYER " + parts[1] + " " + numPlayers);
							
							message = "player:"+(numPlayers);
							writeString(client,message);
							
						}
						break;
					case "startGame":
						System.out.println("ATTEMPT TO START");
						if (started == true){
							System.out.println("GAME ALREADY STARTED");
							break;
						}
						if (numPlayers>=2){
							System.out.println("STARTING GAME: " + numPlayers + " PLAYERS");
							started = true;
							game = new Game(numPlayers);
							for (int x = 1; x <= numPlayers; x++){ //start from one, maybe change later
								game.addPlayer(players[x]);
							}
							
							
							game.dealTokens();
							game.getDeck().shuffle();		
							game.dealDeck();
							
							sendGame(game);
							//TODO: SEND GAME STATE
							
						} else {
							System.out.println("NOT ENOUGH PLAYERS");
						}
						break;
					case "purple":
						System.out.println("ATTEMPT TO CHOOSE TOKEN");
						game.purple = false;
						Integer.parseInt(parts[1]);
						
						game.setCustomColor(Card.CardColor.values()[Integer.parseInt(parts[1])]);
						game.awardToken(game.getCurrentPlayer());
						game.tournamentColor = CardColor.None;
						System.out.println("TOKEN GIVEN!!s");
						sendGame(game);
						break;
					case "start":
						System.out.println("ATTEMPT TO START TURN");
						if (started == true){
							game.startTurn();
							sendGame(game);
							
						} else {
							System.out.println("GAME NOT STARTED");
						}
						break;
					case "play":
						System.out.println("ATTEMPT TO PLAY CARD");
						if (started == true){
							index = Integer.parseInt(parts[1]);
							game.performPlay(index);
							System.out.println("PLAYED CARD: " + index);
							
							sendGame(game);
						} else {
							System.out.println("GAME NOT STARTED");
						}
						break;
					case "end":
						System.out.println("ATTEMPT TO END TURN");
						if (started == true){
							game.endTurn();
							
							sendGame(game);
						} else {
							System.out.println("GAME NOT STARTED");
						}
						break;
					case "widthdraw":
						System.out.println("ATTEMPT TO WIDTHRAW");
						if (started == true){
							if(game.withdrawPlayer()){
								for (Player p : game.getPlayers()) {
									if (p.isPlaying()){
										message = "msg:player "+ p.getId()+ " has won";
										System.out.println(message);
										
										charBuffer.clear();										
										charBuffer.append(message);
										charBuffer.flip();
										
										encoder.reset();
										writeAll(encoder.encode(charBuffer));
										
										byteBuffer.clear();
										charBuffer.clear();										
									}
								}
								game.endTournament();
							};
							//Thread.sleep(1500);
							sendGame(game);
						} else {
							System.out.println("GAME NOT STARTED");
						}
						break;	
					case "target":
						System.out.println("APPLYING TARGET PLAYER");
						for (String s : parts) {
							System.out.println("HI " + s);
						}
						if (started == true) {
							game.setTargetPlayer(Integer.parseInt(parts[1]));
						}
				}

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
