package server;
import java.util.Set;

public interface Searchable<T> {
	State<T> getInitialState();
	boolean isGoalState(State<T> s);
	Set<State<T>> getAllPossibleStates(State<T> s);
	double getMovePrice(State<T> toState);
}
