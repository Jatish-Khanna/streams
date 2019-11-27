
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Solution {

  public static void main(String[] args) {
    
    Map<String, Integer> players= new HashMap<>();
    players.put("Christiano Ronaldo",10);
    players.put("Messi",50);
    players.put("Kaka",10);
    players.put("Ronaldinho",100);
    
    System.out.println(players.entrySet().stream()
    .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue)
            .thenComparing(Map.Entry<String, Integer>::getKey))
    .map(Map.Entry::getKey)
    .collect(Collectors.toList()));
    
        System.out.println(players.entrySet().stream()
    .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed()
            .thenComparing(Map.Entry<String, Integer>::getKey))
    .collect(Collectors.toList()));
    
        System.out.println(players.entrySet().stream()
    .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue)
            .thenComparing(Map.Entry<String, Integer>::getKey)).reversed()
    .collect(Collectors.toList()));
    
    // In this Map.Entry.<K,V> is important to compile the code
    System.out.println(players.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Entry.comparingByKey()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
  }
}
