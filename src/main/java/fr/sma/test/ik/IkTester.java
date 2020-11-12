package fr.sma.test.ik;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Deque;

public class IkTester extends Application {

	public void start(Stage primaryStage) {
		Canvas canvas = new Canvas(640, 640);

		final Segment s1 = new Segment(150, 45);
		Segment s2 = new Segment(100, 0, s1);

		final GraphicsContext graphic = canvas.getGraphicsContext2D();
		//draw(graphic, s1);

		final FlowPane fp = new FlowPane();
		fp.getChildren().add(canvas);

		primaryStage.setScene(new Scene(fp, Color.gray(0.1)));
		primaryStage.show();

		AnimationTimer at = new AnimationTimer() {
			private long nextStep;

			private int t = 0;

			@Override
			public void handle(long now) {
				if (nextStep == 0) {
					nextStep = now;
				} else if (nextStep > now) {
					return;
				}

				double x = Math.cos(t / 500d * Math.PI * 2) * 240;
				double y = Math.sin(t / 500d * Math.PI * 2) * 240;

				s1.moveIk2Seg(x, y);
				t++;

				//s1.moveFk(new ArrayDeque<>(List.of(Math.PI/300)));

				draw(graphic, s1);
				graphic.setFill(Color.RED);
				double x0 = graphic.getCanvas().getWidth() / 2, y0 = graphic.getCanvas().getHeight() / 2;
				graphic.fillOval(x + x0, y + y0, 5, 5);
				nextStep += 10_000_000;  // 10ms
			}
		};
		at.start();
	}

	private void draw(GraphicsContext g, Segment s1) {
		final double cw = g.getCanvas().getWidth();
		final double ch = g.getCanvas().getHeight();
		g.clearRect(0, 0, cw, ch);

		drawGrid(g, 6);
		drawSegments(g, s1);
	}

	private void drawSegments(GraphicsContext g, Segment s1) {
		g.setStroke(Color.gray(0.9));
		g.setLineWidth(5);
		double x = g.getCanvas().getWidth() / 2, y = g.getCanvas().getHeight() / 2;
		double angle = 0;
		for (Segment cur = s1; cur != null; cur = cur.getNext()) {
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

	private static class Segment {
		private double len;
		private double angle;
		private Segment next;

		public Segment(double len, double angle) {
			this(len, angle, null);
		}

		public Segment(double len, double angle, Segment parent) {
			this.len = len;
			this.angle = angle;
			if (parent != null) {
				parent.next = this;
			}
		}

		public void moveIk2Seg(double x, double y) {
			if (this.next == null) {
				throw new IllegalArgumentException("no second segment to apply");
			}

			double squareLength = x * x + y * y;
			double l1 = this.len;
			double l2 = this.next.len;

			if (Math.pow(squareLength, 0.5d) > l1 + l2) {
				throw new IllegalArgumentException("position is too far");
			} else if (Math.pow(squareLength, 0.5d) < l1 - l2) {
				throw new IllegalArgumentException("position is too close");
			}

			this.angle = - Math.acos(x / (Math.pow(squareLength, 0.5))) +
					Math.asin((l1 * l1 + squareLength - l2 * l2) / (2 * l1 * Math.pow(squareLength, 0.5)));
			this.next.angle = - Math.acos((squareLength - l1 * l1 - l2 * l2) / (2 * l1 * l2));
		}

		public void moveFk(Deque<Double> angles) {
			if (!angles.isEmpty()) {
				this.angle += angles.pop();
				if (next != null) {
					next.moveFk(angles);
				}
			}

		}

		public double getLen() {
			return len;
		}

		public Segment getNext() {
			return next;
		}

		public double getAngle() {
			return angle;
		}

		public void setAngle(double angle) {
			this.angle = angle;
		}
	}
}
