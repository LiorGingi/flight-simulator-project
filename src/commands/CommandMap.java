package commands;

import java.util.HashMap;

import expression.CommandExpression;
import expression.Expression;

public class CommandMap {
	HashMap<String, Expression> map;

	public CommandMap() {
		map = new HashMap<>();
		loadCommands();
	}

	private void loadCommands() {
		map.put("openDataServer", new CommandExpression(new OpenServerCommand()));
		map.put("connect", new CommandExpression(new ConnectCommand()));
		map.put("var", new CommandExpression(new VarCommand()));
		map.put("=", new CommandExpression(new PlacementCommand()));
		map.put("bind", new CommandExpression(new BindCommand()));
	}

	public Expression get(String arg) {
		return map.get(arg);
	}

}
