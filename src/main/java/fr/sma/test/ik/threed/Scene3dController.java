package fr.sma.test.ik.threed;

import fr.sma.test.ik.utils.CameraManager;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Scene3dController {
	private final Scene scene;
	private final Group mainGroup;
	private final CameraManager cameraManager;

	private final Sphere targetSphere;
	private final Rotate angleH, angleV, angle2;
	private final Group baseVectors;
	private final Box arm1;
	private final Box arm2;

	public Scene3dController(double width, double height) {
		// define base elements
		this.mainGroup = new Group();
		final Pane pane = new Pane(mainGroup);

		// define camera
		PerspectiveCamera camera = new PerspectiveCamera(true);
		mainGroup.getChildren().add(camera);
		this.cameraManager = new CameraManager(camera, pane, mainGroup);


		// define target
		this.targetSphere = new Sphere(3, 10);
		this.targetSphere.setMaterial(new PhongMaterial(Color.RED));
		mainGroup.getChildren().add(targetSphere);

		// define axis
		baseVectors = new Group();
		final Box xAxis = new Box(50, 2, 2);
		xAxis.setMaterial(new PhongMaterial(Color.RED));
		xAxis.getTransforms().add(new Translate(25, 0, 0));
		final Box yAxis = new Box(2, 50, 2);
		yAxis.setMaterial(new PhongMaterial(Color.GREEN));
		yAxis.getTransforms().add(new Translate(0, 25, 0));
		final Box zAxis = new Box(2, 2, 50);
		zAxis.setMaterial(new PhongMaterial(Color.BLUE));
		zAxis.getTransforms().add(new Translate(0, 0, 25));
		baseVectors.getChildren().addAll(xAxis, yAxis, zAxis);
		mainGroup.getChildren().add(baseVectors);

		// define arms
		arm1 = new Box(150, 5, 5);
		this.angleV = new Rotate(0, Rotate.Z_AXIS);
		this.angleH = new Rotate(0, Rotate.Y_AXIS);
		final Translate translateArm1Size = new Translate();
		translateArm1Size.xProperty().bind(arm1.widthProperty().divide(2));
		arm1.getTransforms().addAll(angleH, angleV, translateArm1Size);
		arm2 = new Box(100, 4, 4);
		this.angle2 = new Rotate(0, Rotate.Z_AXIS);
		final Translate translateArm2Size = new Translate();
		translateArm2Size.xProperty().bind(arm2.widthProperty().divide(2));
		arm2.getTransforms().addAll(arm1.getTransforms());
		arm2.getTransforms().addAll(translateArm1Size, angle2, translateArm2Size);
		mainGroup.getChildren().addAll(arm1, arm2);

		// build scene
		this.scene = new Scene(pane, width, height, true);
		this.scene.setFill(Color.gray(0.1));
		this.scene.setCamera(camera);
	}

	public Scene getScene() {
		return scene;
	}

	public void draw(Vector3d target, TwoLegRotating arm) {
		drawArm(arm);
		drawTarget(target);
	}

	private void drawTarget(Vector3d target) {
		this.targetSphere.setTranslateX(target.getX());
		this.targetSphere.setTranslateY(target.getY());
		this.targetSphere.setTranslateZ(target.getZ());
	}

	private void drawArm(TwoLegRotating arm) {
		this.angleH.angleProperty().set(360 * arm.getAngle1H() / (2 * Math.PI));
		this.angleV.angleProperty().set(360 * arm.getAngle1V() / (2 * Math.PI));
		this.angle2.angleProperty().set(360 * arm.getAngle2() / (2 * Math.PI));
	}
}
