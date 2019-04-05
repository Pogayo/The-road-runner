package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;

public class Main extends Application {
    public static FileReader fr;
    public static BufferedReader br;
    public static String FILENAME="C:\\Users\\Student\\IdeaProjects\\Perez-Ian\\pp-ii-the-road-runner-perez-ian\\src\\sample_test_input_1.txt";
    public static int[][] matrix;

    //setting variables for the images
    static String boulderpath="C:\\Users\\Student\\IdeaProjects\\Perez-Ian\\pp-ii-the-road-runner-perez-ian\\Image Files\\boulder.jpg";
    static String coyetepath="C:\\Users\\Student\\IdeaProjects\\Perez-Ian\\pp-ii-the-road-runner-perez-ian\\Image Files\\coyote.jpg";
    static String coyetealtpath="C:\\Users\\Student\\IdeaProjects\\Perez-Ian\\pp-ii-the-road-runner-perez-ian\\Image Files\\coyote_alt.jpg";





    //functions
    public static void readFile() {
        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;
            sCurrentLine = br.readLine(); //reading first line
            String[] matrixDescription= sCurrentLine.split(" ");
            int noRows=Integer.parseInt(matrixDescription[0]);
            int noColumns=Integer.parseInt(matrixDescription[1]);
            System.out.println(noRows);
            System.out.println(noColumns);

            //making the 2d array...
            matrix=new int[noRows][noColumns];
            int rowCount=0;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                for(int i=0;i<sCurrentLine.length();i++){
                    matrix[rowCount][i]=Character.getNumericValue(sCurrentLine.charAt(i));
                }
                rowCount++; //incrementing rowcount
            }
            br.close();// closing the file

            for (int[] row:matrix){
                for(int num:row){
                    System.out.print(num+" ");
                }
                System.out.println();
            }

        } catch (IOException e) {

            e.printStackTrace();}
    }

      public HBox createImage(String imagePath) throws FileNotFoundException{


        String path=imagePath;
          Image image = new Image(new FileInputStream(path));
                  //creating image view
          ImageView imageView = new ImageView(image);
          //Setting the position of the image
          //imageView.setX(50);
          //imageView.setY(25);

          //setting the fit height and width of the image view
          imageView.setFitHeight(100);
          imageView.setFitWidth(200);
          imageView.getStyleClass().add("imageView");
          HBox image_container = new HBox();

          String style_inner = "-fx-border-color: black;"

                  + "-fx-border-width: 2;"

                  + "-fx-border-style: solid;";

          image_container .setStyle(style_inner);
          image_container .getChildren().add(imageView);


          //Setting the preserve ratio of the image view
          imageView.setPreserveRatio(true);
          return image_container ;
      }

      public ButtonGroup  oneMoveButton(){
        ButtonGroup buttonGroupOne=new ButtonGroup();
         //up button
        Button up=new Button("Move up");

        Button left=new Button("Move left");

        Button down=new Button("Move down"); //down button

          Button right=new Button("Move right"); //right button


          buttonGroupOne.getElements();


        return  buttonGroupOne;
      }
    @Override
    public void start(Stage primaryStage) throws Exception {
        readFile();
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);//for debug purposes
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25, 25, 25, 25));
        //adding the images
        grid.add(createImage(boulderpath),0,0);
        grid.add(createImage(coyetepath),1,0);
        grid.add(createImage(coyetealtpath),0,1);
        grid.add(createImage(coyetepath),1,1);

        Scene scene = new Scene(grid, 600, 575);
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);

    }
}



