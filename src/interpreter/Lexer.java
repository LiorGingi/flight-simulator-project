package interpreter;

public interface Lexer<Input> {
	public String[] tokenize(Input input);
}
