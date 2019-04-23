package sample;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Weights {
    static GridPane changeWGrid=new GridPane();

    static String[] roadsS={"Road", "Boulder", "Pothole", "Explosive", "Coyote", "Tarred", "Gold"};
    static TextField[] pointInput=new TextField[roadsS.length];

    public static GridPane changeWeightsScreen(){
        changeWGrid.setVgap(5);
        changeWGrid.setHgap(5);
        for(int i=0;i<roadsS.length;i++){
            if(i==1){
                continue;
            }
            Text lbl=new Text(roadsS[i]);
            changeWGrid.add(lbl,0,i);
        }
        for(int j=0;j< roadsS.length;j++){

            if(j==1){
                continue;
            }
            TextField input=new TextField();
            changeWGrid.add(input,1,j);
            pointInput[j]=input;
        }
        return  changeWGrid;
    }
}
