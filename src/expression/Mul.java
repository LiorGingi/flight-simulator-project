package expression;

public class Mul extends BinaryExpression {

	public Mul(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate(String[] args, int index) throws Exception {
		return left.calculate(args, index-1)*right.calculate(args,index+1);
	}

}
