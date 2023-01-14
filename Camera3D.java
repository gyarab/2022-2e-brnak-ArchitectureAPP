package com.example.demo2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Camera3D extends Application {

    private static final float WIDTH = 2000;
    private static final float HEIGHT = 1000;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);


    @Override
    public void start(Stage stage) throws IOException {

        Sphere s1 = prepareSphere();
        s1.translateXProperty().set(200);
        s1.translateZProperty().set(-200);

        Box box = prepareBox();


        Camera cm = new PerspectiveCamera(true);

        cm.translateXProperty().set(100);
        cm.translateYProperty().set(0);
        cm.translateZProperty().set(-500);

        //Transform tf = new Rotate(10, new Point3D(0,0,0));
        cm.setNearClip(1);
        cm.setFarClip(1500);



        SmartGroup sg [] = {new SmartGroup(), new SmartGroup()};
        sg[0].getChildren().addAll(cm);
        sg[1].getChildren().addAll(s1, box);


        Group gp = new Group();
        gp.getChildren().addAll(sg[0], sg[1]);


        Button bt = new Button("Kurwa");
        bt.setOnAction(actionEvent -> {

            Box b = prepareBox();
            sg[0].getChildren().addAll(b);
        });
        BorderPane bp = new BorderPane(gp);
        bp.setLeft(bt);



        Scene scene = new Scene(bp, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);
        scene.setCamera(cm);



        initMouseControl( sg[1] , scene, stage);



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

        stage.setTitle("ArchitectureAPP");
        stage.setScene(scene);
        stage.show();
    }

    private Sphere prepareSphere() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/mramor.png")));
        Sphere sp = new Sphere(80);
        sp.setMaterial(material);
        return sp;
    }

    private Box prepareBox() {
        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/mramor.png")));
        Box b1 = new Box(80,50, 20);
        b1.setMaterial(material);
        b1.translateZProperty().set(-200);
        return b1;
    }


    private void initMouseControl(SmartGroup sg, Scene scene, Stage stage) {

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

        scene.addEventHandler(ScrollEvent.SCROLL, event ->{
            double delta = event.getDeltaY();
            sg.translateZProperty().set(sg.getTranslateZ() - delta);

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