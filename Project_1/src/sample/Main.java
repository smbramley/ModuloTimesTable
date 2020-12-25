/*
* Shane Bramley
* 9/3/2020
* CS351 - Project 1: Modulo Times Tables
 * This program will show a circular times table and create
 * the image and change the colors as well
*
* Main.java will run the modulo times table application
* to run the program click the play button.
*
* How to:
*  - Running the program, you can select your number of points and multiple by Slider or Text Box then hit draw.
*  - To run animator, after drawing a starting point set increment then hit run and it will run animation state.
*  - To explore my favorite circular times tables click the explore button
* */

package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends Application {

    /**
     * main is a default java method which will run the project 1 program.
     * @param args
     */
    public static void main(String[] args) { launch(args); }

    private GridPane grid = new GridPane();
    private GridPane grid2 = new GridPane();

    //Create Labels
    private Label numOfPointsLabel = new Label("Number of Points:");
    private Label multLabel  = new Label("Times Table Number:");
    private Label selectParamSecLabel = new Label("Select Parameters Section:");
    private Label incrementSectionLabel = new Label("Increment Section:");
    private Label incrementByLabel = new Label("Increment By:");
    private Label delayByLabel = new Label("Speed(seconds):");

    //Create TextFields
    private TextField numOfPointsTxtBox = new TextField();
    private TextField timesTableTxtBox = new TextField();

    //Create buttons
    private Button draw = new Button("Draw");
    private Button run = new Button("Run");
    private Button pause = new Button("Pause");
    private Button explore = new Button("Explore");

    //Panels needed for structure
    private Pane pane = new Pane();
    private BorderPane borderPane= new BorderPane();

    //Create sliders
    private Slider numOfPointsSlider = new Slider(0,360,0);
    private Slider multipleSlider = new Slider(0,360,0);
    private Slider incrementBySlider = new Slider(0,5,0);
    private Slider delayBySlider = new Slider(0,5,0);

    //Set up variables for the circle
    private double totalPoints;
    private double multipleNum;
    private int x = 350;
    private int y = 250;
    private int radius = 250;

    private Circle c = new Circle(x,y,radius);
    private List<Double> numberOfPointsPrefs = new ArrayList<>();
    private List<Double> multiplePrefs = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * start is a method being called from the inherited class.
     * This method will start the JavaFX panel and add all of the parts to it.
     * @param stage
     */
    @Override
    public void start(Stage stage) {

        //Favorite points on the circle
        numberOfPointsPrefs.add(360.00);
        numberOfPointsPrefs.add(360.00);
        numberOfPointsPrefs.add(100.00);

        //Favorite multiples
        multiplePrefs.add(3.00);
        multiplePrefs.add(.50);
        multiplePrefs.add(1.50);

        //Give the panel a title that has meaning to it
        stage.setTitle("Project 1: Modulo Times Table, Shane Bramley");
        c.setStroke(Color.BLACK);
        numOfPointsSlider.setShowTickMarks(true);
        numOfPointsSlider.setShowTickLabels(true);
        numOfPointsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                numOfPointsTxtBox.setText(String.valueOf(df.format(newValue)));
            }
        });

        multipleSlider.setShowTickMarks(true);
        multipleSlider.setShowTickLabels(true);
        multipleSlider.setMajorTickUnit(80);
        multipleSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                timesTableTxtBox.setText(String.valueOf(df.format(newValue)));
            }
        });

        incrementBySlider.setShowTickLabels(true);
        incrementBySlider.setShowTickMarks(true);
        incrementBySlider.setMajorTickUnit(80);
        incrementBySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue)
            {
                incrementByLabel.setText("Increment By:"+df.format(newValue));
            }
        });

        delayBySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue)
            {
                delayByLabel.setText("Speed(seconds):"+df.format(newValue));
            }
        });

        delayBySlider.setShowTickMarks(true);
        delayBySlider.setShowTickLabels(true);
        delayBySlider.setMinorTickCount(1);

        //Add Labels to grids
        grid.add(selectParamSecLabel,0,0);
        grid.add(numOfPointsLabel,0,1);
        grid.add(multLabel,0,2);
        grid2.add(incrementSectionLabel, 0,0);
        grid2.add(incrementByLabel, 0,1);
        grid2.add(delayByLabel, 0,2);


        //Add text box to grids
        grid.add(numOfPointsTxtBox,1,1);
        grid.add(timesTableTxtBox,1,2);

        //Add buttons to grids
        grid.add(draw,0,4);
        grid.add(explore,2,4);
        grid2.add(run,0,3);
        grid2.add(pause,1,3);

        //Add sliders to grids
        grid.add(numOfPointsSlider,2,1);
        grid.add(multipleSlider,2,2);
        grid2.add(incrementBySlider,1,1);
        grid2.add(delayBySlider, 1,2);


        timesTableTxtBox.setPrefColumnCount(6);
        numOfPointsTxtBox.setPrefColumnCount(6);


        grid.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER);

        AnimationTimer timer = new AnimationTimer(){
            private long lastUpdate = 0;
            @Override
            public void handle(long now){
                if(now - lastUpdate >= 2_000_000_000/delayBySlider.getValue()){
                    if(totalPoints != 0 && multipleNum < 360) {
                        multipleNum += incrementBySlider.getValue();
                        timesTableTxtBox.setText(String.valueOf(multipleNum));
                        multipleSlider.setValue(multipleNum);
                        buildCircle(totalPoints, multipleNum, radius, x, y);
                    }
                    lastUpdate = now;
                }
            }
        };
        explore.setOnAction(event ->{
            if(explore.getText().equals("Explore")) {
                timesTableTxtBox.setText(String.valueOf(multiplePrefs.get(0)));
                multipleSlider.setValue(multiplePrefs.get(0));
                numOfPointsTxtBox.setText(String.valueOf(numberOfPointsPrefs.get(0)));
                numOfPointsSlider.setValue(numberOfPointsPrefs.get(0));
                buildCircle(numberOfPointsPrefs.get(0), multiplePrefs.get(0), radius, x, y);
                explore.setText("Explore 1");
            }
            else if(explore.getText().equals("Explore 1")){
                timesTableTxtBox.setText(String.valueOf(multiplePrefs.get(1)));
                multipleSlider.setValue(multiplePrefs.get(1));
                numOfPointsTxtBox.setText(String.valueOf(numberOfPointsPrefs.get(1)));
                numOfPointsSlider.setValue(numberOfPointsPrefs.get(1));
                buildCircle(numberOfPointsPrefs.get(1), multiplePrefs.get(1), radius, x, y);
                explore.setText("Explore 2");
            }
            else if(explore.getText().equals("Explore 2")){
                timesTableTxtBox.setText(String.valueOf(multiplePrefs.get(2)));
                multipleSlider.setValue(multiplePrefs.get(2));
                numOfPointsTxtBox.setText(String.valueOf(numberOfPointsPrefs.get(2)));
                numOfPointsSlider.setValue(numberOfPointsPrefs.get(2));
                buildCircle(numberOfPointsPrefs.get(2), multiplePrefs.get(2), radius, x, y);
                explore.setText("Explore 3");
            }
            else{
                timesTableTxtBox.setText(String.valueOf(0));
                multipleSlider.setValue(0);
                numOfPointsTxtBox.setText(String.valueOf(0));
                numOfPointsSlider.setValue(0);
                buildCircle(0, 0, radius, x, y);
                explore.setText("Explore");
            }
        });
        run.setOnAction(event ->{
            timer.start();
        });
        pause.setOnAction(event ->{
            timer.stop();
        });

        draw.setOnAction(event -> {
            totalPoints = Double.parseDouble(numOfPointsTxtBox.getText());
            multipleNum = Double.parseDouble(timesTableTxtBox.getText());
            buildCircle(totalPoints,multipleNum,radius,x,y);
        });

        borderPane.setCenter(pane);
        borderPane.setTop(grid);
        borderPane.setBottom(grid2);

        Scene scene = new Scene(borderPane,700,750);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * buildCircle this method will build the circular
     * times table complete with the variables from the user
     * @param totalPoints this variable is the amount of points around the circle
     * @param multipleNumber this variable is the multiplication variable
     * @param r this variable is the radius of the circle
     * @param x this variable is the x location on the circle
     * @param y this variable is the y location on the circle
     */
    public void buildCircle(double totalPoints, double multipleNumber, int r, int x, int y){
        pane.getChildren().clear();
        double red,green,blue;
        Random rand = new Random();
        red = rand.nextDouble();
        green = rand.nextDouble();
        blue = rand.nextDouble();
        Color color = new Color(red,green,blue,1);

        /*Logic: Creation of the lines requires the
          location of point 1 and point 2 then
          Stroking between these two points*/
        for (int i = 0; i < totalPoints; i++) {
            Line line = new Line(
                    //Build Point 1
                    (x+(Math.cos(2*Math.PI*i/totalPoints)*r)),
                    (y+(Math.sin(2*Math.PI*i/totalPoints)*r)),

                    //Build Point 2
                    (x+(Math.cos(2*Math.PI*multipleNumber*i/totalPoints)*r)),
                    (y+(Math.sin(2*Math.PI*multipleNumber*i/totalPoints)*r))
            );
            line.setStroke(color);
            pane.getChildren().addAll(line);
        }
        c.setStroke(color);
        c.setStrokeWidth(1);
        //Need to set fill to null so the circle is not filled at the end.
        c.setFill(null);
        pane.getChildren().addAll(c);
    }
}