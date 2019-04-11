package commands;

import interpreter.SymbolTable;
import java.util.concurrent.ConcurrentHashMap;

public class VarCommand implements Command {
	ConcurrentHashMap<String, Double> symbolTable;
	
	public VarCommand() {
		symbolTable=SymbolTable.getInstance();
	}

	@Override
	public int execute(String[] args, int index) {
		// TODO Auto-generated method stub
		return 0;
	}

}
