
import java.util.*;

public class ShortestPath extends BFSDFS {
	private PriorityQueue<Distance>[] queues;

	//Constructor for graph with distance
	public ShortestPath(List<Distance> edges, List nodes){
		super((List)edges, nodes);
		createQueues(edges, nodes.size());
	}

	//Create priority queue adjacency list from edge list
	public void createQueues(List<Distance> edges, int numOfNodes){
		//queues is an array of priority queues
		queues = new PriorityQueue[numOfNodes];
		//Fill queue 
		for (int i = 0;i < queues.length;i++ ) {
			queues[i] = new PriorityQueue();
		}
		//Add the edges to the priorities by there node from
		for (Distance edge: edges) {
			queues[edge.pFrom].offer(edge);
		}
	}


	//Method to create a copy of the queue
	private PriorityQueue<Distance>[] copyQueues(PriorityQueue<Distance>[] queues){
		PriorityQueue<Distance>[] copiedQueues = new PriorityQueue[queues.length];

		for (int i = 0;i < queues.length; i++ ) {
			copiedQueues[i] = new PriorityQueue<Distance>();
			for(Distance e: queues[i]){
				copiedQueues[i].add(e);
			}
		}

		return copiedQueues;
	}

/*-------------------------------------------------Shortest Path--------------------------------------------------*/
	public ShortestPathTree getShortestPath(int source, int destination){
		//V stores the nodes whose path found so far
		Set<Integer> V = new HashSet<Integer>();
		//V initially contains the source
		V.add(source);
		//Number of nodes
		int numOfNodes = nodes.length;
		//Parent stores the previous node of source in the path
		int[] parent = new int[numOfNodes];

		parent[source] = -1;

		//cost[i] stores the cost of source to the source
		double[] costs = new double[numOfNodes];
		for(int i = 0;i < costs.length;i++){
			costs[i] = Double.MAX_VALUE;//
		}
		costs[source] = 0.0; //cost of the source is 0

		//Get a copy of queues
		PriorityQueue<Distance>[] queues = copyQueues(this.queues);

		//Expand nodes found
		while(V.size() < numOfNodes){
			int v = -1;
			double minimumCost = Double.MAX_VALUE; 
			for(int u : V){
				//Stop the search if u equals destination
				if(u == destination){
					break;
				}
				//the path to u has already been found and is in the set V
				while(!queues[u].isEmpty() && V.contains(queues[u].peek().pTo)) {
					queues[u].remove(); //remove nodes in nodes found
				}

				if(queues[u].isEmpty()){
					//all nodes ajacent to u are in nodesFound
					continue;
				}

				//check to see if the cost of u is the minimumCost
				Distance e = queues[u].peek();
				if (costs[u] + e.distance < minimumCost){
					v = e.pTo;
					minimumCost = costs[u] + e.distance;
					//u will be the parent of v, if v is added to the tree
					parent[v] = u;
				}
			}

			if(v != -1) {
				//Add the new node to the set V
				V.add(v);
				costs[v] = minimumCost;
			}
			//We reached the edge of the graph
			else {
				break;
			}
			
			
		}

		return new ShortestPathTree(source, destination, parent, costs);
	}
/*--------------------------------------------------------------------------------------------------------------------*/

	public class ShortestPathTree extends Tree {
	//costs[v] is the cost from v to source
	private	double[] costs;

	//Contruct a path
	public ShortestPathTree(int source, Integer destination, int[] parent, double[] costs){
		super(source, destination, parent);
		this.costs = costs;
	}

	//Return the cost of the root node to a node v
	public double getCost(int v){
		return costs[v];
	}
}
}

