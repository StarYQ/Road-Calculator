/**
 * Name: Arnab Bhowmik
 * ID: 115610923
 * Recitation Section: 1
 */
/**
 * Exception to be thrown if the user inputs a city name that is not contained within the graph of connected cities
 */
public class NodeNotFoundException extends Exception {
    public NodeNotFoundException (String message) {
        super(message);
    }
}
