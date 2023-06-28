package heap;
import java.util.*;

public class HeapExercise {
    public static void main(String[] args) {
        final String [] words = {"the","day","is","sunny","the","the","the","sunny","is","is"};
        final HeapExercise heapExercise = new HeapExercise();
        System.out.println(heapExercise.topKFrequent(words, 4));
    }

    public List<String> topKFrequent(String[] words, int k) {
        final Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], map.getOrDefault(words[i], 0) + 1);
        }
        final PriorityQueue<Element> minHeap = new PriorityQueue<>();
        final Element[] elements = new Element[map.size()];
        int cnt = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            final Element element = new Element(entry.getKey(), entry.getValue());
            elements[cnt++] = element;
        }
        for (int i = 0; i < k; i++) {
            minHeap.offer(elements[i]);
        }
        for (int i = k; i < elements.length && !minHeap.isEmpty(); i++) {
            final Element curr = elements[i];
            final Element min = minHeap.peek();
            if (curr.freq > min.freq) {
                minHeap.poll();
                minHeap.offer(curr);
            } else if (curr.freq == min.freq && curr.val.compareTo(min.val) <= 0) {
                minHeap.poll();
                minHeap.offer(curr);
            }
        }
        final List<String> res = new LinkedList<>();
        while (!minHeap.isEmpty()) {
            res.add(0, minHeap.poll().val);
        }
        return res;
    }

    private static class Element implements Comparable<Element> {
        String val;
        int freq;

        private Element(final String val, final int freq) {
            this.val = val;
            this.freq = freq;
        }

        public int compareTo(final Element el) {
            if (this.freq < el.freq) {
                return -1;
            } else if (this.freq == el.freq) {
                return el.val.compareTo(this.val);
            } else {
                return 1;
            }
        }
    }
    private static class Heap{
        private int [] heapArr;
        private int size = 0;
        public Heap(final int [] heapArr){
            this.heapArr = heapArr;
            size = heapArr.length;
            buildMaxHeap(this.heapArr);
        }
        public int pop() {
            int root = heapArr[0];
            //swap root and last node
            heapArr[0] = heapArr[size - 1];
            heapArr[size - 1] = root;
            size--;
            maxHeapify(heapArr, 0, size);
            return root;
        }
        public boolean isEmpty(){
            return size == 0;
        }
        private void maxHeapify(final int [] heapArr, int idx, final int heapSize){
            int largest = idx;
            while(largest < heapSize/2){
                final int left = 2*largest +1;
                final int right = 2*largest + 2;
                if(left < heapSize && heapArr[left] > heapArr[largest]){
                    largest = left;
                }
                if(right < heapSize && heapArr[right] > heapArr[largest]){
                    largest = right;
                }
                if(idx != largest){
                    int tmp = heapArr[idx];
                    heapArr[idx] = heapArr[largest];
                    heapArr[largest] = tmp;
                    idx = largest;
                }else {
                    break;
                }
            }
        }
        private void buildMaxHeap(final int [] arr){
            final int size = arr.length;
            for(int i = (size - 1)/2; i>=0; i--){
                maxHeapify(arr, i, size);
            }
        }

    }
}