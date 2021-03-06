package fr.sma.test.ik.twod;

import fr.sma.test.ik.threed.sequence.Sequence3d;
import fr.sma.test.ik.twod.sequence.Sequence2d;
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

	public void draw(TwoLegPlanar s1, Vector2d target, Sequence2d sequence) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		final double cw = g.getCanvas().getWidth();
		final double ch = g.getCanvas().getHeight();
		g.clearRect(0, 0, cw, ch);

		drawGrid(g, 6);
		drawSequence(g, sequence);
		drawArm(g, s1);
		drawTarget(g, target);
	}

	private void drawSequence(GraphicsContext graphic, Sequence2d sequence2d) {
		final double step = 1/20d;
		Vector2d prev = sequence2d.getPoint(0);
		graphic.setLineWidth(1);
		graphic.setStroke(Color.YELLOW);
		double x0 = graphic.getCanvas().getWidth() / 2, y0 = graphic.getCanvas().getHeight() / 2;
		for(double i=step; i<=1; i += step) {
			Vector2d cur = sequence2d.getPoint(i);
			graphic.strokeLine(prev.getX()+x0, prev.getY()+y0, cur.getX()+x0, cur.getY()+y0);
			prev = cur;
		}
	}

	private void drawTarget(GraphicsContext graphic, Vector2d target) {
		graphic.setFill(Color.RED);
		double x0 = graphic.getCanvas().getWidth() / 2 - 2.5, y0 = graphic.getCanvas().getHeight() / 2 - 2.5;
		graphic.fillOval(target.getX() + x0, target.getY() + y0, 5, 5);
	}

	private void drawArm(GraphicsContext g, TwoLegPlanar arm) {
		g.setStroke(Color.gray(0.9));
		g.setLineWidth(5);
		double x = g.getCanvas().getWidth() / 2, y = g.getCanvas().getHeight() / 2;
		double angle = arm.getAngle1();
		double x1 = x + arm.getL1() * Math.cos(angle);
		double y1 = y + arm.getL1() * Math.sin(angle);
		g.strokeLine(x, y, x1, y1);
		angle += arm.getAngle2();
		double x2 = x1 + arm.getL2() * Math.cos(angle);
		double y2 = y1 + arm.getL2() * Math.sin(angle);
		g.strokeLine(x1, y1, x2, y2);
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
