import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class Solution {
	private static final int MAX_ARRAY_SIZE = 2147483639;

	public static void main(String[] args) {
		long s = System.nanoTime();
		IntStream.range(1, Integer.MAX_VALUE)
				.filter(element -> (element - MAX_ARRAY_SIZE > 0))
				.findFirst();
		long f = System.nanoTime();
		
		System.out.println("Time take by subtract filter: " + TimeUnit.MILLISECONDS.convert(f-s, TimeUnit.NANOSECONDS));
		
		s = System.nanoTime();
		IntStream.range(1, Integer.MAX_VALUE)
				.filter(element -> (element > MAX_ARRAY_SIZE))
				.findFirst();
		f = System.nanoTime();
		
		System.out.println("Time take by subtract filter: " + TimeUnit.MILLISECONDS.convert(f-s, TimeUnit.NANOSECONDS));
	
	}
}
