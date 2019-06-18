package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import algorithms.BestFS;

public class MyClientHandler implements ClientHandler {
	
	Solver<Board, LinkedList<State<Position>>> solver;
	CacheManager<String, String> cm;

	public MyClientHandler(CacheManager<String, String> cm) {
		super();
		this.cm = cm;
	}

	@Override
	public void handleClient(InputStream in, OutputStream out) {
		BufferedReader clientInputBuffer = new BufferedReader(new InputStreamReader(in));
		PrintWriter responseToClient = new PrintWriter(out);

		Board board;
		String strArr[];
		ArrayList<String[]> requestsArr = new ArrayList<>();
		int boardRows, boardCols;
		String problemName = "";

		try {
			String clientRequest;
			// get matrix from client
			while (!(clientRequest = clientInputBuffer.readLine()).equals("end")) {
				problemName += "{" + clientRequest + "}";
				requestsArr.add(clientRequest.split(","));
			}
			String pathSolution;
			if (cm.isExist(String.valueOf(problemName.hashCode()))) {
				pathSolution=cm.get(problemName);
			} else {
				boardRows = requestsArr.size();
				boardCols = requestsArr.get(0).length;
				board = new Board(boardRows, boardCols);
				// fill the board
				for (int i = 0; i < boardRows; i++) {
					for (int j = 0; j < boardCols; j++) {
						board.getBoard()[i][j] = new State<Position>(
								new Position(i, j, Double.parseDouble(requestsArr.get(i)[j])), 0);
					}
				}

				// update the source and destination
				clientRequest = clientInputBuffer.readLine();
				strArr = clientRequest.split(",");
				board.setSource(board.getBoard()[Integer.parseInt(strArr[0])][Integer.parseInt(strArr[1])]);
				clientRequest = clientInputBuffer.readLine();
				strArr = clientRequest.split(",");
				board.setDest(board.getBoard()[Integer.parseInt(strArr[0])][Integer.parseInt(strArr[1])]);

				// BestFS to solve the problem
				solver = new SolverSearcher<Board, LinkedList<State<Position>>>(new BestFS<>());
				pathSolution = listToDirections(solver.solve(board));
				cm.save(String.valueOf(problemName.hashCode()), pathSolution);
			}
			responseToClient.println(pathSolution);
			responseToClient.flush();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientInputBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			responseToClient.close();
		}

	}

	private String listToDirections(LinkedList<State<Position>> path) {
		String directions="";
		for (int i = 0; i < path.size() - 1; i++) {
			if (path.get(i + 1).getState().getColumn() - path.get(i).getState().getColumn() == 1)
				directions += "Right";
			else if (path.get(i + 1).getState().getColumn() - path.get(i).getState().getColumn() == -1)
				directions += "Left";
			else if (path.get(i + 1).getState().getRow() - path.get(i).getState().getRow() == 1)
				directions += "Down";
			else if (path.get(i + 1).getState().getRow() - path.get(i).getState().getRow() == -1)
				directions += "Up";
			if (i != path.size() - 2)
				directions += ",";
		}
		return directions;
	}
};