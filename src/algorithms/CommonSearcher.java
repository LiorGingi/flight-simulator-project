package algorithms;

import java.util.List;
import java.util.PriorityQueue;

import server.Searchable;
import server.State;

public abstract class CommonSearcher<T> implements Searcher<T> {

	protected PriorityQueue<State<T>> openList;
	private int evaluatedNodes;

	public CommonSearcher() {
		this.openList = new PriorityQueue<>((s1, s2) -> {
			if (s1.getTotalCost() > s2.getTotalCost())
				return 1;
			if (s1.getTotalCost() < s2.getTotalCost())
				return -1;
			return 0;
		});
		this.evaluatedNodes = 0;
	}

	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}

	public abstract List<State<T>> search(Searchable<T> s);
}
