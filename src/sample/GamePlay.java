//will contain undo,redo and possible reset and start

package sample;

import javafx.scene.input.KeyCode;
import sample.Main.*;

import java.io.IOException;

import static sample.Move.*;
import static sample.Main.*;


//import static sample.Main.undo;

public class GamePlay {
    public static void handleRedo() {
     //
        int colchange=currentCordinates[1]-prevGlobal[1];
        int rowchange=currentCordinates[0]-prevGlobal[0];
        try {
        if(colchange==1){
            if(rowchange==1){
                handleKeys(KeyCode.S);
            }
            else if(rowchange==0){
                handleKeys(KeyCode.RIGHT);
            }
            else if(rowchange==-1){
                handleKeys(KeyCode.W);

            }

        }
        else if (colchange==0){
            if(rowchange==1){
                handleKeys(KeyCode.DOWN);
            }
            else if(rowchange==-1){
                handleKeys(KeyCode.UP);

            }
        }
        else if (colchange==-1){
            if(rowchange==1){
                handleKeys(KeyCode.A);
            }
            else if(rowchange==0){
                handleKeys(KeyCode.LEFT);
            }
            else if(rowchange==-1){
                handleKeys(KeyCode.Q);

            }
        }
        //updateCordGrid(rowchange,colchange);
        } catch (IOException e) {
            System.out.println("It did not work");
            e.printStackTrace();
        }

    }

    public static void handleUndo() {

        if(undo.size()>0){
            goBack();
        }

    }

    public static void goBack() {
        try {
            Integer[] prev=undo.remove(undo.size()-1);
            grid.add(createImage(img.get(7)), prev[1], prev[0]); //taking it to the previous
            int prevType=matrix[prev[0]][prev[1]];
            //making the current have it's normal image

            grid.add(createImage(img.get(matrix[currentCordinates[0]][currentCordinates[1]])), currentCordinates[1], currentCordinates[0]);
            currentCordinates[0]=prev[0];
            currentCordinates[1]=prev[1];
            score=score-points[prevType];
            scoreUI.setText(String.valueOf(score));


        }
        catch(Exception E){
            System.out.println("Whoops!");
        }

    }
}
