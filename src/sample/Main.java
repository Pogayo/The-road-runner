package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.io.IOException;

public class Main extends Application {
    public static FileReader fr;
    public static BufferedReader br;
    public static String FILENAME="C:\\Users\\Student\\IdeaProjects\\Perez-Ian\\pp-ii-the-road-runner-perez-ian\\src\\sample_test_input_1.txt";
    public static int[][] matrix;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        readFile();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}



