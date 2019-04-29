package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    static ArrayList<Integer[]> undo = new ArrayList<>();    //structure to store my moves  for undo purposes
    static int windowWidth = 1100;
    static int windowHeight = 700;

    static int noRows = 0;
    static int noColumns = 0;

    static GridPane grid = new GridPane();  //will hold the environment UI

    static GridPane mainGrid = new GridPane();  //will hold everything, grid, and the control buttons
    static int[] startCordinates = new int[2];  //where the start is , 0 is row index and 1 is column index
    static int[] currentCordinates = new int[2];  //where the roadrunner is , 0 is row index and 1 is column index
    static int[] prevGlobal = new int[2]; //variable to help while redoing
    static ArrayList<Integer> prevUndoforRedo = new ArrayList<Integer>(); //variable to be used when redoing after an undo

    static boolean setNewStart=false;

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


    public static ImageView createImage(String imagePath) throws FileNotFoundException {


        String path = imagePath;
        Image image = new Image(new FileInputStream(path));

        ImageView imageView = new ImageView(image);  //creating image view

        imageView.setFitHeight(600 / noRows);  //setting the fit height and width of the image view
        imageView.setFitWidth(900 / noColumns);

        imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, imageClickHandler);

        imageView.setPreserveRatio(true);
        return imageView;
    }

    static EventHandler<MouseEvent> imageClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            ImageView source = ( ImageView) e.getSource() ;
            int row=GridPane.getRowIndex(source);
            int col=GridPane.getColumnIndex(source);
            System.out.println("Row: "+row+"\nCol: "+ col);
            if(setNewStart) {
                setNewStart(row, col);
            }
        }
    };

    private static void setNewStart(int row,int col) {
        try {
        matrix[startCordinates[0]][startCordinates[1]]=matrix[row][col];
        String imagePath = img.get(matrix[startCordinates[0]][startCordinates[1]]);
        grid.add(createImage(imagePath), startCordinates[1],startCordinates[0]);
        startCordinates[0]=row;
        startCordinates[1]=col;
        matrix[row][col]=8;
        imagePath = img.get(matrix[row][col]);
        grid.add(createImage(imagePath), col, row);
        //createEnv();
            setNewStart=false;  //making it false so that you will have to click the set new start button for it to work

        } catch (FileNotFoundException e) {
            System.out.println("something wrong happened");
            e.printStackTrace();
        }
    }


    //function that will return the grid with the images of the environment
    public static GridPane createEnv() throws FileNotFoundException {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setGridLinesVisible(true);
        populateImgGrid();
        return grid;
    }

    public static void populateImgGrid() throws FileNotFoundException {  //function that populates the grid with the images at the start or reset
        System.out.println("No rows:"+noRows);
        for (int i = 0; i < noRows; i++) { //for every row
            for (int j = 0; j < noColumns; j++) { //for every colum
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
        mainGrid.add(getControlGrid(), 0, 0);

        Text preScore = new Text("Your score: ");
        scoreUI = new Text(String.valueOf(score));
        scoreArea = new HBox();
        gameStateUI = new Text();
        scoreArea.getChildren().addAll(preScore, scoreUI, gameStateUI);
        mainGrid.add(scoreArea, 1, 2, 1, 1);

        return mainGrid;
    }
}

