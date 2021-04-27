import java.util.*;
import java.util.concurrent.locks.*;

public class DiningTable {
    
    private final ReentrantLock entLock;
    private final Condition self[];
    private State states[];

    public DiningTable (int people){
        entLock = new ReentrantLock();
		self = new Condition [people];
		states = new State[people];
	    
        for(int i = 0; i < people; i++){
			self[i] = entLock.newCondition();
			states[i] = State.THINKING;
		}
    }

    void go(int who){
		try {
			pickup(who);
			putdown(who);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    void pickup(int who) throws InterruptedException {
		entLock.lock();
		states[who]=State.HUNGRY;
		System.out.println("Philosopher " + who + " is hungry.\n");
		/** Test(who); */
	
		if(states[who]!=State.EATING)
			self[who].await();
		
		entLock.unlock();
	}

    void putdown(int who){
		entLock.lock();
		states[who]=State.THINKING;
		System.out.println("Philosopher " + who + " is thinking.\n");
		
		/** Test(Global.Left(who));
		Test(Global.Right(who)); **/
		
		entLock.unlock();
	}

    /** 
    void Test(int who){
		if(states[Global.Left(who)]!=State.EATING && states[Global.Right(who)]!=State.EATING && states[who]==State.HUNGRY){

			states[who]=State.EATING;
			System.out.println("Philosopher " + who + " is eating.\n");
			
			self[who].signal();
		}
	}
    */


}
