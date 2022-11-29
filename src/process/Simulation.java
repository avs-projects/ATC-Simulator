package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import config.Configuration;
import data.Aircraft;
import data.Airport;
import data.Block;
import data.Clock;
import data.Flight;
import data.Map;
import data.Runway;

/**
 * 
 * The simulation management class. Its contains and prepares all BlockManager
 * and all AircraftManaer.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class Simulation {
	private Map map;

	private Clock clock;

	private volatile HashMap<Block, BlockManager> blockManagersByPosition = new HashMap<Block, BlockManager>();

	/**
	 * CopyOnWriteArrayList is a thread-safe variant of ArrayList in which
	 * operations like add or set are used by making a copy of the underlying array.
	 * 
	 * With a basic ArrayList, a ConcurrentModificationException may occur.
	 */
	private CopyOnWriteArrayList<AircraftManager> aircraftManagers = new CopyOnWriteArrayList<AircraftManager>();
	private ArrayList<Flight> flights = new ArrayList<Flight>();

	private ObstacleBuilder obstacleBuilder = new ObstacleBuilder();
	private AircraftBuilder aircraftBuilder = new AircraftBuilder();
	private FlightBuilder flightBuilder = new FlightBuilder();

	public Simulation() {
		map = new Map(40, 40);
		clock = new Clock(0, 0);

		for (int lineIndex = 0; lineIndex < map.getColumnCount(); lineIndex++) {
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) {
				Block block = new Block(lineIndex, columnIndex);
				BlockManager blockManager = new BlockManager(block);
				blockManagersByPosition.put(block, blockManager);
			}
		}

		obstacleBuilder.buildObstacle(this);

		for (Airport airport : Configuration.AIRPORTS) {
			Airport tmpAirport = airport;
			Runway runway = null;
			for (Airport airport2 : Configuration.AIRPORTS) {
				if (airport != airport2) {
					runway = new Runway(airport2);
					tmpAirport.addRunway(runway);
				}
			}

			map.addAirport(tmpAirport);
		}

		Flight flight = null;

		for (Airport departureAirport : Configuration.AIRPORTS) {
			for (Airport arrivalAirport : Configuration.AIRPORTS) {
				if (departureAirport != arrivalAirport) {
					flight = flightBuilder.buildFlight(departureAirport, arrivalAirport, this);
					flights.add(flight);
				}
			}
		}
	}

	public synchronized void createAircraft() {

		aircraftBuilder.buildAircraft(this, new Clock(clock.getMinute(), clock.getHour()));

		if (this.getAircraftBuilder().isBuilt() == true) {
			Aircraft aircraft = new Aircraft(aircraftBuilder.getLastAircraft().getCurrentPosition(),
					aircraftBuilder.getLastAircraft().getFlight(),
					aircraftBuilder.getLastAircraft().getClockDeparture());

			aircraftManagers.add(new AircraftManager(aircraft, this));
		}
	}

	public BlockManager getBlockManager(Block block) {
		BlockManager blockManager = null;
		for (Entry<Block, BlockManager> mapentry : blockManagersByPosition.entrySet()) {
			if (mapentry.getKey().getY() == block.getY() && mapentry.getKey().getX() == block.getX()) {
				blockManager = mapentry.getValue();
			}
		}
		return blockManager;
	}

	public Map getMap() {
		return map;
	}

	public Clock getClock() {
		return clock;
	}

	public HashMap<Block, BlockManager> getBlockManagersByPosition() {
		return blockManagersByPosition;
	}

	public CopyOnWriteArrayList<AircraftManager> getAircraftManagers() {
		return aircraftManagers;
	}

	public ArrayList<Airport> getAirports() {
		return map.getAirports();
	}

	public AircraftManager getLastAircraftManager() {
		return aircraftManagers.get(aircraftManagers.size() - 1);
	}

	public AircraftBuilder getAircraftBuilder() {
		return aircraftBuilder;
	}

	public ArrayList<Flight> getFlights() {
		return flights;
	}

	public int getRandomNumber() {
		return (int) (Math.random()) * 3 + 3;
	}
}
