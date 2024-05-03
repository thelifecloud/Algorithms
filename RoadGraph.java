import java.util.*;
import java.io.*;

/**
Stores the collection of waypoints and road segments (edges) from map file.
The adjacency list indicates which waypoints (nodes) this point connects to.
If 2 nodes connect, it means that there exists a road from one point to the other. The distance of the RoadSegment indicates how far between each node.
The distance of an edge is referred to as its weight.
*/
public class RoadGraph {

	/** All waypoints (locations of road intersections) within the graph. */
	ArrayList<Waypoint> points = null;
	
	/** All connections between waypoints within the graph. */
	ArrayList<RoadSegment> edges = null;
	
	/** Graph structure wrapping each Waypoint */
	ArrayList<Vertex> vertices = null;
	
	/** 
	* Constructor (no default).
	* @param points List of all waypoints in the graph.
	* @param edges List of all connected waypoints in the graph.
	* @param nodes List of nodes that wrap waypoints and provide adjacent nodes
	*/
	public RoadGraph(
		ArrayList<Waypoint> points, 
		ArrayList<RoadSegment> edges,
		ArrayList<Vertex> vertices) {
		
		this.points = points;
		this.edges = edges;
		this.vertices = vertices;
	}
	
	/** Nice display of all waypoints and edges. */
	public void display() {

		displayPoints();
		
		System.out.println("\nROAD SEGMENTS (edges)");
		for (RoadSegment r: edges) {
			System.out.println(r);
		}
		
		System.out.println("\nVertices");
		for (Vertex v: vertices) {
			System.out.println(v);
		}
	} // end display()
	
	/** Nice display of waypoints only - with indices so user can choose. */
	public void displayPoints() {
		System.out.println("\nWAYPOINTS");
		for (int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));
		}	
	}
	
} // end class RoadGraph