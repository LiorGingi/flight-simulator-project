package commands;

import java.util.concurrent.ConcurrentHashMap;

import expression.ExpressionCommand;

public class ExpressionCommandFactory {
	private interface CommandCreator {
		public ExpressionCommand create();
	}

	private ConcurrentHashMap<String, CommandCreator> creators;

	public ExpressionCommandFactory() {
		creators = new ConcurrentHashMap<>();
		creators.put("openDataServer", () -> new ExpressionCommand(new OpenServerCommand()));
		creators.put("connect", () -> new ExpressionCommand(new ConnectCommand()));
		creators.put("var", () -> new ExpressionCommand(new VarCommand()));
		creators.put("=", () -> new ExpressionCommand(new PlacementCommand()));
		creators.put("bind", () -> new ExpressionCommand(new BindCommand()));
		creators.put("while", () -> new ExpressionCommand(new WhileCommand()));
		creators.put("if", () -> new ExpressionCommand(new IfCommand()));
		creators.put("disconnect", () -> new ExpressionCommand(new DisconnectCommand()));
	}

	public ExpressionCommand createCommand(String name) {
		CommandCreator creator = creators.get(name);
		if (creator != null)
			return creator.create();
		return null;
	}
}
