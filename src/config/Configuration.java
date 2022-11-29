package config;

/**
 * 
 * This class is the simulation configuration.
 *
 */
import data.Airport;
import data.Block;

public class Configuration {

	public static final int SIMULATION_SPEED = 1000;
	public static final int SIMULATION_SPEED_2 = 200;

	public static final int BLOCK_SIZE = 25;
	public static final int BLOCK_SIZE_AIRPORT = 40;

	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 1000;

	public static final int TIME_FOR_TAKE_OFF = 4;

	public static final Airport[] AIRPORTS = new Airport[] { new Airport("Paris-Charles-de-Gaulle", new Block(12, 7)),
			new Airport("Aeroport de Lyon-Saint Exup�ry", new Block(25, 35)),
			new Airport("Aeroport de Bordeaux", new Block(2, 30)),
			new Airport("Aeroport de Strasbourg", new Block(28, 20)) };
}
