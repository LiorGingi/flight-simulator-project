package commands;


public class OpenServerCommand implements Command {
	int port;
	int frequency;
	
	public OpenServerCommand() {
		this.port = 0;
		this.frequency = 0;
	}

	@Override
	public int execute() {
		//implements command
		
		return this.getNumOfParameters();
	}

	@Override
	public int getNumOfParameters() {
		return 2;
	}

	@Override
	public void setParameters(String[] args) {//need to add syntax check for parameters.
		this.port=Integer.parseInt(args[0]);
		this.frequency=Integer.parseInt(args[1]);
	}

}
