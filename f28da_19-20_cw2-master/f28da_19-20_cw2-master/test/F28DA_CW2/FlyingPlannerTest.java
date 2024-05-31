package F28DA_CW2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * These JUnit 4 test cases are used to check various methods within the implementation
 * of the flight graph and see if they are working to ensure that the program is
 * robust and works as expected.
 * 
 * @version 1.0
 * @since 28-11-19 
 * 
 */

public class FlyingPlannerTest {

	FlyingPlanner fi;

	// Initialises the variable fi using an instance of FlyingPlanner
	@Before
	public void initialize() {
		fi = new FlyingPlanner();
		try {
			fi.populate(new FlightsReader());
		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
		}
	}

	// Tests whether returning the airport code for airports is working
	@Test
	public void airportCodeTest() {
		Airport i = fi.airport("EDI");
		assertEquals("EDI", i.getCode());
	}
	
	// Tests whether returning the departure time for flights is working
	@Test
	public void flightDepartureTimeTest() {
		Flight i = fi.flight("1343");
		assertEquals("1343", i.getFromGMTime());
	}
	
	// Tests whether returning the flight code is working
	@Test
	public void flightCodeTest() {
		Flight i = fi.flight("LH7679");
		assertEquals("LH7679", i.getFlightCode());
	}

}
