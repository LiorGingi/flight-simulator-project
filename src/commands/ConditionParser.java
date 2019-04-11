package commands;

import java.util.List;

import interpreter.ConsoleParser;

public abstract class ConditionParser implements Command {
	String[] scope;
	ConsoleParser cp;
	
	public ConditionParser() {
		cp = new ConsoleParser();
	}
}
