package data;

/**
 * 
 * This class represents a runway airport.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class Runway {

	/*
	 * This the time occupied by an aircraft.
	 * When it reaches a certain number, the aircraft takes off.
	 */
	private int occupiedTime = 0;

	private Airport arrivalAirport;
	private Aircraft currentAircraft;

	public Runway(Airport arrivalAirport) {
		super();
		this.arrivalAirport = arrivalAirport;
	}

	public void incrementTime() {
		occupiedTime++;
	}

	public int getOccupiedTime() {
		return occupiedTime;
	}

	public void setOccupiedTime(int occupiedTime) {
		this.occupiedTime = occupiedTime;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Aircraft getCurrentAircraft() {
		return currentAircraft;
	}

	public void setCurrentAircraft(Aircraft currentAircraft) {
		this.currentAircraft = currentAircraft;
	}

}
