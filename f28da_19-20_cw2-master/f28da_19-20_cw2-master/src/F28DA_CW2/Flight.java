package F28DA_CW2;

//import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * This class is used to retrieve the values from the flights.csv file
 * for use elsewhere in the program. It also provides some constructors
 * to be used by other methods elsewhere in the program.
 * 
 * @version 1.0
 * @since 28-11-19 
 * 
 */

public class Flight extends DefaultWeightedEdge implements IFlight {

	private static final long serialVersionUID = 1L;

	// Creates a new field called flightInfo set to an array of String
	private String[] flightInfo;

	/*
	 * This constructor is used to initialise the parameter code
	 * using the flightInfo variable so that the code parameter can
	 * use info from Flight.java elsewhere in the program.
	 */
	public Flight(String[] code, String code1) {

		// Sets the flightInfo field to the array of String, code.
		flightInfo = code;
	}

	// Constructor to allow the string parameter Code to be used.
	public Flight(String code) {
	}

	// Allows for other methods in the program to access the Flight class.
	public Flight() {
	}

	/*
	 * This method is a setter method for flightInfo which is designed to
	 * make it so that the field flightInfo can be referenced
	 * elsewhere in the program.
	 */
	public void setFlightInfo(String[] flightInfo) {
		this.flightInfo = flightInfo;
	}

	// Getter method for flightInfo, used to return the current value of flightInfo
	public String[] getFlightInfo() {
		return flightInfo;
	}

	// Returns the values in the first column of flights.csv, in this case the flight codes
	public String getFlightCode() {
		return flightInfo[0];
	}

	/*
	 * Creates a new instance of Airport in order to return the values in the fourth
	 * column of flights.csv, in this case, the destination airport
	 */
	public Airport getTo() {
		return new Airport (flightInfo[3]);
	}

	/*
	 * Creates a new instance of Airport in order to return the values in the second
	 * column of flights.csv, in this case, the starting airport
	 */
	public Airport getFrom() {
		return new Airport (flightInfo[1]);	
	}

	// Returns values from the third column of flights.csv, in this case the departure time
	public String getFromGMTime() {
		return flightInfo[2];
	}

	// Returns values from the fifth column of flights.csv, in this case the arrival time
	public String getToGMTime() {
		return flightInfo[4];	
	}

	/*
	 * Returns values from the sixth column of flights.csv, in this case the flight cost -
	 * to return the cost as an integer so the costs can be added together, the string
	 * is parsed into an integer.
	 */
	public int getCost() {
		return Integer.parseInt(flightInfo[5]);
	}

}
