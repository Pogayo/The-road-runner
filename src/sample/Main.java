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
import sample.Move.*;

import static sample.Move.gameOver;
import static sample.Move.handleKeys;

public class Main extends Application {
    static int windowWidth = 1000;
    static int windowHeight = 600;

    static int noRows = 0;
    static int noColumns = 0;

    static GridPane grid = new GridPane();

    static GridPane mainGrid = new GridPane();
    static int[] startCordinates = new int[2];
    static int[] currentCordinates = new int[2];

    static boolean live = false;   //variable that tracks the state of the game
    static boolean enable8 = false; // will help in toggling from one mode to another

    static int score = 0;
    static Text scoreUI;
    static HBox scoreArea;
    static Text gameStateUI;


    static int[] points = {-1, 0, -2, -4, -8, 1, 5};

    public static FileReader fr;
    public static BufferedReader br;
    public static String FILENAME = "/home/engineersticity/IdeaProjects/programs/practice/pp-ii-the-road-runner-perez-ian/src/sample_test_input_1.txt";
    public static int[][] matrix;
    public static boolean[][] visited;
    static HashMap<Integer, String> img = new HashMap<Integer, String>();
    String prePath = "/home/engineersticity/IdeaProjects/programs/practice/pp-ii-the-road-runner-perez-ian/Image Files/";
    static HashMap<Integer, String> imgAlt = new HashMap<Integer, String>(); //hashmap holding the alternative images


    public void populateImg() {   ///roadrunner will be no 7
        String[] images = {"road", "boulder", "pothole", "explosive", "coyote", "tarred", "gold", "road_runner", "start", "goal"};
        for (int i = 0; i < images.length; i++) {
//            if (i==7){
//                img.put(7,"C:\\Users\\Student\\IdeaProjects\\RoadRunner\\src\\Image Files\\road_runner.jpg");
//            }
            img.put(i, prePath + images[i] + ".jpg");
        }
        for (int i = 0; i < 7; i++) {
            if (i == 1) {
                continue;
            }
            imgAlt.put(i, prePath + images[i] + "_alt.jpg");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        populateImg();
        readFile();
        primaryStage.setTitle("Road Runner");


        //grid.setGridLinesVisible(true);//for debug purposes

        Scene scene = new Scene(startGame(), windowWidth, windowHeight);
        //scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                try {
                    handleKeys(ke);
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


    //function that will return the grid with the images..initially before the game starts
    public static GridPane createEnv() throws FileNotFoundException {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(2);
        for (int i = 0; i < noRows; i++) { //for every row
            for (int j = 0; j < noRows; j++) { //for every colum
                String imagePath = img.get(matrix[i][j]);
                grid.add(createImage(imagePath), j, i);
            }
        }

        return grid;
    }

    public GridPane startGame() throws IOException {


        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        mainGrid.add(grid, 0, 0);

        Button start = new Button("Start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
             handleStart();
            }
        });
        mainGrid.add(start, 0, 1);

        Button enable8Btn = new Button("Enable 8 Directions");
        enable8Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (enable8) {
                    enable8 = false;
                    enable8Btn.setText("Enable 8 Directions");
                } else {
                    enable8 = true;
                    enable8Btn.setText("Disable 8 Directions");
                }
            }

        });

        mainGrid.add(enable8Btn, 1, 1);
        Text preScore = new Text("Your score: ");
        scoreUI = new Text(String.valueOf(score));
        scoreArea = new HBox();
        gameStateUI = new Text();
        scoreArea.getChildren().addAll(preScore, scoreUI, gameStateUI);
        mainGrid.add(scoreArea, 0, 2, 1, 1);

        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            handleReset();

        });

        mainGrid.add(reset, 0, 3);


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
           createEnv();
           score = 0;
           scoreUI.setText(String.valueOf(score));
           gameStateUI.setText("");

           for (int i = 0; i < noRows; i++) {
               for (int j = 0; j < noColumns; j++) {
                   visited[i][j] = false;

               }
           }
           currentCordinates[0] = startCordinates[0]; //this is where the road runner is is;
           currentCordinates[1] = startCordinates[1]; //this is where the road runner is is;

           live = false;

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

   }
}
