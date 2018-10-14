import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;

class FindWordsFrequency {

 public static void calculateAndPrintWordsFrequency (String remarks){
		String r1 = remarks;
    
    // Unique Set of stop words
		Set<String> stopWords = new HashSet<>();
    
    // Adding Sample stop words in HashSet - Lookup O(1)
		stopWords.add("I");
		stopWords.add("this");
    
    /*
    * Split the string delimited by " "
    * Map each string to equivalent -> lower case ("hotel".equalsIgnoreCase("Hotel") == true)
    * collect the collections using Collectors.groupingBy
    * Function.identity() -> the lowercase value or argument to pipe
    * Collectors.counting -> count the frequency as value 
    */
		Map<String, Long> count = Stream.of(r1.split(" "))
					.filter(word -> !stopWords.contains(word))
					.map(String::toLowerCase)
					.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		
    /*
    * Iterate over each entitySet
    * print entry.key and entry.value
    */
		count.entrySet()
			.stream()
			.forEach(keyPair -> System.out.println(keyPair.getKey() + " " + keyPair.getValue()));
	
  }
  
  public static void main (String []args) {
    String remarks = "I love this love hotel";
    calculateAndPrintWordsFrequency( remarks );
  }

}
