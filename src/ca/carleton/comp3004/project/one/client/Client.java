package ca.carleton.comp3004.project.one.client;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.net.*;
import java.nio.*;

import java.nio.channels.*;
import java.nio.charset.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import ca.carleton.comp3004.project.GUI.Model;
import ca.carleton.comp3004.project.GUI.View;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;

public class Client {

	SocketChannel channel;	
	Selector selector;
		
	static Game game;
	static Player one;
	static Player two;
	static Player three;
	
	public void connect(String address, int port) throws UnknownHostException, IOException{
		channel = null;
		channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress(address, port));
	
		selector = Selector.open();
		channel.register(selector,SelectionKey.OP_CONNECT|SelectionKey.OP_READ);
		
		selector.select(500); //CONNECT TIMEOUT
		if (!channel.finishConnect()) {
			throw new SocketTimeoutException();
		} else {
			//System.out.println("CONNECTED");
		}
	}

	public void sendString(String message) throws Exception{
		Charset charset = Charset.forName("US-ASCII");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();
			
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);		
	
		charBuffer.put(message);
		charBuffer.flip();
		channel.write(encoder.encode(charBuffer));
	}

	public String receiveString() throws Exception{
		String string = "";
		
		Charset charset = Charset.forName("US-ASCII");
		CharsetDecoder decoder = charset.newDecoder();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);		
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		
		channel.read(byteBuffer);
		byteBuffer.flip();
		decoder.decode(byteBuffer,charBuffer,false);
		charBuffer.flip();
		string = charBuffer.toString();
		
		byteBuffer.clear();
		charBuffer.clear();

		return string;		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {       
            	        		
                Model model = new Model(game);
                //model.setGame(game);
                
    			//model.addPlayerSpecials("Shield");
    			//model.addPlayerSpecials("Stunned");
    			//model.addPlayerSpecials("Ivanhoe");
                View view = new View(model); 
               // Controller controller = new Controller(model,view);
                //controller.control();
            }
        });  
	}

}
