package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import static sample.Main.*;
import static sample.Move.*;
import static sample.GamePlay.*;
import static sample.ReadFile.*;
import static sample.Weights.*;
import static sample.WriteFile.*;
import static sample.A_StarAlgorithm.*;

public class Controls {

   static GridPane controlGrid=new GridPane(); //will contain all the buttons

    public static GridPane getControlGrid(){

        controlGrid.setVgap(20.0);
        controlGrid.setHgap(5.0);


        Button start = new Button("Start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleStart();
            }
        });
        controlGrid.add(start, 0, 0);

        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            handleReset();

        });



        controlGrid.add(reset, 0, 1);

        Button undoBtn=new Button("Undo");
        undoBtn.setOnAction(event -> {
            handleUndo();
        });

        Button redoBtn=new Button("Redo");
        redoBtn.setOnAction(event -> {
            handleRedo();
        });

        controlGrid.add(undoBtn,0,2);
        controlGrid.add(redoBtn,0,3);

        Button enable8Btn = new Button("Enable 8 Ds");
        enable8Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (enable8) {
                    enable8 = false;
                    enable8Btn.setText("Enable 8 Ds");
                } else {
                    enable8 = true;
                    enable8Btn.setText("Disable 8 Ds");
                }
            }

        });


        controlGrid.add(enable8Btn, 0, 4);

        Button setNewStart=new Button ("Set New Start");
        controlGrid.add(setNewStart, 0, 5);
        setNewStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setNewStartVariable();
            }


        });

        Button loadNewMap=new Button ("Load New Map");
        controlGrid.add(loadNewMap, 0, 6);
        loadNewMap.setOnAction(event -> {
            handleLoadNewMap();
        });

        Button changeWeights = new Button("Change Weights");
        changeWeights.setOnAction(event -> {
            handleChangeWeights();
        });

        controlGrid.add(changeWeights, 0, 7);

        Button Astar = new Button("Solve with A*");
        Astar.setOnAction(event -> {
            handleAstar();
        });

        controlGrid.add(Astar, 0, 8);

        Button DFS = new Button("Solve with DFS");
        DFS.setOnAction(event -> {
            handleDFS();
        });

        controlGrid.add(DFS, 0, 9);

        Button djikstra = new Button("Solve with Djikstra");
        djikstra.setOnAction(event -> {
           // handleDFS();
        });

        controlGrid.add(djikstra, 0, 10);



        return controlGrid;

    }


    private static void handleDFS() { //0 for A*, 1 for DFS, 2 for Dijkstra
     handleSearch(1);
    }

    private static void handleAstar()  {
     handleSearch(0);

    }
    public static void handleSearch(int type){

        int[][] mymatrix=matrix;
        InputHandler handler = new InputHandler();
        Graph graph = null;
        try {
            graph = handler.readMap(mymatrix,currentCordinates);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidLetterException e) {
            e.printStackTrace();
        }
        ArrayList<Node> path=new  ArrayList<Node>();
        if(type==0){
             path = graph.executeAStar();
         }
         else if(type==1){
             path = graph.executeDFS();

         }
         else if(type==2){
             path = graph.executeDijkstras();

         }

        if(path == null||path.size()==0){
            System.out.println("There is no path to target");
        }
        else{
            System.out.println("The total number of moves from distance to the target are : " + path.size());
            System.out.println("You want to see the whole path to the target ? (y/n) ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if(response.equals("y")){
                System.out.println("--- Path to target ---");
                graph.printPath(path);
                int[][] res=graph.getPathCoord(path);
                try {
                    writeResult(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(int i=1;i<res.length;i++){
                    int rowC=res[i][0]-currentCordinates[0];
                    int colC=res[i][1]-currentCordinates[1];
                    try {
                        updateCordGrid(rowC,colC);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    private static void handleChangeWeights() {
        Dialog<Integer[]> dialog = new Dialog<>();
        dialog.setTitle("Change weights");
        dialog.setHeaderText("Please specify the weights of your environment");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.setContent(changeWeightsScreen());
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                Integer[] result=new Integer[pointInput.length]; //should handle the error when somebody does not enter  number
                for (int i=0;i<pointInput.length;i++){
                    if(i==1){
                        continue;
                    }
                    try{
                    result[i]=Integer.parseInt(pointInput[i].getText());
                    points[i]=result[i];}
                    catch(Exception E){
                        System.out.println("You entered a non-number "+pointInput[i].getText());
                    }
                }
                for(int num:points){
                    System.out.println(num);
                }
                return result;
            }
            return null;
        });
        Optional<Integer[]> optionalResult = dialog.showAndWait();

    }
    public static void setNewStartVariable() {
        setNewStart=true;
    }

    public static void handleLoadNewMap() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            FILENAME=selectedFile.getAbsolutePath();
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
        }
        else {
            System.out.println("An error ocurred or file selection was cancelled");

            return;
        }
        GridPane oldGrid=grid;
        grid=new GridPane();
        readFile();
        mainGrid.getChildren().remove(oldGrid);//removing the old grid
        mainGrid.add(grid, 1, 0);
        live=false; //making sure you can't play till you click start

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
