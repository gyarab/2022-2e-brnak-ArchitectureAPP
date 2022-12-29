package com.example.demo2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import java.io.IOException;

public class Camera3D extends Application {

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 800;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);


    @Override
    public void start(Stage stage) throws IOException {

        Sphere s1 = new Sphere(50);
        s1.translateXProperty().set(200);

        Box b1 = new Box(80,50, 20);

        Camera cm = new PerspectiveCamera(true);

        cm.translateXProperty().set(100);
        cm.translateYProperty().set(0);
        cm.translateZProperty().set(-500);

        //Transform tf = new Rotate(10, new Point3D(0,0,0));
        cm.setNearClip(1);
        cm.setFarClip(1000);



        SmartGroup sg [] = {new SmartGroup(), new SmartGroup()};
        sg[0].getChildren().addAll(cm);
        sg[1].getChildren().addAll(s1, b1);

        Group gp = new Group();
        gp.getChildren().addAll(sg[0], sg[1]);

        Scene scene = new Scene(gp, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);
        scene.setCamera(cm);

        initMouseControl( sg[1] , scene);



        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W: {
                    cm.translateZProperty().set(cm.getTranslateZ() + 10);
                    break;
                }

                case S: {
                    cm.translateZProperty().set(cm.getTranslateZ() - 10);
                    break;
                }

                case D: {
                    cm.translateXProperty().set(cm.getTranslateX() + 10);
                    break;
                }

                case A: {
                    cm.translateXProperty().set(cm.getTranslateX() - 10);
                    break;
                }
                case NUMPAD8: {
                    cm.translateYProperty().set(cm.getTranslateY() - 10);
                    break;
                }
                case NUMPAD2: {
                    cm.translateYProperty().set(cm.getTranslateY() + 10);
                    break;
                }

                //rotace kamery

                case UP: {
                    sg[0].rotateByX(+5);
                    break;
                }
                case DOWN: {
                    sg[0].rotateByX(-5);
                    break;
                }
                case RIGHT: {
                    sg[0].rotateByY(+5);
                    break;
                }
                case LEFT: {
                    sg[0].rotateByY(-5
                    );
                    break;
                }

            }
        });


        stage.setTitle("Test!");
        stage.setScene(scene);
        stage.show();
    }

    private void initMouseControl(SmartGroup sg, Scene scene) {

            Rotate xRotate;
            Rotate yRotate;
            sg.getTransforms().addAll(
                    xRotate = new Rotate(0, Rotate.X_AXIS),
                    yRotate = new Rotate(0, Rotate.Y_AXIS)
            );
            xRotate.angleProperty().bind(angleX);
            yRotate.angleProperty().bind(angleY);

            scene.setOnMousePressed(event -> {

                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = angleX.get();
                anchorAngleY = angleY.get();

            });
            scene.setOnMouseDragged(event -> {
                angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
                angleY.set(anchorAngleY + (anchorX - event.getSceneX()));
            });
    }



    class SmartGroup extends Group{
        Rotate r;
        Transform t = new Rotate();

        void rotateByX(int ang){
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        void rotateByY(int ang){
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}