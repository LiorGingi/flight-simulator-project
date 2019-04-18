package expression;

public class Number implements Expression {
	
	private double value;
	
	public Number(double value) {
		this.value = value;
	}
	@Override
	public double calculate(String[] args, int index) throws Exception {
		return this.value;
	}

}
