package server;

public interface CacheManager<Problem, Solution>{
	boolean isExist(Problem p);
	Solution get(Problem p);
	void save(Problem p,Solution s);
}
