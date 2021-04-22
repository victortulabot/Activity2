class retArray{
	private int[] array;
	public int[] getArray() {
		return array;
	}
	public void setArray(int[] array) {
		this.array = array;
	}
}

class Sort implements Runnable{
	private int [] array;
	private retArray arrayValue;

	public Sort(int [] array, retArray arrayValue) {
		this.array = array;
		this.arrayValue = arrayValue;
	}
	public void run() {
		System.out.println(Thread.currentThread().getName());

        for(int i = 0; i < array.length; i++){
            for(int j = i + 1; j < array.length; j++){
                int tmp = 0;
                if(array[i] > array[j]){
                    tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
                System.out.println("Sorting List-" + Thread.currentThread().getName() + ": " + Arrays.toString(array));
            }
        }
		
		arrayValue.setArray(array);
	}
}