import java.util.*;
import java.util.concurrent.locks.*;

public class Philosopher implements Runnable {
    	// Private data.
	private int myId;
	private int timesToEat;		
	private DiningTable mon;
	public Thread t;	
	private int sleepLength;		
	// Constructor.
	Philosopher(int id, int numToEat, DiningTable m){
		this.myId = id;
		this.timesToEat = numToEat;
		this.mon = m;
		sleepLength = 10;			
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
	
	void eat(int count){
		System.out.format("Philosopher %d is eating\n", myId+1);

		try {
		    Thread.sleep(sleepLength); //sleep
		} 
		catch (InterruptedException e) {}
	}
	
}