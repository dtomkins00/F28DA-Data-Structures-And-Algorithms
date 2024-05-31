package F28DA_CW2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;

/**
 * This class is used to carry out the actual methods used to generate information
 * to be used in FlyingPlanner.java and FlyingPlannerMainPartBC.java.
 * 
 * @version 1.0
 * @since 28-11-19 
 * 
 */

public class Journey implements IJourneyPartB<Airport, Flight>, IJourneyPartC<Airport, Flight> {

	/*
	 * Creates a graph called journey, using the types Airport and Flight to
	 * represent a path
	 */
	GraphPath<Airport, Flight> journey;

	/*
	 * Similar to the above line of code, except it uses an array of String instead
	 * of the Airport type - this lets it read in values from the .csv files
	 */
	GraphPath<String[], Journey> journey2;

	/*
	 * This Journey constructor initialises the journey variable declared earlier
	 * and set up a variant of the leastCost parameter
	 */
	public Journey(GraphPath<Airport, Flight> j, GraphPath<Airport, Journey> leastCost) {
		journey = j;	
	}

	/*
	 * This is almost identical to the above constructor - however it initialises
	 * the journey2 variable using the leastCost parameter, using an array of
	 * String and type Journey
	 */
	public Journey(GraphPath<String[], Journey> leastCost) {
		journey2 = leastCost;
	}

	/*
	 * This method returns the airports that the flight will be stopping at
	 */
	public List<String> getStops() {

		// Creates stops variable and makes it a LinkedList of type String
		List<String> stops = new LinkedList<String>();

		/*
		 * For loop checks for airports being passed through using the journey
		 * variable initialised earlier and getting the vertices used in the
		 * journey by calling the getVertexList method from JGraphT on journey
		 */
		for (Airport stop : journey.getVertexList()) {

			/*
			 *  Adds found stops to the stops variable by calling the getName
			 *  method from the Airport class
			 */
			stops.add(stop.getName());
		}

		// Returns the current value of the stops variable
		return stops;
	}

	/*
	 * This method returns the flights that the user will have to travel
	 * on in order to get to their destination
	 */
	public List<String> getFlights() {

		//Creates flights variable and makes it a LinkedList of type String
		List<String> flights = new LinkedList<String>();

		/*
		 * For loop checks for flights involved in the journey to the destination
		 * using the journey variable from the first constructor and it retrieves
		 * the flights the user takes via calling the getEdgeList method from
		 * JGraphT to return the edges (flights) of the graph (flight route)
		 */
		for (Flight flight : journey.getEdgeList()) {

			/*
			 * Adds found flight codes to the flights variable by calling the 
			 * getFlightCode method from the Flight class
			 */
			flights.add(flight.getFlightCode());
		}

		// Returns the current value of the flights variable
		return flights;
	}

	/*
	 * This method returns the number of changeovers made by the user over the
	 * course of their flight.
	 */

	public int totalHop() {

		// Returns the number of flights using the java.util size method, attaches to journey
		return journey.getEdgeList().size();
	}

	/*
	 * Returns the total cost of the journey for the user by
	 * adding up the costs (weight) of each flight (edge).
	 */

	public int totalCost() {

		// Creates a variable of type Integer called cost, sets it to 0
		int cost = 0;

		/*
		 * Sets up an iterator of type Flight called totalCost which will check
		 * the edges (flights) of the journey.
		 */
		Iterator<Flight> totalCost = journey.getEdgeList().iterator();

		// While loop that checks if other required elements are in the list
		while (totalCost.hasNext()) {
			/*
			 *  Creates output variable of type Flight, sets it to
			 *  return the next weight (cost).
			 */
			Flight output = totalCost.next();
			/*
			 * Sets the array of String out to the output of calling
			 * the getFlightInfo method.
			 */
			String[] out = output.getFlightInfo();
			/*
			 * Sets the cost variable to the returned value from the
			 * flights.csv cost column - parsed as an integer so it can
			 * be used to calculate the overall cost of the journey
			 */
			cost += Integer.parseInt(out[5]);

		}

		//Returns the current value of the cost variable.
		return cost;		
	}

	/*
	 * This method is used to determine how long the user
	 * will be in the air for.
	 */
	public int airTime() {

		// Sets the diff variable to 0 - is of type long to more accurately determine time
		long diff = 0;

		/*
		 * format variable of type SimpleDateFormat is set to hours and minutes using
		 * Java's built in SimpleDateFormat method
		 */
		SimpleDateFormat format = new SimpleDateFormat("HHmm");

		/*
		 * For loop to go through the flight paths by using the getEdgeList
		 * method on the journey variable and setting the flight variable of
		 * type Flight to it
		 */
		for (Flight flight : journey.getEdgeList()) {

			/*
			 * Set info variable which is an array of String to the current
			 * value of the flight variable with the getFlightInfo method called
			 * on it
			 */
			String[] info = flight.getFlightInfo();

			// Set the timeLeave variable of type Date (for millisecond precision) to null
			Date timeLeave = null;

			/*
			 * Return the times from the departure times of the flights.csv
			 * file by parsing them to return a date, then assign it to the
			 * timeLeave variable. Also surround it in a try-catch loop
			 * in case there are any possible exceptions
			 */
			try {
				// Set the timeLeave variable as described above
				timeLeave = format.parse(info[4]);
			} catch (ParseException e) {
				// Print out a message for possible exceptions
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			// Set the timeArrive variable of type Date (for millisecond precision) to null
			Date timeArrive = null;

			/*
			 * Return the times from the arrival times of the flights.csv
			 * file by parsing them to return a date, then assign it to the
			 * timeArrive variable. Also surround it in a try-catch loop
			 * in case there are any possible exceptions
			 */
			try {
				// Set the timeArrive variable as described above
				timeArrive = format.parse(info[2]);
			} catch (ParseException e) {
				// Print out a message for possible exceptions
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			/*
			 * To get the total time of the flight, subtract the 
			 * milliseconds of the arrival time from the departure time
			 * and then assign it to the diff variable
			 */
			diff += timeLeave.getTime() - timeArrive.getTime();
		}

		/*
		 * Divide the diff variable by the value of 60 * 1000
		 * so that the time is represented in hours. Then cast an integer
		 * onto the diff variable for convenience and return the total
		 * air time
		 */
		return (int) diff / (60 * 1000);
	}

	/*
	 * This method allows for various methods in the program to work
	 * by allowing them to easily access the Flight class to retrieve
	 * information from it.
	 */
	public Flight getFrom() {
		// Set the start variable of type Flight to a new instance of Flight
		Flight start = new Flight();
		// Call the getFrom method on the start variable
		start.getFrom();
		// Return the start variable
		return start;
	}

	/*
	 * See airTime for further explanation on how this method works - 
	 * This method however returns the total time in connection of
	 * the journey.
	 */
	public int connectingTime() {

		// Sets the diff variable to 0 - is of type long to more accurately determine time
		long diff = 0;

		/*
		 * format variable of type SimpleDateFormat is set to hours and minutes using
		 * Java's built in SimpleDateFormat method
		 */
		SimpleDateFormat format = new SimpleDateFormat("HHmm");

		/*
		 * Set info variable which is an array of String to the current
		 * value of the flight variable with the getFlightInfo method called
		 * on it
		 */
		for (Flight flight : journey.getEdgeList()) {

			/*
			 * Set info variable which is an array of String to the current
			 * value of the flight variable with the getFlightInfo method called
			 * on it
			 */
			String[] info = flight.getFlightInfo();

			// Set the timeLeave variable of type Date (for millisecond precision) to null
			Date timeConnectLeave = null;

			/*
			 * Return the times from the arrival times of the flights.csv
			 * file by parsing them to return a date, then assign it to the
			 * timeArrive variable. Also surround it in a try-catch loop
			 * in case there are any possible exceptions
			 */
			try {
				timeConnectLeave = format.parse(info[4]);
				// Set the timeLeave variable as described above
			} catch (ParseException e) {
				// Print out a message for possible exceptions
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			/*
			 * Return the times from the arrival times of the flights.csv
			 * file by parsing them to return a date, then assign it to the
			 * timeArrive variable. Also surround it in a try-catch loop
			 * in case there are any possible exceptions
			 */
			Date timeConnectArrive = null;
			try {
				// Set the timeArrive variable as described above
				timeConnectArrive = format.parse(info[2]);
			} catch (ParseException e) {
				// Print out a message for possible exceptions
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			/*
			 * To get the total time of the flight, subtract the 
			 * milliseconds of the arrival time from the departure time
			 * and then assign it to the diff variable
			 */
			diff += timeConnectLeave.getTime() - timeConnectArrive.getTime();
		}

		/*
		 * Divide the diff variable by the value of 60 * 1000
		 * so that the time is represented in hours. Then cast an integer
		 * onto the diff variable for convenience and return the total
		 * time in connection of the journey
		 */
		return (int) diff / (60 * 1000);
	}

	/*
	 * See airTime for further explanation on how this works -
	 * This method however returns the overall total time the user
	 * will have travelled across their entire journey, not just
	 * flight time.
	 */
	public int totalTime() {
		// Sets the diff variable to 0 - is of type long to more accurately determine time
		long diff = 0;

		/*
		 * format variable of type SimpleDateFormat is set to hours and minutes using
		 * Java's built in SimpleDateFormat method
		 */
		SimpleDateFormat format = new SimpleDateFormat("HHmm");

		/*
		 * Set info variable which is an array of String to the current
		 * value of the flight variable with the getFlightInfo method called
		 * on it
		 */
		for (Flight flight : journey.getEdgeList()) {

			/*
			 * Set info variable which is an array of String to the current
			 * value of the flight variable with the getFlightInfo method called
			 * on it
			 */
			String[] info = flight.getFlightInfo();

			// Set the timeTotal variable of type Date (for millisecond precision) to null
			Date timeTotalLeave = null;

			/*
			 * Return the times from the arrival times of the flights.csv
			 * file by parsing them to return a date, then assign it to the
			 * timeTotal variable. Also surround it in a try-catch loop
			 * in case there are any possible exceptions
			 */
			try {
				timeTotalLeave = format.parse(info[4]);
				// Set the timeTotal variable as described above
			} catch (ParseException e) {
				// Print out a message for possible exceptions
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			/*
			 * Return the times from the arrival times of the flights.csv
			 * file by parsing them to return a date, then assign it to the
			 * timeTotal2 variable. Also surround it in a try-catch loop
			 * in case there are any possible exceptions
			 */
			Date timeTotalArrive = null;
			try {
				// Set the timeTotal2 variable as described above
				timeTotalArrive = format.parse(info[2]);
			} catch (ParseException e) {
				// Print out a message for possible exceptions
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			/*
			 * To get the total travel time of the journey, subtract the 
			 * milliseconds of the first instance of total time from the second
			 * instance of total time and then assign it to the diff variable
			 */
			diff += timeTotalLeave.getTime() - timeTotalArrive.getTime();
		}

		/*
		 * Divide the diff variable by the value of 60 * 1000
		 * so that the time is represented in hours. Then cast an integer
		 * onto the diff variable for convenience and return the total
		 * travel time
		 */
		return (int) diff / (60 * 1000);

	}
}
