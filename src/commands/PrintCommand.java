package commands;

import java.util.ListIterator;

import algorithms.ShuntingYard;

public class PrintCommand implements Command {
	private String text;
	@Override
	public void execute() throws Exception {
		try {
			System.out.println(ShuntingYard.calc(text).calculate());
		}catch(Exception e) {
			System.out.println(text);
		}
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		text=it.next();
	}

}
