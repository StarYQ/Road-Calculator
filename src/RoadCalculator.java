/**
 * Name:  Arnab Bhowmik
 * ID:  115610923
 * Recitation Section:  1
 */
import java.util.*;
import big.data.DataSource;
import java.util.Collections;
public class RoadCalculator {
    /**
     * Represents a road calculator that calculates the shortest path and minimum spanning tree
     * based on a graph of cities and roads it builds using the file input by the user
     * and takes user inputs for source and destination nodes to provide the shortest path for
     * alongside the path's length
     */
    private static HashMap<String, Node> graph;
    private static LinkedList<Edge> mst;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> roadStorage = new ArrayList<>();
        System.out.println("Please enter graph URL");
        String location = sc.nextLine();
        graph = buildGraph(location, roadStorage);
        mst = buildMST(graph);
        printMST(mst, roadStorage);
        while (true) {
            System.out.println("Enter a starting point for shortest path or Q to quit: ");
            String src = sc.nextLine();
            if (src.equalsIgnoreCase("Q")) {
                System.out.println("Goodbye; PSA, there's a cop on the right in 3 miles!");
                System.exit(0);
            } else {
                src = findCity(src);
                System.out.println("Enter a destination: ");
                String dest = findCity(sc.nextLine());
                try {
                    System.out.println("\nDistance: " + Djikstra(graph, src, dest) + "\nPath:\n" + printPath(graph.get(dest)));
                    System.out.println(Djikstra(graph, src, dest));
                }
                catch (NodeNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Builds a graph of cities and roads based on the input URL
     * @param location URL to retrieve the city and road data from
     * @param roadStorage List storing the original list of roads
     * @return HashMap representing the constructed graph of nodes and edges
     */
    public static HashMap<String, Node> buildGraph(String location, List<String> roadStorage) {
        HashMap<String, Node> cities = new HashMap<>();
        DataSource ds = DataSource.connectXML(location);
        ds.load();
        String cityNamesStr = ds.fetchString("cities");
        String[] cityNames = cityNamesStr.substring(1, cityNamesStr.length() - 1).replace("\"", "").split(",");
        String roadNamesStr = ds.fetchString("roads");
        String[] roadNames = roadNamesStr.substring(2, roadNamesStr.length() - 2).split("\",\"");
        for (String cityName : cityNames) {
            cities.put(cityName, new Node(cityName));
        }
        String[] roads = new String[roadNames.length];
        int i = 0;
        for (String road : roadNames) {
            String[] edgeParts = road.split(",");
            Node A = cities.get(edgeParts[0]);
            Node B = cities.get(edgeParts[1]);
            int edgeCost = Integer.parseInt(edgeParts[2]);
            A.getEdges().add(new Edge(A, B, edgeCost));
            roads[i] = (new Edge(A, B, edgeCost).toString());
            B.getEdges().add(new Edge(B, A, edgeCost));
            i++;
        }
        roadStorage.addAll(Arrays.asList(roads));
        initialPrint(Arrays.asList(cityNames), roadStorage);
        return cities;
    }

    /**
     * Constructs a Minimum Spanning Tree (MST) in the form of a Linked List
     * @param graph the graph of nodes and edges to construct the MST from
     * @return an MST in the form of a Linked List
     */
    public static LinkedList<Edge> buildMST(HashMap<String, Node> graph) {
        HashMap<Node, Integer> C = new HashMap<>();
        HashMap<Node, Edge> E = new HashMap<>();
        LinkedList<Edge> F = new LinkedList<>();
        HashSet<Node> Q = new HashSet<>();
        for (Node v : graph.values()) {
            C.put(v, Integer.MAX_VALUE);
            E.put(v, null);
            Q.add(v);
        }
        Node current = (Node) graph.values().toArray()[0];
        C.put(current, 0);
        while (!Q.isEmpty()) {
            Node minNode = null;
            int minDist = Integer.MAX_VALUE;
            for (Node v : Q) {
                if (C.get(v) < minDist) {
                    minDist = C.get(v);
                    minNode = v;
                }
            }
            Q.remove(minNode);
            Edge minEdge = E.get(minNode);
            if (minEdge!= null) {
                F.add(minEdge);
            }
            for (Edge e : minNode.getEdges()) {
                Node adj = e.getB();
                if (Q.contains(adj) && e.getCost() < C.get(adj)) {
                    C.put(adj, e.getCost());
                    E.put(adj, e);
                }
            }
        }
        return F;
    }

    /**
     * Calculates the shortest path and distance between two cities using Dijkstra's algorithm
     * @param graph the graph of nodes and edges to find the shortest path between two cities from
     * @param source the source city
     * @param dest the destination city
     * @return the cost of the cheapest path from source to dest
     * @throws NodeNotFoundException if either or both of the source or destination cities
     * are not in the graph
     */
    public static int Djikstra(HashMap<String, Node> graph, String source, String dest) throws NodeNotFoundException {
        if (source!= null && dest!= null) {
            Node current = graph.get(source);
            current.setDistance(0);
            for (Node v : graph.values()) {
                v.setPath(new LinkedList<>());
                if (v!= current) {
                    v.setDistance(Integer.MAX_VALUE);
                    v.setVisited(false);
                }
            }
            while (!graph.get(dest).getVisited()) {
                current.setVisited(true);
                for (Edge e : current.getEdges()) {
                    Node adjacent = e.getB();
                    if (!adjacent.getVisited()) {
                        int newDist = current.getDistance() + e.getCost();
                        if (newDist < e.getB().getDistance()) {
                            adjacent.setDistance(newDist);
                            adjacent.setPath(new LinkedList<>(current.getPath()));
                            adjacent.getPath().add(current.getName());
                        }
                    }
                }
                int minDistance = Integer.MAX_VALUE;
                for (Node v : graph.values()) {
                    if (!v.getVisited() && v.getDistance() < minDistance) {
                        minDistance = v.getDistance();
                        current = v;
                    }
                }
            }
            return graph.get(dest).getDistance();
        } else {
            throw new NodeNotFoundException("Cannot find the given city.");
        }
    }
    /**
     * Prints the lists of cities and roads
     * @param cityNames List of city names
     * @param roads List storing the original list of roads
     */
    public static void initialPrint(List<String> cityNames, List<String> roads) {
        System.out.println("\nLoading Map...\n\nCities:");
        Collections.sort(cityNames);
        for (String cityName : cityNames) {
            System.out.println(cityName);
        }
        System.out.println("\nRoads: ");
        Collections.sort(roads);
        for (String road : roads) {
            System.out.println(road);
        }
        System.out.println("\nMinimum Spanning Tree:\n");
    }

    /**
     * Prints the minimum spanning tree based on the graph
     * @param mst the minimum spanning tree of the graph
     * @param roadStorage List storing the original list of roads
     */
    public static void printMST(LinkedList<Edge> mst, List<String> roadStorage) {
        List<String> MSTRoads = new ArrayList<>();
        for (Edge edge : mst) {
            Edge edgeReversed = new Edge(edge.getB(), edge.getA(), edge.getCost());
            if (roadStorage.contains(edge.toString())) {
                MSTRoads.add(edge.toString());
            } else if (roadStorage.contains(edgeReversed.toString())) {
                MSTRoads.add(edgeReversed.toString());
            }
        }
        Collections.sort(MSTRoads);
        for (String road : MSTRoads) {
            System.out.println(road);
        }
    }

    /**
     * Prints the shortest path (calculated using Djikstra's algorithm)
     * to a specified destination node
     * @param dest the destination node
     * @return a string representation of the shortest path to the destination node
     */
    public static StringBuilder printPath(Node dest) {
        StringBuilder path = new StringBuilder();
        String[] nodes = new String[dest.getPath().size()];
        dest.getPath().toArray(nodes);
        for (String node : nodes) {
            path.append(node).append(", ");
        }
        path.append(dest.getName());
        return path;
    }

    /**
     * Returns the city corresponding to a user input of a city name
     * regardless of text capitalization, if applicable
     * @param str user input of city name
     * @return the corresponding city to a user input of a city name if one exists
     * and null otherwise
     */
    public static String findCity(String str) {
        for (String cityName : graph.keySet()) {
            if (str.equalsIgnoreCase(cityName)) {
                return cityName;
            }
        }
        return null;
    }
}