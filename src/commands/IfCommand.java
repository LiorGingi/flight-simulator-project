package commands;

import java.util.ArrayList;

public class IfCommand extends ConditionParser {

	ConditionCommand condition;
	ArrayList<String> script;
	
	public IfCommand() {
		condition = new ConditionCommand();
		script = new ArrayList<>();
	}
	
	@Override
	public int execute(String[] args, int index) throws Exception {
		int currIndex = index;
		currIndex += condition.execute(args, currIndex);
		if (condition.getAnswer()) {
			currIndex++; //open curly brackets {
			while(!args[currIndex].equals("}"))
				currIndex++;
		} else {
			while(!args[currIndex].equals("}"))
				currIndex++;
		}
		
		return currIndex-index;
	}

}
