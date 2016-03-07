package ca.carleton.comp3004.project.one.server;

import java.net.*;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.networking.NetworkCallbackInterface;
import ca.carleton.comp3004.project.networking.Server;

public class GameServer extends Thread {
	
	class ServerImpl implements NetworkCallbackInterface{
		
		SelectableChannel select;
		String string;
		SocketChannel client;
		
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

		Charset charset = Charset.forName("US-ASCII");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();
		Game game;
		
		int bytes;
		int players = 0;
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
				switch(string){
				case "join":
					System.out.println("ATTEMPT TO JOIN");
					//TODO : GAME ALREADY STARTED CHECK
					if (players>=5){
						System.out.println("TOO MANY");
					} else {
						players++;
						System.out.println("JOINED: PLAYER " + players);
					}
					break;
				case "start":
					System.out.println("ATTEMPT TO START");
					if (players>=2){
						System.out.println("STARTING GAME");
						started = true;
						game = new Game(players);
						
					} else {
						System.out.println("NOT ENOUGH PLAYERS");
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
	
	public GameServer(){
		ServerImpl impl = new ServerImpl();
		System.out.println("STARTING GAME SERVER");
		Server server = new Server("0.0.0.0",1001, impl);
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
