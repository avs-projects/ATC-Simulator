package process;

import config.Configuration;
import data.Aircraft;
import data.Airport;
import data.Block;
import data.Runway;

/**
 * 
 * This class is the train controller. Each instance represents an aircraft on a
 * block flight.
 *
 */

public class AircraftManager extends Thread {

	private Aircraft aircraft;
	private Runway runway;

	private BlockManager blockManager;
	private Simulation simulation;

	/**
	 * The aircraft departs from the departure airport.
	 */
	private boolean running = true;

	public AircraftManager(Aircraft aircraft, Simulation simulation) {
		super();
		this.aircraft = aircraft;
		this.simulation = simulation;
	}

	@Override
	public void run() {
		
		// Looking for a corresponding runway depending on the departure and arrival
		// airport.
		lookingForARunway();

		while (running == true) {
			
			SimulationUtility.unitTime();

			// The aircraft prepares for take off.
			if (runway.getOccupiedTime() < Configuration.TIME_FOR_TAKE_OFF && runway.getCurrentAircraft() == aircraft) {

				runway.incrementTime();

			} else if (runway.getCurrentAircraft() == aircraft) {

				runway.setOccupiedTime(0);
				runway.setCurrentAircraft(null);

			} else {
				
				// Increments the aircraft flight.
				aircraft.getFlight().incrementStepFlight();

				// The altitude varies during the flight.
				setAircraftAltitude();

				BlockManager nextBlockManager = simulation.getBlockManager(aircraft.getFlight().getCurrentBlock());

				// The aircraft will enter the next block.
				nextBlockManager.enter(this);

				// This is a probability that the aircraft can be deviant.
				if (this.aircraft.getAlert() == false) {
					if (SimulationUtility.getRandom(0, 10000) < 2) {
						this.aircraft.setAlert(true);
						createFlightForUrgence();
					}
				}

				running = continueRunning(aircraft.getFlight().getCurrentBlock());

			}

		}

		// The aircraft leaves the last occupied block.
		blockManager.exit();
		simulation.getAircraftManagers().remove(this);
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public BlockManager getBlockManager() {
		return blockManager;
	}

	public void setBlockManager(BlockManager blockManager) {
		this.blockManager = blockManager;
	}

	public void updatePosition(Block position) {
		aircraft.setCurrentPosition(position);
	}

	public boolean isRunning() {
		return running;
	}

	private void setAircraftAltitude() {
		if (aircraft.getFlight().getStepFlight() == 1
				|| aircraft.getFlight().getStepFlight() == aircraft.getFlight().getBlocks().size() - 2) {
			aircraft.setAltitude(SimulationUtility.getRandom(4000, 7000));
		} else {
			aircraft.setAltitude(SimulationUtility.getRandom(9200, 12000));
		}
	}

	private void lookingForARunway() {
		for (Airport airport : simulation.getMap().getAirports()) {
			if (airport == aircraft.getFlight().getDepartureAirport()) {
				runway = airport.findRunway(aircraft.getFlight().getArrivalAirport());
				runway.setCurrentAircraft(aircraft);
			}
		}
	}

	private boolean continueRunning(Block block) {
		if ((aircraft.getFlight().getArrivalAirport().getPosition().getX() == block.getX())
				&& (aircraft.getFlight().getArrivalAirport().getPosition().getY() == block.getY())) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Generates a flight for an urgent aircraft.
	 */
	public void createFlightForUrgence() {
		FlightBuilder flightBuilder = new FlightBuilder();

		removeInitialWay();

		Airport closerAirport = createWay();

		aircraft.setFlight(flightBuilder.buildUrgentFlight(aircraft.getFlight().getDepartureAirport(), closerAirport,
				simulation, aircraft.getCurrentPosition()));
	}

	/**
	 * Allows to remove the initial way to create a new one to reach a proximity
	 * airport.
	 */
	private void removeInitialWay() {
		int size = (aircraft.getFlight().getBlocks().size() - 1);
		int stepFlight = aircraft.getFlight().getStepFlight();

		for (int index = stepFlight; index < (size - stepFlight); index++) {
			aircraft.getFlight().getBlocks().remove((aircraft.getFlight().getBlocks().size() - 1));
		}
	}

	/**
	 * Create a new way for an urgent aircraft
	 * 
	 * @return closerAirport
	 */

	private Airport createWay() {
		Airport closerAirport = Configuration.AIRPORTS[0];

		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;

		// Looking for the closest airport.
		for (int index = 1; index < Configuration.AIRPORTS.length; index++) {

			Airport currentAirport = Configuration.AIRPORTS[index];

			if (currentAirport.getPosition().getX() > aircraft.getCurrentPosition().getX()) {
				x1 = currentAirport.getPosition().getX() - aircraft.getCurrentPosition().getX();
			} else {
				x1 = aircraft.getCurrentPosition().getX() - currentAirport.getPosition().getX();
			}

			if (currentAirport.getPosition().getY() > aircraft.getCurrentPosition().getY()) {
				y1 = currentAirport.getPosition().getY() - aircraft.getCurrentPosition().getY();
			} else {
				y1 = aircraft.getCurrentPosition().getY() - currentAirport.getPosition().getY();
			}

			if (closerAirport.getPosition().getX() > aircraft.getCurrentPosition().getX()) {
				x2 = closerAirport.getPosition().getX() - aircraft.getCurrentPosition().getX();
			} else {
				x2 = aircraft.getCurrentPosition().getX() - closerAirport.getPosition().getX();
			}
			if (closerAirport.getPosition().getY() > aircraft.getCurrentPosition().getY()) {
				y2 = closerAirport.getPosition().getY() - aircraft.getCurrentPosition().getY();
			} else {
				y2 = aircraft.getCurrentPosition().getY() - closerAirport.getPosition().getY();
			}

			if (x1 + y1 < x2 + y2) {
				closerAirport = Configuration.AIRPORTS[index];
			}
		}
		return closerAirport;
	}

}
