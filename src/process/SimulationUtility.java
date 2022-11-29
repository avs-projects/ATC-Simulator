package process;

import java.util.Random;

import config.Configuration;

/**
 * 
 * This utility class contains util functions used by the simulation.
 *
 */
public class SimulationUtility {

	/**
	 * Simulates the unit time defined for the simulation.
	 */
	public static void unitTime() {
		try {
			Thread.sleep(Configuration.SIMULATION_SPEED);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Generates a random number between two numbers.
	 */
	public static int getRandom(int min, int max) {
		return (int) (Math.random() * (max + 1 - min)) + min;
	}

	/**
	 * Generates a random letter.
	 */
	public static String getRandomnLetter() {
		String CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder build = new StringBuilder();
		Random randomn = new Random();
		while (build.length() < 1) {
			int index = (int) (randomn.nextFloat() * CHAR.length());
			build.append(CHAR.charAt(index));
		}
		String finalID = build.toString();
		return finalID;
	}

	/**
	 * Generates a random name with 1 letter and 3 numbers.
	 * @return a random name.
	 */
	public static String createRandomName() {
		return SimulationUtility.getRandomnLetter() + SimulationUtility.getRandom(0, 9)
				+ SimulationUtility.getRandom(0, 9) + SimulationUtility.getRandom(0, 9);
	}

}
