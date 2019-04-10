package commands;

import interpreter.SymbolTable;
import java.util.HashMap;

public class VarCommand implements Command {
	HashMap<String, Double> symbolTable;
	
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
		// TODO Auto-generated method stub
		
	}

}
