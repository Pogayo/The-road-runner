package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

import static sample.Main.currentCordinates;
import static sample.Main.visited;
import static sample.Main.matrix;
import static sample.Main.enable8;
import static sample.Main.*;

import static sample.Main.noColumns;
import static sample.Main.noRows;

public class Move {

    public static void handleKeys(KeyEvent ke) throws IOException {
        //if (ke.getCode() == KeyCode.ESCAPE) {
        System.out.println("Key Pressed: " + ke.getCode());

        //}
        if (live) {
            if (ke.getCode() == KeyCode.UP) {  //I am trying to move up
                if (currentCordinates[0] > 0 && matrix[currentCordinates[0] - 1][currentCordinates[1]] != 1 && !visited[currentCordinates[0] - 1][currentCordinates[1]]) {
                    updateCordGrid(-1, 0);
                }
            }
            if (ke.getCode() == KeyCode.DOWN) {  //I am trying to move up
                if (currentCordinates[0] <= noRows - 2 && matrix[currentCordinates[0] + 1][currentCordinates[1]] != 1 && !visited[currentCordinates[0] + 1][currentCordinates[1]]) {
                    updateCordGrid(1, 0);
                }
            }
            if (ke.getCode() == KeyCode.LEFT) {  //I am trying to move up
                if (currentCordinates[1] > 0 && matrix[currentCordinates[0]][currentCordinates[1]] - 1 != 1 && !visited[currentCordinates[0]][currentCordinates[1] - 1]) {
                    updateCordGrid(0, -1);
                }
            }
            if (ke.getCode() == KeyCode.RIGHT) {  //I am trying to move up
                if (currentCordinates[1] <= noColumns - 2 && matrix[currentCordinates[0]][currentCordinates[1] + 1] != 1 && !visited[currentCordinates[0]][currentCordinates[1] + 1]) {
                    updateCordGrid(0, 1);
                }
            }
            if (enable8) {
                move8(ke);
            }
        }

    }

    public static void move8(KeyEvent ke) throws IOException {

        if (ke.getCode() == KeyCode.W) {
            if (currentCordinates[0] > 0 && currentCordinates[1] <= noColumns - 2 && matrix[currentCordinates[0] - 1][currentCordinates[1] + 1] != 1 && !visited[currentCordinates[0] - 1][currentCordinates[1] + 1]) {
                updateCordGrid(-1, +1);
            }

        }

        if (ke.getCode() == KeyCode.S) {
            if (currentCordinates[0] <= noRows - 2 && currentCordinates[1] <= noColumns - 2 && matrix[currentCordinates[0] + 1][currentCordinates[1] + 1] != 1 && !visited[currentCordinates[0] + 1][currentCordinates[1] + 1]) {
                updateCordGrid(1, 1);
            }

        }
        if (ke.getCode() == KeyCode.A) {
            if (currentCordinates[0] <= noRows - 2 && currentCordinates[1] > 0 && matrix[currentCordinates[0] + 1][currentCordinates[1] - 1] != 1 && !visited[currentCordinates[0] + 1][currentCordinates[1] - 1]) {
                updateCordGrid(1, -1);
            }

        }
        if (ke.getCode() == KeyCode.Q) {
            if (currentCordinates[0] > 0 && currentCordinates[1] > 0 && matrix[currentCordinates[0] - 1][currentCordinates[1] - 1] != 1 && !visited[currentCordinates[0] - 1][currentCordinates[1] - 1]) {
                updateCordGrid(-1, -1);
            }

        }

    }

    //function that updates the cordinates after clicking buttons then updates the grid
    public static void updateCordGrid(int rowChange, int colChange) throws IOException {
        int[] prevCordinates = {currentCordinates[0], currentCordinates[1]};
        visited[prevCordinates[0]][prevCordinates[1]] = true;
        currentCordinates[0] = currentCordinates[0] + rowChange;
        currentCordinates[1] = currentCordinates[1] + colChange;
        updateGrid(currentCordinates, prevCordinates);


    }

    private static void updateGrid(int[] currentCordinates, int[] prevCordinates) throws IOException {
        int no = matrix[prevCordinates[0]][prevCordinates[1]];

        if (no < 7) {
            score += points[no];
        }
        System.out.println(score);
        if (no != 8 && no != 9) {
            grid.add(createImage(imgAlt.get(no)), prevCordinates[1], prevCordinates[0]);
        } else if (no == 9) {
            grid.add(createImage(img.get(9)), prevCordinates[1], prevCordinates[0]);
        } else if (no == 8) {
            grid.add(createImage(img.get(8)), prevCordinates[1], prevCordinates[0]);

        }
        grid.add(createImage(img.get(7)), currentCordinates[1], currentCordinates[0]);
    }

}
