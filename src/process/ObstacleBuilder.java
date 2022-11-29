package process;

import data.Block;
import data.Obstacle;

/**
 * 
 * This class allows an obstacle construction.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class ObstacleBuilder {

	/**
	 * Build and set an obstacle for each block.
	 * 
	 * @param simulation
	 */
	public void buildObstacle(Simulation simulation) {

		Obstacle obstacle = new Obstacle();
		obstacle.setAltitude(10000);

		for (int index = 23; index < 30; index++) {
			for (int secondIndex = 23; secondIndex < 30; secondIndex++) {
				Block block = new Block(index, secondIndex);
				BlockManager blockManager = simulation.getBlockManager(block);
				blockManager.setAnObstacle(true);
				if (index == 23) {
					obstacle.addLeftBlock(block);

				}
				if (secondIndex == 23) {
					obstacle.addTopBlock(block);

				}
				if (secondIndex == 29) {
					obstacle.addBotBlock(block);

				}
				if (index == 29) {
					obstacle.addRightBlock(block);

				}
			}
		}

		Obstacle obstacle2 = new Obstacle();
		obstacle2.setAltitude(9000);

		for (int index = 15; index < 21; index++) {
			for (int secondIndex = 15; secondIndex < 21; secondIndex++) {
				Block block = new Block(index, secondIndex);
				BlockManager blockManager = simulation.getBlockManager(block);
				blockManager.setAnObstacle(true);
				if (index == 15) {
					obstacle2.addLeftBlock(block);

				}
				if (secondIndex == 15) {
					obstacle2.addTopBlock(block);

				}
				if (secondIndex == 20) {
					obstacle2.addBotBlock(block);

				}
				if (index == 20) {
					obstacle2.addRightBlock(block);

				}
			}
		}

		Obstacle obstacle3 = new Obstacle();
		obstacle3.setAltitude(6000);

		for (int index = 7; index < 11; index++) {
			for (int secondIndex = 29; secondIndex < 33; secondIndex++) {
				Block block = new Block(index, secondIndex);
				BlockManager blockManager = simulation.getBlockManager(block);
				blockManager.setAnObstacle(true);
				if (index == 7) {
					obstacle3.addLeftBlock(block);

				}
				if (secondIndex == 29) {
					obstacle3.addTopBlock(block);

				}
				if (secondIndex == 32) {
					obstacle3.addBotBlock(block);

				}
				if (index == 10) {
					obstacle3.addRightBlock(block);

				}
			}
		}

		for (int index = 23; index < 30; index++) {
			for (int secondIndex = 23; secondIndex < 30; secondIndex++) {
				Block block = new Block(index, secondIndex);
				BlockManager blockManager = simulation.getBlockManager(block);
				blockManager.setObstacle(obstacle);

			}
		}

		for (int index = 15; index < 21; index++) {
			for (int secondIndex = 15; secondIndex < 21; secondIndex++) {
				Block block = new Block(index, secondIndex);
				BlockManager blockManager = simulation.getBlockManager(block);
				blockManager.setObstacle(obstacle2);

			}
		}

		for (int index = 7; index < 11; index++) {
			for (int secondIndex = 29; secondIndex < 33; secondIndex++) {
				Block block = new Block(index, secondIndex);
				BlockManager blockManager = simulation.getBlockManager(block);
				blockManager.setObstacle(obstacle3);

			}
		}
	}
}
