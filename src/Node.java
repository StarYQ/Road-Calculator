/**
 * Name: Arnab Bhowmik
 * ID: 115610923
 * Recitation Section: 1
 */
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Represents a city that will be a part of the graph of connected cities
 */
public class Node {
    private String name; //name of city
    private HashSet<Edge> edges; //set of Edges the node is connected to
    private boolean visited; //whether the node has been visited during the traversal of the graph
    //Djikstra's algorithm
    private LinkedList<String> path; //current known shortest path from the given node to the source node in Dijkstra's
    private int distance; //length of current known shortest path from the given node to the source node in Dijkstra's

    /**
     * Constructs a Node with a specified city name
     * @param name the name of the city that the node represents
     */
    public Node(String name){
        this.name = name;
        this.edges = new HashSet<>();
    }

    /**
     * Getter method for the name of the node
     * @return the name of the node
     */
    public String getName(){
        return this.name;
    }
    /**
     * Getter method for the Edges connected to the node
     * @return the set of Edges connected to the node
     */
    public HashSet<Edge> getEdges(){
        return this.edges;
    }
    /**
     * Getter method for the boolean determining if the node has been visited in graph traversal
     * @return true if the node has been visited, false otherwise
     */
    public boolean getVisited(){
        return this.visited;
    }
    /**
     * Getter method for the current shortest known path leading to the node from the source node
     * @return the current shortest known path leading to the node from the source node
     */
    public LinkedList<String> getPath(){
        return this.path;
    }
    /**
     * Getter method for the length of the current shortest known path leading to the node from the source node
     * @return length of the current shortest known path leading to the node from the source node
     */
    public int getDistance(){
        return this.distance;
    }
    /**
     * Setter method for the name of the node
     * @param name the name of the node
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Setter method for the Edges connected to the node
     * @param edges the set of Edges connected to the node
     */
    public void setEdges(HashSet<Edge> edges){
        this.edges = edges;
    }
    /**
     * Setter method for the boolean determining if the node has been visited in graph traversal
     * @param visited true if the node has been visited, false otherwise
     */
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    /**
     * Setter method for the current shortest known path leading to the node from the source node
     * @param path the current shortest known path leading to the node from the source node
     */
    public void setPath(LinkedList<String> path){
        this.path = path;
    }
    /**
     * Setter method for the length of the current shortest known path leading to the node from the source node
     * @param distance length of the current shortest known path leading to the node from the source node
     */
    public void setDistance(int distance){
        this.distance = distance;
    }
}
