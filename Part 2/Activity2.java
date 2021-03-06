/***************************************************************
Names:  Cuasi, Beaverly
        Tulabot, Victor
Group:   48
Section:  S16
***************************************************************/

import java.util.concurrent.locks.*;
import java.util.*;
import java.lang.*;

public class Activity2 {
    public static void main(String args[]){

		int numOfPhilosophers = 5;
		int timesToEat = 5;
		
		DiningTable mon = new DiningTable(numOfPhilosophers);
		Philosopher [] p = new Philosopher[numOfPhilosophers];
		
		System.out.println("Dinner is starting!");
		System.out.println("");
		
		
		for(int i = 0; i < numOfPhilosophers; i++)
			p[i] = new Philosopher(i, timesToEat, mon);
		
		for(int i = 0; i < numOfPhilosophers; i++)
			try {
				p[i].t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("");
		System.out.println("Dinner is over!");		

	}
}
