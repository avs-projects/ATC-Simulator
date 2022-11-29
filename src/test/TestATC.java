package test;

import gui.MainGUI;

/**
 * 
 * The air trafic control manuel test class.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class TestATC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGUI simulationGUI = new MainGUI();
		Thread guiTread = new Thread(simulationGUI);
		guiTread.start();
	}

}
