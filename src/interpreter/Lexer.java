package interpreter;

public interface Lexer<Input> {
	public String[] separate(Input input);
}
