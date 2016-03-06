package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import ca.carleton.comp3004.project.networking.Client;
import ca.carleton.comp3004.project.networking.NetworkCallbackInterface;
import ca.carleton.comp3004.project.networking.Server;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import java.nio.*;
import java.nio.charset.*;


public class NetworkTest {

	class Dummy implements NetworkCallbackInterface{
		SelectableChannel select;
		
		public void accept(){}
		public void read(SelectionKey key){}
		public void write(){}
		public void connect(){}
	}

	class StoreStringImpl extends Dummy{
		String string;
		SocketChannel client;
		
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

		Charset charset = Charset.forName("US-ASCII");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();

		public void read(SelectionKey key){
			select = key.channel();
			client = (SocketChannel)select;
			try {
				client.read(byteBuffer);
				byteBuffer.flip();
				decoder.decode(byteBuffer,charBuffer,false);
				charBuffer.flip();
				string = charBuffer.toString();
				//SERVER READ KEEP SELECTING IF THESE BUFFERS NOT CLEARED; SOME SORT OF LAZY READ?
				byteBuffer.clear();
				charBuffer.clear();

			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	class ReplyStringImpl extends StoreStringImpl {
		
		String message;

		public ReplyStringImpl(String string){
			message = string;
		}		

		public void read(SelectionKey key){
			select = key.channel();
			client = (SocketChannel)select;
			try {
				client.read(byteBuffer);
				byteBuffer.clear();

        		charBuffer.put(message);
      	  		charBuffer.flip();
        		
				encoder.encode(charBuffer,byteBuffer,false);
				byteBuffer.flip();
				client.write(byteBuffer);
				byteBuffer.clear();	
				charBuffer.clear();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@Test
    public void testConnect() throws Exception{
		Server server = new Server("0.0.0.0",1099, new Dummy());
		Client client = new Client();
		server.startServerThread();
		client.connect("127.0.0.1",1099);
		server.stopServerThread();
	}

	
	@Test
	public void testClientSend() throws Exception{
		StoreStringImpl store = new StoreStringImpl();
		
		Server server = new Server("0.0.0.0",1099, store);
		Client client = new Client();
		String message = "SENTFROMCLIENT";
		server.startServerThread();
		
		client.connect("127.0.0.1",1099);
		client.sendString(message);
		
		try {
			Thread.sleep(100);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		server.stopServerThread();
		assertEquals(message,store.string);
	}
	
	@Test
	public void testMultipleSend() throws Exception{
		StoreStringImpl store = new StoreStringImpl();

		Server server = new Server("0.0.0.0",1101, store);
		Client client = new Client();
		server.startServerThread();

		String message = "SENTFROMCLIENT";
		String message2 = "SENTFROMCLIENT2";

		client.connect("127.0.0.1",1101);
		client.sendString(message);
		
		try {
			Thread.sleep(10);
		} catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(message,store.string);
		
		client.sendString(message2);
		try {
			Thread.sleep(10);
		} catch (Exception e){
			e.printStackTrace();
		}
		server.stopServerThread();
		assertEquals(message2,store.string);
	}
	
	@Test
	public void testMultipleClientsSend() throws Exception{
		StoreStringImpl store = new StoreStringImpl();

		Server server = new Server("0.0.0.0",1102, store);
		Client client = new Client();
		Client client2 = new Client();
		server.startServerThread();

		String message = "SENTFROMCLIENT";
		String message2 = "SENTFROMCLIENT2";

		client.connect("127.0.0.1",1102);
		client2.connect("127.0.0.1",1102);
		
		client.sendString(message);
		try {
			Thread.sleep(10);
		} catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(message,store.string);
		
		client2.sendString(message2);
		try {
			Thread.sleep(10);
		} catch (Exception e){
			e.printStackTrace();
		}
		server.stopServerThread();
		assertEquals(message2,store.string);
	}

	@Test
	public void testServerSend() throws Exception{
		String message = "SENTFROMCLIENT";
		String message2 = "SENTFROMSERVER";
		StoreStringImpl reply = new ReplyStringImpl(message2);

		Server server = new Server("0.0.0.0",1103, reply);
		Client client = new Client();
		server.startServerThread();

		client.connect("127.0.0.1",1103);
		client.sendString(message);	
		try {
			Thread.sleep(10);
		} catch (Exception e){
			e.printStackTrace();
		}
		String receive = client.receiveString();
		server.stopServerThread();
		assertEquals(message2,receive);
	}

}
