package commands;

import java.util.HashMap;

import expression.CommandExpression;
import expression.Expression;

public class CommandMap {
	HashMap<String, Expression> commands;
	HashMap<String, Expression> scopeCommands;

	private DisconnectCommand diconnect;
	
	public CommandMap() {
		this.diconnect=new DisconnectCommand();
		commands = new HashMap<>();
		loadCommands();
		scopeCommands = new HashMap<>();
		loadScopeCommands();
	}

	private void loadCommands() {
		commands.put("openDataServer", new CommandExpression(new OpenDataServerCommand()));
		commands.put("connect", new CommandExpression(new ConnectCommand(this.diconnect)));
		commands.put("var", new CommandExpression(new VarCommand()));
		commands.put("=", new CommandExpression(new PlacementCommand()));
		commands.put("bind", new CommandExpression(new BindCommand()));
		commands.put("disconnect", new CommandExpression(this.diconnect));
	}

	private void loadScopeCommands() {
		scopeCommands.put("while", new CommandExpression(new WhileCommand()));
		scopeCommands.put("if", new CommandExpression(new IfCommand()));
	}
	
	public Expression getCommand(String arg) throws Exception {
		if (commands.containsKey(arg))
			return commands.get(arg);
		else if(scopeCommands.containsKey(arg))
			return scopeCommands.get(arg);
		else
			throw new Exception("Command doesn't exists");
	}
	public boolean isScopeCommand(String arg){
		return scopeCommands.containsKey(arg);
	}
}
