package com.example.demo2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Camera3D extends Application {


    private static final float WIDTH = 2000;
    private static final float HEIGHT = 1000;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    java.net.URL logourl  = getClass().getResource("/com/example/demo2/Symbol.jpg");

    int pomocnahodnota = 0;

    //tady jsou veci co borec zadava jakoze informace relocate kde a velikost
    //Sphere
    int v1;
    int x11;
    int y11;
    int z11;

    //Box
    int x2;
    int y2;
    int z2;
    int x22;
    int y22;
    int z22;

    //cube
    int x3;
    int x33;
    int y33;
    int z33;

    //Cylindr
    int x4;
    int y4;
    int x44;
    int y44;
    int z44;

    int Sphere = 0;
    int [] zaznamSphere = new int[1000];
    int Box = 0;
    int [] zaznamBox = new int[1000];
    int Cube = 0;
    int [] zaznamCube = new int[1000];
    int Cylindr = 0;
    int [] zaznamCylindr = new int[1000];

    int pocetobjektu = 0;

    int k = 0;
    int [] cisla = new int[1000];

    ArrayList <Sphere> spe = new ArrayList();
    ArrayList <Box> b1 = new ArrayList();
    ArrayList <Box> b2 = new ArrayList();
    ArrayList <Cylinder> cil = new ArrayList<>();

    int vybrano = 1;

    String s;

    Label l = new Label("Vítám tě :)");

    SmartGroup sg  = new SmartGroup();
    
    Button bt [] = {new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()};
    Button delete = new Button("Delete");

    PhongMaterial materials [] = {new PhongMaterial(), new PhongMaterial(), new PhongMaterial(), new PhongMaterial()};


    ChoiceBox chb [] = {new ChoiceBox(), new ChoiceBox()};


    ScrollPane sp = new ScrollPane();

    VBox v = new VBox();

    HBox uvodh = new HBox();

    BorderPane bp = new BorderPane(sg);

    Label lb = new Label("ArchitectureAPP");

    Scene uvodscene = new Scene(uvodh, WIDTH, HEIGHT);
    Scene hlscene = new Scene(bp, WIDTH, HEIGHT);
    //Scene datascene =  new Scene(lb);


    @Override
    public void start(Stage stage) throws NullPointerException {


        materials[0].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/mramor.png")));
        materials[1].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/orechdrevo-3.jpg")));
        materials[2].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));
        materials[3].setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/Trava.jpg")));

        chb[0].getItems().add("Mramor");
        chb[0].getItems().add("Ořechové dřeevo");
        chb[0].getItems().add("Bílá barva");
        chb[0].getItems().add("Trava");


        //Priprava linie
        Box lineX = new Box(100, 2, 2);
        PhongMaterial material1 = new PhongMaterial();
        material1.setDiffuseColor(Color.RED);
        lineX.setMaterial(material1);
        lineX.translateXProperty().set(50);
        
        Box lineY = new Box(2, 100, 2);
        PhongMaterial material2 = new PhongMaterial();
        material2.setDiffuseColor(Color.YELLOW);
        lineY.setMaterial(material2);
        lineY.translateYProperty().set(50);

        Box lineZ = new Box(2,2,100);
        PhongMaterial material3 = new PhongMaterial();
        material3.setDiffuseColor(Color.BLUE);
        lineZ.setMaterial(material3);
        lineZ.translateZProperty().set(50);

        sg.getChildren().addAll(lineX, lineY, lineZ);

        l.setTextFill(Color.WHITE);
        l.setFont(new Font("Arial", 15));



        //Buttony tvoření
        bt[0].setText("Box");
        bt[0].setPrefSize(200,30);
        bt[0].relocate(0,0);
        bt[0].setOnAction(actionEvent -> {

            TextField [] tf = {new TextField(), new TextField(), new TextField(), new TextField(), new TextField(), new TextField(), new TextField()};
            tf[0].setPromptText("Zadejte hranu X...");
            tf[1].setPromptText("Zadejte hranu Y...");
            tf[2].setPromptText("Zadejte hranu Z...");
            tf[3].setPromptText("Zadejte souřadnici X...");
            tf[4].setPromptText("Zadejte souřadnici Y...");
            tf[5].setPromptText("Zadejte souřadnici Z...");
            tf[6].setPromptText("Zadejte název objektu...");
            v.getChildren().addAll(tf[0], tf[1], tf[2], tf[3], tf[4], tf[5], tf[6]);

            bp.setOnKeyPressed((event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        x2 = Integer.parseInt(tf[0].getText());//sc.nextInt();
                        y2 = Integer.parseInt(tf[1].getText());//sc.nextInt();
                        z2 = Integer.parseInt(tf[2].getText());//sc.nextInt();
                        x22 = Integer.parseInt(tf[3].getText());//sc.nextInt();
                        y22 = Integer.parseInt(tf[4].getText());//sc.nextInt();
                        z22 = Integer.parseInt(tf[5].getText());//sc.nextInt();
                        s = String.valueOf(tf[6].getText());//sc.next();

                        v.getChildren().remove(tf[0]);
                        v.getChildren().remove(tf[1]);
                        v.getChildren().remove(tf[2]);
                        v.getChildren().remove(tf[3]);
                        v.getChildren().remove(tf[4]);
                        v.getChildren().remove(tf[5]);
                        v.getChildren().remove(tf[6]);

                        Box b = prepareBox();
                        sg.getChildren().addAll(b);
                    } catch (NumberFormatException e) {
                        l.setText("Zadejte číslo do textového řádku");
                    }
                }
            });
        });

        bt[1].setText("Cube");
        bt[1].setPrefSize(200,30);
        bt[1].relocate(0,100);
        bt[1].setOnAction(actionEvent -> {

            TextField [] tf = {new TextField(), new TextField(), new TextField(), new TextField(), new TextField()};
            tf[0].setPromptText("Zadejte hranu A...");
            tf[1].setPromptText("Zadejte souřadnici X...");
            tf[2].setPromptText("Zadejte souřadnici Y...");
            tf[3].setPromptText("Zadejte souřadnici Z...");
            tf[4].setPromptText("Zadejte název objektu...");
            v.getChildren().addAll(tf[0], tf[1], tf[2], tf[3], tf[4]);

            bp.setOnKeyPressed((event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        x3 = Integer.parseInt(tf[0].getText());
                        x33 = Integer.parseInt(tf[1].getText());
                        y33 = Integer.parseInt(tf[2].getText());
                        z33 = Integer.parseInt(tf[3].getText());
                        s = String.valueOf(tf[4].getText());

                        v.getChildren().remove(tf[0]);
                        v.getChildren().remove(tf[1]);
                        v.getChildren().remove(tf[2]);
                        v.getChildren().remove(tf[3]);
                        v.getChildren().remove(tf[4]);

                        Box c = prepareCube();
                        sg.getChildren().addAll(c);
                    } catch (NumberFormatException e) {
                        l.setText("Zadejte číslo do textového řádku");
                    }
                }
            });
        });

        bt[2].setText("Sphere");
        bt[2].setPrefSize(200,30);
        bt[2].relocate(0,200);
        bt[2].setOnAction(actionEvent -> {

            TextField [] tf = {new TextField(), new TextField(), new TextField(), new TextField(), new TextField()};
            tf[0].setPromptText("Zadejte poloměr r...");
            tf[1].setPromptText("Zadejte souřadnici X...");
            tf[2].setPromptText("Zadejte souřadnici Y...");
            tf[3].setPromptText("Zadejte souřadnici Z...");
            tf[4].setPromptText("Zadejte název objektu...");
            v.getChildren().addAll(tf[0], tf[1], tf[2], tf[3], tf[4]);

            bp.setOnKeyPressed((event) -> {
                if (event.getCode() == KeyCode.ENTER) {

                    try {
                        v1 = Integer.parseInt(tf[0].getText());
                        x11 = Integer.parseInt(tf[1].getText());
                        y11 = Integer.parseInt(tf[2].getText());
                        z11 = Integer.parseInt(tf[3].getText());
                        s = String.valueOf(tf[4].getText());

                        v.getChildren().remove(tf[0]);
                        v.getChildren().remove(tf[1]);
                        v.getChildren().remove(tf[2]);
                        v.getChildren().remove(tf[3]);
                        v.getChildren().remove(tf[4]);

                        Sphere s = prepareSphere();
                        sg.getChildren().addAll(s);
                    } catch (NumberFormatException e) {
                        l.setText("Zadejte číslo do textového řádku");
                    }
                }
            });
        });

        bt[3].setText("Cylinder");
        bt[3].setPrefSize(200,30);
        bt[3].relocate(0,300);
        bt[3].setOnAction(actionEvent -> {

            TextField [] tf = {new TextField(), new TextField(), new TextField(), new TextField(), new TextField(), new TextField()};
            tf[0].setPromptText("Zadejte poloměr r...");
            tf[1].setPromptText("Zadejte délku d...");
            tf[2].setPromptText("Zadejte souřadnici X...");
            tf[3].setPromptText("Zadejte souřadnici Y...");
            tf[4].setPromptText("Zadejte souřadnici Z...");
            tf[5].setPromptText("Zadejte název objektu...");
            v.getChildren().addAll(tf[0], tf[1], tf[2], tf[3], tf[4], tf[5]);


            bp.setOnKeyPressed((event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        x4 = Integer.parseInt(tf[0].getText());
                        y4 = Integer.parseInt(tf[1].getText());
                        x44 = Integer.parseInt(tf[2].getText());
                        y44 = Integer.parseInt(tf[3].getText());
                        z44 = Integer.parseInt(tf[4].getText());
                        s = String.valueOf(tf[5].getText());

                        v.getChildren().remove(tf[0]);
                        v.getChildren().remove(tf[1]);
                        v.getChildren().remove(tf[2]);
                        v.getChildren().remove(tf[3]);
                        v.getChildren().remove(tf[4]);
                        v.getChildren().remove(tf[5]);

                        Cylinder c = prepareCylinder();
                        sg.getChildren().addAll(c);

                    } catch (NumberFormatException e) {
                        l.setText("Zadejte číslo do textového řádku");
                    }
                }
            });
        });


        //Buttony posunutí
        bt[4].setText("X +");
        bt[4].setPrefSize(60, 60);
        bt[4].relocate(0, 700);
        bt[4].setOnAction(actionEvent -> {
            if (cisla[vybrano] == 1) {
                pomocnahodnota = Sphere;
                for (int i = 0; i <= Sphere; i++) {
                    if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                x11 += 10;
                spe.get(Sphere - pomocnahodnota).translateXProperty().set(x11);
            }

            else if (cisla[vybrano] == 2) {
                pomocnahodnota = Box;
                for (int i = 0; i <= Box; i++) {
                    if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }

                x22 += 10;
                b1.get(Box - pomocnahodnota).translateXProperty().set(x22);
            }

            else if (cisla[vybrano] == 3) {
                pomocnahodnota = Cube;
                for (int i = 0; i <= Cube; i++) {
                    if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                x33 += 10;
                b2.get(Cube - pomocnahodnota).translateXProperty().set(x33);
            }

            else if (cisla[vybrano] == 4) {
                pomocnahodnota = Cylindr;
                for (int i = 0; i <= Cylindr; i++) {
                    if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                x44 += 10;
                cil.get(Cylindr - pomocnahodnota).translateXProperty().set(x44);
            }
        });

        bt[5].setText("X -");
        bt[5].setPrefSize(60, 60);
        bt[5].relocate(0, 760);
        bt[5].setOnAction(actionEvent -> {
            if (cisla[vybrano] == 1) {
                pomocnahodnota = Sphere;
                for (int i = 0; i <= Sphere; i++) {
                    if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                x11 -= 10;
                spe.get(Sphere - pomocnahodnota).translateXProperty().set(x11);
            }

            else if (cisla[vybrano] == 2) {
                pomocnahodnota = Box;
                for (int i = 0; i <= Box; i++) {
                    if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }

                x22 -= 10;
                b1.get(Box - pomocnahodnota).translateXProperty().set(x22);
            }

            else if (cisla[vybrano] == 3) {
                pomocnahodnota = Cube;
                for (int i = 0; i <= Cube; i++) {
                    if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                x33 -= 10;
                b2.get(Cube - pomocnahodnota).translateXProperty().set(x33);
            }

            else if (cisla[vybrano] == 4) {
                pomocnahodnota = Cylindr;
                for (int i = 0; i <= Cylindr; i++) {
                    if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                x44 -= 10;
                cil.get(Cylindr - pomocnahodnota).translateXProperty().set(x44);
            }
        });

        bt[6].setText("Y +");
        bt[6].setPrefSize(60, 60);
        bt[6].relocate(60, 700);
        bt[6].setOnAction(actionEvent -> {
            if (cisla[vybrano] == 1) {
                pomocnahodnota = Sphere;
                for (int i = 0; i <= Sphere; i++) {
                    if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                y11 += 10;
                spe.get(Sphere - pomocnahodnota).translateYProperty().set(y11);
            }

            else if (cisla[vybrano] == 2) {
                pomocnahodnota = Box;
                for (int i = 0; i <= Box; i++) {
                    if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }

                y22 += 10;
                b1.get(Box - pomocnahodnota).translateYProperty().set(y22);
            }

            else if (cisla[vybrano] == 3) {
                pomocnahodnota = Cube;
                for (int i = 0; i <= Cube; i++) {
                    if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                y33 += 10;
                b2.get(Cube - pomocnahodnota).translateYProperty().set(y33);
            }

            else if (cisla[vybrano] == 4) {
                pomocnahodnota = Cylindr;
                for (int i = 0; i <= Cylindr; i++) {
                    if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                y44 += 10;
                cil.get(Cylindr - pomocnahodnota).translateYProperty().set(y44);
            }
        });

        bt[7].setText("Y -");
        bt[7].setPrefSize(60, 60);
        bt[7].relocate(60, 760);
        bt[7].setOnAction(actionEvent -> {
            if (cisla[vybrano] == 1) {
                pomocnahodnota = Sphere;
                for (int i = 0; i <= Sphere; i++) {
                    if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                y11 -= 10;
                spe.get(Sphere - pomocnahodnota).translateYProperty().set(y11);
            }

            else if (cisla[vybrano] == 2) {
                pomocnahodnota = Box;
                for (int i = 0; i <= Box; i++) {
                    if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }

                y22 -= 10;
                b1.get(Box - pomocnahodnota).translateYProperty().set(y22);
            }

            else if (cisla[vybrano] == 3) {
                pomocnahodnota = Cube;
                for (int i = 0; i <= Cube; i++) {
                    if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                y33 -= 10;
                b2.get(Cube - pomocnahodnota).translateYProperty().set(y33);
            }

            else if (cisla[vybrano] == 4) {
                pomocnahodnota = Cylindr;
                for (int i = 0; i <= Cylindr; i++) {
                    if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                y44 -= 10;
                cil.get(Cylindr - pomocnahodnota).translateYProperty().set(y44);
            }
        });

        bt[8].setText("Z +");
        bt[8].setPrefSize(60, 60);
        bt[8].relocate(120, 700);
        bt[8].setOnAction(actionEvent -> {
            if (cisla[vybrano] == 1) {
                pomocnahodnota = Sphere;
                for (int i = 0; i <= Sphere; i++) {
                    if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                z11 += 10;
                spe.get(Sphere - pomocnahodnota).translateZProperty().set(z11);
            }

            else if (cisla[vybrano] == 2) {
                pomocnahodnota = Box;
                for (int i = 0; i <= Box; i++) {
                    if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }

                z22 += 10;
                b1.get(Box - pomocnahodnota).translateZProperty().set(z22);
            }

            else if (cisla[vybrano] == 3) {
                pomocnahodnota = Cube;
                for (int i = 0; i <= Cube; i++) {
                    if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                z33 += 10;
                b2.get(Cube - pomocnahodnota).translateZProperty().set(z33);
            }

            else if (cisla[vybrano] == 4) {
                pomocnahodnota = Cylindr;
                for (int i = 0; i <= Cylindr; i++) {
                    if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                z44 += 10;
                cil.get(Cylindr - pomocnahodnota).translateZProperty().set(z44);
            }
        });

        bt[9].setText("Z -");
        bt[9].setPrefSize(60, 60);
        bt[9].relocate(120, 760);
        bt[9].setOnAction(actionEvent -> {
            if (cisla[vybrano] == 1) {
                pomocnahodnota = Sphere;
                for (int i = 0; i <= Sphere; i++) {
                    if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                z11 -= 10;
                spe.get(Sphere - pomocnahodnota).translateZProperty().set(z11);
            }

            else if (cisla[vybrano] == 2) {
                pomocnahodnota = Box;
                for (int i = 0; i <= Box; i++) {
                    if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }

                z22 -= 10;
                b1.get(Box - pomocnahodnota).translateZProperty().set(z22);
            }

            else if (cisla[vybrano] == 3) {
                pomocnahodnota = Cube;
                for (int i = 0; i <= Cube; i++) {
                    if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                z33 -= 10;
                b2.get(Cube - pomocnahodnota).translateZProperty().set(z33);
            }

            else if (cisla[vybrano] == 4) {
                pomocnahodnota = Cylindr;
                for (int i = 0; i <= Cylindr; i++) {
                    if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                        break;
                    } else {
                        pomocnahodnota--;
                    }
                }
                z44 -= 10;
                cil.get(Cylindr - pomocnahodnota).translateZProperty().set(z44);
            }
        });


        sp.setPrefSize(200,1000);
        v.setPrefSize(300, 1500);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setContent(v);



        v.getChildren().addAll(bt[0], bt[1], bt[2], bt[3], bt[4], bt[5], bt[6], bt[7], bt[8], bt[9], chb[0], chb[1], l);

        bp.setCenter(sg);
        bp.setLeft(sp);

        v.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), Insets.EMPTY)));
        sp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0), Insets.EMPTY)));
        bp.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(0), Insets.EMPTY)));


        hlscene.setFill(Color.SILVER);

        //pohyb myší - zoom
        initMouseControl( sg , hlscene, stage, bp);


/* priprava na case
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
        //vybrani objektu
        chb[1].setPrefSize(200,30);
        chb[1].relocate(0,400);
        chb[1].getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    vybrano = new_val.intValue();
                    System.out.println(vybrano);
                });

        //Material na objekt
        chb[0].setPrefSize(200,30);
        chb[0].relocate(0,500);
        chb[0].getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    if (cisla[vybrano] == 1) {
                        pomocnahodnota = Sphere;
                        for (int i = 0; i <= Sphere; i++) {
                            if (zaznamSphere[k - (Cylindr + Cube + Box)] == zaznamSphere[i]){
                                break;
                            } else {
                                pomocnahodnota--;
                            }
                        }
                        spe.get(Sphere - pomocnahodnota).setMaterial(materials[new_val.intValue()]);
                    } else if (cisla[vybrano] == 2) {
                        pomocnahodnota = Box;
                        for (int i = 0; i <= Box; i++) {
                            if (zaznamBox[k - (Cylindr + Cube + Sphere)] == zaznamBox[i]){
                                break;
                            } else {
                                pomocnahodnota--;
                            }
                        }
                        b1.get(Box - pomocnahodnota).setMaterial(materials[new_val.intValue()]);
                    } else if (cisla[vybrano] == 3) {
                        pomocnahodnota = Cube;
                        for (int i = 0; i <= Cube; i++) {
                            if (zaznamCube[k - (Cylindr + Box + Sphere)] == zaznamCube[i]){
                                break;
                            } else {
                                pomocnahodnota--;
                            }
                        }
                        b2.get(Cube - pomocnahodnota).setMaterial(materials[new_val.intValue()]);
                    }else if (cisla[vybrano] == 4) {
                        pomocnahodnota = Cylindr;
                        for (int i = 0; i <= Cylindr; i++) {
                            if (zaznamCylindr[k - (Cube + Box + Sphere)] == zaznamCylindr[i]){
                                break;
                            } else {
                                pomocnahodnota--;
                            }
                        }
                        cil.get(Cylindr - pomocnahodnota).setMaterial(materials[new_val.intValue()]);
                    }
                });

        stage.setTitle("ArchitectureAPP");
        stage.getIcons().add(new Image(String.valueOf(logourl)));
        stage.setScene(hlscene);
        stage.show();

    }

    //predpriprava sphere
    private Sphere prepareSphere() {
        Sphere++;
        System.out.println("PIS");

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));
        spe.add(new Sphere(v1));
        spe.get(Sphere-1).setMaterial(material);

        spe.get(Sphere-1).translateXProperty().set(x11);
        spe.get(Sphere-1).translateYProperty().set(y11);
        spe.get(Sphere-1).translateZProperty().set(z11);

        chb[1].getItems().add(s);


        cisla[k] = 1;
        k++;
        zaznamSphere[Sphere - 1] = pocetobjektu;
        pocetobjektu++;

        return spe.get(Sphere-1);
    }

    //predpriprava box
    private Box prepareBox() {
        Box++;
        System.out.println("PIS");


        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));


        b1.add(new Box(x2,y2,z2));
        b1.get(Box-1).setMaterial(material);

        b1.get(Box-1).translateXProperty().set(x22);
        b1.get(Box-1).translateYProperty().set(y22);
        b1.get(Box-1).translateZProperty().set(z22);


        chb[1].getItems().add(s);


        cisla[k] = 2;
        k++;
        zaznamBox[Box - 1] = pocetobjektu;
        pocetobjektu++;

        return b1.get(Box-1);
    }

    //predpriprava cube
    private Box prepareCube() {
        Cube++;
        System.out.println("PIS");


        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));


        b2.add(new Box(x3,x3,x3));
        b2.get(Cube-1).setMaterial(material);

        b2.get(Cube-1).translateXProperty().set(x33);
        b2.get(Cube-1).translateYProperty().set(y33);
        b2.get(Cube-1).translateZProperty().set(z33);


        chb[1].getItems().add(s);

        cisla[k] = 3;
        k++;
        zaznamCube[Cube - 1] = pocetobjektu;
        pocetobjektu++;

        return b2.get(Cube-1);
    }

    //predpriprava cylindr
    public Cylinder prepareCylinder() {
        Cylindr++;
        System.out.println("PIS");

        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.MIDNIGHTBLUE);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/example/demo2/ffffff.png")));


        cil.add(new Cylinder(x4,y4, 100));
        cil.get(Cylindr-1).setMaterial(material);

        cil.get(Cylindr-1).translateXProperty().set(x44);
        cil.get(Cylindr-1).translateYProperty().set(y44);
        cil.get(Cylindr-1).translateZProperty().set(z44);


        chb[1].getItems().add(s);

        cisla[k] = 4;
        k++;
        zaznamCylindr[Cylindr - 1] = pocetobjektu;
        pocetobjektu++;

        return cil.get(Cylindr-1);

    }


    //rotace mysi
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

    //hlavni metoda
    public static void main(String[] args) {
        launch();
    }
}