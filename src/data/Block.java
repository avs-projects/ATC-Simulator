package data;

/**
 * 
 * This class represents a block of the map.
 * 
 * Each aircraft has a block in position.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class Block {
	
	/**
	 * In an coordinate system, 
	 * x matches to the value obtained by projecting this point on the horizontal axis.
	 */
	private int x;
	
	/**
	 * In an coordinate system, 
	 * y matches to the value obtained by projecting this point on the vertical axis.
	 */
	private int y;

	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
