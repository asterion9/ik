package fr.sma.test.ik;

import fr.sma.test.ik.sequence.EllipticSequence2d;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class IkTester extends Application {

	public void start(Stage primaryStage) {
		final Segment2d s1 = new Segment2d(150, 45,
				new Segment2d(100, 0));

		Canvas canvas = new Canvas(640, 640);
		final GraphicsContext graphic = canvas.getGraphicsContext2D();
		final FlowPane fp = new FlowPane();
		fp.getChildren().add(canvas);
		final Scene scene2d = new Scene(fp, Color.gray(0.1));

		primaryStage.setScene(scene2d);
		primaryStage.show();

		AnimationTimer at = new AnimationTimer() {
			private final long t0 = System.currentTimeMillis();

			private final EllipticSequence2d ellipse = new EllipticSequence2d(150, 0, 75, 25);
			@Override
			public void handle(long now) {
				long t = System.currentTimeMillis() - t0;
				Vector2d target = ellipse.getPoint((t/2000d)%1);

				s1.moveIk2Seg(target);
				//s1.moveFk(new ArrayDeque<>(List.of(Math.PI/300)));

				draw(graphic, s1, target);
			}
		};
		at.start();
	}

	private void drawTarget(GraphicsContext graphic, Vector2d target) {
		graphic.setFill(Color.RED);
		double x0 = graphic.getCanvas().getWidth() / 2-2.5, y0 = graphic.getCanvas().getHeight() / 2-2.5;
		graphic.fillOval(target.getX() + x0, target.getY() + y0, 5, 5);
	}

	private void draw(GraphicsContext g, Segment2d s1, Vector2d target) {
		final double cw = g.getCanvas().getWidth();
		final double ch = g.getCanvas().getHeight();
		g.clearRect(0, 0, cw, ch);

		drawGrid(g, 6);
		drawSegments(g, s1);
		drawTarget(g, target);
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
