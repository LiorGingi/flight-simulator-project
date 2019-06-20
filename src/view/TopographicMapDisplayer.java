package view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TopographicMapDisplayer extends Canvas {
	private double[][] groundField;
	private ArrayList<Circle> pathObjects;
	private double heightRange;
	private double minHeight;
	private double cellW;
	private double cellH;
	
	
	public double getCellW() {
		return cellW;
	}
	public double getCellH() {
		return cellH;
	}

	public static boolean mapLoaded = false;

	// source and destination cells in the grid
	public static double destX; // the destination point on the grid itself
	public static double destY;
	public static int sourceX; // the source point of the grid itself
	public static int sourceY;
	public Circle planeLocation;

	@FXML
	private Circle circle;

	public void setGroundField(double min, double max, double[][] table) {
		this.groundField = table;
		this.minHeight = min;
		this.heightRange = max - min;
		if (pathObjects==null) {
			pathObjects = new ArrayList<Circle>();
		}
		draw();
	}
	public double[][] getGroundField(){
		return this.groundField;
	}
	private void draw() {
		if (groundField != null) {
			cellW = getWidth() / groundField[0].length;
			cellH = getHeight() / groundField.length;
			double normVal;
			GraphicsContext gc = getGraphicsContext2D();
			for (int i = 0; i < groundField.length; i++) {
				for (int j = 0; j < groundField[i].length; j++) {
					normVal = (((groundField[i][j] - minHeight) * 255) / heightRange);
					gc.setFill(Color.rgb(255 - ((int) (90 * (normVal / 255))), 250 - ((int) (208 * (normVal / 255))),
							250 - ((int) (208 * (normVal / 255)))));
					gc.fillRect(j * cellW, i * cellH, cellW, cellH);
				}
			}
			mapLoaded = true;
		}
	}

	public void paintPath(String[] directions, Group group, double sourceX, double sourceY) {
		double currentX = sourceX;
		double currentY = sourceY;
		ArrayList<Circle> newPath = new ArrayList<Circle>();
		
		pathObjects.forEach((circle) -> {
			group.getChildren().remove(circle);
		});
		
		for (int i = 0; i < directions.length; i++) {
			Circle positionInPath = new Circle();
			switch (directions[i]) {
			case "Up":
				currentY -=cellH;
				break;
			case "Down":
				currentY +=cellH;
				break;
			case "Right":
				currentX +=cellW;
				break;
			case "Left":
				currentX -=cellW;
				break;
			}
			 // paint the point
				positionInPath = new Circle(2, Color.BLUE);
				positionInPath.setCenterX(currentX);
				positionInPath.setCenterY(currentY);
				group.getChildren().add(positionInPath);
				newPath.add(positionInPath);
		}
		pathObjects = newPath;
	}
}
