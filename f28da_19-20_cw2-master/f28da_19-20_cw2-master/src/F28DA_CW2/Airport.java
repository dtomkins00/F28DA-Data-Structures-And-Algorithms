package F28DA_CW2;

import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;

/**
 * This class is used to retrieve the values from the airports.csv file
 * for use elsewhere in the program. It also provides some constructors
 * to be used by other methods elsewhere in the program.
 * 
 * @version 1.0
 * @since 28-11-19 
 * 
 */

@SuppressWarnings("unused")
public class Airport extends DefaultWeightedEdge implements IAirportPartB, IAirportPartC {

	private static final long serialVersionUID = 1L;

	// Creates a field of type String[] called airportInfo which stores values from airports.csv
	private String[] airportInfo;

	// Creates a field of type String called string
	private String string;

	/*
	 * Constructor used to initialise the flightInfo parameter so it can be used
	 * elsewhere in the program.
	 */
	public Airport(String[] flightInfo) {

		// Sets the airportInfo field to the flightInfo parameter.
		airportInfo = flightInfo;
	}

	/*
	 * This constructor is created so that string can be referenced elsewhere
	 * in the program, allowing for methods to read in values from this class.
	 */
	public Airport(String string) {

		// Appends this. to the field string and sets it to the parameter string.
		this.string = string;
	}

	/*
	 * Setter method for airportInfo so it can be referenced elsewhere in the program -
	 * also allowing for methods to read from this class.
	 */
	public void setAirportInfo(String[] airportInfo) {

		// Appends this. to the field airportInfo and sets it to the parameter airportInfo.
		this.airportInfo = airportInfo;
	}

	// Returns values from the first column of airports.csv, in this case the airport codes.
	public String getCode() {
		return airportInfo[0];
	}

	// Returns values from the second column of airports.csv, in this case the city names.
	public String getName() {
		return airportInfo[1];
	}

	// Returns values from the third column of airports.csv, in this case the airport names.
	public String getAirportName() {
		return airportInfo[2];
	}

	/////////////////////////////////////////////////////// Part C below

	@Override
	public void setDirectlyConnected(Set<Airport> directlyConnected) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Airport> getDirectlyConnected() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDirectlyConnectedOrder(int order) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
