import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;

/**
Node within a Graph depicting roads, which have been read in from a file
(based on Travel Maps from https://travelmapping.net/graphs/). 
*/
public class Vertex implements Comparable<Vertex> {
	
	/** Roadway intersection with description and position (latitude, longitude) */
	private final Waypoint point;
	
	/** List of edges that this waypoint is part of (i.e. an endpoint) */
	private LinkedList<RoadSegment> adjacent = new LinkedList<>();
	
	/** During graph traversal, indicates if already visited. */
	private Boolean visited = false;
	
	/** During graph tranversal, indicates which Vertex led to this one. */
	private Vertex parent = null;
	
	/** The distance measure for Dijkstra algo */
	private Double distance = Double.POSITIVE_INFINITY;
	
	// Note that the type is Double so we can make use of infinity
	
	/**
	* Constructor that sets point -- no setter for this value.
	* @param point Waypoint designating a road intersection
	*/
	public Vertex(Waypoint point) {
	
		this.point = point;
	}
	
	@Override
	public String toString() {
		String vertexString = point.toString()+" ADJ --> ";
		for (RoadSegment edge : adjacent) {
			if (edge.point1().ID()==point.ID()) {
				vertexString += edge.point2().ID()+" ";
			} else {
				vertexString += edge.point1().ID()+" ";
			}
		}
		return vertexString;
	}

	/**
 	* Basis of comparison
  	*/
	@Override
	public int compareTo(Vertex other) {
		return distance.compareTo(other.distance);
	}
	
	/**
	* Add an edge to which this.point belongs.
	* @param edge Represents a road segment connecting 2 waypoints.
	*/
	public void addAdjacent(RoadSegment edge) {
		adjacent.add(edge);
	}
	
	// setters and getters
	
	// getter only - do not want user to modify waypoing
	public Waypoint point() {
		return point;
	}

	public Vertex parent() {
		return parent;
	}
	public void parent(Vertex p) {
		parent = p;
	}
	
	public Boolean visited() {
		return visited;
	}
	public void visited(Boolean v) {
		visited = v;
	}
	
	public Double distance() {
		return distance;
	}
	
	public void distance(double dist) {
		distance = dist;
	}
	
	// getter only. The list can be changed through addAdjacent.
	public LinkedList<RoadSegment> adjacent() {
		return adjacent;	
	}
}
