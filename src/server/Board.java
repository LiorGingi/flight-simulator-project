package server;

import java.util.HashSet;
import java.util.Set;

public class Board implements Searchable<Position> {
	State<Position>[][] board;
	int cols;
	int rows;
	State<Position> source;
	State<Position> dest;

	public Board(int rows, int cols) {
		this.cols = cols;
		this.rows = rows;
		this.board = (State<Position>[][]) new State[rows][cols];

	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public State<Position> getSource() {
		return source;
	}

	public void setSource(State<Position> source) {
		this.source = source;
	}

	public State<Position> getDest() {
		return dest;
	}

	public void setDest(State<Position> dest) {
		this.dest = dest;
	}

	public State<Position>[][] getBoard() {
		return board;
	}

	public void setBoard(State<Position>[][] board) {
		this.board = board;
	}

	@Override
	public State<Position> getInitialState() {
		return this.getSource();
	}

	@Override
	public boolean isGoalState(State<Position> s) {
		return s.equals(this.getDest());
	}

	@Override
	public Set<State<Position>> getAllPossibleStates(State<Position> s) {
		HashSet<State<Position>> successors = new HashSet<>();
		int currentRow = s.getState().getRow();
		int currentCol = s.getState().getColumn();
		if (s.getState().getRow() > 0)
			successors.add(this.getBoard()[currentRow - 1][currentCol]);
		if (s.getState().getRow() < this.getRows() - 1)
			successors.add(this.getBoard()[currentRow + 1][currentCol]);
		if (s.getState().getColumn() > 0)
			successors.add(this.getBoard()[currentRow][currentCol - 1]);
		if (s.getState().getColumn() < this.getCols() - 1)
			successors.add(this.getBoard()[currentRow][currentCol + 1]);
		return successors;
	}

	@Override
	public double getMovePrice(State<Position> toState) {
		return toState.getState().getValue();// returns the value of position in the board.
	}

}
