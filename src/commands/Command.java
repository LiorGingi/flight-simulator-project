package commands;

public interface Command {
	public int execute();
	public int getNumOfParameters();
	public void setParameters(String[] args);
}
