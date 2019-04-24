package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import static sample.Main.*;
import static sample.ReadFile.*;

public class Move {

    public static void handleKeys(KeyCode key) throws IOException {

        //System.out.println("Key Pressed: " + key);

        if (live) {
            if (key== KeyCode.UP) {  //I am trying to move up
                if (currentCordinates[0] > 0 && matrix[currentCordinates[0] - 1][currentCordinates[1]] != 1 && !visited[currentCordinates[0] - 1][currentCordinates[1]]) {
                    updateCordGrid(-1, 0);
                }
            }
            if (key == KeyCode.DOWN) {  //I am trying to move up
                if (currentCordinates[0] <= noRows - 2 && matrix[currentCordinates[0] + 1][currentCordinates[1]] != 1 && !visited[currentCordinates[0] + 1][currentCordinates[1]]) {
                    updateCordGrid(1, 0);
                }
            }
            if (key == KeyCode.LEFT) {  //I am trying to move up
                if (currentCordinates[1] > 0 && matrix[currentCordinates[0]][currentCordinates[1]] - 1 != 1 && !visited[currentCordinates[0]][currentCordinates[1] - 1]) {
                    updateCordGrid(0, -1);
                }
            }
            if (key == KeyCode.RIGHT) {  //I am trying to move up
                if (currentCordinates[1] <= noColumns - 2 && matrix[currentCordinates[0]][currentCordinates[1] + 1] != 1 && !visited[currentCordinates[0]][currentCordinates[1] + 1]) {
                    updateCordGrid(0, 1);
                }
            }
            if (enable8) {
                move8(key);
            }
        }

    }

    public static void move8(KeyCode key) throws IOException {

        if (key == KeyCode.W) {
            if (validNorthEast()) {
                updateCordGrid(-1, +1);
            }

        }

        if (key == KeyCode.S) {
            if (validSouthEast()) {
                updateCordGrid(1, 1);
            }

        }
        if (key == KeyCode.A) {
            if (validSouthWest()) {
                updateCordGrid(1, -1);
            }

        }
        if (key == KeyCode.Q) {
            if (validNorthWest()) {
                updateCordGrid(-1, -1);
            }

        }

    }

    //function that updates the cordinates after clicking buttons then updates the grid
    public static void updateCordGrid(int rowChange, int colChange) throws IOException {
        int[] prevCordinates = {currentCordinates[0], currentCordinates[1]};
        prevGlobal[0]=currentCordinates[0];
        prevGlobal[1]=currentCordinates[1];
        visited[prevCordinates[0]][prevCordinates[1]] = true;
        currentCordinates[0] = currentCordinates[0] + rowChange;

        currentCordinates[1] = currentCordinates[1] + colChange;
        if(undo.size()==3){
            undo.remove(0);
        }

       Integer[] prevClone=new Integer[2];
       prevClone[0]=prevCordinates[0];
       prevClone[1]=prevCordinates[1];

        undo.add(prevClone);


        updateGrid(currentCordinates, prevCordinates);


    }


    private static void updateGrid(int[] currentCordinates, int[] prevCordinates) throws IOException {
        int no = matrix[prevCordinates[0]][prevCordinates[1]];

        if (no < 7) {
            score += points[no];
            scoreUI.setText(String.valueOf(score));

        }
        if (no != 8 && no != 9) {
            grid.add(createImage(imgAlt.get(no)), prevCordinates[1], prevCordinates[0]);
        } else if (no == 9) {
            grid.add(createImage(img.get(9)), prevCordinates[1], prevCordinates[0]);
        } else if (no == 8) {
            grid.add(createImage(img.get(8)), prevCordinates[1], prevCordinates[0]);

        }
        grid.add(createImage(img.get(7)), currentCordinates[1], currentCordinates[0]);

        //checking if game is over
        no = matrix[currentCordinates[0]][currentCordinates[1]];

        if (no == 9 || gameOver()) {
            gameStateUI.setText("  Game Over! Reset then click Start to start the game again");
            live = false;

        }
    }

    public static boolean gameOver() {  //to check if the game is over


        if ((currentCordinates[0] > 0 && visited[currentCordinates[0] - 1][currentCordinates[1]]) || currentCordinates[0] == 0 ||matrix[currentCordinates[0] - 1][currentCordinates[1]] == 1 ) { //no valid up move

            if ((currentCordinates[0] <= noRows - 2 && visited[currentCordinates[0] + 1][currentCordinates[1]]) || currentCordinates[0] == noRows - 1 || matrix[currentCordinates[0] + 1][currentCordinates[1]] == 1) { //no valid down move

                if ((currentCordinates[1] > 0 && visited[currentCordinates[0]][currentCordinates[1] - 1]) || currentCordinates[1] == 0 || matrix[currentCordinates[0]][currentCordinates[1]-1]  == 1) { //no valid left  move
                    if ((currentCordinates[1] <= noColumns - 2 && visited[currentCordinates[0]][currentCordinates[1] + 1]) || currentCordinates[1] == noColumns - 1 ||matrix[currentCordinates[0]][currentCordinates[1] + 1] == 1 ) { //no valid right  move
                       if (enable8){
                           if(!validNorthEast() && !validNorthWest() && !validSouthEast() &&!validSouthWest()){
                               return true;}
                       }
                       else {
                           return true;
                       }

                    }
                }

            }

        }
        return false;

    }

    public static boolean validNorthEast(){

        return currentCordinates[0] > 0 && currentCordinates[1] <= noColumns - 2 && matrix[currentCordinates[0] - 1][currentCordinates[1] + 1] != 1 && !visited[currentCordinates[0] - 1][currentCordinates[1] + 1];

    }
    public static boolean validNorthWest(){
        return currentCordinates[0] > 0 && currentCordinates[1] > 0 && matrix[currentCordinates[0] - 1][currentCordinates[1] - 1] != 1 && !visited[currentCordinates[0] - 1][currentCordinates[1] - 1];
    }

    public static boolean validSouthEast(){
        return currentCordinates[0] <= noRows - 2 && currentCordinates[1] <= noColumns - 2 && matrix[currentCordinates[0] + 1][currentCordinates[1] + 1] != 1 && !visited[currentCordinates[0] + 1][currentCordinates[1] + 1] ;
    }
    public static boolean validSouthWest(){
        return currentCordinates[0] <= noRows - 2 && currentCordinates[1] > 0 && matrix[currentCordinates[0] + 1][currentCordinates[1] - 1] != 1 && !visited[currentCordinates[0] + 1][currentCordinates[1] - 1];
    }
}

