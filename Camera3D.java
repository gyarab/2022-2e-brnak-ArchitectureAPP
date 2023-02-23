package com.example.demo2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Camera3D extends Application {

    Scanner sc = new Scanner(System.in);

    private static final float WIDTH = 2000;
    private static final float HEIGHT = 1000;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    //Group gp = new Group();

    int pomocnahodnota = 0;

    int Sphere = 1;
    int [] zaznamSphere = new int[1000];
    int Box = 0;
    int Cube = 0;
    int Cylindr = 0;

    int pocetobjektu = 0;

    int k = 0;
    int [] cisla = new int[1000];

    ArrayList <Sphere> spe = new ArrayList();
    ArrayList <Box> b1 = new ArrayList();
    ArrayList <Box> b2 = new ArrayList();
    ArrayList <Cylinder> cil = new ArrayList<>();

    int vybrano = 1;


    SmartGroup sg  = new SmartGroup();

    Button bt [] = {new Button(), new Button(), new Button(), new Button()};
    Button btplusminus [] = {new Button(), new Button()};
    Button xyz [] = {new Button("X"), new Button("Y"), new Button("Z")};
    Button start = new Button("Start");

    PhongMaterial materials [] = {new PhongMaterial(), new PhongMaterial(), new PhongMaterial()};


    ChoiceBox chb [] = {new ChoiceBox(), new ChoiceBox()};


    ScrollPane sp = new ScrollPane();

    VBox v = new VBox();

    BorderPane bp = new BorderPane(sg);

    //Scene uvodscene = new Scene();
    Scene hlscene = new Scene(bp, WIDTH, HEIGHT);
    //Scene datascene =  new Scene(lb);


    @Override
    public void start(Stage stage) throws IOException {




        materials[0].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/mramor.png")));
        materials[1].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/orechdrevo-3.jpg")));
        materials[2].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));

        chb[0].getItems().add("Mramor");
        chb[0].getItems().add("Ořechové dřeevo");
        chb[0].getItems().add("Bílá barva");



        Box lineX = new Box(100, 2, 2);
        PhongMaterial material1 = new PhongMaterial();
        material1.setDiffuseColor(Color.BLUE);
        lineX.setMaterial(material1);
        lineX.translateXProperty().set(50);


        Box lineY = new Box(2, 100, 2);
        PhongMaterial material2 = new PhongMaterial();
        material2.setDiffuseColor(Color.YELLOW);
        lineY.setMaterial(material2);
        lineY.translateYProperty().set(50);

        Box lineZ = new Box(2,2,100);
        PhongMaterial material3 = new PhongMaterial();
        material3.setDiffuseColor(Color.RED);
        lineZ.setMaterial(material3);
        lineZ.translateZProperty().set(50);




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
        sg.getChildren().addAll(lineX, lineY, lineZ);


        //tady bylo Group gp = new Group();
        // gp.getChildren().addAll(sg);



        bt[0].setText("Box");
        bt[0].setPrefSize(200,30);
        bt[0].relocate(0,0);
        bt[0].setOnAction(actionEvent -> {

            Box b = prepareBox();
            sg.getChildren().addAll(b);
        });

        bt[1].setText("Cube");
        bt[1].setPrefSize(200,30);
        bt[1].relocate(0,100);
        bt[1].setOnAction(actionEvent -> {

            Box c = prepareCube();
            sg.getChildren().addAll(c);
        });

        bt[2].setText("Sphere");
        bt[2].setPrefSize(200,30);
        bt[2].relocate(0,200);
        bt[2].setOnAction(actionEvent -> {

            Sphere s = prepareSphere();
            sg.getChildren().addAll(s);
        });

        bt[3].setText("Cylinder");
        bt[3].setPrefSize(200,30);
        bt[3].relocate(0,300);
        bt[3].setOnAction(actionEvent -> {

            Cylinder c = prepareCylinder();
            sg.getChildren().addAll(c);
        });




        sp.setPrefSize(200,1000);
        v.setPrefSize(200, 1500);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setContent(v);

        v.getChildren().addAll(bt[0], bt[1], bt[2], bt[3], chb[0], chb[1]);




        //HBox h = new HBox(vb, sp);
        //vb.relocate(500, 500);
        //sp.relocate(1500, 500);

        //Pane p = new Pane();
        //p.getChildren().addAll(gp);

        //Tady bylo BorderPane bp = new BorderPane(gp);
        bp.setCenter(sg);
        bp.setLeft(sp);

        v.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), Insets.EMPTY)));
        sp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0), Insets.EMPTY)));
        bp.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(0), Insets.EMPTY)));







        hlscene.setFill(Color.SILVER);



        initMouseControl( sg , hlscene, stage, bp, v);


/*
        sg.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
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
        chb[1].setPrefSize(200,30);
        chb[1].relocate(0,400);
        chb[1].getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    vybrano = new_val.intValue();
                });

        chb[0].setPrefSize(200,30);
        chb[0].relocate(0,500);
        chb[0].getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    if (cisla[vybrano] == 1) {
                        pomocnahodnota = Sphere;
                        for (int i = 0; i <= Sphere; i++) {
                            if (zaznamSphere[vybrano] == zaznamSphere[i]){
                                break;
                            } else {
                                pomocnahodnota--;
                            }
                        }
                        spe.get(Sphere - pomocnahodnota).setMaterial(materials[new_val.intValue()]);
                    } else if (cisla[vybrano] == 2) {
                        b1.get(Box).setMaterial(materials[new_val.intValue()]);
                    } else if (cisla[vybrano] == 3) {
                        b2.get(Cube).setMaterial(materials[new_val.intValue()]);
                    }else if (cisla[vybrano] == 4) {
                        cil.get(Cylindr).setMaterial(materials[new_val.intValue()]);
                    }

                });



        stage.setTitle("ArchitectureAPP");
        stage.setScene(hlscene);
        stage.show();

        //Infoscene

        Label lb = new Label("ArchitectureAPP");






    }

    private Sphere prepareSphere() {
        Sphere++;
        System.out.println("PIS");
        int v = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();
        String s = sc.next();
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));
        spe.add(new Sphere(v));
        spe.get(Sphere-2).setMaterial(material);

        spe.get(Sphere-2).translateXProperty().set(x);
        spe.get(Sphere-2).translateYProperty().set(y);
        spe.get(Sphere-2).translateZProperty().set(z);

        chb[1].getItems().add(s);


        cisla[k] = 1;
        k++;
        zaznamSphere[Sphere - 2] = pocetobjektu;
        pocetobjektu++;

        return spe.get(Sphere-2);
    }

    private Box prepareBox() {
        Box++;
        System.out.println("PIS");
        int x1 = sc.nextInt();
        int y2 = sc.nextInt();
        int z3 = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();
        String s = sc.next();

        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));


        b1.add(new Box(x1,y2,z3));
        b1.get(Box-1).setMaterial(material);

        b1.get(Box-1).translateXProperty().set(x);
        b1.get(Box-1).translateYProperty().set(y);
        b1.get(Box-1).translateZProperty().set(z);


        chb[1].getItems().add(s);


        cisla[k] = 2;
        k++;
        pocetobjektu++;

        return b1.get(Box-1);
    }

    private Box prepareCube() {
        Cube++;
        System.out.println("PIS");
        int x1 = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();
        String s = sc.next();

        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));


        b2.add(new Box(x1,x1,x1));
        b2.get(Cube-1).setMaterial(material);

        b2.get(Cube-1).translateXProperty().set(x);
        b2.get(Cube-1).translateYProperty().set(y);
        b2.get(Cube-1).translateZProperty().set(z);


        chb[1].getItems().add(s);

        cisla[k] = 3;
        k++;
        pocetobjektu++;

        return b2.get(Cube-1);
    }

    public Cylinder prepareCylinder() {
        Cylindr++;
        System.out.println("PIS");
        int x1 = sc.nextInt();
        int y2 = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();
        String s = sc.next();

        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));


        cil.add(new Cylinder(x1,y2, 100));
        cil.get(Cylindr-1).setMaterial(material);

        cil.get(Cylindr-1).translateXProperty().set(x);
        cil.get(Cylindr-1).translateYProperty().set(y);
        cil.get(Cylindr-1).translateZProperty().set(z);


        chb[1].getItems().add(s);

        cisla[k] = 4;
        k++;
        pocetobjektu++;

        return cil.get(Cylindr-1);

    }


    private void initMouseControl(SmartGroup sg, Scene scene, Stage stage, BorderPane bp, VBox v) {

        Rotate xRotate;
        Rotate yRotate;
        sg.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        bp.setOnMousePressed(event -> {

            anchorX = event.getScreenX();
            anchorY = event.getScreenY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();

        });
        bp.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY - (anchorX - event.getSceneX()));
        });

        bp.addEventHandler(ScrollEvent.SCROLL, event ->{
            double delta = event.getDeltaY();
            sg.translateZProperty().set(sg.getTranslateZ() + 10);


        });


        bp.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W: {
                    sg.translateZProperty().set(sg.getTranslateX() + 10);
                }
                case S: {
                    sg.translateZProperty().set(sg.getTranslateX() - 10);
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