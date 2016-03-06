package ca.carleton.comp3004.project.networking;

import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

import java.util.Iterator;
import java.util.Set;

public class Server extends Thread {

	boolean run = true;	
	Selector selector;

	String ip;
	int port;
	
	ServerSocketChannel serverSocketChannel;

	CharBuffer charBuffer;
	ByteBuffer byteBuffer;
	NetworkCallbackInterface netInterface;

	public Server(String ip, int port, NetworkCallbackInterface netInterface ){
		this.ip = ip;
		this.port = port;
		this.netInterface = netInterface;
	}

	public void stopServerThread() throws InterruptedException{
		run = false;
		selector.wakeup();
		this.join();
		try {
			serverSocketChannel.close();
			Thread.sleep(1000);
		} catch (IOException e){
		} 
	}

	public void startServerThread() throws IOException{
		this.start();
		try {
            Thread.sleep(100);
        } catch (Exception e){
            e.printStackTrace();
        }
	}

	public void startServer() throws IOException{
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(ip,port));
		serverSocketChannel.configureBlocking(false);
		selector = Selector.open();
		
		serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
		
		Set<SelectionKey> selectedKeys;
		Iterator<SelectionKey> keyIterator;
		SelectableChannel select;
		SelectionKey key;
		SocketChannel client = null;

		while (true){
			int z = selector.select();
			if (run == false) break;
			
			selectedKeys = selector.selectedKeys();
			keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()){
				key = keyIterator.next();
				
				if (key.isAcceptable()){
					client = serverSocketChannel.accept();
					client.configureBlocking(false);
					client.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()){
					netInterface.read(key);
				}		

				keyIterator.remove();
			}
		}
	}

	public void run(){
		try {
			startServer();	
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
