package fr.sma.test.ik;

import fr.sma.test.ik.utils.CameraManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class SceneTester extends Application {

	CameraManager cameraManager;

	@Override
	public void start(Stage primaryStage) {
		Group mainGroup = new Group();
		final Pane pane = new Pane(mainGroup);

		PerspectiveCamera camera = new PerspectiveCamera(true);
		mainGroup.getChildren().add(camera);
		this.cameraManager = new CameraManager(camera, pane, mainGroup);

		Sphere targetSphere = new Sphere(3, 10);
		targetSphere.setMaterial(new PhongMaterial(Color.RED));
		mainGroup.getChildren().add(targetSphere);

		// define arms
		Cylinder arm1 = new Cylinder(5, 150);
		arm1.translateYProperty().bind(arm1.heightProperty().divide(2));

		Rotate angleV = new Rotate(75, Rotate.Z_AXIS);
		angleV.pivotYProperty().bind(arm1.heightProperty().divide(2).negate());
		Rotate angleH = new Rotate(0, Rotate.X_AXIS);
		angleH.pivotYProperty().bind(arm1.heightProperty().divide(2).negate());
		arm1.getTransforms().addAll(angleV, angleH);
		/*Cylinder arm2 = new Cylinder(4, 100);
		Rotate angle2 = new Rotate(0, Rotate.Z_AXIS);
		final Translate translateArm2Size = new Translate(0, 50);
		translateArm2Size.yProperty().bind(arm2.heightProperty().divide(2));
		arm2.getTransforms().addAll(translateArm2Size, angleV, angleH,
				translateArm1Size, translateArm1Size, angle2);*/
		mainGroup.getChildren().addAll(arm1/*, arm2*/);

		Scene scene = new Scene(pane, 1600, 720, true);
		scene.setFill(Color.gray(0.1));
		scene.setCamera(camera);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
