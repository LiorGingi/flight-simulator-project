package view;



import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TopographicMapDisplayer extends Canvas {
	private double[][] groundField;
	private double heightRange;
	private double minHeight;
	private double cellW;
	private double cellH;
	public static boolean mapLoaded = false;
	public static int destX;
	public static int destY;
	
	@FXML
	private Circle circle;
	
	public void setGroundField(double min, double max, double[][] table) {
		this.groundField = table;
		this.minHeight = min;
		this.heightRange=max-min;
		draw();
	}

	private void draw() {
		if (groundField != null) {
			cellW = getWidth()/groundField[0].length;
			cellH = getHeight()/groundField.length;
			double normVal;
			GraphicsContext gc = getGraphicsContext2D();
			for(int i=0; i<groundField.length; i++) {
				for(int j=0; j<groundField[i].length; j++) {
					normVal = (((groundField[i][j]-minHeight)*255)/heightRange);
					gc.setFill(Color.rgb(255-((int)(90*(normVal/255))), 250-((int)(208*(normVal/255))), 250-((int)(208*(normVal/255)))));
					gc.fillRect(j*cellW, i*cellH, cellW, cellH);
				}
			}
			mapLoaded = true;
		}
	}
	
	public void calculateCellOnMap(double x, double y) {
		destX = (int)(x/cellW);
		destY = (int)(y/cellH);
	}
}
