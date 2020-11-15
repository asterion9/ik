package fr.sma.test.ik;

import fr.sma.test.ik.sequence.EllipticSequence2d;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class IkTester extends Application {

	public void start(Stage primaryStage) {
		final Segment2d s1 = new Segment2d(150, 45,
				new Segment2d(100, 0));

		final Scene2dController scene2dController = new Scene2dController(640, 640);

		primaryStage.setScene(scene2dController.getScene());
		primaryStage.show();

		AnimationTimer at = new AnimationTimer() {
			private final long t0 = System.currentTimeMillis();

			private final EllipticSequence2d ellipse = new EllipticSequence2d(150, 0, 75, 25);

			@Override
			public void handle(long now) {
				long t = System.currentTimeMillis() - t0;
				Vector2d target = ellipse.getPoint((t / 2000d) % 1);

				s1.moveIk2Seg(target);
				//s1.moveFk(new ArrayDeque<>(List.of(Math.PI/300)));

				scene2dController.draw(s1, target);
			}
		};
		at.start();
	}
}
