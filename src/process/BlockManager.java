package process;

import config.Configuration;
import data.Block;
import data.Obstacle;

/**
 * 
 * This class is a block controller. It controls the entry and the exit of each
 * aircraft in a block.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class BlockManager {
	private Block block;

	private boolean isOccupied = false;
	private boolean isAnObstacle = false;

	private Obstacle obstacle;

	public BlockManager(Block block) {
		super();
		this.block = block;
	}

	/**
	 * Make an aircraft enter into to the block. If the block is not free, the
	 * aircraft will change his speed. Attention : In a large-scale, we consider
	 * that the aircraft will slow down but in fact, he will wait.
	 * 
	 * When the aircraft is in emergency, he will enter the block even if there is
	 * an aircraft in the block, but he must change his altitude.
	 * 
	 * @param aircraftManager the aircraft asking for entry.
	 */
	public synchronized void enter(AircraftManager aircraftManager) {

		// The method "isAnAeroport(Block block)" allows to several aircrafts to take
		// off from the same airport.

		if (isOccupied != false && isAnAeroport(block) == false) {
			if (aircraftManager.getAircraft().getAlert() == true) {
				aircraftManager.getAircraft().setAltitude(SimulationUtility.getRandom(7500, 9000));
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}

		}

		// The train leaves and frees its previous block.

		BlockManager previousBlockManager = aircraftManager.getBlockManager();
		previousBlockManager.exit();

		// The train entries into this block.

		aircraftManager.setBlockManager(this);
		isOccupied = true;

		aircraftManager.updatePosition(block);

	}

	/**
	 * Makes the occupying train leave and notifies the waiting train.
	 */
	public synchronized void exit() {
		isOccupied = false;

		/*
		 * Notify the waiting train (which can enter into the block now).
		 */
		notify();
	}

	public boolean isFree() {
		return isOccupied == false;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public boolean isAnObstacle() {
		return isAnObstacle;
	}

	public void setAnObstacle(boolean isAnObstacle) {
		this.isAnObstacle = isAnObstacle;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Obstacle getObstacle() {
		return obstacle;
	}

	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}

	/**
	 * Finds if the block is a place where an airport is.
	 * 
	 * @param block position
	 * @return true if it's an airport, false if not.
	 */
	public boolean isAnAeroport(Block block) {
		for (int index = 0; index < Configuration.AIRPORTS.length; index++) {
			if (Configuration.AIRPORTS[index].getPosition().getX() == block.getX()
					&& Configuration.AIRPORTS[index].getPosition().getY() == block.getY()) {
				return true;
			}
		}

		return false;
	}

}
