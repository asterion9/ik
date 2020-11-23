package fr.sma.test.ik;

import fr.sma.test.ik.threed.Scene3dController;
import fr.sma.test.ik.threed.TwoLegRotating;
import fr.sma.test.ik.threed.Vector3d;
import fr.sma.test.ik.threed.sequence.SphericalSequence;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class ThreedTester extends Application {

	public void start(Stage primaryStage) {
		final TwoLegRotating arm = new TwoLegRotating(150, 100, 0, 0, 0);
		final AtomicReference<Vector3d> target = new AtomicReference<>(new Vector3d(0, 0, 0));
		final Scene3dController scene3dController = new Scene3dController(1200, 800);

		primaryStage.setScene(scene3dController.getScene());
		primaryStage.show();

		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				scene3dController.draw(target.get(), arm);
			}
		};
		at.start();

		final long t0 = System.currentTimeMillis();
		final SphericalSequence sphereSeq = new SphericalSequence(0, 0, 200);

		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				double t = ((System.currentTimeMillis() - t0) / 10000d) % 1;
				Vector3d nextTarget = sphereSeq.getPoint(t);
				synchronized (arm) {
					arm.moveIk(nextTarget);
					target.set(nextTarget);
					//arm.moveFk(t * Math.PI * 2, t * Math.PI * 2, /*t * Math.PI * 2*/0);
				}
			}
		}, 0, 10);
	}
}
