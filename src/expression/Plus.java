package expression;

public class Plus extends BinaryExpression {

	public Plus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate(String[] args, int index) throws Exception {
		return left.calculate(args, index-1)+right.calculate(args,index+1);
	}

}
