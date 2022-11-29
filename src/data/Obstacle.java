package data;

import java.util.ArrayList;

/**
 * 
 * This class represents an quadrilateral obstacle.
 *
 */
public class Obstacle {

	/*
	 * This is the altitude for an obstacle in general.
	 * All blocks of the obstacle have the same altitudes.
	 */
	private int altitude = 0;
	
	/*
	 * Each side of the obstacle is made up of blocks.
	 */
	private ArrayList<Block> leftBlocks = new ArrayList<Block>();
	private ArrayList<Block> rightBlocks = new ArrayList<Block>();
	private ArrayList<Block> topBlocks = new ArrayList<Block>();
	private ArrayList<Block> botBlocks = new ArrayList<Block>();

	public Obstacle() {

	}

	public void addLeftBlock(Block block) {
		leftBlocks.add(block);
	}

	public void addRightBlock(Block block) {
		rightBlocks.add(block);
	}

	public void addTopBlock(Block block) {
		topBlocks.add(block);
	}

	public void addBotBlock(Block block) {
		botBlocks.add(block);
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public ArrayList<Block> getLeftBlocks() {
		return leftBlocks;
	}

	public ArrayList<Block> getRightBlocks() {
		return rightBlocks;
	}

	public ArrayList<Block> getTopBlocks() {
		return topBlocks;
	}

	public ArrayList<Block> getBotBlocks() {
		return botBlocks;
	}

}
