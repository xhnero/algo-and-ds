package hash;
import java.util.HashMap;
import java.util.Map;

public class HashExercise {
    public static void main(String[] args) {
        final HashExercise hashExercise = new HashExercise();
    }
    /**
     * Find all symmetric in an array
     * E.g {4,3} is symmetric of {3,4}
     * This solution only works if there's no duplicated elements in array. For example :
     * put {1,3} then following {1,5} will result fail
     * @param arr
     * @return
     */
    public String findSymmetricPair(int[][] arr) {
        final Map<Integer, Integer> map = new HashMap<>();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            final int first = arr[i][0];
            final int second = arr[i][1];
            if (map.containsKey(second) && map.get(second) == first) {
                sb.append('{').append(second).append(',').append(first).append('}');
            } else {
                map.put(first, second);
            }
        }
        return sb.toString();
    }

    /**
     * Find path in a map, we need to find starting point first by reverse the path - the starting point in reverse path
     * will never point to any direction
     * @param map
     * @return
     */
    public String tracePath(final Map<String, String> map){
        final String from = getStartingPoint(map);
        if(from != null && !from.isEmpty()){
            return tracePath(map, from);
        }
        return "";
    }
    public String getStartingPoint(final Map<String, String> map){
        final Map<String,String> reverseMap = new HashMap<>();
        map.forEach((key, value) -> {
            reverseMap.put(value, key);
        });
        String from = "";
        for(Map.Entry<String, String> entry: map.entrySet()){
            if(!reverseMap.containsKey(entry.getKey())){
                from = entry.getKey();
                break;
            }
        }
        return from;
    }

    /**
     * Given the map of path and source -> print the path
     * @param map
     * @param source
     * @return
     */
    public String tracePath(final Map<String, String> map, final String source){
        String curr = source;
        String res = "";
        while (curr != null) {
            res = res + curr + "->";
            curr = map.get(curr);
        }
        return res;
    }

    /**
     * find the pair in an array which sum to target using Hash - we can use binary search or two pointers as well, but
     * we need to sort the array
     * @param arr
     * @param sum
     * @return
     */
    public String pairSum(final int[] arr, final int sum) {
        final StringBuilder sb = new StringBuilder();
        final Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(sum - arr[i], i);
        }
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i]) && map.get(arr[i]) != i) {
                int idx = map.get(arr[i]);
                sb.append('{').append(arr[i]).append(',').append(arr[idx]).append('}');
            }
        }
        return sb.toString();
    }

    /**
     * find if arrays contain continuous elements which sum = 0
     * The idea is: if a + b + c = 0 then s[i] + a + b + c = s, s is the sum at index [i]. We can put s to the map,
     * and check if s is in the map => there must be continuous element which some = 0
     *
     * @param arr
     * @return
     */
    public boolean findSubZero(final int[] arr) {
        final Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (arr[i] == 0 || sum == 0 || map.containsKey(sum)) {
                return true;
            }
            map.put(sum, i);
        }
        return false;
    }

    /**
     * find the first unique element in an array. The idea is very simple: count the frequency of each element
     * then take the first one which count = 1. If input is an array of character we can use array of 256 character
     * to reduce the need of memory
     *
     * @param nums
     * @return
     */
    public int findFirstUnique(int[] nums) {
        final Map<Integer, Integer> map = new HashMap<>();
        for (final int val : nums) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) == 1) {
                return nums[i];
            }
        }
        return -1;
    }
}