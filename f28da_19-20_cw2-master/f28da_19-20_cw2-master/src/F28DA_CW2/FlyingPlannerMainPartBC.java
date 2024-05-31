package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import F28DA_CW2.FlyingPlanner;

/**
 * This class is designed to serve as the user interface for the program where 
 * the user will enter airport codes to determine the shortest flight route
 * that they can take.
 *
 * @version 1.0
 * @since 28-11-19
 *
 */

public class FlyingPlannerMainPartBC {

	public static void main(String[] args) {

		/*
		 * This is used to initialise the FlyingPlanner so that it can be used
		 * in the interface and for the output of the program.
		 */
		FlyingPlanner fi;
		// Create a new instance of FlyingPlanner and set it to fi
		fi = new FlyingPlanner();
		// Create a try-catch loop in case any exceptions pop up
		try {
			// Call the populate method on fi using the FlightsReader
			fi.populate(new FlightsReader());

			// This code block takes input from the user which is airport codes
			System.out.println("Please enter your starting airports code.");
			// Create new scanner to take input for starting airport
			Scanner start = new Scanner(System.in);
			// Store this input in a String variable called from
			String from = start.nextLine();

			// This code block functions similarly to the above one
			System.out.println("Please enter the destination airports code.");
			// Create new scanner called destination to take input for destination airport
			Scanner destination = new Scanner(System.in);
			// Store the input in a String variable called to
			String to = destination.nextLine();

			/*
			 * Notifies the user that they can choose to avoid airports
			 * by typing them in, also tells them they can exit this process
			 */
			System.out.println("If you have airports you wish to avoid, enter their codes. Once you are done, type 'exit'.");

			/*
			 * Creates a list of String called avoid and sets it to a new 
			 * LinkedList of type String
			 */
			List<String> exclude = new LinkedList<String>();

			// Prints the route that the user will be travelling on
			System.out.println("Route for " + from + " to " + to);

			/*
			 * This block of code iterates through the airports.csv file to
			 * return the desired airport code that will be used in calculating
			 * the shortest flight route for the user - starts by creating an
			 * array of String called from1, initialises it to null. This is for
			 * departures.
			 */
			String[] from1 = null;

			/*
			 * Sets up an iterator of type String called fiIterator which will use the
			 * g2 graph in FlyingPlanner to go and iterate through vertices
			 * (airport codes) in the airports.csv file
			 */
			Iterator<String[]> fiIterator = FlyingPlanner.g2.vertexSet().iterator();

			// While loop checking if fiIterator has more required elements to look at
			while (fiIterator.hasNext()) {

				/*
				 * An array called output of type String is created and it is
				 * set to register the next element iterated using fiIterator
				 */
				String output[] = fiIterator.next();

				/*
				 * If statement that checks to see if the users input matches
				 * the current element being iterated through in the output
				 * variable
				 */
				if (output[0].equals(from)) {
					// Sets the from1 variable to the current output value
					from1 = output;
				}

			}

			/*
			 * This block of code iterates through the airports.csv file to
			 * return the desired airport code that will be used in calculating
			 * the shortest flight route for the user - starts by creating an
			 * array of String called from1, initialises it to null. This is for
			 * arrivals.
			 */
			String[] to1 = null;

			/*
			 * Sets up an iterator of type String called fiIterator2 which will use the
			 * g2 graph in FlyingPlanner to go and iterate through vertices
			 * (airport codes) in the airports.csv file
			 */
			Iterator<String[]> fiIterator2 = FlyingPlanner.g2.vertexSet().iterator();

			// While loop checking if fiIterator2 has more required elements to look at
			while (fiIterator2.hasNext()) {

				/*
				 * An array called output of type String is created and it is
				 * set to register the next element iterated using fiIterator2
				 */
				String output[] = fiIterator2.next();

				/*
				 * If statement that checks to see if the users input matches
				 * the current element being iterated through in the output
				 * variable
				 */
				if (output[0].equals(to)) {

					// Sets the to1 variable to the current output value
					to1 = output;
				}
			}

			/*
			 * Create a new graph called leastCost which is comprised of the
			 * types String[] and Journey and utilises DijkstraShortestPath
			 * to find the shortest path between the airport codes that were
			 * entered by the user - this also allows for the path to have
			 * the lowest cost possible
			 */
			GraphPath<String[], Journey> leastCost = DijkstraShortestPath.findPathBetween(FlyingPlanner.g2, from1, to1);

			/*
			 * Create a new variable of type Journey called j, and set this to
			 * the leastCost variable which has been given the type Journey
			 */
			Journey j = new Journey (leastCost);

			// Assign the from1, to1 and j variables values to output
			output(from1, to1, j);

			start.close();
			destination.close();

		} catch (FileNotFoundException | FlyingPlannerException e) {
			// Print out message if an exception is found
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * This method is what prints out the results of the search for the 
	 * shortest flight route - it will print out what flights the user should
	 * take, the cost of these flights and the air time they will have by
	 * the end of the journey.
	 *
	 * Start off by taking the parameters of type String[] from and to as well
	 * as the parameter journey, which has type Journey.
	 */
	public static void output(String[] from, String[] to, Journey journey) {

		// Creates a variable of type Integer called count and sets it to 1
		int count = 1;

		/*
		 * For loop that gets the flights the user is travelling on
		 * using the Journey class, creates a variable of type Journey
		 * called edge
		 */
		for (Journey edge : journey.getFlightEdges()) {

			/*
			 * Creates an array of String called source which will
			 * find the starting vertice of the edge (flight) using the g2
			 * graph in FlyingPlanner as well as calling the JGraphT
			 * getEdgeSource method on the edge variable before assigning
			 * the airport code to the source variable.
			 */
			String[] source = FlyingPlanner.g2.getEdgeSource(edge);

			/*
			 * Creates an array of String called target which will
			 * find the end vertice of the edge (flight) using the g2
			 * graph in FlyingPlanner as well as calling the JGraphT
			 * getEdgeTarget method on the edge variable before assigning
			 * the airport code to the target variable.
			 */
			String[] target = FlyingPlanner.g2.getEdgeTarget(edge);

			/*
			 * Creates a variable called info which has type String[]
			 * and assigns it to the other data columns in the flights.csv
			 * file so that the rest of the data can be retrieved from it
			 */
			String[] info = edge.getFlightEdges();

			/*
			 * Prints out headings for each column of information using Java's
			 * printf method to format this information so that it will be 
			 * printed in a specific way
			 */
			System.out.printf("%5s%15s%10s%10s%15s%10s\n", "Leg", "Leave", "At", "On", "Arrive", "At");

			/*
			 * Prints out the obtained values of the variables that have been
			 * taking data from the .csv files and used in the main method for
			 * each column of information using Java's printf method to format
			 * this information so that it will be printed in a specific way
			 */
			System.out.printf("%5s%15s%10s%10s%15s%10s\n", count, source[1], info[2], info[0], target[1], info[4]);

			// Add 1 onto the count variable for each flight the user takes
			count++;
		}

		/*
		 * Calculates the minutes taken in the flight by calling the airTime
		 * method from the Journey class
		 */
		float minutes = journey.airTime();

		/*
		 * Gets the hours travelled by the user by dividing the minutes
		 * by 60
		 */
		float hours = minutes / 60;

		// Prints the total cost of the journey
		System.out.println("Total Journey Cost: £" + journey.totalCost());

		/* Prints the hours taken to take the journey using Java's Maths.abs
		 * to make the hours value absolute so it is easier to interpret
		 */
		System.out.println("Total Time in the Air: " + Math.abs(hours) + " hours");
	}
}
