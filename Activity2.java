import java.util.concurrent.locks.*;
import java.util.*;
import java.lang.*;

public class Activity2 {
    public static void main(String args[]){

		int i = 5;


		DiningTable diningTable = new DiningTable(i);
		Philosopher philosophers[] = new Philosopher[i];

		for (int j = 0; j < philosophers.length; j++){
			philosophers[j] = new Philosopher(diningTable, j);
		}

	}
}
