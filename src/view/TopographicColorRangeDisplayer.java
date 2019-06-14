package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TopographicColorRangeDisplayer extends Canvas {
	private double heightRange;
	private double minHeight;
	private double maxHeight;
	
	public void setColorRange(double minHeight, double maxHeight) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.heightRange=maxHeight-minHeight; //maxHeight - minHeight
		draw();
	}

	private void draw() {
		double canvasW = getWidth();
		double cellW = canvasW/heightRange;
		double cellH = getHeight();
		double normVal;
	
		GraphicsContext gc = getGraphicsContext2D();
		GraphicsContext gcText = getGraphicsContext2D();
		
		for(int i=(int)minHeight; i<=(int)maxHeight; i++) {
			normVal = (((i-minHeight)*255)/heightRange);
			gc.setFill(Color.rgb(255-((int)(90*(normVal/255))), 250-((int)(208*(normVal/255))), 250-((int)(208*(normVal/255)))));
			gc.fillRect(canvasW*(normVal/255), 0, cellW+5, cellH);
		}
		
		for(int i=1; i<=3; i++) {
			gcText.setFill(Color.BLACK);
			gcText.setFont(Font.font ("Verdana", cellH/2.5));
			gcText.fillText(""+(int)(minHeight+((heightRange/4)*i)), (canvasW/4)*i, cellH-1);
		}
		
	}
}
