package commands;

public interface Command {
	public int execute (String[] args, int index) throws Exception;
}
