package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import config.Configuration;
import data.Airport;
import data.Block;
import data.Map;
import process.AircraftManager;
import process.BlockManager;
import process.Simulation;

/**
 * 
 * This class is the panel in which each airport, aircraft, flight and obstacle
 * are printed.
 *
 */

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1L;
	private Simulation simulation;
	private Map map;

	public Dashboard(Simulation simulation, Map map) {
		super();
		this.simulation = simulation;
		this.map = map;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paint(map, g);
		printObstacle(g);
		printAirport(g);
		printAircraft(g);
		printFlight(g);

	}

	public void paintMap(Map map, Graphics graphics) {
		int blockSize = Configuration.BLOCK_SIZE;
		Block[][] blocks = map.getBlocks();

		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) {
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) {
				Block block = blocks[lineIndex][columnIndex];

				if ((lineIndex + columnIndex) % 2 == 0) {
					graphics.setColor(Color.GRAY);
					graphics.fillRect(block.getY() * blockSize, block.getX() * blockSize, blockSize, blockSize);
				}
			}
		}
	}

	public void paint(Map map, Graphics graphics) {
		int blockSize = Configuration.BLOCK_SIZE;
		Block[][] blocks = map.getBlocks();

		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) {
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) {
				Block block = blocks[lineIndex][columnIndex];
				graphics.setColor(Color.BLACK);
				graphics.fillRect(block.getY() * blockSize, block.getX() * blockSize, blockSize, blockSize);

			}
		}
	}

	private void printAirport(Graphics graphics) {
		int blockSize = Configuration.BLOCK_SIZE;

		// Draw the name of the airport.
		for (Airport airport : simulation.getAirports()) {
			graphics.setColor(Color.WHITE);
			graphics.drawString(airport.getName(), airport.getPosition().getX() * blockSize,
					(airport.getPosition().getY() - 1) * blockSize);

		}

		// Draw a rectangle represented by the airport.
		for (Airport airport : simulation.getAirports()) {
			graphics.setColor(Color.WHITE);
			graphics.fillRect(airport.getPosition().getX() * blockSize, airport.getPosition().getY() * blockSize,
					blockSize, blockSize);

		}
	}

	private synchronized void printAircraft(Graphics g) {

		int blockSize = Configuration.BLOCK_SIZE;
		g.setColor(Color.GREEN);

		CopyOnWriteArrayList<AircraftManager> aircraftManagers = simulation.getAircraftManagers();

		/*
		 * Draw the aircraft informations. Draw its altitude and its state.
		 */
		for (AircraftManager aircraftManager : aircraftManagers) {
			if (aircraftManager.isRunning() && !aircraftManager.getAircraft().getAlert()) {
				g.drawString(
						aircraftManager.getAircraft().getName() + " - " + aircraftManager.getAircraft().getAltitude()
								+ " m - ALT",
						aircraftManager.getAircraft().getCurrentPosition().getX() * blockSize,
						aircraftManager.getAircraft().getCurrentPosition().getY() * blockSize);
			} else if (aircraftManager.isRunning() && aircraftManager.getAircraft().getAlert()) {
				g.drawString("Avion d�viant - Urgence",
						aircraftManager.getAircraft().getCurrentPosition().getX() * blockSize,
						aircraftManager.getAircraft().getCurrentPosition().getY() * blockSize);
			}

		}

		// Draw the aircraft with an image depending on its position.
		for (AircraftManager aircraftManager : simulation.getAircraftManagers()) {

			if (aircraftManager.isRunning() == true) {
				int stepFlight = aircraftManager.getAircraft().getFlight().getStepFlight();

				Block futureBlock;

				if (stepFlight == aircraftManager.getAircraft().getFlight().getBlocks().size() - 1) {
					futureBlock = aircraftManager.getAircraft().getFlight().getArrivalAirport().getPosition();
				} else {
					futureBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight + 1);
				}

				Block currentBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight);

				try {
					if (futureBlock.getY() == currentBlock.getY()) {
						draw(aircraftManager, g);
					} else if (futureBlock.getY() > currentBlock.getY()) {
						drawBot(aircraftManager, g);
					} else if (futureBlock.getY() < currentBlock.getY()) {
						drawTop(aircraftManager, g);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				g.setColor(Color.BLACK);

			}

		}

	}

	private void printFlight(Graphics g) {
		Block block = null;

		int blockSize = Configuration.BLOCK_SIZE;

		// Print way of each flight.
		for (int index = 0; index < simulation.getFlights().size(); index++) {
			for (int index2 = 0; index2 < simulation.getFlights().get(index).getBlocks().size(); index2++) {

				block = simulation.getFlights().get(index).getBlocks().get(index2);

				g.drawLine(block.getX() * blockSize + blockSize / 2, block.getY() * blockSize + blockSize / 2,
						block.getX() * blockSize + blockSize / 2, (block.getY()) * blockSize + blockSize / 2);

				g.setColor(Color.GREEN);
			}
		}
	}

	private void printObstacle(Graphics g) {
		int blockSize = Configuration.BLOCK_SIZE;
		HashMap<Block, BlockManager> blocks = simulation.getBlockManagersByPosition();

		for (Entry<Block, BlockManager> mapentry : blocks.entrySet()) {
			if (mapentry.getValue().isAnObstacle()) {

				g.setColor(Color.LIGHT_GRAY);

				int x = mapentry.getKey().getX();
				int y = mapentry.getKey().getY();

				Block topBlock = new Block(x - 1, y);
				Block botBlock = new Block(x + 1, y);
				Block leftBlock = new Block(x, y - 1);
				Block rightBlock = new Block(x, y + 1);

				BlockManager topBlockManager = simulation.getBlockManager(topBlock);
				BlockManager botBlockManager = simulation.getBlockManager(botBlock);
				BlockManager leftBlockManager = simulation.getBlockManager(leftBlock);
				BlockManager rightBlockManager = simulation.getBlockManager(rightBlock);

				if (!topBlockManager.isAnObstacle()) {
					if (!leftBlockManager.isAnObstacle()) {
						int[] xPoints = { (x + 1) * blockSize, (x + 1) * blockSize, x * blockSize };
						int[] yPoints = { (y + 1) * blockSize, y * blockSize, (y + 1) * blockSize };
						g.fillPolygon(xPoints, yPoints, xPoints.length);
					} else if (!rightBlockManager.isAnObstacle()) {
						int[] xPoints = { (x + 1) * blockSize, x * blockSize, (x + 1) * blockSize };
						int[] yPoints = { (y + 1) * blockSize, y * blockSize, y * blockSize };
						g.fillPolygon(xPoints, yPoints, xPoints.length);
					} else {
						g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
					}
				} else if (!botBlockManager.isAnObstacle()) {
					if (!leftBlockManager.isAnObstacle()) {
						int[] xPoints = { x * blockSize, x * blockSize, (x + 1) * blockSize };
						int[] yPoints = { y * blockSize, (y + 1) * blockSize, (y + 1) * blockSize };
						g.fillPolygon(xPoints, yPoints, xPoints.length);
					} else if (!rightBlockManager.isAnObstacle()) {
						int[] xPoints = { x * blockSize, x * blockSize, (x + 1) * blockSize };
						int[] yPoints = { y * blockSize, (y + 1) * blockSize, y * blockSize };
						g.fillPolygon(xPoints, yPoints, xPoints.length);
					} else {
						g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
					}
				} else {
					g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
				}
			}
		}
		g.setColor(Color.LIGHT_GRAY);

		g.drawString(" 10000 m", 30 * blockSize, 23 * blockSize);
		g.drawString(" 9000 m", 21 * blockSize, 15 * blockSize);
		g.drawString(" 6000 m", 11 * blockSize, 29 * blockSize);
	}

	private void draw(AircraftManager aircraftManager, Graphics g) throws IOException {

		int blockSize = Configuration.BLOCK_SIZE;

		Image aircraft = null;

		int x = aircraftManager.getAircraft().getCurrentPosition().getX();
		int y = aircraftManager.getAircraft().getCurrentPosition().getY();

		int stepFlight = aircraftManager.getAircraft().getFlight().getStepFlight();

		Block futureBlock;

		if (stepFlight == aircraftManager.getAircraft().getFlight().getBlocks().size() - 1) {
			futureBlock = aircraftManager.getAircraft().getFlight().getArrivalAirport().getPosition();
		} else {
			futureBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight + 1);
		}

		Block currentBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight);

		if (futureBlock.getX() > currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_right.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);

		} else if (futureBlock.getX() < currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_left.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		}

	}

	private void drawBot(AircraftManager aircraftManager, Graphics g) throws IOException {
		int blockSize = Configuration.BLOCK_SIZE;

		Image aircraft = null;

		int x = aircraftManager.getAircraft().getCurrentPosition().getX();
		int y = aircraftManager.getAircraft().getCurrentPosition().getY();

		int stepFlight = aircraftManager.getAircraft().getFlight().getStepFlight();

		Block futureBlock;

		if (stepFlight == aircraftManager.getAircraft().getFlight().getBlocks().size() - 1) {
			futureBlock = aircraftManager.getAircraft().getFlight().getArrivalAirport().getPosition();
		} else {
			futureBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight + 1);
		}

		Block currentBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight);

		if (futureBlock.getX() > currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_bot_right.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		} else if (futureBlock.getX() < currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_bot_left.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		} else if (futureBlock.getX() == currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_bot.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		}

	}

	private void drawTop(AircraftManager aircraftManager, Graphics g) throws IOException {
		int blockSize = Configuration.BLOCK_SIZE;

		Image aircraft = null;

		int x = aircraftManager.getAircraft().getCurrentPosition().getX();
		int y = aircraftManager.getAircraft().getCurrentPosition().getY();

		int stepFlight = aircraftManager.getAircraft().getFlight().getStepFlight();

		Block futureBlock;

		if (stepFlight == aircraftManager.getAircraft().getFlight().getBlocks().size() - 1) {
			futureBlock = aircraftManager.getAircraft().getFlight().getArrivalAirport().getPosition();
		} else {
			futureBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight + 1);
		}

		Block currentBlock = aircraftManager.getAircraft().getFlight().getBlockByPosition(stepFlight);

		if (futureBlock.getX() > currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_top_right.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		} else if (futureBlock.getX() < currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_top_left.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		} else if (futureBlock.getX() == currentBlock.getX()) {
			aircraft = ImageIO.read(new File("src/images/aircraft_top.png"));
			g.drawImage(aircraft, x * blockSize, y * blockSize, blockSize, blockSize, null);
		}

	}

}
