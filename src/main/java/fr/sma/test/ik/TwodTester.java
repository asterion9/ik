package fr.sma.test.ik;

import fr.sma.test.ik.threed.Scene3dController;
import fr.sma.test.ik.threed.TwoLegRotating;
import fr.sma.test.ik.threed.Vector3d;
import fr.sma.test.ik.threed.sequence.SphericalSequence;
import fr.sma.test.ik.twod.Scene2dController;
import fr.sma.test.ik.twod.TwoLegPlanar;
import fr.sma.test.ik.twod.Vector2d;
import fr.sma.test.ik.twod.sequence.EllipticSequence2d;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class TwodTester extends Application {

	public void start(Stage primaryStage) {
		final TwoLegPlanar arm = new TwoLegPlanar(150, 100, 0, 0);
		final AtomicReference<Vector2d> target = new AtomicReference<>(new Vector2d(0, 0));
		final Scene2dController scene2dController = new Scene2dController(1200, 800);

		primaryStage.setScene(scene2dController.getScene());
		primaryStage.show();

		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				scene2dController.draw(arm, target.get());
			}
		};
		at.start();

		final long t0 = System.currentTimeMillis();
		final EllipticSequence2d ellipse = new EllipticSequence2d(0, 0, 75, 215);

		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				double t = ((System.currentTimeMillis() - t0) / 5000d) % 1;
				Vector2d nextTarget = ellipse.getPoint(t);
				synchronized (arm) {
					arm.moveIk2Seg(nextTarget);
					target.set(nextTarget);
					//arm.moveFk(t * Math.PI * 2, t * Math.PI * 2, /*t * Math.PI * 2*/0);
				}
			}
		}, 0, 10);
	}
}
