package view;



import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TopographicMapDisplayer extends Canvas {
	private double[][] groundField;
	private double heightRange;
	private double minHeight;
	private static double destX;
	private static double destY;
	private GraphicsContext destGc;
	
	public void setGroundField(double min, double max, double[][] table) {
		this.groundField = table;
		this.minHeight = min;
		this.heightRange=max-min;
		draw();
	}

	private void draw() {
		if (groundField != null) {
			double canvasW = getWidth();
			double canvasH = getHeight();
			double cellW = canvasW/groundField[0].length;
			double cellH = canvasH/groundField.length;
			double normVal;
		
			GraphicsContext gc = getGraphicsContext2D();
			
			for(int i=0; i<groundField.length; i++) {
				for(int j=0; j<groundField[i].length; j++) {
					normVal = (((groundField[i][j]-minHeight)*255)/heightRange);
					gc.setFill(Color.rgb(255-((int)(90*(normVal/255))), 250-((int)(208*(normVal/255))), 250-((int)(208*(normVal/255)))));
					gc.fillRect(j*cellW, i*cellH, cellW, cellH);
				}
			}
		}
	}
	
	public void setDestination(MouseEvent event) {
		if(destGc == null) {
			destGc = getGraphicsContext2D();
		}
		Circle c = new Circle(event.getX(), event.getY(), 5);
		destGc.strokeOval(event.getX()-10, event.getY()-10, 20, 20);
	}
	
}
