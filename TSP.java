import java.util.*;
import java.io.*;
import java.util.regex.*;

public class TSP {
	int name; 
	double x, y;
	
	//Stores the information about the city.
	TSP(int node, double x, double y) {
		this.name = node;
		this.x = x;
		this.y = y;
	}

	TSP(int node) {
		name = node; //node with 0 edges
	}
	
	// Method to compute the shortest distance using formula d=√((x2-x1 )^2+(y2-y1 )^2 )
	public static double computeDistance(TSP a, TSP b) {
		return Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)));
	}
	
	public static void main(String[] args) throws IOException {
		
		// The list showing the connections between the cities.
		List<String> pathConnection = Arrays.asList(
				"1-2,3,4", 
				"2-3", 
				"3-4,5", 
				"4-5", 
				"5-7,8", 
				"6-8", 
				"7-9,10",
				"8-9,10,11", 
				"9-11", 
				"10-11");

		// List to store the edges
		List<Distance> edges = new ArrayList<Distance>();

		// List to store the edges with distances
		List<Distance> edgesDistances = new ArrayList<Distance>();

		// List of nodes
		List<Integer> nodes = new ArrayList<Integer>();

		// We need to add 0 to the nodes to keep the array big enough while using nodes.size()
		nodes.add(0);

		// Name of the input file. In case to check with different data set, change the file name here.
		String filename = "locations.tsp";

		// List to store the city information from the input file.
		List<TSP> cities = new ArrayList<TSP>();

		// To read the input file
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		// The regular expression for parsing the input file to find cities and its x and y values.
		// Example (1 87.951292 2.658162)
		// (?m) 	- Turns Flag On/Off Multi Line = true
		// (^) 		- Begin Of Line
		// (\\d) 	- Repeat Digit one or more times
		// (\\s)	- White Space Character
		// (\\d) 	- Repeat Digit one or more times then "."
		// (\\d) 	- Repeat Digit one or more times
		// (\\s)	- White Space Character
		// (\\d) 	- Repeat Digit one or more times then "."
		// (\\d) 	- Repeat Digit one or more times
		String regex = "(?m)^\\d+\\s\\d+\\.\\d+\\s\\d+\\.\\d+"; 
		Pattern r = Pattern.compile(regex);

		
		String value = null;
		while ((value = reader.readLine()) != null) { 	// while loop to read the input file line by line
			Matcher m = r.matcher(value); 				// Matches the value with the regular expression
			if (m.find()) { 							// If matched adds the city and its x and y value to the cities list
				cities.add(new TSP(Integer.parseInt(value.split(" ")[0]), Double.parseDouble(value.split(" ")[1]),
						Double.parseDouble(value.split(" ")[2])));
			}
		}

		// Create the cities map
		HashMap<Integer, TSP> citiesMap = new HashMap<Integer, TSP>();
		for (TSP p : cities) {
			citiesMap.put(p.name, p);
		}


		// Get nodes and edges and add it to nodes and edges list
		for (String s : pathConnection) {  // From the list path connection
			String[] parChd = s.split("-"); 
			nodes.add(Integer.parseInt(parChd[0])); // Add the node in the nodes List
			for (String a : parChd[1].split(",")) {
				Distance e = new Distance(Integer.parseInt(parChd[0]), Integer.parseInt(a), 0);
				edges.add(e); // Add the edge in the edges List
			}
		}

		// As we don't have any path with the last node 11 and 11 is not present in the path connection list
		//Add node 11 to nodes list
		nodes.add(11);

		// Get the distance between two cities
		for (Distance e : edges) {
			if (e.pTo != 0) {
				Double dis = computeDistance(citiesMap.get(e.pFrom), citiesMap.get(e.pTo));
				Distance we = new Distance(e.pFrom, e.pTo, dis);
				edgesDistances.add(we); // Add the distance in the edgesDistances List
			}
		}
		
		
		
		
		// Perform BFS and DFS search and compute the shortest distance between two cities.
		
		//Initialize graph with nodes and edges (no distance)
		NodesAndEdges graph = new NodesAndEdges(edges, nodes);
		
		//Initialize Start Time and End Time Variables.
		long startTime;
		long endTime;
/*-------------------------------------------------BFS--------------------------------------------------*/
		//Start Time before performing BFS
		startTime = System.nanoTime();
		//Perform BFS
		BFSDFS.Tree tbfs = graph.bfs(1,11);
		//Print BFS
		System.out.println("BFS: " + tbfs.getSearchOrders());
		//End Time end performing BFS
		endTime = System.nanoTime();
		//Time taken for BFS 
		System.out.println("BFS Run time: " + (endTime - startTime)+" nanoseconds\n");
/*------------------------------------------------------------------------------------------------------*/

		
/*-------------------------------------------------DFS--------------------------------------------------*/
		//Start Time before performing DFS
		startTime = System.nanoTime();
		//Perform DFS
		BFSDFS.Tree tdfs = graph.dfs(1,11);
		//Print DFS
		System.out.println("DFS: " + tdfs.getSearchOrders());
		//End Time end performing DFS
		endTime = System.nanoTime();
		//Time taken for DFS 
		System.out.println("DFS Run time: " + (endTime - startTime)+" nanoseconds\n");
/*------------------------------------------------------------------------------------------------------*/

		
/*----------------------------Compute Shortest Path and Shortest Distance-------------------------------*/
		ShortestPath shortestDistanceGraph = new ShortestPath(edgesDistances, nodes);
		ShortestPath.ShortestPathTree spt = shortestDistanceGraph.getShortestPath(1,11);
		System.out.println("The shortest path from 1 to 11 is: [ " + spt.printPath(11)+ "]\n");
		System.out.println("The shortest distance from 1 to 11 is: " + spt.getCost(11));
	}
/*------------------------------------------------------------------------------------------------------*/
	
}


