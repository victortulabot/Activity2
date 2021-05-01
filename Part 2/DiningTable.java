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
		
		//condition and state
		for(int i = 0; i < numOfPhilosophers; i++){
			state[i] = States.thinking;
			cond[i] = lock.newCondition();
		}
	}
	
	public void PickUp(int i){
		lock.lock();
		try{
			// check chopsticks
			state[i] = States.hungry;
			// pick up chopsticks (no neighbors)
			if( ( state[(i-1+numOfPhilosophers)%numOfPhilosophers] != States.eating ) &&					
			    (state[(i+1)%numOfPhilosophers] != States.eating) ){
				System.out.format("Philosopher %d picks up left and right chopstick\n", i+1);
			
				state[i] = States.eating;
			} 
			else {	// wait 
				try {
					cond[i].await();
					System.out.format("Philosopher %d picks up left and right chopstick\n", i+1);
					state[i] = States.eating;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	public void PutDown(int i){
		lock.lock();
		try{ //end eating 
			System.out.format("Philosopher %d puts down left and right chopstick\n", i+1);

			state[i] = States.thinking;

			// left neighbor will eat
			int left = (i - 1 + numOfPhilosophers)%numOfPhilosophers;
			int left2 = (i - 2 + numOfPhilosophers)%numOfPhilosophers;
			if( (state[left] == States.hungry) &&
				(state[left2] != States.eating) ){
				cond[left].signal();
			}
			
			// right neighbor will eat
			if( (state[(i+1)%numOfPhilosophers] == States.hungry) &&
				(state[(i+2)%numOfPhilosophers] != States.eating) ){
				cond[(i+1)%numOfPhilosophers].signal();
			}
		}
		finally {
			lock.unlock();
		}
	}		
}