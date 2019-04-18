package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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

public class Main extends Application {
    static int windowWidth=1000;
    static int windowHeight=600;

    static int noRows=0;
    static int noColumns=0;

    static GridPane grid=new GridPane();

    static GridPane mainGrid=new GridPane();
    static int[] startCordinates=new int[2];
    static int[] currentCordinates=new int[2];

    static boolean live=false;   //variable that tracks the state of the game
    static boolean enable8=false; // will help in toggling from one mode to another

    public static FileReader fr;
    public static BufferedReader br;
    public static String FILENAME="C:\\Users\\Student\\IdeaProjects\\pp-ii-the-road-runner-perez-ian\\src\\sample_test_input_1.txt";
    public static int[][] matrix;
    public static boolean[][] visited;
    static HashMap<Integer, String> img=new HashMap<Integer, String>();
    String prePath="C:\\Users\\Student\\IdeaProjects\\RoadRunner\\src\\Image Files\\";
    static HashMap<Integer, String> imgAlt=new HashMap<Integer, String>(); //hashmap holding the alternative images


    public void populateImg(){   ///roadrunner will be no 7
        String[] images={"road","boulder","pothole","explosive","coyote","tarred","gold","road_runner","start","goal"};
        for(int i=0;i<images.length;i++) {
//            if (i==7){
//                img.put(7,"C:\\Users\\Student\\IdeaProjects\\RoadRunner\\src\\Image Files\\road_runner.jpg");
//            }
            img.put(i, prePath + images[i]+".jpg");
        }
        for (int i=0;i<7;i++){
            if(i==1){
                continue;
            }
            imgAlt.put(i,prePath+images[i]+"_alt.jpg");
        }
    }
    public static void handleKeys(KeyEvent ke) throws  IOException{
        //if (ke.getCode() == KeyCode.ESCAPE) {
        System.out.println("Key Pressed: " + ke.getCode());

        //}
        if(ke.getCode()==KeyCode.UP){  //I am trying to move up
            if(currentCordinates[0]>0 && matrix[currentCordinates[0]-1][currentCordinates[1]]!=1 && !visited[currentCordinates[0]-1][currentCordinates[1]] ){
                int[] prevCordinates={currentCordinates[0],currentCordinates[1]};
                visited[prevCordinates[0]][prevCordinates[1]]=true;
                currentCordinates[0]=currentCordinates[0]-1;
                updateGrid(currentCordinates,prevCordinates);
            }
        }
        if(ke.getCode()==KeyCode.DOWN){  //I am trying to move up
            if(currentCordinates[0]<=noRows-2 && matrix[currentCordinates[0]+1][currentCordinates[1]]!=1 && !visited[currentCordinates[0]+1][currentCordinates[1]]){
                int[] prevCordinates={currentCordinates[0],currentCordinates[1]};
                visited[prevCordinates[0]][prevCordinates[1]]=true;
                currentCordinates[0]=currentCordinates[0]+1;
                updateGrid(currentCordinates,prevCordinates);
            }
        }
        if(ke.getCode()==KeyCode.LEFT){  //I am trying to move up
            if(currentCordinates[1]>0 && matrix[currentCordinates[0]][currentCordinates[1]]-1!=1 && !visited[currentCordinates[0]][currentCordinates[1]-1]){
                int[] prevCordinates={currentCordinates[0],currentCordinates[1]};
                visited[prevCordinates[0]][prevCordinates[1]]=true;
                currentCordinates[1]=currentCordinates[1]-1;
                updateGrid(currentCordinates,prevCordinates);
            }
        }
        if(ke.getCode()==KeyCode.RIGHT){  //I am trying to move up
            if(currentCordinates[1]<=noColumns-2 && matrix[currentCordinates[0]][currentCordinates[1]+1]!=1 && !visited[currentCordinates[0]][currentCordinates[1]+1]){
                int[] prevCordinates={currentCordinates[0],currentCordinates[1]};
                visited[prevCordinates[0]][prevCordinates[1]]=true;
                currentCordinates[1]=currentCordinates[1]+1;
                updateGrid(currentCordinates,prevCordinates);
            }
        }
        if(enable8){
            System.out.println("i AM HERE OOO");
            move8(ke);
        }

    }
    public static void move8(KeyEvent ke) throws IOException{
        if(ke.getCode()==KeyCode.W){
            if(currentCordinates[0]>0 && currentCordinates[1]<=noColumns-2 &&matrix[currentCordinates[0]-1][currentCordinates[1]+1]!=1 && !visited[currentCordinates[0]-1][currentCordinates[1+1]] ){
                int[] prevCordinates={currentCordinates[0],currentCordinates[1]};
                visited[prevCordinates[0]][prevCordinates[1]]=true;
                currentCordinates[0]=currentCordinates[0]-1;
                currentCordinates[1]=currentCordinates[1]+1;
                updateGrid(currentCordinates,prevCordinates);
            }

        }
    }

    private static void updateGrid(int[] currentCordinates, int[] prevCordinates) throws IOException {
        int no=matrix[prevCordinates[0]][prevCordinates[1]];
        System.out.println(no);
        if(no!=8 && no !=9){
            grid.add(createImage(imgAlt.get(no)),prevCordinates[1],prevCordinates[0]);

        }
        else if(no==9){
            grid.add(createImage(img.get(9)),prevCordinates[1],prevCordinates[0]);
        }
        else if(no==8){
            grid.add(createImage(img.get(8)),prevCordinates[1],prevCordinates[0]);

        }
        grid.add(createImage(img.get(7)),currentCordinates[1],currentCordinates[0]);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        populateImg();
        readFile();
        primaryStage.setTitle("Road Runner");



        //grid.setGridLinesVisible(true);//for debug purposes

        Scene scene=new Scene(startGame(), windowWidth, windowHeight);
        //scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke){
                try{
                    handleKeys(ke);}
                catch(Exception e){
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
            String[] matrixDescription= sCurrentLine.split(" ");
            noRows=Integer.parseInt(matrixDescription[0]);
            noColumns=Integer.parseInt(matrixDescription[1]);
            System.out.println(noRows);
            System.out.println(noColumns);

            //making the 2d array...
            matrix=new int[noRows][noColumns];
            visited=new boolean[noRows][noColumns];
            int rowCount=0;

            while ((sCurrentLine = br.readLine()) != null) {

                for(int i=0;i<sCurrentLine.length();i++){
                    matrix[rowCount][i]=Character.getNumericValue(sCurrentLine.charAt(i));
                    visited[rowCount][i]=false;

                    if(matrix[rowCount][i]==8){
                        startCordinates[0]=rowCount;
                        startCordinates[1]=i;

                    }
                }
                rowCount++; //incrementing rowcount
            }
            br.close();// closing the file

            createEnv();

        } catch (IOException e) {

            e.printStackTrace();}

    }

    public static HBox createImage(String imagePath) throws FileNotFoundException{


        String path=imagePath;
        Image image = new Image(new FileInputStream(path));
        //creating image view
        ImageView imageView = new ImageView(image);
        //setting the fit height and width of the image view
        imageView.setFitHeight(windowWidth/noRows-50);
        imageView.setFitWidth(windowHeight/noColumns-50);
        imageView.getStyleClass().add("imageView");
        HBox image_container = new HBox();

        String style_inner = "-fx-border-color: black;" + "-fx-border-width: 2;" + "-fx-border-style: solid;";

        image_container .setStyle(style_inner);
        image_container .getChildren().add(imageView);


        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);
        return image_container ;
    }


    //function that will return the grid with the images..initially before the game starts
    public static GridPane createEnv() throws FileNotFoundException{
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        for(int i=0;i<noRows;i++){ //for every row
            for(int j=0;j<noRows;j++){ //for every colum
                String imagePath=img.get(matrix[i][j]);
                grid.add(createImage(imagePath),j,i);
            }
        }

        return grid;
    }
    public  GridPane startGame(){


        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        mainGrid.add(grid,0,0);

        Button start=new Button("Start");
        start.setOnAction(new EventHandler<ActionEvent>()  {
            @Override public void handle(ActionEvent e) {
                //label.setText("Accepted");
                try{
                    grid.add(createImage(img.get(7)),startCordinates[1],startCordinates[0]);
                    currentCordinates=startCordinates; //this is where the road runner is is;
                    visited[currentCordinates[0]][currentCordinates[1]]=true;
                    live=true;
                }
                catch(Exception E){
                    System.out.println("Road runner not found");
                }
            }
        });
        mainGrid.add(start,0,1);

        Button enable8Btn=new Button("Enable 8 Directions");
        enable8Btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                if(enable8){
                    enable8=false;
                    enable8Btn.setText("Enable 8 Directions");
                }
                else{
                    enable8=true;
                    enable8Btn.setText("Disable 8 Directions");
                }
            }

        });
        mainGrid.add(enable8Btn,1,1);


        return mainGrid;
    }


}
