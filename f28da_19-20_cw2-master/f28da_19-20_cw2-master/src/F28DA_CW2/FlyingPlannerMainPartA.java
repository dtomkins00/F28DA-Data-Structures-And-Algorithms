package F28DA_CW2;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used to represent direct flights between the vertices below
 * using edges to create a path between the vertices whilst keeping the cost as low
 * as possible by using theDijkstraShortestPath algorithm to find the shortest path
 * between the vertices.
 * 
 * @version 1.0
 * @since 28-11-19 
 * 
 */

public class FlyingPlannerMainPartA {

	public static void main(String[] args) {

		/*
		 * Creates a new graph g of type String and DefaultWeightedEdge, and uses
		 * JGraphT's SimpleDirectedWeightedGraph to allow for the edges to have weights
		 * attached to them, whilst using the DefaultWeightedEdge implementation to get
		 * these weights on the edges.
		 */
		Graph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		// Assign the airport names/destinations to string variables to be used in the graph vertices
		String v1 = "Edinburgh";
		String v2 = "Heathrow";
		String v3 = "Dubai";
		String v4 = "Sydney";
		String v5 = "Kuala Lumpur";

		// Add the vertices to the graph
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);

		/*
		 *  Add edges to create a circuit to be used in the graph as well as
		 *  adding weights to the edges to allow the DijkstaShortestPath
		 *  algorithm to work
		 */
		DefaultWeightedEdge e1 = g.addEdge(v1, v2);
		g.setEdgeWeight(e1, 80); 

		DefaultWeightedEdge e2 = g.addEdge(v2, v1);
		g.setEdgeWeight(e2, 80); 

		DefaultWeightedEdge e3 = g.addEdge(v2, v3);
		g.setEdgeWeight(e3, 130);

		DefaultWeightedEdge e4 = g.addEdge(v3, v2);
		g.setEdgeWeight(e4, 130);

		DefaultWeightedEdge e5 = g.addEdge(v2, v4);
		g.setEdgeWeight(e5, 570);

		DefaultWeightedEdge e6 = g.addEdge(v4, v2);
		g.setEdgeWeight(e6, 570);

		DefaultWeightedEdge e7 = g.addEdge(v3, v5);
		g.setEdgeWeight(e7, 170);

		DefaultWeightedEdge e8 = g.addEdge(v5, v3);
		g.setEdgeWeight(e8, 170);

		DefaultWeightedEdge e9 = g.addEdge(v3, v1);
		g.setEdgeWeight(e9, 190); 

		DefaultWeightedEdge e10 = g.addEdge(v1, v3);
		g.setEdgeWeight(e10, 190); 

		DefaultWeightedEdge e11 = g.addEdge(v5, v4);
		g.setEdgeWeight(e11, 150); 

		DefaultWeightedEdge e12 = g.addEdge(v4, v5);
		g.setEdgeWeight(e12, 150);

		/*
		 * User interface is created by printing out a description of the program
		 * and the airports + flight routes used in it.
		 */
		System.out.println("Hello. This program will calculate the shortest and most cost effective route to your destination.");
		System.out.println("");

		System.out.println("The following airports and flight routes are used:");

		// Prints out the vertices and edges (airports and flight routes)
		System.out.println(g.toString());

		System.out.println("");
		System.out.println("Please enter the start airport:");	

		// A scanner will accept the starting airport from the user
		Scanner from = new Scanner(System.in);

		// This value is then store in the variable of type String, s
		String s = from.nextLine();

		// A scanner will accept the destination airport from the user
		System.out.println("Please enter the destination airport:");
		Scanner to = new Scanner(System.in);

		// This value is then store in the variable of type String, d
		String d = to.nextLine();

		/*
		 *  Assign the variables s and d to the start and destination variables
		 *  respectively so they can be used by the DijkstraShortestPath algorithm
		 */
		String start = s;
		String destination = d;

		//Creates a new variable called dijkstraShortestPath using the DijkstraShortestPath class in JGraphT, creating a new instance of the algorithm for the graph "g"
		//Also takes the vertices and weighted edges from the graph "g"
		DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<String, DefaultWeightedEdge>(g);
		//Creates a new variable of list String so it can display the route using the vertices above - has the above dijkstraShortestPath variable assigned to it
		List<String> shortestPath = dijkstraShortestPath
				//Calls the dijkstraShortestPath getPath() method on the input values start and destination received from the scanner input and returns the path as a sequence of vertices
				.getPath(start,destination).getVertexList();

		//Creates a new variable called dijkstraShortestPathWeight using the DijkstraShortestPath class in JGraphT, creating a new instance of the algorithm for the graph "g"
		DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraShortestPathWeight = new DijkstraShortestPath<String, DefaultWeightedEdge>(g);
		//Creates a new variable of type double so it can take the weight values from the edges - has the above dijkstraShortestPathWeight variable assigned to it
		double shortestPathCost = dijkstraShortestPathWeight
				//Calls the baseShortestPath getPathWeight() method on the input values start and destination received from the scanner input
				//Then the weights of the edges traversed in the path are added up and assigned to the shortestPathCost variable
				.getPathWeight(start,destination);

		System.out.println("");

		// Prints the shortest path between the two airports entered by the user
		System.out.println("Shortest (i.e. cheapest) path: " + shortestPath);

		// Prints out the cost (total weight) of the shortest (cheapest) path
		System.out.println("Cost of shortest (i.e. cheapest) path = £" + shortestPathCost);

		// Close the scanners to prevent resource leaks
		from.close();
		to.close();

	}

}
