package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import config.Configuration;
import data.Airport;
import data.Map;
import data.Runway;

/**
 * 
 * This class is the panel in which each runway and airport are printed.
 *
 */
public class AirportDashboard extends JPanel {

	private static final long serialVersionUID = 1L;

	private Airport airport;

	/*
	 * A mini map where the animation of the take off is printed.
	 */
	private Map map;

	private Dimension dimension = new Dimension((int) (Configuration.WINDOW_WIDTH * 0.25),
			(int) (Configuration.WINDOW_HEIGHT / 8));

	public AirportDashboard(Airport airport) {
		super();
		this.airport = airport;

		this.setPreferredSize(dimension);

		map = new Map(5, 3);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintMap(map, g);
		paint(map, g);
	}

	private void paint(Map map, Graphics graphics) {

		int blockSize = Configuration.BLOCK_SIZE_AIRPORT;
		int blockLine = 0;

		Image aircraft = null;

		graphics.setColor(Color.BLACK);

		try {
			aircraft = ImageIO.read(new File("src/images/aircraft.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Runway runway : airport.getRunways()) {
			if (runway.getCurrentAircraft() != null) {
				graphics.drawImage(aircraft, runway.getOccupiedTime() * blockSize, blockLine * blockSize, blockSize,
						blockSize, null);
			}
			blockLine++;

		}
	}

	private void paintMap(Map map, Graphics graphics) {

		int blockSize = Configuration.BLOCK_SIZE_AIRPORT;

		Image runaway = null;

		try {
			runaway = ImageIO.read(new File("src/images/runway.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int x = 0; x < map.getLineCount(); x++) {
			for (int y = 0; y < map.getColumnCount(); y++) {
				graphics.drawImage(runaway, x * blockSize, y * blockSize, blockSize, blockSize, null);
			}
		}
	}
}
