package sample;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Node[][] map;
    private Point startPosition;
    private Point targetPosition;
    private Heap<Node> openNodes;
    private Set<Node> closedNodes;

    public Graph(int noRows, int noCols){
        map = new Node[noRows][noCols];
        startPosition = new Point();
        targetPosition = new Point();
        openNodes = new Heap<Node>();
        closedNodes = new HashSet<Node>();
    }

    public Node getMapCell(Point coord){
        return map[(int)coord.getX()][(int)coord.getY()];
    }

    public void setMapCell(Point coord, Node n){
        map[(int)coord.getX()][(int)coord.getY()] = n;
    }

    public Point getStartPosition(){
        return startPosition;
    }

    public Point getTargetPosition(){
        return targetPosition;
    }

    public void setStartPosition(Point coord){
        startPosition.setLocation(coord);
    }

    public void setTargetPosition(Point coord){
        targetPosition.setLocation(coord);
    }

    public int getDimension(){
        return map.length;
    }

    public void addToOpenNodes(Node n){
        n.setOpen();
        openNodes.add(n);
    }

    public Node popBestOpenNode(){
        return openNodes.remove();
    }

    public void addToClosedNodes(Node n){
        n.setClosed();
        closedNodes.add(n);
    }

    public boolean isInsideMap(Point p){
        return ( (p.getX() >= 0) && (p.getX() < getDimension())  && (p.getY() >= 0) && (p.getY() < getDimension()) );
    }

    public Set<Node> getNeighbours(Node n){
        Set<Node> neighbours = new HashSet<Node>();
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if( !(i==0 && j==0) )
                    if(isInsideMap(new Point(n.getX() + i,n.getY() + j))){
                        Node temp = getMapCell(new Point(n.getX() + i,n.getY() +  j));
                        if(!temp.isObstacle())
                            neighbours.add(temp);
                    }

            }
        }
        return neighbours;
    }

    static double calculateDistance(Point from, Point to){
        return Math.pow(Math.pow(from.getX()-to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2) , 0.5);
    }

    public ArrayList<Node> reconstructPath(Node target){
        ArrayList<Node> path = new ArrayList<Node>();
        Node current = target;

        while(current.getParent() != null){
            path.add(current.getParent());
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    public void printPath(ArrayList<Node> path){
        for(int i=0; i<path.size(); i++){
            Node node = path.get(i);
            System.out.println("node : (" + node.getX() + "," + node.getY() + ")");
        }
    }
    public int[][] getPathCoord(ArrayList<Node> path){ //method that will return  an array of the indexes
        int[][] result=new int[path.size()][2];
        for(int i=0; i<path.size(); i++){
            Node node = path.get(i);
            result[i][0]=node.getX();
            result[i][1]=node.getY();
            //System.out.println("node : (" + node.getX() + "," + node.getY() + ")");
        }
        return result;
    }

    public ArrayList<Node> executeAStar(){
        Node start = getMapCell(getStartPosition());
        Node target = getMapCell(getTargetPosition());
        addToOpenNodes(start);

        start.setCostFromStart(0);
        start.setTotalCost( start.getCostFromStart() + calculateDistance(start.getPosition(), target.getPosition()) );
        while(!openNodes.isEmpty()){
            Node current = popBestOpenNode();
            if(current.equals(target)){
                ArrayList<Node> path=reconstructPath(target);
                path.add(target);
                return path;
            }

            addToClosedNodes(current);
            Set<Node> neighbours = getNeighbours(current);
            for(Node neighbour : neighbours){
                if(!neighbour.isClosed()){
                    double tentativeCost = current.getCostFromStart() + calculateDistance(current.getPosition(), neighbour.getPosition());

                    if( (!neighbour.isOpen()) || (tentativeCost < neighbour.getCostFromStart()) ){
                        neighbour.setParent(current);
                        neighbour.setCostFromStart(tentativeCost);
                        neighbour.setTotalCost(neighbour.getCostFromStart() + calculateDistance(neighbour.getPosition(), start.getPosition()));
                        if(!neighbour.isOpen())
                            addToOpenNodes(neighbour);
                    }
                }

            }
        }

        return null;
    }

    public ArrayList<Node> executeDFS(){
        Node start = getMapCell(getStartPosition());
        Node target = getMapCell(getTargetPosition());

        addToOpenNodes(start);

        while(!openNodes.isEmpty()) {
            Node current = popBestOpenNode(); // will change it later
            if (current.equals(target)) {
                ArrayList<Node> path = reconstructPath(target);
                path.add(target);
                System.out.println("Goal node found");
                return path;
            }

            addToClosedNodes(current);
            Set<Node> neighbours = getNeighbours(current);
            //openNodes.addAll(neighbours);
            for (Node neighbour : neighbours) {
                if (!neighbour.isClosed()) {
                    neighbour.setParent(current);
                    if (!neighbour.isOpen()) {
                        addToOpenNodes(neighbour);
                    }

                }

            }
        }
        return null;

    }

}
