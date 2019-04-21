//File will contain all the methods and variables relating to reading the file

package sample;
import java.io.BufferedReader;
import java.io.FileReader;
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

}
