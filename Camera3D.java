package com.example.demo2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
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

    Group gp = new Group();

    BorderPane bp = new BorderPane(gp);

    SmartGroup sg [] = {new SmartGroup(), new SmartGroup()};


    @Override
    public void start(Stage stage) throws IOException {

        Sphere s1 = prepareSphere();
        s1.translateXProperty().set(200);
        s1.translateZProperty().set(-200);

        Button bt [] = {new Button(), new Button()};

        Box box = prepareBox();

/*
        Camera cm = new PerspectiveCamera(true);

        cm.translateXProperty().set(100);
        cm.translateYProperty().set(0);
        cm.translateZProperty().set(-500);

        //Transform tf = new Rotate(10, new Point3D(0,0,0));
        cm.setNearClip(1);
        cm.setFarClip(1500);

 */



        // bylo SmartGroup sg [] = {new SmartGroup(), new SmartGroup()};
        //sg[0].getChildren().addAll(cm);
        sg[1].getChildren().addAll(s1, box);


        //tady bylo Group gp = new Group();
        gp.getChildren().addAll(sg[1]);


        ScrollPane sp = new ScrollPane();

        bt[0].setText("Box");
        bt[0].setOnAction(actionEvent -> {

            Box b = prepareBox();
            gp.getChildren().addAll(b);
        });

        bt[1].setText("BoxWOW");
        bt[1].setOnAction(actionEvent -> {

            Box b = prepareBox();
            gp.getChildren().addAll(b);
        });

        sp.setContent(bt[0]);
        sp.setContent(bt[1]);



        //HBox h = new HBox(vb, sp);
        //vb.relocate(500, 500);
        //sp.relocate(1500, 500);

        //Pane p = new Pane();
        //p.getChildren().addAll(gp);

        //Tady bylo BorderPane bp = new BorderPane(gp);
        bp.setCenter(gp);
        bp.setLeft(sp);








        Scene scene = new Scene(bp, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);
        //scene.setCamera(cm);



        initMouseControl( sg[1] , scene, stage, bp);


/*
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

 */

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


    private void initMouseControl(SmartGroup sg, Scene scene, Stage stage, BorderPane bp) {

        Rotate xRotate;
        Rotate yRotate;
        sg.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        bp.setOnMousePressed(event -> {

            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();

        });
        bp.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + (anchorX - event.getSceneX()));
        });

        bp.addEventHandler(ScrollEvent.SCROLL, event ->{
            double delta = event.getDeltaY();
            sg.translateZProperty().set(sg.getTranslateZ() - delta);

        });

        bp.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case UP: {
                    sg.translateXProperty().set(sg.getTranslateX() + 10);
                }
            }
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