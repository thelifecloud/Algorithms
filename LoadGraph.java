import java.util.*;
import java.io.*;

/** 
Reader for a file of type tmg (Travel Mapping Graph), 
which is a way to store road information.
https://travelmapping.net/graphs/
*/
public class LoadGraph {
		
	public static RoadGraph load(String filename) {
	
		// Store waypoints read from file - stored in order that they are read
		ArrayList<Waypoint> points = new ArrayList<>();
		
		// Store edges read from file - reference order (indexing) of waypoints
		ArrayList<RoadSegment> edges = new ArrayList<>();
		
		// Store Graph Vertices comprised of a Waypoint and Adjacent Edges
		ArrayList<Vertex> vertices = new ArrayList<>();
	
		// Try with resource to open the file.
		try (Scanner scanner = new Scanner(new File(filename))) {
		
			String[] parsed;
			
			// 1st line describes the file contents (not relevant)
			scanner.nextLine();
			
			// 2nd line specifies the number of vertices and edges
			int pointCount = scanner.nextInt();
			int edgeCount = scanner.nextInt();
			scanner.nextLine(); // eat the linefeed
			
			// Store all waypoints in an ArrayList
			for (int i=0; i<pointCount; i++) {

				// parse each line that corresponds to a single waypoint
				parsed = scanner.nextLine().split(" ",0);
				
				// order is description, latitude, longitude
				points.add(new Waypoint(
					i,
					parsed[0], 
					Double.parseDouble(parsed[1]),
					Double.parseDouble(parsed[2])
				));
				
				// Now we make a Vertex -- it wraps the new Waypoint
				vertices.add(new Vertex(points.get(i)));
				
			} // end read waypoints
			
			// Store all edges in an ArrayList 
			for (int i=0; i<edgeCount; i++) {
			
				parsed = scanner.nextLine().split(" ",0);
			
				// 2 numbers correspond to indexing of waypoints just read in.
				int index1 = Integer.valueOf(parsed[0]);
				int index2 = Integer.valueOf(parsed[1]);
				
				edges.add(
					new RoadSegment(points.get(index1), points.get(index2)
				));
				
				// Now add the edge to the adjacency list of each endpoint
				// endpoints are referenced using the indices read from File
				// add the edge just added to the edges.
				vertices.get(index1).addAdjacent(edges.get(i));
				vertices.get(index2).addAdjacent(edges.get(i));
			} // end read edges
			
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file. Jumping ship.");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return new RoadGraph(points,edges,vertices);

	} // end load()

} // end class LoadGraph
