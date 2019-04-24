package sample;




import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class A_StarAlgorithm {



    public static void main(String[] args) throws InvalidLetterException,FileNotFoundException,IOException,HeapException{

           int[][] mymatrix={{1,2,0,9}, {0,0,0,3}, {6,0,4,5}, {0,8,0,5}};
            InputHandler handler = new InputHandler();
            Graph graph = handler.readMap(mymatrix);

            ArrayList<Node> path = graph.executeDFS();

            if(path == null){
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
                }
            }

    }


}

