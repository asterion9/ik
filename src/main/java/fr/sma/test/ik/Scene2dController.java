package fr.sma.test.ik;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;


public class Scene2dController {
	private final Scene scene;
	private final Canvas canvas;

	public Scene2dController(double width, double height) {
		this.canvas = new Canvas(width, height);
		final FlowPane fp = new FlowPane();
		fp.getChildren().add(canvas);
		this.scene = new Scene(fp, Color.gray(0.1));
	}

	public Scene getScene() {
		return scene;
	}

	public void draw(Segment2d s1, Vector2d target) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		final double cw = g.getCanvas().getWidth();
		final double ch = g.getCanvas().getHeight();
		g.clearRect(0, 0, cw, ch);

		drawGrid(g, 6);
		drawSegments(g, s1);
		drawTarget(g, target);
	}

	private void drawTarget(GraphicsContext graphic, Vector2d target) {
		graphic.setFill(Color.RED);
		double x0 = graphic.getCanvas().getWidth() / 2 - 2.5, y0 = graphic.getCanvas().getHeight() / 2 - 2.5;
		graphic.fillOval(target.getX() + x0, target.getY() + y0, 5, 5);
	}

	private void drawSegments(GraphicsContext g, Segment2d s1) {
		g.setStroke(Color.gray(0.9));
		g.setLineWidth(5);
		double x = g.getCanvas().getWidth() / 2, y = g.getCanvas().getHeight() / 2;
		double angle = 0;
		for (Segment2d cur = s1; cur != null; cur = cur.getNext()) {
			angle += cur.getAngle();
			double x1 = x + cur.getLen() * Math.cos(angle);
			double y1 = y + cur.getLen() * Math.sin(angle);
			g.strokeLine(x, y, x1, y1);
			x = x1;
			y = y1;
		}
	}

	private void drawGrid(GraphicsContext g, final int gridDepth) {
		final double cw = g.getCanvas().getWidth();
		final double ch = g.getCanvas().getHeight();

		g.setLineWidth(1);
		g.setStroke(Color.gray(1, 0.1));
		for (int d = 0; d < gridDepth; d++) {
			final double nbSegment = Math.pow(2, (d + 1));
			final double segmentWidth = cw / (nbSegment);
			final double segmentHeight = ch / (nbSegment);
			for (int i = 1; i <= nbSegment - 1; i++) {
				g.strokeLine(0, i * segmentHeight, cw, i * segmentHeight);  // horizontal line
				g.strokeLine(i * segmentWidth, 0, i * segmentWidth, cw);  // vertical line
			}
		}
	}
}
