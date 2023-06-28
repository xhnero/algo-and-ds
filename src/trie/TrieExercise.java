package trie;
import java.util.*;

public class TrieExercise {
    public static void main(String[] args) {
        final String [] strs = {"ab", "a"};
        System.out.println(new TrieExercise().getLongestCommonPrefix(strs));
    }

    /**
     * find the longest common prefix eg. flight, flow, flood have 'fl' as their longest common prefix
     * The ide is put all words to trie and search for each level such that number of children is 1, keep searching until
     * root is null or end of word. We also need to check for empty word since an empty string cannot be inserted, so
     * there is possibility not all words can be inserted ( trie.numberOfWords != input.length)
     * @param strs an array contain words
     * @return
     */
    public String getLongestCommonPrefix(final String [] strs){
        final Trie trie = new Trie();
        for(final String val: strs){
            trie.insert(val);
        }
        if(strs.length != trie.numberOfWord){
            return "";
        }
        TrieNode root = trie.root;
        final StringBuilder sb = new StringBuilder();
        while (root != null && !root.isEndWord){
            final TrieNode [] children = root.children;
            int count = 0;
            int idx = 0;
            for(int i = 0; i < 26; i++){
                if(children[i] != null){
                    root = children[i];
                    count ++;
                    idx = i;
                }
            }
            if(count > 1 || count == 0){
                return sb.toString();
            }
            final char val = (char) (idx + 'a');
            sb.append(val);
        }
        return sb.toString();
    }

    /**
     * Class trie
     */
    private static class Trie {
        private TrieNode root;
        private int numberOfWord = 0;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * Return index of current character
         * @param c
         * @return
         */
        public int getIndexOf(final char c) {
            return c - 'a';
        }

        /**
         * insert a string to the trie
         * note that the trie will not store the char value, it only stores the index of characters
         * @param key
         */
        public void insert(final String key) {
            if (key == null || key.isEmpty()) {
                return;
            }
            TrieNode currentNode = root;
            key.toLowerCase(Locale.ROOT);
            for (int level = 0; level < key.length(); level++) {
                final char current = key.charAt(level);
                final int currentIdx = getIndexOf(current);
                if (currentNode.children[currentIdx] == null) {
                    currentNode.children[currentIdx] = new TrieNode();
                }
                currentNode = currentNode.children[currentIdx];
            }
            numberOfWord ++;
            currentNode.markAsLeaf();
        }

        /**
         * Search a word in trie using iterative
         * @param key
         * @return
         */
        public boolean search(final String key) {
            if (key == null) {
                return false;
            }
            TrieNode current = root;
            key.toLowerCase(Locale.ROOT);
            for (int level = 0; level < key.length(); level++) {
                final char c = key.charAt(level);
                final int idx = getIndexOf(c);
                if (current.children[idx] == null) {
                    return false;
                }
                current = current.children[idx];
            }
            return current.isEndWord;
        }

        /**
         * search a word in trie using recursive
         * @param key
         * @param curr
         * @param level
         * @return
         */
        public boolean searchUsingRecursive(final String key, final TrieNode curr, final int level){
            if(curr == null){
                return false;
            }
            if(level == key.length() && curr != null){
                return curr.isEndWord;
            }
            int idx = getIndexOf(key.charAt(level));
            return searchUsingRecursive(key, curr.children[idx],level+1 );
        }

        /**
         * delete a word in a trie
         * @param key
         */
        public void delete(final String key){
            deleteHelper(key, root, 0);
        }

        /**
         * Helper method to delete the word in a trie using recursive
         * @param key
         * @param currentNode
         * @param level
         * @return
         */
        public boolean deleteHelper(final String key, final TrieNode currentNode, final int level) {
            boolean deleteSelf = false;
            if (currentNode == null) {
                return false;
            }
            // we found the key
            if (level == key.length()) {
                if (hasChildren(currentNode)) {
                    currentNode.unMarkAsLeaf();
                    deleteSelf = false;
                } else {
                    deleteSelf = true;

                }
            } else {
                final boolean childDeleted = deleteHelper(key, currentNode.children[getIndexOf(key.charAt(level))], level + 1);
                if (childDeleted) {
                    currentNode.children[getIndexOf(key.charAt(level))] = null;
                    if (currentNode.isEndWord) {
                        deleteSelf = false;
                    } else if (hasChildren(currentNode)) {
                        deleteSelf = false;
                    } else {
                        deleteSelf = true;
                    }
                } else {
                    deleteSelf = false;
                }
            }
            return deleteSelf;
        }

        /**
         * Check if node has children
         * @param current
         * @return
         */
        public boolean hasChildren(final TrieNode current){
            final TrieNode [] children = current.children;
            for(final TrieNode child: children){
                if(child != null){
                    return true;
                }
            }
            return false;
        }

        /**
         * count words in trie
         * @param root
         * @return
         */
        public int countWord(final TrieNode root){
            if(root == null){
                return 0;
            }
            int count = 0;
            final TrieNode [] children = root.children;
            for(int i = 0; i < 26; i ++){
                count += countWord(children[i]);
            }
            return root.isEndWord ? count + 1 : count;
        }
        public List<String> getWords(final TrieNode root){
            final List<String> res = new ArrayList<>();
            final StringBuilder sb = new StringBuilder();
            getWords(root, res, sb);
            return res;
        }

        public void getWords(final TrieNode root, List<String> res, final StringBuilder sb) {
            if (root.isEndWord) {
                res.add(sb.toString());
            }
            final TrieNode[] children = root.children;
            for (int i = 0; i < 26; i++) {
                if(children[i] != null){
                    char c = (char)(i + 'a');
                    sb.append(c);
                    getWords(children[i], res, sb);
                }
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }

    /**
     * class trie node to represent a node of a trie
     */
    private static class TrieNode{
        private TrieNode [] children;
        private boolean isEndWord;
        public TrieNode(){
           children = new TrieNode[26];
           isEndWord = false;
        }
        public void markAsLeaf(){
            this.isEndWord = true;
        }
        public void unMarkAsLeaf(){
            this.isEndWord = false;
        }
    }
}
