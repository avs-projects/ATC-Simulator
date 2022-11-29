package process;

import java.util.ArrayList;

import config.Configuration;
import data.Aircraft;
import data.Airport;
import data.Clock;
import data.Flight;

/**
 * 
 * This class allows an aircraft construction.
 *
 */
public class AircraftBuilder {

	private Aircraft aircraft;

	private FlightBuilder flightBuilder = new FlightBuilder();

	/**
	 * Stores all created aircrafts.
	 */
	private ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();

	/**
	 * This boolean allows the aircraft construction if conditions are met.
	 */
	private boolean isBuilt = false;

	public AircraftBuilder() {
		super();
	}
	
	/**
	 * Build an aircraft randomly
	 * 
	 * @param simulation 
	 * @param clock the time when the aircraft takes off
	 */
	public void buildAircraft(Simulation simulation, Clock clock) {
		ArrayList<Airport> airports = new ArrayList<Airport>();

		for (int index = 0; index < Configuration.AIRPORTS.length; index++) {
			airports.add(Configuration.AIRPORTS[index]);
		}

		Airport departureAirport = null;
		Airport arrivalAirport = null;

		int random = SimulationUtility.getRandom(0, 3);
		int random2 = SimulationUtility.getRandom(0, 2);

		departureAirport = airports.get(random);
		airports.remove(random);

		arrivalAirport = airports.get(random2);

		for (Airport airport : simulation.getMap().getAirports()) {
			if (airport == departureAirport) {
				for (Airport airport2 : simulation.getMap().getAirports()) {
					if (airport2 == arrivalAirport) {
						// If the runway is occupied, the aircraft can't be created.
						if (airport.findRunway(airport2).getCurrentAircraft() != null) {
							isBuilt = false;
						} else {
							isBuilt = true;
						}
					}
				}
			}
		}

		Flight flight = null;

		if (isBuilt == true) {
			flight = flightBuilder.buildFlight(departureAirport, arrivalAirport, simulation);
			aircraft = new Aircraft(flight.getDepartureAirport().getPosition(), flight, clock);
			aircrafts.add(aircraft);
		}
	}

	public boolean isBuilt() {
		return isBuilt;
	}

	public Aircraft getLastAircraft() {
		return aircrafts.get(aircrafts.size() - 1);
	}

}
