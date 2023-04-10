import java.util.*;

/**
 * Name: Blake Frazzini
 * Class: Data Structures and Algorithms - CS4720
 * Date: 3/23/23
 *
 * Desc: This program implements Dijkstra's SSSP Algorithm as specified/outlined in the
 * Algorithms Illuminated Book we are using in class. More specifically, it follows the pseudocode
 * written on page 80. I used hashmaps and arraylists for the main data structures.
 */

public class DijkstraSSSP {
    /**
     * This method "traverse" handles finding the shortest path from the source node to the destination node.
     * It follows the pseudocode as outlined in the Algorithms Illuminated book on page 80. It is
     * a simple, yet effective implementation of Dijkstra's SSSP Algorithm.
     * @param adjacencyList pass in the adjacency list
     * @param nodes # of nodes so the program can setup the unvisited arraylist properly
     * @param source source node
     * @return the shortest path from the source node to each possible destination node is returned
     * of course, if the destination is unable to be reached from the source node, then nothing will be
     * displayed for that specific route/path
     */
    public static HashMap<Character, Integer> traverse(HashMap<Character, ArrayList<Edge>> adjacencyList, char[] nodes, char source)
    {

        // INITIALIZATION

        // Initialize the shortest path hash map with the starting node and a distance of 0
        HashMap<Character, Integer> shortestPath = new HashMap<>();
        shortestPath.put(source, 0);

        // Initialize an "unvisited" list with all the nodes
        ArrayList<Character> unvisitedNodesList = new ArrayList<>();
        for(char c : nodes)
        {
            unvisitedNodesList.add(c);
        }

        // Start with the source node and remove it from the unvisited list
        char current = source;

        // END INITIALIZATION

        // -----

        // MAIN LOOP FROM 9.2.1

        // Loop through unvisited list until all nodes have been visited
        while(!unvisitedNodesList.isEmpty())
        {
            // Initialize the minimum node and distance to be updated in this iteration
            char min = ' ';
            int minLen = -1;

            unvisitedNodesList.remove((Character)current);

            // Check if the current node has any neighbors
            if(adjacencyList.get(current).isEmpty())
            {
                // if there are no remaining unvisited nodes
                if(unvisitedNodesList.isEmpty())
                {
                    return shortestPath;
                }
                else
                {
                    current = unvisitedNodesList.get(0);
                    continue;
                }
                // Return the shortest path hash map if the current node has no neighbors
                // In the first data set, this handles 't', for example (how 't' has no
                // paths out as a source node

            }

            // Update the shortest path for each neighboring node
            //**********

            for(Edge v : adjacencyList.get(current))
            {

                if (!shortestPath.containsKey(v.v))
                {
                    // If the node has not been visited before, add it to the shortest path hash map
                    shortestPath.put(v.v, shortestPath.get(current) + v.len);
                }
                else if(shortestPath.get(v.v) > shortestPath.get(current) + v.len)
                {
                    // If the node has been visited before and the new path is shorter, update the shortest path
                    shortestPath.replace(v.v, shortestPath.get(current) + v.len);
                }


                // Update the minimum node and distance to be updated in this iteration
                if(minLen == -1 || v.len < minLen)
                {
                    min = v.v;
                    minLen = shortestPath.get(current) + v.len;
                }
            }

            // Select the unvisited node with the smallest distance from the starting node
            current = min;
        } // END MAIN LOOP FROM 9.2.1

        //***********
        // Return the shortest path hash map
        return shortestPath;
    }

    /**
     * Main method where the data sits primarily. This method also calls
     * The traverse method from above in order to find the SSSP. It then prints
     * the correct results to the console
     * @param args the command line args
     */
    public static void main(String[] args)
    {
        // Define the nodes and edges of the graph
        // *Note: the following input data is that of the first dataset that was
        // supplied. More importantly, this implementation follows the "FromToE" style,
        // so this data is realistically here as a formality (with the exception of the
        // char array). See line 21
        char[] V = {'s', 't', 'v', 'w'}; // char array used to pass into traverse method
       // int[] E = {1, 2, 3, 4, 5};
       // int[] LenE = {1, 2, 3, 4, 5};
        char sourceNode = 's';

        // Create an empty adjacency list for each node
        HashMap<Character, ArrayList<Edge>> adjacencyList = new HashMap<>();
        // iterate through the char array V and add each node to the new arraylist
        for (char v : V)
        {
            adjacencyList.put(v, new ArrayList<>());
        }

        // Based off of the char array in line 111
        // Implemented using FromToE {1(s,v), 2(v,w), 3(w,t), 4(s,w), 5(v,t)},
        // It pulls the node and adds an edge ('destination node', length)
        // In other words, it adds edges between the nodes according to the dataset/"graph"

        adjacencyList.get('s').add(new Edge('v', 16));
        adjacencyList.get('v').add(new Edge('w', 2));
        adjacencyList.get('t').add(new Edge('w', 4));
        adjacencyList.get('s').add(new Edge('w', 32));
        adjacencyList.get('s').add(new Edge('t', 8));



        // Find the shortest paths from the source node to all other nodes by calling traverse method
        // pass in adjacency list, array of characters(nodes), and the source node you want
        HashMap<Character, Integer> shortest_paths = traverse(adjacencyList, V, sourceNode);

        // Print the results
        System.out.println("The shortest, traversable paths for the chosen source node ('" + sourceNode + "') are: " + shortest_paths);

    }
}

/**
 * Edge data structure
 */
class Edge
{
        public char v;
        public int len;
        public Edge(char v, int len)
        {
            this.v = v;
            this.len = len;
        }


}