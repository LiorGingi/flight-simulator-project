package expression;

public class Div extends BinaryExpression {

	public Div(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() throws Exception {
		return left.calculate() / right.calculate();
	}

}
