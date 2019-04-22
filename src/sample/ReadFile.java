//File will contain all the methods and variables relating to reading the file

package sample;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static sample.Main.*;

public class ReadFile {

    public static FileReader fr;
    public static BufferedReader br;
    public static String FILENAME = "C:\\Users\\Student\\IdeaProjects\\pp-ii-the-road-runner-perez-ian\\src\\sample_test_input_1.txt";

    public static int[][] matrix;
    public static boolean[][] visited;

    static HashMap<Integer, String> img = new HashMap<Integer, String>();//hashmap holding the main images for the environment
    static String prePath = "C:\\Users\\Student\\IdeaProjects\\pp-ii-the-road-runner-perez-ian\\Image Files\\";
    static HashMap<Integer, String> imgAlt = new HashMap<Integer, String>(); //hashmap holding the alternative images


    public static void populateImg() {   ///roadrunner will be no 7
        String[] images = {"road", "boulder", "pothole", "explosive", "coyote", "tarred", "gold", "road_runner", "start", "goal"};
        for (int i = 0; i < images.length; i++) {
            img.put(i, prePath + images[i] + ".jpg");
        }
        for (int i = 0; i < 7; i++) { // filling the alt images to their array
            if (i == 1) {
                continue;
            }
            imgAlt.put(i, prePath + images[i] + "_alt.jpg");
        }
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
                        prevGlobal[0]=startCordinates[0];
                        prevGlobal[1]=startCordinates[1];

                    }
                }
                if(rowCount<noRows-1){
                      rowCount++; //incrementing rowcount
                }
            }
            br.close();// closing the file

            createEnv();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}
