import java.util.*;
import java.util.concurrent.locks.*;

public class Philosopher implements Runnable {
    	// Private data.
	private int myId;
	private int timesToEat;		// Times to eat.
	private DiningTable mon;
	public Thread t;	
	private int sleepLength;		// How long to sleep during eating.
	// Constructor.
	Philosopher(int id, int numToEat, DiningTable m){
		this.myId = id;
		this.timesToEat = numToEat;
		this.mon = m;
		sleepLength = 10;			// Make a pause of 10 ms while eating.
		t = new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		int count = 1;
		while(count <= timesToEat ){
			mon.PickUp(myId);
			eat(count);
			mon.PutDown(myId);
			++count;
		}		
	}
	// Print the state of the philosopher.
	void eat(int count){
		System.out.format("Philosopher %d eats (%d times)\n", myId+1, count);
		// Sleep a little bit.
		try {
		    Thread.sleep(sleepLength);
		} 
		catch (InterruptedException e) {}
	}
	
}