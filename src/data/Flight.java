package data;

import java.util.ArrayList;

/**
 * 
 * The flight represents the way to go for an aircraft.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class Flight {

	private Airport departureAirport;
	private Airport arrivalAirport;

	/*
	 * Between a departure and an arrival airport, there is a way represented by blocks.
	 */
	private ArrayList<Block> blocks = new ArrayList<Block>();

	/*
	 * The step flight is where the aircraft is on a block.
	 */
	int stepFlight = 0;

	public Flight(Airport departureAirport, Airport arrivalAirport) {
		super();
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}

	public Block getCurrentBlock() {
		return blocks.get(stepFlight);
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public int getStepFlight() {
		return stepFlight;
	}

	public Block getBlockByPosition(int position) {
		return blocks.get(position);
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	public void incrementStepFlight() {
		stepFlight++;
	}
}
