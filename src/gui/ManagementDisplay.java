package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import config.Configuration;
import data.Map;
import process.AircraftManager;
import process.Simulation;
import process.SimulationUtility;

/**
 * 
 * This class contains all airports and its runways.
 * 
 * @author VAZ SILVA Alexandre
 *
 */

public class ManagementDisplay extends JPanel {

	private static final long serialVersionUID = 1L;

	private AirportDashboard airportDashboard1;
	private AirportDashboard airportDashboard2;
	private AirportDashboard airportDashboard3;
	private AirportDashboard airportDashboard4;

	private JLabel clockLabel = new JLabel();

	private JTextPane textPane = new JTextPane();
	private JScrollPane flightPane = new JScrollPane(textPane);

	private JLabel airportLabel1 = new JLabel("Paris-Charles-de-Gaulle");
	private JLabel airportLabel2 = new JLabel("Aeroport de Lyon-Saint Exup�ry");
	private JLabel airportLabel3 = new JLabel("Aeroport de Bordeaux");
	private JLabel airportLabel4 = new JLabel("Aeroport de Strasbourg");

	private JButton emergencyButton = new JButton("Cr�er une urgence al�atoirement");

	private Dimension dimension = new Dimension((int) (Configuration.WINDOW_WIDTH * 0.25),
			(int) (Configuration.WINDOW_HEIGHT / 35));

	private Dimension dimensionButton = new Dimension((int) (Configuration.WINDOW_WIDTH * 0.25),
			(int) (Configuration.WINDOW_HEIGHT / 20));

	private Dimension dimensionPane = new Dimension((int) (Configuration.WINDOW_WIDTH * 0.25),
			(int) (Configuration.WINDOW_HEIGHT / 5));

	private Simulation simulation;

	public ManagementDisplay(Map map, Simulation simulation) {

		this.simulation = simulation;

		airportDashboard1 = new AirportDashboard(map.getAirports().get(0));
		airportDashboard2 = new AirportDashboard(map.getAirports().get(1));
		airportDashboard3 = new AirportDashboard(map.getAirports().get(2));
		airportDashboard4 = new AirportDashboard(map.getAirports().get(3));

		init();
	}

	protected void init() {

		clockLabel.setPreferredSize(dimension);
		clockLabel.setBackground(Color.BLACK);
		clockLabel.setOpaque(true);
		clockLabel.setForeground(Color.GREEN);
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);

		flightPane.setPreferredSize(dimensionPane);
		textPane.setBackground(Color.BLACK);
		textPane.setForeground(Color.GREEN);

		airportLabel1.setPreferredSize(dimension);
		airportLabel1.setBackground(new Color(17, 35, 63));
		airportLabel1.setOpaque(true);
		airportLabel1.setForeground(Color.WHITE);
		airportLabel1.setHorizontalAlignment(SwingConstants.CENTER);

		airportLabel2.setPreferredSize(dimension);
		airportLabel2.setBackground(new Color(17, 35, 63));
		airportLabel2.setOpaque(true);
		airportLabel2.setForeground(Color.WHITE);
		airportLabel2.setHorizontalAlignment(SwingConstants.CENTER);

		airportLabel3.setPreferredSize(dimension);
		airportLabel3.setBackground(new Color(17, 35, 63));
		airportLabel3.setOpaque(true);
		airportLabel3.setForeground(Color.WHITE);
		airportLabel3.setHorizontalAlignment(SwingConstants.CENTER);

		airportLabel4.setPreferredSize(dimension);
		airportLabel4.setBackground(new Color(17, 35, 63));
		airportLabel4.setOpaque(true);
		airportLabel4.setForeground(Color.WHITE);
		airportLabel4.setHorizontalAlignment(SwingConstants.CENTER);

		emergencyButton.setPreferredSize(dimensionButton);
		emergencyButton.setBackground(Color.LIGHT_GRAY);
		emergencyButton.setOpaque(true);
		emergencyButton.setForeground(Color.BLACK);
		emergencyButton.setHorizontalAlignment(SwingConstants.CENTER);
		emergencyButton.addActionListener(new CreateEmergency());

		add(clockLabel);
		add(flightPane);
		add(airportLabel1);
		add(airportDashboard1);
		add(airportLabel2);
		add(airportDashboard2);
		add(airportLabel3);
		add(airportDashboard3);
		add(airportLabel4);
		add(airportDashboard4);
		add(emergencyButton);

		setVisible(true);

	}

	public AirportDashboard getAirportDashboard1() {
		return airportDashboard1;
	}

	public AirportDashboard getAirportDashboard2() {
		return airportDashboard2;
	}

	public AirportDashboard getAirportDashboard3() {
		return airportDashboard3;
	}

	public AirportDashboard getAirportDashboard4() {
		return airportDashboard4;
	}

	public void refreshClock() {
		clockLabel.setText(new DecimalFormat("00").format(simulation.getClock().getHour()) + " : "
				+ new DecimalFormat("00").format(simulation.getClock().getMinute()) + " - UTC+01:00");
	}

	public synchronized void refreshFlights() {
		String message = "";
		String etat = "";

		CopyOnWriteArrayList<AircraftManager> aircraftManagers = simulation.getAircraftManagers();

		for (AircraftManager aircraftManager : aircraftManagers) {

			if (aircraftManager.getAircraft().getAlert()) {
				etat = "Urgence";
			} else {
				etat = "Stable";
			}

			if (aircraftManager.getAircraft().getFlight().getStepFlight() > 0) {
				message += aircraftManager.getAircraft().getName() + " - D�part � "
						+ new DecimalFormat("00").format(aircraftManager.getAircraft().getClockDeparture().getHour())
						+ " : "
						+ new DecimalFormat("00").format(aircraftManager.getAircraft().getClockDeparture().getMinute())
						+ " - " + etat + " - ALT " + aircraftManager.getAircraft().getAltitude() + " m\n"
						+ aircraftManager.getAircraft().getFlight().getDepartureAirport().getName() + " - "
						+ aircraftManager.getAircraft().getFlight().getArrivalAirport().getName()
						+ "\n--------------------------------------------------------------\n";
			} else {
				message += aircraftManager.getAircraft().getName() + " - D�part � "
						+ new DecimalFormat("00").format(aircraftManager.getAircraft().getClockDeparture().getHour())
						+ " : "
						+ new DecimalFormat("00").format(aircraftManager.getAircraft().getClockDeparture().getMinute())
						+ " - " + etat + " -  D�collage... \n"
						+ aircraftManager.getAircraft().getFlight().getDepartureAirport().getName() + " - "
						+ aircraftManager.getAircraft().getFlight().getArrivalAirport().getName()
						+ "\n--------------------------------------------------------------\n";
			}

		}
		textPane.setText(message);
	}

	private class CreateEmergency implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			boolean isFull = true;
			for (AircraftManager aircraftManager : simulation.getAircraftManagers()) {
				if (!aircraftManager.getAircraft().getAlert()
						&& aircraftManager.getAircraft().getFlight().getStepFlight() > 0) {
					isFull = false;
				}
			}

			if (!isFull) {
				int randomEmergency = SimulationUtility.getRandom(0, simulation.getAircraftManagers().size() - 1);

				while (simulation.getAircraftManagers().get(randomEmergency).getAircraft().getAlert() == true
						|| simulation.getAircraftManagers().get(randomEmergency).getAircraft().getFlight()
								.getStepFlight() == 0) {

					randomEmergency = SimulationUtility.getRandom(0, simulation.getAircraftManagers().size() - 1);
				}

				simulation.getAircraftManagers().get(randomEmergency).getAircraft().setAlert(true);
				simulation.getAircraftManagers().get(randomEmergency).createFlightForUrgence();
			}

		}
	}

}
