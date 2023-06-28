package tree;
import java.util.*;
import java.util.List;

public class TreeExercise {
    public static void main(String[] args) {
        final BinarySearchTree binarySearchTree = new BinarySearchTree();
        final String path ="file1.txt\nfile2.txt\nlongfile.txt";
        System.out.println(path.length());
        System.out.println(binarySearchTree.longestPath(path));
    }

    private static class BinarySearchTree {
        private enum TraverseMode {
            Recursion,
            Iteration
        }

        private final Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        private TreeNode<Integer> root;

        public BinarySearchTree() {
            root = null;
        }

        public BinarySearchTree(final int data) {
            root = new TreeNode<>(data);
        }

        public TreeNode<Integer> getRoot() {
            return root;
        }

        public void setRoot(TreeNode<Integer> root) {
            this.root = root;
        }

        public boolean add(final int data) {
            return insertInBSTUsingIterative(root, data);
        }

        /**
         * Add new node to the tree
         *
         * @param data
         * @param mode
         * @return
         */
        public boolean add(int data, TraverseMode mode) {
            switch (mode) {
                case Iteration:
                    return insertInBSTUsingIterative(root, data);
                case Recursion:
                    return insertInBSTUsingRecursion(root, data);
                default:
                    return false;
            }
        }

        /**
         * Delete a node in a tree
         * There are 3 possible cases:
         * <p> Node is a leaf </p>
         * <p> Node is parent node which have only one child </p>
         * <p> Node is parent node which have two children </p>
         *
         * @param data
         * @return
         */
        public boolean delete(int data) {
            TreeNode<Integer> parentNode = null;
            TreeNode<Integer> currentNode = root;
            while (currentNode != null && currentNode.data != data) {
                parentNode = currentNode;
                currentNode = currentNode.data > data ? currentNode.left : currentNode.right;
            }
            if (currentNode == null) {
                return false;
            }
            // we found the leaf node
            if (currentNode.left == null && currentNode.right == null) {
                if (parentNode == null) { // tree has only root
                    root = null;
                } else {
                    if (currentNode.data > parentNode.data) {
                        parentNode.right = null;
                    } else {
                        parentNode.left = null;
                    }
                }
                return true;
                // we found the parent leaf with only child
            } else if (currentNode.left == null) {
                if (parentNode == null) {
                    root = currentNode.right;
                } else if (currentNode.data > parentNode.data) {
                    parentNode.right = currentNode.right;
                } else {
                    parentNode.left = currentNode.right;
                }
                return true;
            } else if (currentNode.right == null) {
                if (parentNode == null) {
                    root = currentNode.left;
                } else if (currentNode.data > parentNode.data) {
                    parentNode.right = currentNode.left;
                } else {
                    parentNode.left = currentNode.left;
                }
                return true;
            } else {
                final TreeNode<Integer> min = getMin(currentNode.right);
                final int tmp = min.data;
                delete(tmp);
                currentNode.data = tmp;
                return true;
            }
        }

        /**
         * Get the minimum node of the tree
         * The minimum node will be the left most of the tree
         *
         * @param root
         * @return
         */
        public TreeNode<Integer> getMin(final TreeNode<Integer> root) {
            TreeNode<Integer> current = root;
            while (current != null && current.left != null) {
                current = current.left;
            }
            return current;
        }

        /**
         * insert new node to the tree using recursion
         *
         * @param root
         * @param data
         * @return
         */
        public boolean insertInBSTUsingRecursion(final TreeNode<Integer> root, final int data) {
            if (root == null && this.root == null) {
                this.root = new TreeNode<>(data);
                return true;
            } else if (root == null && this.root != null) {
                return false;
            }
            if (root.data > data) {
                if (root.left == null) {
                    root.left = new TreeNode<>(data);
                    return true;
                } else {
                    return insertInBSTUsingRecursion(root.left, data);
                }
            } else {
                if (root.right == null) {
                    root.right = new TreeNode<>(data);
                    return true;
                } else {
                    return insertInBSTUsingRecursion(root.right, data);
                }
            }
        }

        /**
         * Insert to the tree using iteration
         *
         * @param root
         * @param data
         * @return
         */
        public boolean insertInBSTUsingIterative(final TreeNode<Integer> root, final int data) {
            if (root == null && this.root == null) {
                this.root = new TreeNode<>(data);
                return true;
            }
            TreeNode<Integer> currentNode = root;
            while (currentNode != null) {
                if (currentNode.data > data) {
                    if (currentNode.left == null) {
                        currentNode.left = new TreeNode<>(data);
                        return true;
                    } else {
                        currentNode = currentNode.left;
                    }
                } else {
                    if (currentNode.right == null) {
                        currentNode.right = new TreeNode<>(data);
                        return true;
                    } else {
                        currentNode = currentNode.right;
                    }
                }
            }
            return false;
        }

        /**
         * Search in binary search tree using iteration
         *
         * @param data
         * @return
         */
        public TreeNode<Integer> search(final int data) {
            TreeNode<Integer> currentNode = root;
            while (currentNode != null) {
                if (currentNode.data == data) {
                    return currentNode;
                } else if (currentNode.data > data) {
                    currentNode = currentNode.left;
                } else {
                    currentNode = currentNode.right;
                }
            }
            return null;
        }

        /**
         * search in BST using recursion
         *
         * @param data
         * @return
         */
        public TreeNode<Integer> searchUsingRecursion(final int data) {
            return searchUsingRecursion(root, data);
        }

        public void preOrderTraverse() {
            preOrderTraverse(root);
        }

        public void preOrderTraverseNoRecursion() {
            if (root == null) {
                return;
            }
            final Stack<TreeNode<Integer>> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                final TreeNode<Integer> current = stack.pop();
                System.out.print(current.data + " ");
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left != null) {
                    stack.push(current.left);
                }
            }
        }

        /**
         * Traverse the tree using preOrder
         *
         * @param root
         */
        public void preOrderTraverse(final TreeNode<Integer> root) {
            if (root == null) return;
            System.out.print(root.data + " ");
            preOrderTraverse(root.left);
            preOrderTraverse(root.right);
        }

        final Queue<TreeNode<Integer>> queue = new LinkedList<>();

        /**
         * Traverse the tree using in-order
         *
         * @param root
         */
        public void inOrderTraverse(TreeNode<Integer> root) {
            final Queue<TreeNode<Integer>> queue = new LinkedList<>();
            inOrderTraverse(root, queue);
        }

        private void inOrderTraverse(final TreeNode<Integer> root, final Queue<TreeNode<Integer>> queue) {
            if (root == null) {
                return;
            }
            inOrderTraverse(root.left);
            queue.offer(root);
            inOrderTraverse(root.right);
        }

        /**
         * Traverse the tree using post-order
         */
        public void postOrderTraverse() {
            final Queue<TreeNode<Integer>> queue = new LinkedList<>();
            postOrderTraverse(queue, root);
            while (!queue.isEmpty()) {
                System.out.print(queue.poll().data + " ");
            }
        }

        /**
         * Convert sorted array into binary search tree
         *
         * @param sortedArray
         * @return
         */
        public TreeNode<Integer> constructBST(final int[] sortedArray) {
            final int l = 0;
            final int h = sortedArray.length - 1;
            return constructBST(sortedArray, l, h);
        }

        public TreeNode<Integer> constructBST(final TreeNode<Integer>[] sortedNode) {
            int l = 0;
            int h = sortedNode.length;
            return constructBST(sortedNode, l, h);
        }

        private TreeNode<Integer> constructBST(final TreeNode<Integer>[] sortedNode, final int l, final int h) {
            if (l > h) return null;
            int mid = l + (h - l) / 2;
            final TreeNode<Integer> root = sortedNode[mid];
            root.left = constructBST(sortedNode, l, mid - 1);
            root.right = constructBST(sortedNode, mid + 1, h);
            return root;
        }

        /**
         * convert unbalanced bst into balanced bst
         *
         * @param root
         * @return
         */
        public TreeNode<Integer> balanceTree(final TreeNode<Integer> root) {
            final Queue<TreeNode<Integer>> inOrderQueue = new LinkedList<>();
            inOrderTraverse(root, inOrderQueue);
            final TreeNode<Integer>[] sortedArray = new TreeNode[queue.size()];
            int cnt = 0;
            while (!inOrderQueue.isEmpty()) {
                sortedArray[cnt++] = inOrderQueue.poll();
            }
            return constructBST(sortedArray);
        }

        Stack<Integer> maxStack = new Stack<>();

        /**
         * Find Kth max nodes in bst
         *
         * @param root
         * @param k
         * @return
         */
        public int findKthMax(TreeNode<Integer> root, int k) {
            findKthMaxUsingRecursive(root, k);
            if (maxStack.isEmpty()) return -1;
            else return maxStack.pop();
        }

        private void findKthMaxUsingRecursive(TreeNode<Integer> root, int k) {
            if (root == null) return;
            findKthMaxUsingRecursive(root.right, k);
            if (maxStack.size() < k) {
                maxStack.push(root.data);
            }
            if (maxStack.size() == k) {
                return;
            } else {
                findKthMaxUsingRecursive(root.left, k);
            }
        }

        public String travelBSTUsingGraph(TreeNode<Integer> root, int n, int des) {
            final int[] edgeTo = new int[n];
            travelBSTUsingGraph(edgeTo, root, n);
            final Stack<Integer> stack = new Stack<>();
            for (int x = des; x != root.data; x = edgeTo[x]) {
                stack.push(x);
            }
            stack.push(root.data);
            List<String> res = new LinkedList<>();
            while (!stack.isEmpty()) {
                res.add(stack.pop() + "");
            }
            return String.join(",", res);
        }

        public void travelBSTUsingGraph(final int[] edgeTo, TreeNode<Integer> root, int n) {
            if (root == null) {
                return;
            }
            final TreeNode<Integer> left = root.left;
            final TreeNode<Integer> right = root.right;
            if (left != null) {
                edgeTo[left.data] = root.data;
                travelBSTUsingGraph(edgeTo, root.left, n);
            }
            if (right != null) {
                edgeTo[right.data] = root.data;
                travelBSTUsingGraph(edgeTo, root.right, n);
            }
        }

        /**
         * Find all ancestors of a node in BST
         *
         * @param root
         * @param k
         * @return
         */
        public String findAncestorsOfBST(TreeNode<Integer> root, int k) {
            final List<String> list = new LinkedList<>();
            final boolean isFound = findAncestorsOfBST(list, root, k);
            if (isFound) {
                return String.join(",", list);
            } else {
                return "";
            }
        }

        private boolean findAncestorsOfBST(final List<String> list, TreeNode<Integer> root, int k) {
            if (root == null) {
                return false;
            }
            list.add(root.data + "");
            if (root.data == k) {
                return true;
            }
            if (root.data < k) {
                return findAncestorsOfBST(list, root.right, k);
            } else {
                return findAncestorsOfBST(list, root.left, k);
            }
        }

        /**
         * find ancestors of a node in the BST using backtracking
         *
         * @param root
         * @param k
         * @return
         */
        public static boolean isFound = false;

        public List<Integer> findAncestors(TreeNode<Integer> root, int k) {
            final List<Integer> list = new LinkedList<>();
            findAncestors(root, k, list);
            return list;
        }

        /**
         * Travel the tree by level order using dfs
         *
         * @param root
         */
        public void levelOrderTraverseUsingRecursive(final TreeNode<Integer> root) {
            final int h = getHeightUsingBFS(root);
            for (int i = 1; i <= h; i++) {
                levelOrderTraverseUsingRecursive(root, i);
                System.out.println();
            }
        }

        /**
         * Travel the tree by level order using dfs
         *
         * @param root
         * @param h
         */
        public void levelOrderTraverseUsingRecursive(final TreeNode<Integer> root, int h) {
            if (root == null) {
                return;
            }
            if (h == 1) {
                System.out.print(root.data + " ");
            } else if (h > 1) {
                levelOrderTraverseUsingRecursive(root.getLeft(), h - 1);
                levelOrderTraverseUsingRecursive(root.getRight(), h - 1);
            }
        }

        /**
         * get height of the tree using dfs
         *
         * @param root
         * @return
         */
        public int getHeight(final TreeNode<Integer> root) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max(getHeight(root.getLeft()), getHeight(root.getRight()));
        }

        public int getHeightUsingBFS(final TreeNode<Integer> root) {
            if (root == null) return 0;
            final Queue<TreeNode<Integer>> queue = new LinkedList<>();
            queue.offer(root);
            int height = 0;
            while (!queue.isEmpty()) {
                final int size = queue.size();
                for (int i = 0; i < size; ++i) {
                    final TreeNode<Integer> curr = queue.poll();
                    if (curr.left != null) {
                        queue.offer(curr.left);
                    }
                    if (curr.right != null) {
                        queue.offer(curr.right);
                    }
                }
                height++;
            }
            return height;
        }

        /**
         * find the nodes have k distance from root using dfs (using bfs is much easier but have more space complexity)
         *
         * @param root
         * @param k
         * @return
         */
        public String findKNodes(final TreeNode<Integer> root, int k) {
            final StringBuilder stringBuilder = new StringBuilder();
            findK(stringBuilder, root, k);
            return stringBuilder.toString();
        }

        private void findK(final StringBuilder sb, final TreeNode<Integer> root, final int k) {
            if (root == null) {
                return;
            }
            if (k == 0) {
                sb.append(root.data + ",");
            } else {
                findK(sb, root.left, k - 1);
                findK(sb, root.right, k - 1);
            }
        }

        /**
         * Travel the tree by level order using bfs
         *
         * @param root
         * @param k
         */
        public void levelTraverse(TreeNode<Integer> root, int k) {
            if (root == null) {
                return;
            }
            final Queue<TreeNode<Integer>> queue = new LinkedList<>();
            queue.offer(root);
            int depth = 0;
            while (!queue.isEmpty() && depth < k) {
                final int size = queue.size();
                System.out.print("Level " + depth + ":");
                for (int i = 0; i < size; i++) {
                    final TreeNode<Integer> curr = queue.poll();
                    System.out.print(" " + curr.data);
                    if (curr.left != null) {
                        queue.offer(curr.left);
                    }
                    if (curr.right != null) {
                        queue.offer(curr.right);
                    }
                }
                depth++;
                System.out.println();
            }
            System.out.println();
        }

        private void findAncestors(final TreeNode<Integer> root, final int k, final List<Integer> stack) {
            if (root == null) {
                isFound = false;
                return;
            }
            stack.add(root.data);
            if (root.data == k) {
                isFound = true;
                return;
            }
            findAncestors(root.left, k, stack);
            findAncestors(root.right, k, stack);
            if (!isFound && !stack.isEmpty()) {
                stack.remove(stack.size() - 1);
            }
        }

        private TreeNode<Integer> constructBST(final int[] sortedArr, final int l, final int h) {
            if (l > h) return null;
            final int mid = l + (h - l) / 2;
            final TreeNode<Integer> root = new TreeNode<>(sortedArr[mid]);
            root.left = constructBST(sortedArr, l, mid - 1);
            root.right = constructBST(sortedArr, mid + 1, h);
            return root;
        }

        private void postOrderTraverse(final Queue<TreeNode<Integer>> queue, final TreeNode<Integer> root) {
            if (root == null) {
                return;
            }
            postOrderTraverse(queue, root.left);
            postOrderTraverse(queue, root.right);
            queue.offer(root);
        }

        private TreeNode<Integer> searchUsingRecursion(final TreeNode<Integer> root, final int data) {
            if (root == null || root.data == data) {
                return root;
            } else if (root.data > data) {
                return searchUsingRecursion(root.left, data);
            } else {
                return searchUsingRecursion(root.right, data);
            }
        }

        /**
         * populate a map to store all node in the tree along with their parent node
         *
         * @param root
         */
        public void constructParent(final TreeNode<Integer> root) {

            constructParent(root, null);
        }

        private void constructParent(final TreeNode<Integer> child, final TreeNode<Integer> parent) {
            if (child == null || parentMap.containsKey(child)) {
                return;
            }
            parentMap.put(child, parent);
            constructParent(child.left, child);
            constructParent(child.right, child);
        }

        /**
         * get parent of current node
         *
         * @param root
         * @param target
         * @return
         */
        public TreeNode<Integer> getParentOf(final TreeNode<Integer> root, final TreeNode<Integer> target) {
            if (root == null) {
                return null;
            }
            if (root.left == target || root.right == target) {
                return root;
            }
            final TreeNode<Integer> left = getParentOf(root.left, target);
            final TreeNode<Integer> right = getParentOf(root.right, target);
            return left != null ? left : right;
        }
        public long digitTreeSum(final TreeNode<Character> tree){
            long sum = 0;
            return digitTreeSum(tree, sum);
        }

        /**
         * calculate the sum in binary tree - each node represents an integer number
         * @param tree
         * @param sum
         * @return
         */
        public long digitTreeSum(final TreeNode<Character> tree, long sum){
            long tmpSum = sum * 10 + tree.data - '0';
            if(tree.left == null && tree.right == null){
                return tmpSum;
            }
            long left = digitTreeSum(tree.left, tmpSum);
            long right = digitTreeSum(tree.right, tmpSum);
            return left + right;
        }

        /**
         * Method is used to convert system path to normal tree
         * Eg. `user\n\tpictures\n\tdocuments\n\t\tnotes.txt` can be converted to the tree below:
         *      user
         *          pictures
         *          documents
         *              nodes.txt
         * @return
         */
        public List<Integer> [] convertSystemPathToTree(final String [] paths){
            final List<Integer> [] connections = new LinkedList[paths.length];
            for(int i = 0; i < connections.length; i++){
                connections[i] = new LinkedList<>();
            }
            final Map<Integer, List<Integer>> levelMap = new HashMap<>();
            final int [] levels = new int[paths.length];
            for(int i = 0; i < paths.length; i++){
                String val = paths[i];
                int first = val.indexOf("\t");
                int last = val.lastIndexOf("\t");
                int level = last != -1?  last - first + 1 : 0;
                final List<Integer> vertices = levelMap.containsKey(level) ? levelMap.get(level): new LinkedList<>();
                levels[i] = level;
                vertices.add(i);
                levelMap.put(level, vertices);
            }
            for(int i = 0; i < connections.length; i++){
                buildTree(connections, levelMap, i, levels);
            }
            return connections;

        }
        private void buildTree(final List<Integer> [] connections, final Map<Integer, List<Integer>> levelMap, final int node, final int [] levels){
            final int level = levels[node];
            final List<Integer> possibleChildren = levelMap.get(level + 1);
            if (possibleChildren == null){
                return;
            }
            for(final int child: possibleChildren){
                if(isChild(node, child, levels)){
                    connections[node].add(child);
                }
            }
        }
        private boolean isChild(final int parent, final int node, final int [] levels){
            int parentLevel = levels[parent];
            int i = node;
            while(i >= 0 && levels[i] != parentLevel){
                i--;
            }
            return i == parent;
        }
        public int longestPath(final String systemPath){
            final String [] paths = systemPath.split("\n");
            final List<Integer>[] connections = convertSystemPathToTree(paths);
            return longestPath(connections, paths);
        }
        /**
         * find the longest path in the tree - this is not necessary a binary tree
         * @param connections
         * @return
         */
        public int longestPath(final List<Integer> [] connections, final String [] paths){
            final List<List<Integer>> list = new LinkedList<>();
            final List<Integer> subList = new LinkedList<>();
            for(int i = 0; i < connections.length; i++){
                longestPath(connections,list, subList, i, paths);
            }
            int max = 0;
            for(int i = 0; i < list.size(); i++){
                final List<Integer> tmp = list.get(i);
                int count = tmp.size() - 1;
                for(final int idx : tmp){
                    final String val = paths[idx];
                    final int lastIdx = val.lastIndexOf("\t");
                    count += val.substring(lastIdx + 1).length();
                }
                max = Math.max(max, count);
            }
            return max;
        }
        public void longestPath(final List<Integer> [] connections, final List<List<Integer>> res, final List<Integer> subList, final int node, final String [] paths){
            subList.add(node);
            if(connections[node].isEmpty() && paths[node].contains(".")){
               final List<Integer> list = new LinkedList<>(subList);
               res.add(list);
            }
            for(int i = 0; i < connections[node].size(); i++){
                longestPath(connections, res, subList, connections[node].get(i), paths);
            }
            subList.remove(subList.size() - 1);
        }

    }
    private static class NormalTree<T>{
        private T data;
        private List<NormalTree<T>> children;
        public NormalTree (T data) {
            this.data = data;
            children = new LinkedList<>();
        }
    }

    private static class TreeNode<T> {
        private T data;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
            left = null;
            right = null;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        public void setRight(TreeNode<T> right) {
            this.right = right;
        }

    }
}
