package commands;

public class ReturnCommand implements Command {

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (!isNumber(args[index]) || args.length != 2)
			throw new Exception("Invalid return value");
		else
			return (int) Double.parseDouble(args[index]);
	}

	private boolean isNumber(String arg) {
		try {
			Double.parseDouble(arg);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
