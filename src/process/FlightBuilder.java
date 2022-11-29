package process;

import data.Airport;
import data.Block;
import data.Flight;

/**
 * 
 * This class allows an flight construction.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class FlightBuilder {

	int position = 0;

	/**
	 * Creates a flight from departure airport to arrival airport.
	 * 
	 * @param departureAirport the start of the flight.
	 * @param arrivalAirport   the end of the flight.
	 * @param simulation
	 * @return flight the flight between the two airports.
	 */
	public Flight buildFlight(Airport departureAirport, Airport arrivalAirport, Simulation simulation) {

		Flight flight = new Flight(departureAirport, arrivalAirport);

		flight.addBlock(departureAirport.getPosition());

		buildWay(arrivalAirport, simulation, flight);

		return flight;
	}

	/**
	 * Creates a flight from the aircraft position to arrival airport. We start from
	 * the aircraft position and not from the departure airport.
	 * 
	 * @param departureAirport the start of the flight.
	 * @param arrivalAirport   the end of the flight.
	 * @param simulation
	 * @param currentPosition  the aircraft current position
	 * @return flight the flight between the aircraft current position and the
	 *         arrival airport.
	 */
	public Flight buildUrgentFlight(Airport departureAirport, Airport arrivalAirport, Simulation simulation,
			Block currentPosition) {
		Flight flight = new Flight(departureAirport, arrivalAirport);

		flight.addBlock(currentPosition);

		buildWay(arrivalAirport, simulation, flight);

		return flight;
	}

	/**
	 * Builds a way between airports. Increments block until the last block equals
	 * at the airport block.
	 * 
	 * @param arrivalAirport the end of the way.
	 * @param simulation
	 * @param flight         the initialized flight
	 */
	private void buildWay(Airport arrivalAirport, Simulation simulation, Flight flight) {
		position = 0;

		while ((flight.getBlockByPosition(position).getY() != arrivalAirport.getPosition().getY())
				|| (flight.getBlockByPosition(position).getX() != arrivalAirport.getPosition().getX())) {
			Block previousBlock = null;

			Block currentBlock = flight.getBlocks().get(flight.getBlocks().size() - 1);
			Block nextBlock = flight.getBlocks().get(flight.getBlocks().size() - 1);

			if (position > 0) {
				previousBlock = flight.getBlockByPosition(position - 1);
			}

			if (arrivalAirport.getPosition().getY() > currentBlock.getY()) {
				currentBlock = new Block(currentBlock.getX(), currentBlock.getY() + 1);
			}
			if (arrivalAirport.getPosition().getY() < currentBlock.getY()) {
				currentBlock = new Block(currentBlock.getX(), currentBlock.getY() - 1);
			}
			if (arrivalAirport.getPosition().getX() > currentBlock.getX()) {
				currentBlock = new Block(currentBlock.getX() + 1, currentBlock.getY());
			}
			if (arrivalAirport.getPosition().getX() < currentBlock.getX()) {
				currentBlock = new Block(currentBlock.getX() - 1, currentBlock.getY());
			}

			// If the aircraft will meet an obstacle, it will change a way to round this
			// one.
			if (simulation.getBlockManager(currentBlock).isAnObstacle() == true && previousBlock != null
					&& simulation.getBlockManager(currentBlock).getObstacle().getAltitude() > 7000) {
				if (simulation.getBlockManager(currentBlock).getBlock().getY() > previousBlock.getY()) {

					roundObstacleByTop(simulation, flight, currentBlock, nextBlock);

				} else if (simulation.getBlockManager(currentBlock).getBlock().getY() < previousBlock.getY()) {

					roundObstacleByBot(simulation, flight, currentBlock, nextBlock);

				} else if (simulation.getBlockManager(currentBlock).getBlock().getX() > previousBlock.getX()) {

					roundObstacleByLeft(simulation, flight, currentBlock, nextBlock);

				} else {

					roundObstacleByRight(simulation, flight, currentBlock, nextBlock);
				}

			} else {
				flight.addBlock(currentBlock);
				position++;
			}
		}
	}

	public void roundObstacleByTop(Simulation simulation, Flight flight, Block currentBlock, Block nextBlock) {
		int firstBlock = simulation.getBlockManager(currentBlock).getObstacle().getTopBlocks().get(0).getX();
		int lastBlock = simulation.getBlockManager(currentBlock).getObstacle().getTopBlocks()
				.get((simulation.getBlockManager(currentBlock).getObstacle().getTopBlocks().size() - 1)).getX();

		if ((currentBlock.getX() - firstBlock) < (lastBlock - currentBlock.getX())) {
			while (nextBlock.getX() != firstBlock - 1) {
				nextBlock = new Block(nextBlock.getX() - 1, nextBlock.getY());
				flight.addBlock(nextBlock);
				position++;
			}

		} else {
			while (nextBlock.getX() != lastBlock + 1) {
				nextBlock = new Block(nextBlock.getX() + 1, nextBlock.getY());
				flight.addBlock(nextBlock);
				position++;
			}
		}
		while (nextBlock.getY() != (simulation.getBlockManager(currentBlock).getObstacle().getLeftBlocks()
				.get((simulation.getBlockManager(currentBlock).getObstacle().getLeftBlocks().size() - 1)).getY())) {

			nextBlock = new Block(nextBlock.getX(), nextBlock.getY() + 1);
			flight.addBlock(nextBlock);
			position++;
		}
	}

	public void roundObstacleByBot(Simulation simulation, Flight flight, Block currentBlock, Block nextBlock) {
		int firstBlock = simulation.getBlockManager(currentBlock).getObstacle().getBotBlocks().get(0).getX();
		int lastBlock = simulation.getBlockManager(currentBlock).getObstacle().getBotBlocks()
				.get((simulation.getBlockManager(currentBlock).getObstacle().getBotBlocks().size() - 1)).getX();

		if ((currentBlock.getX() - firstBlock) < (lastBlock - currentBlock.getX())) {
			while (nextBlock.getX() != firstBlock - 1) {
				nextBlock = new Block(nextBlock.getX() - 1, nextBlock.getY());
				flight.addBlock(nextBlock);
				position++;
			}

		} else {
			while (nextBlock.getX() != lastBlock + 1) {
				nextBlock = new Block(nextBlock.getX() + 1, nextBlock.getY());
				flight.addBlock(nextBlock);
				position++;
			}
		}
		while (nextBlock
				.getY() != (simulation.getBlockManager(currentBlock).getObstacle().getLeftBlocks().get(0).getY())) {
			nextBlock = new Block(nextBlock.getX(), nextBlock.getY() - 1);
			flight.addBlock(nextBlock);
			position++;
		}
	}

	public void roundObstacleByLeft(Simulation simulation, Flight flight, Block currentBlock, Block nextBlock) {
		int firstBlock = simulation.getBlockManager(currentBlock).getObstacle().getLeftBlocks().get(0).getY();
		int lastBlock = simulation.getBlockManager(currentBlock).getObstacle().getLeftBlocks()
				.get((simulation.getBlockManager(currentBlock).getObstacle().getLeftBlocks().size() - 1)).getY();

		if ((currentBlock.getY() - firstBlock) < (lastBlock - currentBlock.getY())) {
			while (nextBlock.getY() != firstBlock - 1) {
				nextBlock = new Block(nextBlock.getX(), nextBlock.getY() - 1);
				flight.addBlock(nextBlock);
				position++;
			}

		} else {
			while (nextBlock.getY() != lastBlock + 1) {
				nextBlock = new Block(nextBlock.getX(), nextBlock.getY() + 1);
				flight.addBlock(nextBlock);
				position++;
			}
		}
		while (nextBlock.getX() != (simulation.getBlockManager(currentBlock).getObstacle().getTopBlocks()
				.get((simulation.getBlockManager(currentBlock).getObstacle().getTopBlocks().size() - 1)).getX())) {
			nextBlock = new Block(nextBlock.getX() + 1, nextBlock.getY());
			flight.addBlock(nextBlock);
			position++;
		}
	}

	public void roundObstacleByRight(Simulation simulation, Flight flight, Block currentBlock, Block nextBlock) {
		int firstBlock = simulation.getBlockManager(currentBlock).getObstacle().getRightBlocks().get(0).getY();
		int lastBlock = simulation.getBlockManager(currentBlock).getObstacle().getRightBlocks()
				.get((simulation.getBlockManager(currentBlock).getObstacle().getRightBlocks().size() - 1)).getY();

		if ((currentBlock.getY() - firstBlock) < (lastBlock - currentBlock.getY())) {
			while (nextBlock.getY() != firstBlock - 1) {
				nextBlock = new Block(nextBlock.getX(), nextBlock.getY() - 1);
				flight.addBlock(nextBlock);
				position++;
			}

		} else {
			while (nextBlock.getY() != lastBlock + 1) {
				nextBlock = new Block(nextBlock.getX(), nextBlock.getY() + 1);
				flight.addBlock(nextBlock);
				position++;
			}
		}
		while (nextBlock
				.getX() != (simulation.getBlockManager(currentBlock).getObstacle().getTopBlocks().get(0).getX())) {
			nextBlock = new Block(nextBlock.getX() - 1, nextBlock.getY());
			flight.addBlock(nextBlock);
			position++;
		}
	}
}
