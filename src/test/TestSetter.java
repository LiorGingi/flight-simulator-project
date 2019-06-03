package test;

import server.FileCacheManager;
import server.MyClientHandler;
import server.MySerialServer;
import server.Server;

public class TestSetter {
	

	static Server s; 
	
	public static void runServer(int port) {
		// put the code here that runs your server
		s=new MySerialServer(); // initialize
		s.start(port,new MyClientHandler(new FileCacheManager<String,String>()));
	}

	public static void stopServer() {
		s.stop();
	}
	

}
