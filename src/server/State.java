package server;

public class State<T> {
	private T state;
	private double totalCost;
	private State<T> cameFrom;
	public State(T state, double totalCost) {
		super();
		this.state = state;
		this.totalCost = totalCost;
		this.cameFrom = null;
	}
	public boolean equals(State<T> s) {
		return state.equals(s.getState());
	}
	
	//getters and setters
	public T getState() {
		return state;
	}
	public void setState(T state) {
		this.state = state;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double cost) {
		this.totalCost = cost;
	}
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	
}
