package expression;

public class Minus extends BinaryExpression {

	public Minus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate(String[] args, int index) throws Exception {
		return left.calculate(args, index-1)-right.calculate(args,index+1);
	}

}
