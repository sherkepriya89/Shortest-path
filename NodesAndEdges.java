import java.util.*;

public class NodesAndEdges extends BFSDFS {
	
	//Constructor
	public NodesAndEdges(List<Distance> edges, List nodes){
		
		// Calls the constructor in BFSDFS to construct the graph without distance.
		super(edges,nodes);
	}

}