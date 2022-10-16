
import java.util.*;

public class BFSDFS {
	protected Object[] nodes; // Store Nodes
	protected List<Integer>[] adjacentNode; // Adjaceny list
	boolean destinationFound = false; // Destination not found

	// Contructor to generate graph from a List of edges and nodes
	protected BFSDFS(List<Distance> edges, List listNodes) {
		this.nodes = listNodes.toArray();
		createAdjacencyLists(edges, listNodes.size());
	}

	// Creates adjaceny list for each node
	private void createAdjacencyLists(List<Distance> edges, int numOfNodes) {
		// Create a linked list
		adjacentNode = new LinkedList[numOfNodes];
		for (int i = 0; i < numOfNodes; i++) {
			adjacentNode[i] = new java.util.LinkedList<Integer>();
		}
		// Create the list of adjacent nodes
		for (Distance e : edges) {
			adjacentNode[e.pFrom].add(e.pTo);
		}
	}

	/*-------------------------------------------------DFS--------------------------------------------------*/

	// DFS Tree from Source s to Destination d
	public Tree dfs(int s, int d) {
		List<Integer> path = new ArrayList<Integer>(); // List to store the path
		int[] parent = new int[nodes.length];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1;
		}

		// Variable to mark the visited nodes.
		boolean[] isVisited = new boolean[nodes.length];

		// Perform DFS search
		dfs(s, d, parent, path, isVisited);

		// return the Tree
		return new Tree(s, d, parent, path);
	}

	// Method to perform the DFS and stop when recursion when the destination is
	// found.
	private void dfs(int s, int d, int[] parent, List<Integer> path, boolean[] isVisited) {
		// Store the visited node starting from source
		path.add(s);
		// Mark the visited node
		isVisited[s] = true;
		// Stop recursive search if the destination is found.
		if (s == d) {
			destinationFound = true;
			return;
		}
		// iterate over all of the adjacent nodes of the node
		for (int i : adjacentNode[s]) {
			if (!isVisited[i]) {
				parent[i] = s; // i's parent is s
				if (!destinationFound) { // If the destination is not found then only it will perform DFS search
					dfs(i, d, parent, path, isVisited); // DFS Search
				}
			}
		}

	}

	/*------------------------------------------------------------------------------------------------------*/

	/*-------------------------------------------------BFS--------------------------------------------------*/

	// BFS Tree from Root r
	public Tree bfs(int r, int d) {
		List<Integer> path = new ArrayList<Integer>(); // List to store the path
		int[] parent = new int[nodes.length];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1;
		}
		// queue represented by a linked list
		LinkedList<Integer> queue = new LinkedList<Integer>();

		// Variable to mark the visited nodes.
		boolean[] isVisited = new boolean[nodes.length];

		// Add the root node to the queue
		queue.offer(r);
		// Mark the visited node
		isVisited[r] = true;

		// Iterate the nodes when added to the queue
		while (!queue.isEmpty()) {
			int u = queue.poll(); // Dequeue the node to u
	 
	        // If last vertex is the desired destination
	        // then print the path
	        if ((u-1) == d)
	        {
	        	return new Tree(r, d, parent, path);
	        }

			path.add(u);
			for (int w : adjacentNode[u]) {
				if (!isVisited[w]) {
					queue.offer(w); // Enqueue w
					parent[w] = u; // w's parent is u
					isVisited[w] = true;
				}
			}
		}
		return new Tree(r, d, parent, path);
	}
	/*------------------------------------------------------------------------------------------------------*/

	/*---------------------------Tree to store results of BFS and DFS---------------------------------------*/
	// Class tree to store the results of BFS and DFS
	public class Tree {
		private int root; // Root node in the tree
		private int d;
		private int[] parent; // Parent of each node
		private List<Integer> path; // Store the path

		// Constructor for graph without distance to record the path.
		public Tree(int root, int d, int[] parent, List<Integer> path) {
			this.root = root;
			this.d = d;
			this.parent = parent;
			this.path = path;
		}

		// Constructor for graph with distance to record the path.
		public Tree(int root, int d, int[] parent) {
			this.root = root;
			this.parent = parent;
			this.d = d;
		}

		// Return the order that the nodes were searched in
		public List<Integer> getSearchOrders() {
			return path;
		}
		/*------------------------------------------------------------------------------------------------------*/

		/*--------------------------------Path Iterator to print the path---------------------------------------*/

		// PathIterator class is need for printing the path
		public class PathIterator implements java.util.Iterator {
			private Stack<Integer> stack;

			public PathIterator(int v) {
				stack = new Stack<Integer>();
				do {
					stack.add(v);
					v = parent[v];
				} while (v != -1);
			}

			// Check if there is next element in the iterator
			public boolean hasNext() {
				return !stack.isEmpty();
			}

			// Gets the current element in the iterator and move
			// the iterator to point to the next element
			public Object next() {
				return nodes[stack.pop()];
			}

		}
		/*------------------------------------------------------------------------------------------------------*/

		// Print path from the root node to destination d
		public String printPath(int d) {
			String sp = "";
			PathIterator pathIterator = new PathIterator(d);
			Iterator it = pathIterator;
			while (it.hasNext()) {
				sp = sp + (it.next() + " ");
			}
			return sp;
		}
	}

}