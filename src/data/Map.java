package data;

import java.util.ArrayList;

/**
 * 
 * This class is a map made up of blocks.
 *
 */
public class Map {

	private Block[][] blocks;

	private int lineCount;
	private int columnCount;

	private ArrayList<Airport> airports = new ArrayList<Airport>();

	public Map(int lineCount, int columnCount) {
		this.lineCount = lineCount;
		this.columnCount = columnCount;

		blocks = new Block[lineCount][columnCount];

		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				this.blocks[lineIndex][columnIndex] = new Block(lineIndex, columnIndex);
			}
		}
	}

	public Block[][] getBlocks() {
		return this.blocks;
	}

	public int getLineCount() {
		return lineCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Block getBlock(int line, int column) {
		return blocks[line][column];
	}

	public ArrayList<Airport> getAirports() {
		return airports;
	}

	public void addAirport(Airport airport) {
		airports.add(airport);
	}

	public Airport getAirport(Block block) {
		for (Airport airport : airports) {
			if (airport.getPosition().getY() == block.getY() && airport.getPosition().getX() == block.getX()) {
				return airport;
			}
		}

		return null;
	}

}
