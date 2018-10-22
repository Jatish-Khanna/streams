import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Test{
	
	public static void main(String[] args) {
		Map<Integer, Long> m = IntStream.range(1,100000)
			.map(Test::hash)
			.map(element -> 15 & element)
			.boxed()
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		m.entrySet().stream()
			.forEach(element -> System.out.println(element.getKey() + " " + element.getValue()));
	}
	
	static final int hash(Object key) {
		int calculatedHash = Objects.hashCode(key);
		return (calculatedHash = key.hashCode()) ^ (calculatedHash >>> 16);
	}
}
