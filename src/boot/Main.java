package boot;

import server.FileCacheManager;
import server.MySerialServer;
import server.MyTestClientHandler;

public class Main {

	public static void main(String[] args) {
		MySerialServer theServer=new MySerialServer();
		theServer.start(Integer.parseInt(args[1]), new MyTestClientHandler(new FileCacheManager<String,String>()));

	}

}
