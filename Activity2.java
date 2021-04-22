import java.util.Arrays;

public class Activity2{
	public static void main(String[] args) {

		int arr[] = { 7, 12, 19, 3, 18, 4, 2, 6, 15, 8 };
		int length = arr.length;
		int half_l = length/2;

		int f_arr[] = new int[half_l];
		int s_arr[] = new int[half_l];
		int m_arr[] = new int[length];

		for(int i=0; i < half_l; i++){
			f_arr[i] = arr[i];
		}
		int counter = 0;
		for(int i=half_l; i < length; i++){
			s_arr[counter] = arr[i];
			counter++;
		}

		
		retArray f_arrObj = new retArray();
		retArray s_arrObj = new retArray();

		Thread thrd = new Thread(new Sort(f_arr, f_arrObj));
		thrd.setName("Sorting Thread 0");
		Thread thrd2 = new Thread(new Sort(s_arr, s_arrObj));
		thrd2.setName("Sorting Thread 1");

		thrd.start();
		thrd2.start();
		
		try {
			thrd.join();
			thrd2.join();
			
			System.out.println("First Sorted List: " + Arrays.toString(f_arrObj.getArray()));
			System.out.println("Second Sorted List: " + Arrays.toString(s_arrObj.getArray()));
		} catch (InterruptedException ie) { }

		retArray m_arrObj = new retArray();

		System.arraycopy(f_arrObj.getArray(), 0, m_arr, 0, half_l);
		System.arraycopy(s_arrObj.getArray(), 0, m_arr, half_l, half_l);
		
		Thread thrd3 = new Thread(new Sort(m_arr, m_arrObj));
		thrd3.setName("Merge Thread");
		thrd3.start();
		try {
			thrd3.join();
			System.out.println("Sorted List: " + Arrays.toString(m_arrObj.getArray()));
		} catch (InterruptedException ie) { }

				
	}
	
}