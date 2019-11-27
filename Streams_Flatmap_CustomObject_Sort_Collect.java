import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {
  static class Result {
    int value;
    public Result(int value) {
      this.value = value;
    }
    
    @Override
    public String toString() {
      System.out.println(" "+ value);
      return String.valueOf(value);
    }
    
    public int getValue() {
      return this.value;
    }
  }

  public static void main(String[] args) {

    Map<Integer, List<Integer>> map = new HashMap<>();
    map.put(1, Arrays.asList(1,2,4,3,2,5,6));
    map.put(2, Arrays.asList(1,10,15,17,13));
    map.put(3, Arrays.asList(8,1));
    map.put(4, Arrays.asList(6,7));
    
     List<Result> result = map.values()
       .stream()
       .flatMap(list -> list.stream())
       .map(Result::new)
       .sorted(Comparator.comparing(Result::getValue).reversed())
       .limit(10)
       .collect(Collectors.toList());
    System.out.println(result);
  }
