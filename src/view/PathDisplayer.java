package view;

import java.util.LinkedList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import server.Board;

public class PathDisplayer extends Canvas {
	
	
	private int planeX;
	private int planeY;
	private int destX;
	private int destY;
	
	
	private void redraw() {
//		if(pathValues!=null) {
			double canvasW = getWidth();
			double canvasH = getHeight();
//			double cellW = canvasW/pathValues[0].length;
//			double cellH = canvasH/pathValues.length;
			
			GraphicsContext gc = getGraphicsContext2D();
			
//			gc.fillRect(planeX*cellW, planeY*cellH, cellW, cellH);
		}

}
