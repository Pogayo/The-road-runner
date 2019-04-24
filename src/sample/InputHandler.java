package sample;

import java.awt.Point;
import java.io.*;

public class InputHandler {

    public Graph readMap(int[][] matrix,int[] current) throws IOException, InvalidLetterException{

        try{

            int rows=matrix.length;
            int cols=matrix[0].length;

            Graph graph = new Graph(rows,cols); //trying to give it the start cordinates
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    int typeSymbol = matrix[i][j];
                    if(typeSymbol == 1){  // will change to not equal to 1
                        Node n = new Node(i,j, "OBSTACLE");
                        graph.setMapCell(new Point(i,j), n);
                    }
                    else if(current[0]==i && current[1]==j){//start
                        Node n = new Node(i,j, "NORMAL");
                        graph.setMapCell(new Point(i,j), n);
                        graph.setStartPosition(new Point(i,j));
                    }
                    else if(typeSymbol == 9){
                        Node n = new Node(i,j, "NORMAL");
                        graph.setMapCell(new Point(i,j), n);
                        graph.setTargetPosition(new Point(i,j));
                    }
                    else{
                        Node n = new Node(i,j, "NORMAL");
                        graph.setMapCell(new Point(i,j), n);
                    }
                }
            }
            return graph;
        }
        catch(Exception e){
            throw e;
        }

    }
}

