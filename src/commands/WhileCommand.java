package commands;

public class WhileCommand extends ConditionCommand {

	@Override
	public int execute(String[] args, int index) throws Exception {
		int currentIndex = 0;
		while(this.checkCondition(args, index)) {
			currentIndex=index;
			//implement after we decide the container in ConditionCommand.
			
		}
		return currentIndex-index;
	}

}
