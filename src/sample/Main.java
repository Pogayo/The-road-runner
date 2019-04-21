package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import javafx.event.Event;

import java.io.*;
import java.io.IOException;
import java.util.*;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static sample.Move.*;  //getting everything from Move
import static sample.GamePlay.*; //getting everything from Gameplay
import static sample.ReadFile.*;
import static sample.Controls.*;


public class Main extends Application {
    static ArrayList<Integer[]> undo=new ArrayList<>();    //structure to store my moves  for undo purposes
    static int windowWidth = 1000;
    static int windowHeight = 600;

    static int noRows = 0;
    static int noColumns = 0;

    static GridPane grid = new GridPane();  //will hold the environment UI

    static GridPane mainGrid = new GridPane();  //will hold everything, grid, and the control buttons
    static int[] startCordinates = new int[2];  //where the start is , 0 is row index and 1 is column index
    static int[] currentCordinates = new int[2];  //where the roadrunner is , 0 is row index and 1 is column index
    static int[] prevGlobal=new int[2]; //variable to help while redoing
    static ArrayList<Integer> prevUndoforRedo=new ArrayList<Integer>(); //variable to be used when redoing after an undo

    static boolean live = false;   //variable that tracks the state of the game
    static boolean enable8 = false; // will help in toggling from one mode to another

    static int score = 0;
    static Text scoreUI;
    static HBox scoreArea;
    static Text gameStateUI;

    static int[] points = {-1, 0, -2, -4, -8, 1, 5}; //points awarded for each road/surface....ordered according to their indexes



    @Override
    public void start(Stage primaryStage) throws Exception {
        populateImg();
        readFile();
        primaryStage.setTitle("Road Runner");


        Scene scene = new Scene(startGame(), windowWidth, windowHeight);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                try {
                    handleKeys(ke.getCode());
                } catch (Exception e) {
                    System.out.println("We could not update the grid");
                }
            }

        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void readFile() { // function to read the file
        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;
            sCurrentLine = br.readLine(); //reading first line
            String[] matrixDescription = sCurrentLine.split(" ");
            noRows = Integer.parseInt(matrixDescription[0]);
            noColumns = Integer.parseInt(matrixDescription[1]);
            System.out.println(noRows);
            System.out.println(noColumns);

            //making the 2d array...
            matrix = new int[noRows][noColumns];
            visited = new boolean[noRows][noColumns];
            int rowCount = 0;

            while ((sCurrentLine = br.readLine()) != null) {

                for (int i = 0; i < sCurrentLine.length(); i++) {
                    matrix[rowCount][i] = Character.getNumericValue(sCurrentLine.charAt(i));
                    visited[rowCount][i] = false;

                    if (matrix[rowCount][i] == 8) {
                        startCordinates[0] = rowCount;
                        startCordinates[1] = i;
                        prevGlobal[0]=startCordinates[0];
                        prevGlobal[1]=startCordinates[1];

                    }
                }
                rowCount++; //incrementing rowcount
            }
            br.close();// closing the file

            createEnv();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public static HBox createImage(String imagePath) throws FileNotFoundException {


        String path = imagePath;
        Image image = new Image(new FileInputStream(path));
        //creating image view
        ImageView imageView = new ImageView(image);
        //setting the fit height and width of the image view
        imageView.setFitHeight(windowWidth / noRows - 50);
        imageView.setFitWidth(windowHeight / noColumns - 50);
        imageView.getStyleClass().add("imageView");
        HBox image_container = new HBox();

        String style_inner = "-fx-border-color: black;" + "-fx-border-width: 2;" + "-fx-border-style: solid;";

        image_container.setStyle(style_inner);
        image_container.getChildren().add(imageView);


        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);
        return image_container;
    }


    //function that will return the grid with the images of the environment
    public static GridPane createEnv() throws FileNotFoundException {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(1);
        grid.setVgap(1);
        populateImgGrid();
        return grid;
    }
    public static void populateImgGrid() throws FileNotFoundException{  //function that populates the grid with the images at the start or reset
        for (int i = 0; i < noRows; i++) { //for every row
            for (int j = 0; j < noRows; j++) { //for every colum
                String imagePath = img.get(matrix[i][j]);
                grid.add(createImage(imagePath), j, i);
            }
        }
    }

    public GridPane startGame() throws IOException {

        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        mainGrid.add(grid, 1, 0);
        mainGrid.add(getControlGrid(),0,0);

        Text preScore = new Text("Your score: ");
        scoreUI = new Text(String.valueOf(score));
        scoreArea = new HBox();
        gameStateUI = new Text();
        scoreArea.getChildren().addAll(preScore, scoreUI, gameStateUI);
        mainGrid.add(scoreArea, 0, 2, 1, 1);

        return mainGrid;
    }



    public static void handleStart(){
        try {
            if (!live) {
                if(gameOver()){
                    handleReset();  //call reset;
                }
                grid.add(createImage(img.get(7)), startCordinates[1], startCordinates[0]);
                System.arraycopy(startCordinates, 0, currentCordinates, 0, startCordinates.length);

                visited[currentCordinates[0]][currentCordinates[1]] = true;
                live = true;
            }
        } catch (Exception E) {
            System.out.println("Road runner not found");
        }
    }

   public static void handleReset(){
       try {
           populateImgGrid();
           score = 0;
           scoreUI.setText(String.valueOf(score));
           gameStateUI.setText("");

           for (int i = 0; i < noRows; i++) {
               for (int j = 0; j < noColumns; j++) {
                   visited[i][j] = false;

               }
           }
           currentCordinates[0] = startCordinates[0]; //;
           currentCordinates[1] = startCordinates[1]; //// ;

           live = false;

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

   }
}
