package commands;


public class OpenServerCommand implements Command {
	int port;
	int frequency;
	
	public OpenServerCommand() {
		this.port = 0;
		this.frequency = 0;
	}

	@Override
	public int execute(String[] args, int index) {
		// TODO Auto-generated method stub
		return 0;
	}



}
