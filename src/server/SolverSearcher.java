package server;

import algorithms.Searcher;

public class SolverSearcher<Problem, Solution> implements Solver<Problem, Solution> {
	Searcher<Problem> searcher;

	public SolverSearcher(Searcher<Problem> searcher) {
		super();
		this.searcher = searcher;
	}

	@Override
	public Solution solve(Problem p) {
		return (Solution) searcher.search((Searchable<Problem>) p);
	}
}
