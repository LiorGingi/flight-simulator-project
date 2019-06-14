package view;

import java.util.HashMap;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TopographicMapDisplayer extends Canvas {
	private double[][] groundField;
	private double heightRange;
	private double minHeight;
	
	public void setGroundField(HashMap<String, double[][]> values) {
		this.groundField = values.get("table");
		this.minHeight = values.get("minMax")[0][0];
		this.heightRange=values.get("minMax")[0][1]-minHeight; //maxHeight - minHeight
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
	
}
