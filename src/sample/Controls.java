package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import static sample.Main.*;
import static sample.Move.*;
import static sample.GamePlay.*;
import static sample.ReadFile.*;


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

        Button loadNewMap=new Button ("Load New Map");
        controlGrid.add(loadNewMap, 0, 6);

        Button changeWeights = new Button("Change Weights");
        controlGrid.add(changeWeights, 0, 7);

        return controlGrid;

    }




}
