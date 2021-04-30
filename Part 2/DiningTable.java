import java.util.*;
import java.util.concurrent.locks.*;

public class DiningTable {
    private int numOfPhilosophers;
	final Lock lock;
	private enum States {hungry, thinking, eating};
	private States [] state;
	final Condition [] cond;	
	// Constructor.
	DiningTable(int numOfPhil){
		this.numOfPhilosophers = numOfPhil;
		lock = new ReentrantLock();
		state = new States[numOfPhilosophers];
		cond = new Condition[numOfPhilosophers];
		// Set initial states and the conditions of the philosophers.
		for(int i = 0; i < numOfPhilosophers; i++){
			state[i] = States.thinking;
			cond[i] = lock.newCondition();
		}
	}
	// Synchronized interface methods.
	// Pick up both chopstics for a philosopher i.
	public void PickUp(int i){
		lock.lock();
		try{
			// Indicate that I want to take chopsticks.
			state[i] = States.hungry;
			// Pick up chopsticks if both neighbors are not eating.
			if( ( state[(i-1+numOfPhilosophers)%numOfPhilosophers] != States.eating ) &&					
			    (state[(i+1)%numOfPhilosophers] != States.eating) ){
				System.out.format("Philosopher %d picks up left chopstick\n", i+1);
				System.out.format("Philosopher %d picks up right chopstick\n", i+1);
				state[i] = States.eating;
			} 
			else {	// If at least one neighbor is eating, then wait. 
				try {
					cond[i].await();
					// Eat after waiting.
					System.out.format("Philosopher %d picks up left chopstick\n", i+1);
					System.out.format("Philosopher %d picks up right chopstick\n", i+1);
					state[i] = States.eating;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}// end else
		}// end try
		finally{
			lock.unlock();
		}
	}
	// Put down both chopstics.
	public void PutDown(int i){
		lock.lock();
		try{
			System.out.format("Philosopher %d puts down right chopstick\n", i+1);
			System.out.format("Philosopher %d puts down left chopstick\n", i+1);
			state[i] = States.thinking;
			// Tell the left neighbor about the possibility to eat.
			int left = (i - 1 + numOfPhilosophers)%numOfPhilosophers;
			int left2 = (i - 2 + numOfPhilosophers)%numOfPhilosophers;
			if( (state[left] == States.hungry) &&
				(state[left2] != States.eating) ){
				cond[left].signal();
			}
			// Tell the right neighbor about the possibility to eat
			if( (state[(i+1)%numOfPhilosophers] == States.hungry) &&
				(state[(i+2)%numOfPhilosophers] != States.eating) ){
				cond[(i+1)%numOfPhilosophers].signal();
			}
		}// end try
		finally {
			lock.unlock();
		}
	}		
}