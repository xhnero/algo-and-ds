package searching_and_sorting;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SearchingAndSortingExercise {
    public static void main(String[] args) {
        final int[] nums = new int[]{0};
        final SearchingAndSortingExercise ss = new SearchingAndSortingExercise();
        System.out.println(ss.longestConsecutive(nums));
    }

    /**
     * Selection sort
     * The idea is find the smallest element in array for each loop
     *
     * @param nums
     */
    public void selectionSort(final int[] nums) {
        final int n = nums.length;
        for (int i = 0; i < n; ++i) {
            int minIdx = getMin(nums, i, n);
            int min = nums[minIdx];
            nums[minIdx] = nums[i];
            nums[i] = min;
        }
    }

    /**
     * Quick sort with recursive
     * The idea is using the pivot ( can be any element in the array ) and move all elements which are smaller than
     * pivot to the left and all bigger one on the right then do recursive call
     *
     * @param nums
     */
    public void quickSort(final int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    private void quickSort(final int[] nums, final int low, final int high) {
        if (low >= high) {
            return;
        }
        int i = partition(nums, low, high);
        quickSort(nums, low, i - 1);
        quickSort(nums, i + 1, high);
    }

    /**
     * Method to find the median of three number, it will be used to get the pivot when sorting with quicksort
     * It ensures the smallest or largest number will be not picked
     *
     * @param low
     * @param high
     * @return
     */
    private int getPivot(final int low, final int high) {
        int range = high - low + 1;
        final Random random = new Random();
        int first = low + random.nextInt(range);
        int second = low + random.nextInt(range);
        int third = low + random.nextInt(range);
        return Math.max(Math.min(first, second), Math.min(Math.max(first, second), third));
    }

    private int partition(int[] nums, int low, int high) {
        int pivotIdx = getPivot(low, high);
        exchange(nums, low, pivotIdx);
        int pivot = nums[low];
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            if (nums[j] <= pivot) {
                i++;
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        nums[low] = nums[i];
        nums[i] = pivot;
        return i;
    }

    private void exchange(final int[] nums, final int low, final int pivotIdx) {
        int tmp = nums[pivotIdx];
        nums[pivotIdx] = nums[low];
        nums[low] = tmp;
    }

    private int getMin(final int[] nums, final int startIdx, final int length) {
        int min = Integer.MAX_VALUE;
        int minIdx = startIdx;
        for (int i = startIdx; i < length; i++) {
            if (min > nums[i]) {
                min = nums[i];
                minIdx = i;
            }
        }
        return minIdx;
    }

    /**
     * Method for insertion sort - add number to sorted array
     *
     * @param nums
     */
    public void insertionSort(final int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int key = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > key) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = key;
        }
    }

    /**
     * Merge sort using divide and conquer
     *
     * @param nums
     * @param low
     * @param high
     */
    public void mergeSort(final int[] nums, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(nums, low, mid);
            mergeSort(nums, mid + 1, high);
            merge(nums, low, high);
        }
    }

    /**
     * Merge two sorted array
     *
     * @param nums
     * @param low
     * @param high
     */
    public void merge(final int[] nums, final int low, final int high) {
        final int mid = low + (high - low) / 2;
        int leftSize = mid - low + 1;
        int rightSize = high - mid;
        final int[] left = new int[leftSize];
        final int[] right = new int[rightSize];
        // copy array
        for (int i = 0; i < leftSize; i++) {
            left[i] = nums[low + i];
        }
        for (int j = 0; j < rightSize; j++) {
            right[j] = nums[mid + j + 1];
        }
        // merge two sorted arr
        int i = 0;
        int j = 0;
        int k = low;
        while (i < leftSize && j < rightSize) {
            if (left[i] < right[j]) {
                nums[k] = left[i];
                i++;
            } else {
                nums[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < leftSize) {
            nums[k++] = left[i++];
        }
        while (j < rightSize) {
            nums[k++] = right[j++];
        }
    }

    /**
     * find the idx of array where rotation starts using two pointers
     *
     * @param nums
     * @return
     */
    public int findStartOfRotation(final int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        int mid = low + (high - low) / 2;
        int left = mid;
        int right = mid + 1;
        while (left >= low && nums[left] <= nums[mid]) {
            left--;
        }
        while (right <= high && nums[right] >= nums[mid]) {
            right++;
        }
        return (right < high) ? right : low;
    }

    /**
     * find the item if it presents in rotated sorted array - elements in the array is not necessary to be distinct
     * @param nums
     * @return
     */
    public int findInRotatedArray(final int[] nums, int val, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = low + (high - low) / 2;
        if (nums[mid] == val) {
            return mid;
        }
        if (nums[mid] > nums[low]) { // left array already sorted
            if (val >= nums[low] && val < nums[mid]) {
                return findInRotatedArray(nums, val, low, mid - 1);// val must be on the left side
            } else {
                return findInRotatedArray(nums, val, mid + 1, high);// val must be on the left side
            }
        } else if (nums[mid] < nums[low]) { //right side already sorted
            if (val <= nums[high] && val > nums[mid]) {
                return findInRotatedArray(nums, val, mid + 1, high);// val must be on the left side
            } else {
                return findInRotatedArray(nums, val, low, mid - 1);// val must be on the left side
            }
        } else {
            if (nums[mid] != nums[high]) {
                return findInRotatedArray(nums, val, mid + 1, high);// val must be on the left side
            } else {
                final int idxFound = findInRotatedArray(nums, val, mid + 1, high);
                if (idxFound == -1) {
                    return findInRotatedArray(nums, val, low, mid - 1);// val must be on the left side
                }
                return idxFound;
            }
        }
    }

    /**
     * Find max value in rotated sorted array, only work if there's no duplicated elements
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public int findMaxInRotatedArray(final int [] nums, final int low, final int high){
        if(low == high){
            return low;
        }
        int mid = low + (high - low)/2;
        if(nums[mid] > nums[mid + 1]){
            return mid;
        }
        if(nums[mid] >= nums[low]){
            return findMaxInRotatedArray(nums, mid + 1, high);
        }else {
            return findMaxInRotatedArray(nums, low, mid - 1);
        }
    }

    /**
     * Find the minimum number in rotated sorted array, only works if there's no duplicated elements
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public int findMinInRotatedArray(final int [] nums, final int low, final int high){
        if(low == high){
            return low;
        }
        int mid = low + (high - low)/2;
        if(mid == 0){
            return nums[low] < nums[high] ? low : high;
        }
        if( nums[mid] < nums[mid - 1]){
            return mid;
        }
        else if(nums[mid] >= nums[high]){
           return findMinInRotatedArray(nums, mid + 1, high);
        }else {
            return findMinInRotatedArray(nums, low, mid - 1);
        }
    }

    /**
     * Find the value if it presents in the array, otherwise return largest number which is closest to value
     * @param nums
     * @param target
     * @return
     */
    public int findClosestNumber(final int [] nums, final int target){
        int low = 0;
        int high = nums.length - 1;
        while(low <= high){
            int mid = low + (high - low)/2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return low;
    }
    /**
     * Find the value if it presents in the array, otherwise return largest number which is closest to value
     * @param nums
     * @param target
     * @return
     */
    public int findNearestNumber(final int [] nums, final int target){
        int low = 0;
        int high = nums.length - 1;
        while(low <= high){
            int mid = low + (high - low)/2;
            if(nums[mid] > target){
                high = mid - 1;
            }else if(nums[mid] < target) {
                low = mid + 1;
            }else {
                low = mid + 1;
            }
        }
        return low;
    }

    public int findClosest(int [] nums, int target){
        int low = 0;
        int high = nums.length - 1;
        while(low <= high){
            int mid = low + (high - low)/2;
            int val = nums[mid];
            if(val < target){
                low = mid + 1;
            }else if(val > target){
                high = mid - 1;
            }else {
                high = mid - 1;
            }
        }
        return high >= 0 && high < nums.length ? high : -1;
    }
    /** * This method based in the idea of flaterned the 2D matrix to 1D array then use binary search to get the output
     * Search in sorted matrix with condition: the first integer of next row greater than the last integer of previous row,
     * if the condition does not meet, we have to use two pointers
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchInSortedMatrix(final int [][] matrix, int target){
        int numberOfColumns = matrix[0].length;
        int numberOfRows = matrix.length;
        int min = 0;
        int max = numberOfColumns * numberOfRows - 1;
        while(min <= max){
            int mid = min + (max - min)/2;
            int row = mid/numberOfColumns;
            int col = mid % numberOfColumns;
            int val = matrix[row][col];
            if(val == target){
                return true;
            }else if(val > target){
                max = mid - 1;
            }else {
                min = mid + 1;
            }
        }
        return false;
    }

    /**
     * Search in sorted matrix using two pointers
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchInSortedMatrix2P(final int [][] matrix, int target){
       int m = matrix.length;
       int n = matrix[0].length;
       int i = m - 1;
       int j = 0;
       while(i >= 0 && j <n){
           if(matrix[i][j] == target){
               return true;
           }else if(matrix[i][j] < target){
               j ++;
           }else {
               i--;
           }
       }
       return false;
    }
    public void printSortedMatrix(final int [][] matrix){
        int numberOfColumns = matrix[0].length;
        int numberOfRows = matrix.length;
        int min = 0;
        int max = numberOfColumns * numberOfRows - 1;
        for(int i = min; i <= max; i++){
            int row = i/numberOfColumns;
            int col = i % numberOfColumns;
            int val = matrix[row][col];
            System.out.print("" + val + ",");
        }
    }

    /**
     * Count number of occurrences of a key in sorted array
     * The idea is using binary search tree ( Linear search is very straight forward, and we will skip it)
     * @param nums
     * @param key
     * @return
     */
    public int calcFreq(final int [] nums, final int key){
        return calcFreq(nums, 0, nums.length - 1, key);
    }
    public int calcFreq(final int [] nums, int low, int high, int key){
        if(low > high) return 0;
        int count = 0;
        final int mid = low + (high - low)/2;
        if(nums[mid] == key){
            count ++;
            count += calcFreq(nums, low, mid - 1, key) + calcFreq(nums, mid + 1, high, key);
        }else if(nums[mid] < key){
            count += calcFreq(nums, mid + 1, high, key);
        }else {
            count += calcFreq(nums, low, mid - 1, key);
        }
        return count;
    }

    /**
     * find the first position of element
     * @param nums
     * @param key
     * @return
     */
    public int findFirstPosition(final int [] nums, final int key){
        int low = 0;
        int high = nums.length - 1;
        int first = -1;
        while(low <= high){
            final int mid = low + (high - low)/2;
            final int val = nums[mid];
            if(val == key){
                first = mid;
                high = mid - 1;
            }else if(val > key) {
                high = mid - 1;
            }else {
                low = mid + 1;
            }
        }
        return first;
    }

    /**
     * find the last position of an element
     * @param nums
     * @param key
     * @return
     */
    public int findLastPosition(final int [] nums, final int key){
        int low = 0;
        int high = nums.length - 1;
        int last = -1;
        while(low <= high){
            final int mid = low + (high - low)/2;
            final int val = nums[mid];
            if(val == key){
                last = mid;
                low = mid + 1;
            }else if(val > key) {
                high = mid - 1;
            }else {
                low = mid + 1;
            }
        }
        return last;
    }
    /**
     * Search the first and last position of an element in sorted array using binary search
     * @param nums
     * @param key
     * @return
     */
    public int[] searchRange(int [] nums, int key){
        int low = 0;
        int high = nums.length - 1;
        return searchRange(nums, low, high, key);
    }

    /**
     * Search the first and last position of an element in sorted array using binary search and linear search
     * We use binary search to search the first index of the target and from that index, shift left to get the first
     * index and shift right to get the last index. Worst case O(n) if all elements in the array are the same
     * @param nums
     * @param key
     * @return
     */
    public int [] searchRangeV2(int [] nums, int key){
        int low = 0;
        int high = nums.length - 1;
        final int idx = find(nums, key);
        if(idx == -1){
            return new int[]{-1,-1};
        }
        int first = idx;
        int last = idx;
        for(int i = idx - 1; i>= 0 && nums[i] == key; i--){
            first --;
        }
        for(int j = idx + 1; j < nums.length && nums[j] == key; j++){
            last ++;
        }
        return new int[]{first, last};
    }

    /**
     * Find first and last position of Elements in sorted array
     * The idea is using binary search to scan the index of element. If element is found in the middle, we need to scan
     * both left and right to get minimum and maximum index. If element is not found in the middle, do binary search to
     * search the key
     * @param nums
     * @param key
     * @return
     */
    public int[] searchRange(final int[] nums, final int low, final int high, final int key) {
        if (low > high) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }
        final int mid = low + (high - low) / 2;
        final int val = nums[mid];
        if (val == key) {
            int left[] = searchRange(nums, low, mid - 1, key);
            int right[] = searchRange(nums, mid + 1, high, key);
            final int first = Math.min(mid, left[0]);
            final int last = Math.max(mid, right[1]);
            return new int[]{first, last};
        } else if (val > key) {
            return searchRange(nums, low, mid - 1, key);
        } else {
            return searchRange(nums, mid + 1, high, key);
        }
    }

    /**
     * Very simple binary search
     * @param nums
     * @param target
     * @return
     */
    public int find(final int [] nums, final int target){
        int low = 0;
        int high = nums.length - 1;
        while(low <= high){
            int mid = low + (high - low)/2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else {
                high = mid -1;
            }
        }
        return -1;
    }

    /**
     * Count negative number in sorted matrix - using two pointers
     * Time complexity: O(m + n)
     * @param grid
     * @return
     */
    public int countNegatives(final int [][] grid){
        int m = grid.length;
        int n = grid[0].length;
        int i = m - 1;
        int j = 0;
        int count = 0;
        while(i >= 0 && j < n){
            if(grid[i][j] < 0){
                count += m - j;
                i --;
                j=0;
            }else{
                j ++;
            }
        }
        return count;
    }
    public void dutchNationalFlag(final int [] arr){
        final int [] freq = new int[3];
        for(int i = 0; i < arr.length; i++){
            freq[arr[i]]++;
        }
        int cnt = 0;
        for(int i = 0; i < 3; i++){
            int frequency = freq[i];
            for(int j = 0; j < frequency; j++){
                arr[cnt++] = i;
            }
        }
    }

    /**
     * Find the third max integer in the array can contain duplicate - not using any extra data structure
     * The idea is use three variable : first, second, third to keep track first max, second max, and third max
     * @param nums
     * @return the third max - it should be distinct
     */
    public int thirdMax(int[] nums) {
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        int thirdMax = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if(max == nums[i] || secondMax == nums[i] || thirdMax == nums[i]){
                continue;
            }
            if (max < nums[i]) {
                int tmp = secondMax;
                thirdMax = tmp;
                secondMax = max;
                max = nums[i];
            } else if (secondMax < nums[i]) {
                thirdMax = secondMax;
                secondMax = nums[i];
            } else if (thirdMax < nums[i]) {
                thirdMax = nums[i];
            }
        }
        return thirdMax != Integer.MIN_VALUE ? thirdMax : max;
    }
    public int findKLargest(final  int [] nums, int k){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        final Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int remain = k;
        for(int i = max; i>=min; i--){
            if(map.containsKey(i)){
                remain = remain - map.get(i);
            }
            if(remain <= 0){
                return i;
            }
        }
        return -1;
    }
    public int longestConsecutive(final int [] nums){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++){
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        int len = max  - min + 1;
        int [] freq = new int[len];
        for (int i = 0; i < nums.length; i++){
            freq[nums[i] - min] = 1;
        }
        int count = 0;
        int res = Integer.MIN_VALUE;
        for(int j = 1; j < len; j++){
            if((freq[j] == 1 && freq[j - 1] == 1) || count == 0){
                count ++;
            }else {
                count = 1;
            }
            res = Math.max(res, count);
        }
        return res != Integer.MIN_VALUE ? res + 1 : 1;
    }
}