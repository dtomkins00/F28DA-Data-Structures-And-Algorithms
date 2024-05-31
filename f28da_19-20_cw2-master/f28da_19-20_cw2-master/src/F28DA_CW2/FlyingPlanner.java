package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.alg.shortestpath.PathValidator;
import org.jgrapht.graph.AsUnweightedGraph;
import org.jgrapht.graph.DirectedWeightedMultigraph;

/**
 * This class functions as the main class of the program where the other classes
 * outside of FlyingPlannerMainPartBC.java have their methods used to populate the graph
 * and enable this class to take the airport and flight values from their respective
 * classes. Also includes methods to find the least cost of the journey
 * and least amount of changeovers on the journey.
 * 
 * @version 1.0
 * @since 28-11-19
 * 
 */

public class FlyingPlanner implements IFlyingPlannerPartB<Airport,Flight>, IFlyingPlannerPartC<Airport,Flight> {

	/*
	 * Creates a graph g of types String[] and Journey - this uses the
	 * DirectdWeightedMultigraph of similar types and uses the Journey class itself to
	 * retrieve data from said class. This type of graph is used so that no loops
	 * can occur, allowing the user to have a normal journey.
	 */
	static Graph<String[], Journey> g = new DirectedWeightedMultigraph<String[], Journey>(Journey.class);

	/*
	 * Creates a graph g2 of types String[] and Journey - this utilises the previous graph
	 * and utilises JGraphT's AsUnweightedGraph so that the paths can also be viewed
	 * without the weights.
	 */
	static AsUnweightedGraph<String[], Journey> g2 = new AsUnweightedGraph<String[], Journey>(g);

	/*
	 * This populate method calls the getAirports and getFlights methods from
	 * FlightsReader.java and fills the variable fr of type FlightsReader with the
	 * data. If adding the data to fr is done successfully, true is returned.
	 */
	public boolean populate(FlightsReader fr) {
		populate(fr.getAirports(), fr.getFlights());
		return true;
	}

	/*
	 * The second populate method utilises the parameters airports and flights,
	 * both using a HashSet of an array of String so that they can store the data
	 * obtained using the last populate method and have the rest of the program able to
	 * access these data by calling the variables.
	 */
	public boolean populate(HashSet<String[]> airports, HashSet<String[]> flights) {

		// Creates a new variable of type FlightsReader called reader
		FlightsReader reader;

		// Try-catch loop set up to catch any possible exceptions
		try {

			// Sets the reader variable do a new instance of FlightsReader
			reader = new FlightsReader();

			/*
			 *  Sets the airports variable to a new instance of the getAirports
			 *  method called on the reader variable so that the airports variable 
			 *  can store info about airports from the respective .csv file
			 */
			airports = reader.getAirports();

			/*
			 *  Sets the flights variable to a new instance of the getFlights
			 *  method called on the reader variable so that the flights variable 
			 *  can store info about flights from the respective .csv file
			 */
			flights = reader.getFlights();

			// Calls the populate method on airports and flights to populate them with data
			populate(airports, flights);

			// If the code above is executed successfully, return true
			return true;

		} catch (FileNotFoundException | FlyingPlannerException e) {
			// Print out an error message if an exception is caught
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		// Return false if the operation is not successful
		return false;
	}

	/*
	 * This method is used to retrieve the airport codes from the .csv files
	 * by calling the getCode method from the Airport.java class.
	 */
	public Airport airport(String code) {

		/*
		 * Creates a new variable called airport of type Airport and sets it to a new
		 * instance of the Airport class, using the code parameter to set its type.
		 */
		Airport airport = new Airport(code);

		/*
		 * Retrieves the airport code from the airports.csv file and assigns
		 * it to the code variable
		 */
		code = airport.getCode();

		// Returns the current value of the airport variable
		return airport;
	}

	/*
	 * This method is used to retrieve the flight codes from the .csv files
	 * by calling the getFlightCode method from the Flight.java class.
	 */
	public Flight flight(String code) {

		/*
		 * Creates a new variable called flight of type Flight and sets it to a new
		 * instance of the Flight class, using the code parameter to set its type.
		 */
		Flight flight = new Flight(code);

		/*
		 * Retrieves the flight code from the flights.csv file and assigns
		 * it to the code variable
		 */
		code = flight.getFlightCode();

		// Returns the current value of the flight variable
		return flight;
	}

	/*
	 * This method is designed to find the flight route with the lowest cost
	 * - it does this by iterating through the g2 graph to find this route.
	 */
	public Journey leastCost(String from, String to) throws FlyingPlannerException {

		// Creates an array of String called start, and initialises it to null
		String[] start = null;

		/*
		 * Creates an iterator of type String[] called lCIterator1 which will go through
		 * the graph g2 and iterate through the starting vertices (airports)
		 * to search for the starting airport code
		 */
		Iterator<String[]> lCIterator1 = g2.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lCIterator1.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lCIterator1.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(from)) {

				// Sets the start variable to the current value of output
				start = output;
			}
		}

		// Creates an array of String called destination, and initialises it to null
		String[] destination = null;

		/*
		 * Creates an iterator of type String[] called lCIterator2 which will go through
		 * the graph g2 and iterate through the end vertices (airports)
		 * to search for the end airport code
		 */
		Iterator<String[]> lCIterator2 = g2.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lCIterator2.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lCIterator2.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(to)) {

				// Sets the destination variable to the current value of output
				destination = output;
			}
		}

		/*
		 * Creates a graph called leastCost of type String[] and Journey
		 * which uses DijkstrasShortestPath to find the shortest path
		 * (cheapest route) between the starting airport and the end airport
		 */
		GraphPath<String[], Journey> leastCost = DijkstraShortestPath.findPathBetween(g2, start, destination);

		/*
		 * Creates a variable called j of type Journey, sets it to
		 * a new instance of the Journey class which uses leastCost
		 * as a parameter
		 */
		Journey j = new Journey(leastCost);

		// Returns the current value of j
		return j;

	}


	/*
	 * This method's purpose is to find the path with the least amount of changeovers
	 * (hops) - it also involves the g2 graph in terms of iterating through it to
	 * find values.
	 */
	public Journey leastHop(String from, String to) throws FlyingPlannerException {

		// Creates an array of String called start, and initialises it to null
		String[] start = null;

		/*
		 * Creates an iterator of type String[] called lHIterator1 which will go through
		 * the graph g2 and iterate through the starting vertices (airports)
		 * to search for the starting airport code
		 */
		Iterator<String[]> lHIterator1 = g.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lHIterator1.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lHIterator1.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(from)) {

				// Sets the start variable to the current value of output
				start = output;
			}

		}

		// Creates an array of String called destination, and initialises it to null
		String[] destination = null;

		/*
		 * Creates an iterator of type String[] called lHIterator2 which will go through
		 * the graph g and iterate through the end vertices (airports)
		 * to search for the ending airport code
		 */
		Iterator<String[]> lHIterator2 = g.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lHIterator2.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lHIterator2.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(to)) {

				// Sets the destination variable to the current value of output
				destination = output;
			}

		}

		/*
		 * Creates a variable called leastHop that utilises the KShortestSimplePaths
		 * algorithm of type String[] and Journey to determine the flight route
		 * with the lowest amount of changeovers by getting the flight paths (edges)
		 * in increasing order of cost (weight). It is set to a new instance of KShortestSimplePaths
		 * using the graph g2.
		 */
		KShortestSimplePaths<String[], Journey> leastHop = new KShortestSimplePaths<>(g2);

		/*
		 * Creates a new variable called lH which uses a list of paths of type String[]
		 * and Journey, then gets the shortest paths in order of increasing weight from the
		 * start to the destination.
		 */
		List<GraphPath<String[], Journey>> lH = leastHop.getPaths(start, destination, 1);

		/*
		 * Creates a new variable of type Journey called j, sets it to
		 * a new instance of the Journey class and uses the lH variable to
		 * return the requested element in the list of paths
		 */
		Journey j = new Journey(lH.get(0));

		// Returns the current value of j
		return j;
	}

	/*
	 * Similar to the previous leastCost method, except this validates
	 * the path as well to ensure that there are no loops in the path as well
	 * as ensuring that the path is correct due to handling the user excluding
	 * airport codes.
	 */
	public Journey leastCost(String from, String to, List<String> excluding)
			throws FlyingPlannerException {

		/*
		 * Creates a new variable called pathValidator which utilises
		 * JGraphT's PathValidator method and makes it so that is of type
		 * String[] and Journey, then it sets the pathValidator variable to a new
		 * instance of this PathValidator.
		 */
		PathValidator<String[], Journey> pathValidator = new PathValidator<String[], Journey>() {

			/*
			 * This method is used to check if an edge can be added to a previous
			 * element in the path.
			 */
			public boolean isValidPath(GraphPath<String[], Journey> path, Journey edge) {

				/*
				 * This line of code returns elements that are not being excluded
				 * by the user, so this ensures that the path is valid due to
				 * not featuring anything that isn't there or shouldn't
				 * be there.
				 */
				return !excluding.contains(edge.getFrom());
			}

		};

		// Creates an array of String called source, and initialises it to null
		String[] start = null;

		/*
		 * Creates an iterator of type String[] called lCIterator1 which will go through
		 * the graph g2 and iterate through the starting vertices (airports)
		 * to search for the starting airport code
		 */
		Iterator<String[]> lCIterator1 = g2.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lCIterator1.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lCIterator1.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(from)) {

				// Sets the start variable to the current value of output
				start = output;
			}

		}

		// Creates an array of String called destination, and initialises it to null
		String[] destination = null;

		/*
		 * Creates an iterator of type String[] called lCIterator2 which will go through
		 * the graph g and iterate through the end vertices (airports)
		 * to search for the ending airport code
		 */
		Iterator<String[]> lCIterator2 = g2.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lCIterator2.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lCIterator2.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(to)) {

				// Sets the destination variable to the current value of output
				destination = output;
			}

		}

		/*
		 * Creates a graph called leastCost of type String[] and Journey
		 * which uses KShortestSimplePaths to find the shortest path
		 * (cheapest route) between the starting airport and the end airport
		 * using g2 and the pathValidator in a new instance of KShortestSimplePaths.
		 */
		KShortestSimplePaths<String[], Journey> leastCost = new KShortestSimplePaths<>(g2, pathValidator);

		/*
		 * Creates a new variable called lC which uses a list of paths of type String[]
		 * and Journey, then gets the shortest paths in order of increasing weight from the
		 * start to the destination.
		 */
		List<GraphPath<String[], Journey>> lC = leastCost.getPaths(start, destination, 1);

		/*
		 * Creates a new variable of type Journey called r, sets it to
		 * a new instance of the Journey class and uses the lC variable to
		 * return the requested element in the list of paths.
		 */
		Journey r = new Journey(lC.get(0));

		// Returns the current value of the variable r.
		return r;
	}

	/*
	 * Functions similarly to the previous leastHop method, except this is also handling 
	 * the exclusion of airport codes by the user so it is also using the PathValidators
	 * and isValidPath method to ensure that the path returned is correct.
	 */
	public Journey leastHop(String from, String to, List<String> excluding)
			throws FlyingPlannerException {

		/*
		 * Creates a new variable called pathValidator which utilises
		 * JGraphT's PathValidator method and makes it so that is of type
		 * String[] and Journey, then it sets the pathValidator variable to a new
		 * instance of this PathValidator.
		 */
		PathValidator<String[], Journey> pathValidator = new PathValidator<String[], Journey>() {

			/*
			 * This method is used to check if an edge can be added to a previous
			 * element in the path.
			 */
			public boolean isValidPath(GraphPath<String[], Journey> path, Journey edge) {

				/*
				 * This line of code returns elements that are not being excluded
				 * by the user, so this ensures that the path is valid due to
				 * not featuring anything that isn't there or shouldn't
				 * be there.
				 */
				return !excluding.contains(edge.getFrom());
			}

		};

		// Creates an array of String called start, and initialises it to null
		String[] start = null;

		/*
		 * Creates an iterator of type String[] called it which will go through
		 * the graph g and iterate through the starting vertices (airports)
		 * to search for the starting airport code
		 */
		Iterator<String[]> lHIterator1 = g2.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lHIterator1.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lHIterator1.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(from)) {

				// Sets the start variable to the current value of output
				start = output;


			}

		}

		// Creates an array of String called destination, and initialises it to null
		String[] destination = null;

		/*
		 * Creates an iterator of type String[] called it2 which will go through
		 * the graph g2 and iterate through the end vertices (airports)
		 * to search for the ending airport code
		 */
		Iterator<String[]> lHIterator2 = g2.vertexSet().iterator();

		// While loop checking to see if there are any required elements to iterate over
		while (lHIterator2.hasNext()) {

			/*
			 *  Creates an array called output of type String, sets it 
			 *  to the current element being iterated over
			 */
			String output[] = lHIterator2.next();

			/*
			 *  If statement checking if the current output value matches
			 *  the requested airport code
			 */
			if (output[0].equals(to)) {

				// Sets the destination variable to the current value of output
				destination = output;


			}

		}

		/*
		 * Creates a variable called leastHop that utilises the KShortestSimplePaths
		 * algorithm of type String[] and Journey to determine the flight route
		 * with the lowest amount of changeovers by getting the flight paths (edges)
		 * in increasing order of cost (weight). It is set to a new instance of KShortestSimplePaths
		 * using the graph g2and the pathValidator in a new instance of KShortestSimplePaths.
		 */
		KShortestSimplePaths<String[], Journey> leastHop = new KShortestSimplePaths<>(g2, pathValidator);

		/*
		 * Creates a new variable called lH which uses a list of paths of type String[]
		 * and Journey, then gets the shortest paths in order of increasing weight from the
		 * start to the destination.
		 */
		List<GraphPath<String[], Journey>> lH = leastHop.getPaths(start, destination, 1);

		/*
		 * Creates a new variable of type Journey called r, sets it to
		 * a new instance of the Journey class and uses the lH variable to
		 * return the requested element in the list of paths
		 */
		Journey r = new Journey(lH.get(0));

		// Returns the current value of the variable r
		return r;
	}

	@Override
	public Set<Airport> directlyConnected(Airport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setDirectlyConnected() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Airport> getBetterConnectedInOrder(Airport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastCostMeetUp(String at1, String at2) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastHopMeetUp(String at1, String at2) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return null;
	}


}
