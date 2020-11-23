package fr.sma.test.ik.utils;

import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;

public class CameraManager {

	private static final double CAMERA_INITIAL_DISTANCE = -1000;
	private static final double CAMERA_INITIAL_X_ANGLE = 0.0;
	private static final double CAMERA_INITIAL_Y_ANGLE = 0.0;
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 10000.0;
	private static final double CONTROL_MULTIPLIER = 0.1;
	private static final double SHIFT_MULTIPLIER = 10.0;
	private static final double MOUSE_SPEED = 1;
	private static final double ROTATION_SPEED = 2.0;
	private static final double TRACK_SPEED = 0.6;

	private final Group cameraXform = new Group();
	private final Group cameraXform2 = new Group();
	private final Rotate rx = new Rotate();
	private final Rotate ry = new Rotate();
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;

	private final Camera camera;

	public CameraManager(Camera cam, Node mainRoot, Group root) {

		camera = cam;

		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(camera);

		rx.setAxis(Rotate.X_AXIS);
		ry.setAxis(Rotate.Y_AXIS);
		cameraXform.getTransforms().addAll(ry, rx);

		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
		ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		rx.setAngle(CAMERA_INITIAL_X_ANGLE);

		// Add keyboard and mouse handler
		handleKeyboard(mainRoot, root);
		handleMouse(mainRoot, root);
	}

	private void handleMouse(Node mainRoot, final Node root) {

		mainRoot.setOnMousePressed(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			mouseOldX = me.getSceneX();
			mouseOldY = me.getSceneY();

			// Set focus on the mainRoot to be able to detect key press
			mainRoot.requestFocus();
		});
		mainRoot.setOnMouseDragged(me -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX);
			mouseDeltaY = (mousePosY - mouseOldY);

			double modifier = 1.0;

			if (me.isControlDown()) {
				modifier = CONTROL_MULTIPLIER;
			}
			if (me.isShiftDown()) {
				modifier = SHIFT_MULTIPLIER;
			}
			if (me.isPrimaryButtonDown()) {
				ry.setAngle(ry.getAngle() + mouseDeltaX * modifier * ROTATION_SPEED);
				rx.setAngle(rx.getAngle() - mouseDeltaY * modifier * ROTATION_SPEED);
			} else if (me.isSecondaryButtonDown()) {
				cameraXform2.setTranslateX(cameraXform2.getTranslateX() - mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
				cameraXform2.setTranslateY(cameraXform2.getTranslateY() - mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
			}
		});
		mainRoot.setOnScroll(event -> {
			double modifier = 1.0;

			if (event.isControlDown()) {
				modifier = CONTROL_MULTIPLIER;
			}
			if (event.isShiftDown()) {
				modifier = SHIFT_MULTIPLIER;
			}
			double z = camera.getTranslateZ();
			double newZ = z + event.getDeltaY() * MOUSE_SPEED * modifier;
			camera.setTranslateZ(newZ);
		});
	}

	private void handleKeyboard(Node mainRoot, final Node root) {
		mainRoot.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case ALT:
					cameraXform2.setTranslateX(0.0);
					cameraXform2.setTranslateY(0.0);

					camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);

					ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
					rx.setAngle(CAMERA_INITIAL_X_ANGLE);
					break;
				default:

			}
		});
	}

}
