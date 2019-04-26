package expression;

public class Mul extends BinaryExpression {

	public Mul(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() throws Exception {
		return left.calculate() * right.calculate();
	}

}
