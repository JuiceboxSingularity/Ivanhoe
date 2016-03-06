package ca.carleton.comp3004.project.networking;

import java.nio.channels.SelectionKey;

public interface NetworkCallbackInterface {

	public void accept();
	public void read(SelectionKey key);
	public void write();
	public void connect();

}
