package data;

import process.SimulationUtility;

/**
 * 
 * This class represents an aircraft for a particular flight.
 * 
 * @author VVAZ SILVA Alexandre
 *
 */
public class Aircraft {

	/**
	 * The current position is the block where the aircraft is.
	 */
	private Block currentPosition;

	/**
	 * The flight is the way the aircraft has to go.
	 */
	private Flight flight;

	/**
	 * A name code with a letter and three numbers.
	 */
	private String name;

	/**
	 * The aircraft can be alerted for an emergency landing.
	 */
	private Boolean alert = false;

	/**
	 * This represents the altitude of the aircraft which can reach around 10 000 m.
	 */
	private int altitude = 0;

	/**
	 * This representes the time where the aircraft takes off.
	 */
	private Clock clockDeparture;

	public Aircraft(Block currentPosition, Flight flight, Clock clockDeparture) {
		super();
		this.currentPosition = currentPosition;
		this.flight = flight;
		this.clockDeparture = clockDeparture;
		this.name = SimulationUtility.createRandomName();
	}

	public Block getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Block currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getName() {
		return name;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public Clock getClockDeparture() {
		return clockDeparture;
	}

}
