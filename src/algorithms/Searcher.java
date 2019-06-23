package algorithms;

import java.util.List;

import server.Searchable;
import server.State;

public interface Searcher<T> {
	public List<State<T>> search(Searchable<T> s);
}
