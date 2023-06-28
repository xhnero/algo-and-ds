package graph;
import java.util.*;

public class GraphExercise {
    public static void main(String[] args) {
        final Graph g = new Graph(11);
    }
    private static class DepthFirstSearchOrder{
        private Graph g;
        private Queue<Integer> preOrderQueue;
        private Queue<Integer> postOrderQueue;
        private Stack<Integer> reversePost;
        private int connectedComponent = 0;
        private int [] components;
        public DepthFirstSearchOrder(Graph g){
            this.g = g;
            preOrderQueue = new LinkedList<>();
            postOrderQueue = new LinkedList<>();
            reversePost = new Stack<>();
            components = new int[g.vertices];
            dfs();
        }

        public Graph getG() {
            return g;
        }

        public Queue<Integer> getPreOrderQueue() {
            return preOrderQueue;
        }

        public Queue<Integer> getPostOrderQueue() {
            return postOrderQueue;
        }

        public Stack<Integer> getReversePost() {
            return reversePost;
        }

        /**
         * Travel the graph using dfs
         */
        private void dfs(){
            final int vertices = g.vertices;
            final boolean [] visited = new boolean[vertices];
            for(int i = 0; i < vertices; ++i){
                if(!visited[i]){
                    dfs(visited, i);
                    connectedComponent ++;
                }
            }
        }

        private void dfs(final boolean[] visited, final int v) {
            visited[v] = true;
            components[v] = connectedComponent;
            preOrderQueue.offer(v);
            final List<Integer> adj = g.adjacency[v];
            final Iterator<Integer> it = adj.iterator();
            while (it.hasNext()) {
                final int w = it.next();
                if (!visited[w]) {
                    dfs(visited, w);
                }
            }
            postOrderQueue.offer(v);
            reversePost.push(v);
        }
        private void printConnectedComponent(){
            final Queue<Integer> [] components = new LinkedList[connectedComponent];
            for(int i = 0; i < connectedComponent; ++i){
                components[i] = new LinkedList<>();
            }
            for(int v = 0; v < g.vertices; ++v){
                components[this.components[v]].offer(v);
            }
            for(int i = 0; i < connectedComponent; i++){
                final Queue<Integer> component = components[i];
                System.out.print("Component " + i + 1 + ":" );
                while (!component.isEmpty()){
                    System.out.print(component.poll() + " ");
                }
                System.out.println();
            }
        }
    }
    private static class Graph {
        private int vertices;
        private List<Integer> adjacency[];

        /**
         * Constructor
         * We use the linked list to implement the graph
         * @param vertices number of vertices
         */
        public Graph(final int vertices) {
            this.vertices = vertices;
            adjacency = new LinkedList[vertices];
            for(int i = 0; i < vertices; ++i){
                adjacency[i] = new LinkedList<>();
            }
        }

        /**
         * connect two vertices
         * @param source
         * @param des
         */
        public void addEdge(final int source, final int des) {
            adjacency[source].add(des);
        }

        /**
         * Traverse the graph using bfs
         * Queue will be used
         */
        public void bfs() {
            final Queue<Integer> queue = new LinkedList<>();
            final boolean[] visited = new boolean[vertices];
            for (int i = 0; i < vertices; i++) {
                queue.offer(i);
                while (!queue.isEmpty()) {
                    final int vertex = queue.poll();
                    if (!visited[vertex]) {
                        System.out.print(vertex);
                        visited[vertex] = true;
                        final List<Integer> adjacency = this.adjacency[vertex];
                        final Iterator<Integer> iterator = adjacency.iterator();
                        while (iterator.hasNext()) {
                            queue.offer(iterator.next());
                        }
                    }
                }
            }
        }

        /**
         * Traverse the graph using dfs (no stack)
         */
        public void dfsUsingRecursion(){
            final boolean[] visited = new boolean[vertices];
            for(int i = 0; i < vertices; i++){
                if(!visited[i]){
                    dfs(i, visited);
                }
            }
            System.out.println();
        }

        /**
         * Traverse the graph without recursion
         * Stack will be used
         */
        public void dfsNoRecursion(){
            final boolean[] visited = new boolean[vertices];
            final Stack<Integer> stack = new Stack<>();
            for(int i = 0; i < vertices; i++){
                stack.push(i);
                while(!stack.isEmpty()){
                    final int vertex = stack.pop();
                    if(!visited[vertex]){
                        visited[vertex] = true;
                        System.out.print(vertex);
                        final List<Integer> adjacencyList = adjacency[vertex];
                        final Iterator<Integer> iterator = adjacencyList.iterator();
                        while(iterator.hasNext()){
                            stack.push(iterator.next());
                        }
                    }
                }
            }
            System.out.println();
        }

        /**
         * Method is used to find the mother vertex of a graph.
         * A mother vertex is a vertex which can have path to every vertex in the Graph
         * The theory is the last vertex which is traversed by dfs could be the mother vertex
         * @return mother vertex if it does exist or -1 otherwise
         */
        public int findMotherVertexOptimize(){
            final boolean [] visited = new boolean[vertices];
            int lastV = 0;
            for(int i = 0; i < vertices; i++){
                if(!visited[i]){
                    dfs(i, visited);
                    lastV = i;
                }
            }
            // lastV could be the mother vertex if it does exist
            Arrays.fill(visited, false);
            dfs(lastV, visited);
            for(int i = 0; i < vertices; i++){
                if(!visited[i]) return -1;
            }
            return lastV;

        }

        /**
         * Brute force to find the mother vertex ( un-optimized)
         * @return
         */
        public int findMotherVertex(){
            for(int i = 0; i < vertices; i++){
                if(isMotherVertex(i)){
                    return i;
                }
            }
            return -1;
        }

        /**
         * check whether a vertex is mother vertex or not
         * @param v
         * @return
         */
        public boolean isMotherVertex(final int v){
            final Queue<Integer> queue = new LinkedList<>();
            queue.offer(v);
            final boolean[] visited = new boolean[vertices];
            while(!queue.isEmpty()){
                final int vertex = queue.poll();
                if(!visited[vertex]){
                    visited[vertex] = true;
                    final List<Integer> adj = adjacency[vertex];
                    final Iterator<Integer> it = adj.iterator();
                    while(it.hasNext()){
                       queue.offer(it.next());
                    }
                }
            }
            for(int i = 0; i < vertices; i++){
                if(!visited[i]) return false;
            }
            return true;
        }

        /**
         * Check if the directed graph has a cycle
         * The theory is: we use two stacks one is used to keep track a vertex is traversed ( visited) or not and other
         * is used to keep track all vertex traversed in the recursive call ( stackFlag)
         * @return
         */
        public boolean hasCycle(){
            final boolean [] visited = new boolean[vertices];
            final boolean [] stackFlag = new boolean[vertices];
            for (int i = 0; i < vertices; i++) {
                if (hasCycle(visited, stackFlag, i)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * count number of edges in graph
         * @return
         */
        public int countEdge(){
            int length = 0;
            for(int i = 0; i < vertices; i++){
                length += adjacency[i].size();
            }
            return length; // if graph is undirected then we just divide by 2: length/2
        }

        /**
         * check if  every vertex has only one parent except root
         * @return
         */
        public boolean checkOneParent(){
            final boolean [] hasOneParent = new boolean[vertices];
            for(int i = 0; i < vertices; ++i) {
                final List<Integer> adj = adjacency[i];
                final Iterator<Integer> iterator = adj.iterator();
                while(iterator.hasNext()){
                    final int vertex = iterator.next();
                    if(hasOneParent[vertex]){
                        return false;
                    }
                    hasOneParent[vertex] = true;
                }
            }
            for(int i = 0;  i < vertices; i++){
                if(i == 0 && hasOneParent[0]){
                    return false;
                }else if(i != 0 && !hasOneParent[i]){
                    return false;
                }
            }
            return true;
        }

        /**
         * Check if an undirected graph is connected or not
         * if the graph is directed, we need to reverse all edges then use bfs or dfs to travel from root
         * @return
         */
        public boolean isConnected() {
            final int root = 0;
            final Queue<Integer> queue = new LinkedList<>();
            final boolean[] visited = new boolean[vertices];
            queue.offer(root);
            while (!queue.isEmpty()) {
                final int v = queue.poll();
                if (!visited[v]) {
                    visited[v] = true;
                    final List<Integer> adj = adjacency[v];
                    final Iterator<Integer> iterator = adj.iterator();
                    while (iterator.hasNext()) {
                        queue.offer(iterator.next());
                    }
                }
            }
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) return false;
            }
            return true;
        }

        /**
         * Find the shortest path from s to u
         * @param s
         * @param u
         * @return
         */
        public int shortestPath(int s, int u){
            final Queue<Integer> queue = new LinkedList<>();
            final boolean [] visited = new boolean[vertices];
            final int [] edgeTo = new int[vertices];
            queue.offer(s);
            visited[s] = true;
            while (!queue.isEmpty()){
                final int v = queue.poll();
                final List<Integer> adj = adjacency[v];
                final Iterator<Integer> iterator = adj.iterator();
                while (iterator.hasNext()) {
                    final int w = iterator.next();
                    if (!visited[w]) {
                        edgeTo[w] = v;
                        visited[w] = true;
                        queue.offer(w);
                    }
                }
            }
            int min = 0;
            for(int x = u; x != s; x = edgeTo[x]){
               min ++;
            }
            return min;
        }
        private boolean hasCycle(final boolean [] visited, final boolean [] stackFlag, final int v){
            if(stackFlag[v]) return true;
            if(visited[v]) return false;
            visited[v] = true;
            stackFlag[v] = true;
            final List<Integer> adj = adjacency[v];
            final Iterator<Integer> iterator = adj.iterator();
            while(iterator.hasNext()){
                final int vertex = iterator.next();
                if(hasCycle(visited, stackFlag, vertex)){
                    return true;
                }
            }
            stackFlag[v] = false;
            return false;
        }

        /**
         * Check if undirected graph has a cycle.
         * The idea is very simple: if a vertex is already visited in recursive call but it is not parent, so we can
         * conclude that the graph has a cycle
         * @param visited
         * @param u
         * @param v
         * @return
         */
        public boolean hasCycle(boolean[] visited, int u, int v) {
            visited[u] = true;
            final List<Integer> adj = adjacency[u];
            final Iterator<Integer> iterator = adj.iterator();
            while (iterator.hasNext()) {
                final int vertex = iterator.next();
                if (!visited[vertex]) {
                    if (hasCycle(visited, vertex, u)) {
                        return true;
                    }
                } else if (vertex != v) {
                    return true;
                }
            }
            return false;
        }

        private void dfs(final int vertex, final boolean[] visited) {
            visited[vertex] = true;
            System.out.print(vertex);
            final List<Integer> adjacencyList = adjacency[vertex];
            final Iterator<Integer> iterator = adjacencyList.iterator();
            while (iterator.hasNext()) {
                final int currentVertex = iterator.next();
                if(!visited[currentVertex]){
                    dfs(currentVertex, visited);
                }
            }
        }

        public void printGraph() {
            System.out.println(">> Adjacency list for graph:");
            for (int i = 0; i < vertices; i++) {
                System.out.print("|" + i + "| => ");
                final List<Integer> adjacencyList = adjacency[i];
                final Iterator<Integer> it = adjacencyList.iterator();
                while (it.hasNext()) {
                    System.out.print("[" + it.next() + "] -> ");
                }
                System.out.println("null");
            }
        }
    }

    /**
     * Find the number of vertices in given source and level
     * The idea is: using BFS and the size of the queue for each loop is the number of vertices
     * @param g
     * @param source
     * @param level
     * @return
     */
    public int numberOfNodesInGivenLevel(final Graph g, final int source, int level){
        final Queue<Integer> queue = new LinkedList<>();
        int numberOfVertices = g.vertices;
        final boolean [] visited = new boolean[numberOfVertices];
        queue.offer(source);
        visited[source] = true;
        int res = 0;
        for(int  l = 1; l <= level && !queue.isEmpty(); l++){
            res = queue.size();
            final int size = queue.size();
            for(int i = 0; i < size; i++){
                final int vertex = queue.poll();
                final List<Integer> edges = g.adjacency[vertex];
                final Iterator<Integer> it = edges.iterator();
                while(it.hasNext()){
                    int w = it.next();
                    if(!visited[w]){
                        visited[w] = true;
                        queue.offer(w);
                    }
                }
            }
        }
        return res;
    }

    /**
     * This does same thing as the method above but we can advance the visited array. Normally, visited array is used
     * to keep track the vertices which are already visited but in this method, we also can use it to keep track the level
     * of each vertex.
     * The advantage of this is : we can list level of each vertex after the bfs.
     * This is more recommended than the above method
     * @param g
     * @param source
     * @param level
     * @return
     */
    public int findNumberOfVerticesInGivenLevel(final Graph g, final int source, final int level){
        final Queue<Integer> queue  = new LinkedList<>();
        final int [] visited = new int[g.vertices];
        queue.offer(source);
        visited[source] = 1;
        for(int i = 1; i <= level && !queue.isEmpty(); i++){
           final int v = queue.poll();
           final List<Integer> edges = g.adjacency[v];
           final Iterator<Integer> it = edges.iterator();
           while(it.hasNext()){
               int w = it.next();
               if(visited[w] == 0){
                   visited[w] = visited[v] + 1;
                   queue.offer(w);
               }
           }
        }
        int count = 0;
        for(int i = 0; i < g.vertices; i++){
            if(visited[i] == level) count ++;
        }
        return count;
    }

    /**
     * This method is used to reverse a graph
     * @param g
     * @return
     */
    public Graph reverse(final Graph g){
        final Graph reverse = new Graph(g.vertices);
        final List<Integer> [] adj = g.adjacency;
        for(int i = 0; i < g.vertices; i++){
            final List<Integer> edges = adj[i];
            final Iterator<Integer> it = edges.iterator();
            while (it.hasNext()){
                final int w = it.next();
                reverse.addEdge(w, i);
            }
        }
        return reverse;
    }

    /**
     * This is used to calculate number of paths from source to destination
     * @param g
     * @param source
     * @param des
     * @return
     */
    public int numberOfPaths(final Graph g, final int source, final int des){
        if(source == des) return 1;
        int count = 0;
        final List<Integer> edges = g.adjacency[source];
        final Iterator<Integer> it = edges.iterator();
        while(it.hasNext()){
            int w = it.next();
            count += numberOfPaths(g, w, des);
        }
        return count;
    }
}
