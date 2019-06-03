package commands;

public class IfCommand extends ConditionCommand {

	@Override
	public void execute() throws Exception {
		if (checkCondition())
			executeCommands();
	}
}
