package commands;


public class IfCommand extends ConditionCommand {

	
	public IfCommand() {
	}
	
	@Override
	public int execute(String[] args, int index) throws Exception {
		int currentIndex=index;
		if(this.checkCondition(args, index)) {
			//implement after we decide the container in ConditionCommand.
		}
		return currentIndex-index;
	}

}
