package commands;

import java.util.HashMap;

public class CommandMap {
	HashMap<String, Command> map;
	
	public CommandMap() {
		map = new HashMap<>();
		loadCommands();
	}
	
	private void loadCommands() {
		map.put("openDataServer", new OpenServerCommand());
		map.put("connect", new ConnectCommand());
		map.put("var", new VarCommand());
		map.put("=", new PlacementCommand());
		map.put("bind", new BindCommand());
	}
	
	public Command get(String command) {
		return map.get(command);
	}
}
