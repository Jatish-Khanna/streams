import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {
	public static void main(String[] args) {
		long s = System.nanoTime();
		List<Integer> module = IntStream.range(1, 1000000)
				.filter(value -> value % 2 == 0)
				.map(value -> value * 2)
				.boxed()
				.collect(Collectors.toList());
		long f = System.nanoTime();
		System.out.println("Time taken to filter [even] values with modulo operation: "+ TimeUnit.SECONDS.convert(f-s, TimeUnit.MICROSECONDS));
		
		s = System.nanoTime();
		List<Integer> bit = IntStream.range(1, 1000000)
				.filter(value -> (value & 1) == 0)
				.map(value -> value << 1)
				.boxed()
				.collect(Collectors.toList());
		f = System.nanoTime();
		System.out.println("Time taken to filter [even] values with bitwise operation: "+ TimeUnit.SECONDS.convert(f-s, TimeUnit.MICROSECONDS));
		Iterator<Integer> itModulo = module.iterator();
		Iterator<Integer> itBit = bit.iterator();
		
		int it1 = 0;
		int it2 = 0;

		while(itModulo.hasNext() && itBit.hasNext()){
			it1 = itModulo.next();
			it2 = itBit.next();
			if(it1 != it2){
				System.out.println("Issues: " + it1 +" "+ it2);
				break;
			}
		}
	}
}
