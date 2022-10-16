public class Distance implements Comparable<Distance> {
	public double distance; //Distance between two cities

	//Constructor to takes two cities and distance between them
	int pFrom;
	int pTo;
	public Distance(int u, int v, double weight){
		this.pFrom = u;
		this.pTo = v;
		this.distance = weight;
	}

	//Method used to compare two edges with distance to cities distance
	public int compareTo(Distance edge){
		if(distance > edge.distance){
			return 1;
		}
		else if(distance == edge.distance){
			return 0;
		}
		else {
			return -1;
		}
	}
}