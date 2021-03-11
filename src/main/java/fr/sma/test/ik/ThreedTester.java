package fr.sma.test.ik;

import fr.sma.test.ik.threed.Scene3dController;
import fr.sma.test.ik.threed.ThreeLegRotating;
import fr.sma.test.ik.threed.Vector3d;
import fr.sma.test.ik.threed.sequence.TurningSequence3d;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class ThreedTester extends Application {

	public void start(Stage primaryStage) {
		final ThreeLegRotating arm = new ThreeLegRotating(20, 100, 80, 0, 0, 0);
		final AtomicReference<Vector3d> target = new AtomicReference<>(new Vector3d(0, 0, 0));
		final Scene3dController scene3dController = new Scene3dController(1200, 800);

		primaryStage.setScene(scene3dController.getScene());
		primaryStage.show();

		//final WalkingSequence3d walk = new WalkingSequence3d(-50, -100, 50, 0, 20, 100);
		final TurningSequence3d walk = new TurningSequence3d(
				new Vector3d(-50, -80, -50), 220, Math.PI/8, Math.PI/4, 20);

		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				scene3dController.draw(target.get(), arm);
				scene3dController.drawTrajectory(walk);
			}
		};
		at.start();
		final long t0 = System.currentTimeMillis();

		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				double t = ((System.currentTimeMillis() - t0) / 3000d) % 1;
				double tSeq = ((System.currentTimeMillis() - t0) / 3000d) % 1;
				Vector3d nextTarget = walk.getPoint(t);
				synchronized (arm) {
					//walk.setyAngle(-Math.PI/4 - (Math.PI/4) * Math.cos(2 * Math.PI * tSeq));
					arm.moveIk(nextTarget);
					target.set(nextTarget);

					//arm.moveFk(t * Math.PI * 2, t * Math.PI * 2, /*t * Math.PI * 2*/0);
				}
			}
		}, 0, 25);
	}
}
