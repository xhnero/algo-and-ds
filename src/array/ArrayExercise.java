import java.util.*;

public class ArrayExercise {
    public void removeDuplicateFromSortedArray(final int [] nums){
        int i = 0;
        for(int j = 1; j < nums.length; j++){
            if(nums[i] != nums[j]){
                i++;
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
    }

}
