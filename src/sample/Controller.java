package sample;

import java.io.*;
import java.io.FileReader;

public class Controller  {

    public static FileReader fr;
    public static BufferedReader br;
    public static String FILENAME="C:\\Users\\Student\\IdeaProjects\\Perez-Ian\\pp-ii-the-road-runner-perez-ian\\src\\sample_test_input_1.txt";
    public static void main() {
        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {

            e.printStackTrace();}
    }
}
