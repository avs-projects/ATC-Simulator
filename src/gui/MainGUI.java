package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map.Entry;

import javax.swing.JFrame;

import config.Configuration;
import data.Block;
import data.Flight;
import data.Map;
import process.AircraftManager;
import process.BlockManager;
import process.Simulation;
import process.SimulationUtility;

/**
 * 
 * The air trafic control simulation main gui.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class MainGUI extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private Simulation simulation;
	private Dashboard dashboard;
	private ManagementDisplay managementDisplay;
	private Map map;

	public MainGUI() {
		super("Air Traffic Control");

		simulation = new Simulation();
		map = simulation.getMap();
		dashboard = new Dashboard(simulation, map);
		managementDisplay = new ManagementDisplay(map, simulation);

		Dimension preferredSize = new Dimension((int) (0.70 * Configuration.WINDOW_WIDTH), 500);
		dashboard.setPreferredSize(preferredSize);

		preferredSize = new Dimension((int) (0.33 * Configuration.WINDOW_WIDTH), 500);
		managementDisplay.setPreferredSize(preferredSize);

		add(dashboard, BorderLayout.WEST);
		add(managementDisplay, BorderLayout.EAST);

		setResizable(false);
		setSize(Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void run() {

		while (true) {
			SimulationUtility.unitTime();
			simulation.getClock().increment();

			if (SimulationUtility.getRandom(0, 10) < 4) {
				simulation.createAircraft();

				if (simulation.getAircraftBuilder().isBuilt() == true) {

					AircraftManager aircraftManager = simulation.getLastAircraftManager();
					Flight flight = aircraftManager.getAircraft().getFlight();

					BlockManager blockManager = null;

					for (Entry<Block, BlockManager> mapentry : simulation.getBlockManagersByPosition().entrySet()) {
						if (mapentry.getKey().getX() == flight.getCurrentBlock().getX()
								&& mapentry.getKey().getY() == flight.getCurrentBlock().getY()) {
							blockManager = mapentry.getValue();
						}
					}
					aircraftManager.setBlockManager(blockManager);
					blockManager.enter(aircraftManager);

					// This is the thread start.
					aircraftManager.start();

				}
			}

			managementDisplay.getAirportDashboard1().repaint();
			managementDisplay.getAirportDashboard2().repaint();
			managementDisplay.getAirportDashboard3().repaint();
			managementDisplay.getAirportDashboard4().repaint();

			managementDisplay.refreshClock();
			managementDisplay.refreshFlights();

			dashboard.repaint();

		}
	}
}
