package algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import server.Searchable;
import server.State;

public class BestFS<T> extends CommonSearcher<T> {
	HashSet<State<T>> closed;

	public BestFS() {
		super();
		this.closed = new HashSet<>();
	}

	private LinkedList<State<T>> backTrace(State<T> n) {
		LinkedList<State<T>> traceList = new LinkedList<State<T>>();
		while (n.getCameFrom() != null) {
			traceList.addFirst(n);
			n = n.getCameFrom();
		}
		traceList.addFirst(n);// add initial state
		return traceList;

	}

	@Override
	public LinkedList<State<T>> search(Searchable<T> s) {
		LinkedList<State<T>> shortestPath = null;
		openList.add(s.getInitialState());
		while (!openList.isEmpty()) {
			State<T> n = popOpenList();
			closed.add(n);
			if (s.isGoalState(n)) {
				shortestPath = (LinkedList<State<T>>) backTrace(n);
				break;
			}

			Set<State<T>> successors = s.getAllPossibleStates(n);
			for (State<T> state : successors) {
				if (!openList.contains(state) && !closed.contains(state)) {// check if it is the first time the state
																			// appears
					state.setCameFrom(n);
					state.setTotalCost(n.getTotalCost() + s.getMovePrice(state));
					openList.add(state);
				} else if (openList.contains(state)) {// state is in closed or in open, we refer only states in open.
					if (state.getTotalCost() > n.getTotalCost() + s.getMovePrice(state)) {// compares the cost of paths
						openList.remove(state);
						state.setTotalCost(n.getTotalCost());
						state.setCameFrom(n);
						openList.add(state);
					}
				}
			}
		}
		return shortestPath;

	}
}
