package commands;

import interpreter.SymbolTable;
import java.util.concurrent.ConcurrentHashMap;

public class VarCommand implements Command {
	ConcurrentHashMap<String, Double> symbolTable;
	
	public VarCommand() {
		symbolTable=SymbolTable.getInstance();
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumOfParameters() {
		return 1;
	}

	@Override
	public void setParameters(String[] args) {
		
	}

}
