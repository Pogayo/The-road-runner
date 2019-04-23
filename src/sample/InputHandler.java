package sample;

import java.awt.Point;
import java.io.*;

public class InputHandler {

    public SquareGraph readMap(int[][] matrix) throws IOException, InvalidLetterException{

        try{

            int rows=matrix.length;
            int cols=matrix[0].length;

            SquareGraph graph = new SquareGraph(rows,cols);
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    int typeSymbol = matrix[i][j];
                    if(typeSymbol == 1){  // will change to not equal to 1
                        Node n = new Node(i,j, "OBSTACLE");
                        graph.setMapCell(new Point(i,j), n);
                    }
                    else if(typeSymbol == 8){
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

