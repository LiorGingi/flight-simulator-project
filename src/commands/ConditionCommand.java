package commands;

import java.util.ListIterator;

import algorithms.ShuntingYard;

public abstract class ConditionCommand extends ScopeCommand {
	protected String conditionsLine;

	protected void setConditionLine(ListIterator<String> it) {
		String token = null;
		StringBuilder builder = new StringBuilder();
		while (!(token = it.next()).equals("{"))
			builder.append(token);
		conditionsLine = builder.toString();
		it.next();// pass {
	}

	protected boolean checkCondition() throws Exception {
		return ShuntingYard.calcLogic(conditionsLine);
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		this.setConditionLine(it);
		this.loadScope(it);
	}
}
