package data;

/**
 * 
 * This class represents a clock with its minute and hour.
 * 
 * @author VAZ SILVA Alexandre
 *
 */
public class Clock {

	
	private int minute;
	private int hour;

	public Clock(int minute, int hour) {
		super();
		this.minute = minute;
		this.hour = hour;
	}

	public void increment() {
		minute = minute + 10;

		if (minute == 60) {
			hour++;
			minute = 0;
		}
		
		if (hour > 23) {
			hour = 0;
		}
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

}
