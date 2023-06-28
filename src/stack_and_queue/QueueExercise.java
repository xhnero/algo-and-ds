package stack_and_queue;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class QueueExercise {

    public void printBinary(int n){
        final Queue<String> queue = new LinkedList<>();
        queue.offer("1");
        for(int i = 0; i < n ; i++){
            final String val = queue.poll();
            System.out.println(val);
            final String s1 = val + "0";
            final String s2 = val + "1";
            queue.offer(s1);
            queue.offer(s2);
        }
    }
    public void sortStack(final Stack<Integer> stack){
        final Stack<Integer> sortedStack = new Stack<>();
        sortedStack.push(stack.pop());
        while (!stack.isEmpty()){
            final int val = stack.pop();
            while( !sortedStack.isEmpty() && sortedStack.peek() < val){
                stack.push(sortedStack.pop());
            }
            sortedStack.push(val);
        }
        System.out.println("Stack after sorting:");
        while(!sortedStack.isEmpty()){
            System.out.println(sortedStack.pop());
        }
    }
    public void sortStackRec(final Stack<Integer> stack){
        if(!stack.isEmpty()){
            final int val = stack.pop();
            sortStackRec(stack);
            insertToStack(stack, val);
        }
    }
    public int evalPostFix(final String expression){
        final Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < expression.length(); i++){
            final char val = expression.charAt(i);
            if(Character.isDigit(val)){
                stack.push(val - '0');
            }
            else {
                final int first = stack.pop();
                final int second = stack.pop();
                switch (val){
                    case '*':
                        stack.push(first * second);
                        break;
                    case '+':
                        stack.push(first + second);
                        break;
                    case '-':
                        stack.push(second - first);
                        break;
                    case '/':
                        stack.push(second/first);
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pop();
    }

    /**
     * Find celebrity using stack - elimination method
     * @param people
     * @return
     */
    public int findCelebrityUsingStack(int [][] people){
        final int n = people.length;
        final Stack<Integer> stack  = new Stack<>();
        for(int i = 0; i < n; i++){
            stack.push(i);
        }
        while(stack.size() > 1){
            final int first = stack.pop();
            final int second = stack.pop();
            if(people[first][second] == 1){
                stack.push(second);
            }else{
                stack.push(first);
            }
        }
        if(stack.isEmpty()) return -1;
        int celebrity = stack.pop();
        for(int i = 0; i < n; i ++){
            if(celebrity != i && (people[celebrity][i] == 1 || people[i][celebrity] == 0)) {
                return -1;
            }
        }
        return celebrity;
    }
    public int findCelebrityUsingRecursion(int [][] people){
        final int n = people.length;
        final int potentialCelebrity =  findCelebrityUsingRecursion(people, n - 1);
        for(int i = 0; i < n; i ++){
            if(potentialCelebrity != i && (people[potentialCelebrity][i] == 1 || people[i][potentialCelebrity] == 0)) {
                return -1;
            }
        }
        return potentialCelebrity;
    }
    private int findCelebrityUsingRecursion(int [][] people, int n){
        if(n < 0) return -1;
        final int celebrity = findCelebrityUsingRecursion(people, n-1);
        if( celebrity == -1){
            // n could be celebrity
            return n;
        }else {
            if(people[celebrity][n] == 0 && people[n][celebrity] == 1){
                return celebrity;
            }else {
                return n;
            }
        }

    }
    private int [] nextGreaterElement(final int [] arr){
        final Stack<Integer> stack = new Stack<>();
        final int [] res = new int[arr.length];
        for( int i = arr.length - 1; i >= 0; i--){
            while (!stack.isEmpty() && stack.peek() <= arr[i]){
                stack.pop();
            }
            if(stack.isEmpty()){
                res[i] = -1;
            }else {
                res[i] = stack.peek();
            }
            stack.push(arr[i]);
        }
        return res;
    }
    private void insertToStack(final Stack<Integer> stack, final int val){
        if(stack.isEmpty() || stack.peek() >= val){
            stack.push(val);
            return;
        }
        final int current = stack.pop();
        insertToStack(stack, val);
        stack.push(current);
    }
    public int [] findGreater(final int [] nums){
       int left []  = findPrevGreater(nums);
       int right [] = findNextGreater(nums);
       int []  res = new int [nums.length];
       for(int i = 0; i < nums.length; i++){
            int leftIdx = left[i];
            int rightIdx = right[i];
            if(leftIdx != -1 && rightIdx != -1){
                res[i] = (i - leftIdx <= rightIdx - i) ? leftIdx : rightIdx;
            }else if( leftIdx == -1){
                res[i] = rightIdx;
            }else {
                res[i] = leftIdx;
            }
       }
       return res;
    }
    public int [] findPrevGreater(final int [] a){
        final Stack<Integer> stack = new Stack<>();
        final int [] res = new int [a.length];
        for(int i = 0; i < a.length; i++){
            while(!stack.isEmpty() && a[i] >= a[stack.peek()]){
                stack.pop();
            }
            if(stack.isEmpty()){
                res[i] = -1;
            }else{
                res[i] = stack.peek();
            }
            stack.push(i);
        }
        return res;
    }
    public int [] findNextGreater(final int [] nums){
        final Stack<Integer> stack = new Stack<>();
        final int [] res = new int[nums.length];
        for(int i = nums.length - 1; i >= 0; i --){
            while(!stack.isEmpty() && nums[i] >= nums[stack.peek()]){
                stack.pop();
            }
            if(stack.isEmpty()){
                res[i] = -1;
            }else{
                res[i] = stack.peek();
            }
            stack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        final QueueExercise queueExercise = new QueueExercise();
        final int [] nums = {58, 477, 371, 149, 185, 8, 66, 325};
        System.out.println(Arrays.toString(queueExercise.findGreater(nums)));
    }
}
