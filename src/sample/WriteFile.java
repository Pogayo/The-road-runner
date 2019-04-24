package sample;
import java.io.*;
import java.util.Date;

public class WriteFile {
    public static void writeResult(int[][]path)  throws IOException {
        String fname=" PerezOgayo_IanObutho_DirectionsforInput#";
        Date date= new Date();

        long time = date.getTime();
        fname=fname+time+".txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fname,true));

            writer.write("Start "+path[0][0]+" "+path[0][1]+"\n");
            writer.write("Goal "+path[path.length-1][0]+" "+path[path.length-1][1]+"\n");
            for(int i=1;i<path.length;i++){
                String move=getMove(path[i-1],path[i]);
                writer.write(move+"\n");
            }


            writer.close();
        }

        catch(Exception e){
            System.out.println("Error!....");

        }

    }

    public static void main(String[] args) {
        int[][] trial={{3,1},{2,1},{1,2},{0,2}};
        try {
            writeResult(trial);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getMove(int[]prev,int[] curr){
        String move="";
        int rowChange=curr[0]-prev[0];
        int colChange=curr[1]-prev[1];

        if(rowChange==1){
            if(colChange==0){
                return "South";
            }
            else if(colChange==1){
                return "South-East";
            }
            else if(colChange==-1){
                return "South-West";
            }
        }
        else if(rowChange==0){
            if(colChange==0){
                return "No move";
            }
            else if(colChange==1){
                return "East";
            }
            else if(colChange==-1){
                return "West";
            }
        }
        else if(rowChange==-1){
            if(colChange==0){
                return "North";
            }
            else if(colChange==1){
                return "North-East";
            }
            else if(colChange==-1){
                return "North-West";
            }
        }
        return "";
    }
}
