package data;

import java.util.ArrayList;

/**
 * 
 * This class represents an airport where the aircraft can land or take off.
 * 
 * An airport has runways that will each be managed.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class Airport {

	private String name;

	/**
	 * The position of the airport represented by a block;
	 */
	private Block position;

	private ArrayList<Runway> runways = new ArrayList<Runway>();

	public Airport(String name, Block position) {
		super();
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public Block getPosition() {
		return position;
	}

	public ArrayList<Runway> getRunways() {
		return runways;
	}

	public void addRunway(Runway runway) {
		runways.add(runway);
	}

	/**
	 * Looking for a runway with the arrival airport.
	 * 
	 * @param airport the arrival airport.
	 * @return the corresponding runway.
	 */
	public Runway findRunway(Airport airport) {
		// Looking for a runway with the arrival airport.
		for (Runway runway : runways) {
			if (runway.getArrivalAirport().getName().equals(airport.getName())) {
				return runway;
			}
		}
		return null;
	}
}
