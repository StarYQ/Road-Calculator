/**
 * Name: Arnab Bhowmik
 * ID: 115610923
 * Recitation Section: 1
 */

/**
 * Represents an adjacency between two cities in the graph of connected cities
 */
public class Edge {
    private Node A; //source node of the adjacency
    private Node B; //destination node of the adjacency
    private int cost; //cost of laying down Internet cable between the two cities

    /**
     * Constructs an Edge with a specified source node, destination node, and cost
     * @param A source node
     * @param B destination node
     * @param cost cost of laying down Internet cable between the two cities
     */
    public Edge(Node A, Node B, int cost) {
        this.A = A;
        this.B = B;
        this.cost = cost;
    }

    /**
     * Provides a string representation of the Edge data fields
     * @return a string representation of the Edge data fields
     */
    public String toString() {
        return this.A.getName()+" to "+this.B.getName()+" "+this.cost;
    }

    /**
     *  Compares the current edge’s cost to the cost of another specified Edge
     * @param otherEdge another specified Edge
     * @return -1 if the current edge’s cost is less than otherEdge’s cost, 0 if equal, and 1 if greater than
     */
    public int compareTo(Object otherEdge) {
        Edge e = (Edge) otherEdge;
        return Integer.compare(this.getCost(), e.getCost());
    }

    /**
     * Getter method for the source node of the Edge
     * @return the source node of the Edge
     */
    public Node getA() {
        return this.A;
    }
    /**
     * Getter method for the destination node of the Edge
     * @return the destination node of the Edge
     */
    public Node getB() {
        return this.B;
    }

    /**
     * Getter method for the cost of the Edge
     * @return the cost of the Edge
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Setter method for the source node of the Edge
     * @param A the source node of the Edge
     */
    public void setA(Node A) {
        this.A = A;
    }
    /**
     * Setter method for the destination node of the Edge
     * @param B the destination node of the Edge
     */
    public void setB(Node B) {
        this.B = B;
    }
    /**
     * Getter method for the cost of the Edge
     * @param cost the cost of the Edge
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
}
