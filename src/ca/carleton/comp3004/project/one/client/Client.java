package ca.carleton.comp3004.project.one.client;

import java.io.*;
import java.net.*;
import java.nio.*;

import java.nio.channels.*;
import java.nio.charset.*;

public class Client {

	SocketChannel channel;	
	Selector selector;
		

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

}
