import java.util.ArrayList;
import java.util.PriorityQueue;


public class PathFinder {

	/** In PathFinder.findPath(), use the SAME CODE to implement the BFS and DFS. Determine what the user wants to run and define the queue appropriately (FIFO for BFS and LIFO for DFS). Also implement Dijkstra's algorithm. Choose which code to execute based on the algo passed to the function. */

	/** findPath calls user input graph traversal algo: "bfs", "dfs", "dijkstra"
	 *@param graph	set of vertices, edges, and waypoints from .tmg file
	 *@param point1	source point for algorithm calls (starting vertex for traversal)
	 *@param algo user input graph traversal algorith : "bfs" "dfs" "dijkstra"
  	 */
	public static void findPath(RoadGraph graph, int point1, String algo) {
		System.out.println("---- Run FindPath ----");
		if(algo.equalsIgnoreCase("dijkstra")) {
			// call dijkstra
			System.out.println("--- call dijkstra ---");
			dijkstra(graph, point1);
		} else {
			// else "bfs" or "dfs", call solve
			System.out.println("--- solve bfs | dfs ---");
			solve(graph, point1, algo);
		}
	}
	/** Solve graph by getting parent pointers back to source for each vertex on graph
	 *@param g the graph to be traversed
	 *@param point1 waypoint value within source vertex
	 *@param algo  traversal type: "bfs" or "dfs"
	 */
	public static void solve(RoadGraph g, int point1, String algo) {
		// init Q to be either Queue or Stack depending on chosen algo
		QueueInterface<Vertex> Q;
		// popped vertex
		Vertex u;
		// adjacent vertex 
		Vertex v;
		if(algo.equals("bfs")) {
			// bfs data structure
			Q = new Queue<>();
		} else {
			// dfs data structure
			Q = new Stack<>();
		}
		// push source vertex onto Q
		Q.push(g.vertices.get(point1)); 
		System.out.println("--- Q.peek: " + Q.peek() + " ---");
		
		// while traversal incomplete
		while(!Q.isEmpty()) {
			// pop prioritized value
			u = Q.pop();
			// for each edge adjacent to popped vertex u get the adjacent vertex v
			for(RoadSegment edge : u.adjacent()) {
				v = null;
				// make sure we are talking about the correct waypoint of the edge
				// if Waypoint of u is not point1 then it is point2
				if(u.point() != edge.point1()) {
					// popped vertex u is point2 of edge
					v = g.vertices.get(edge.point1().ID());
				} else {
					// popped vertex u is point1 of edge
					v = g.vertices.get(edge.point2().ID());
				}
				// only adjust the values of vertices that we haven't visited yet
				if(!v.visited()) {
					Q.push(v);
					v.parent(u);
					v.visited(true);
				}
			}
		} // end while
	} // end solve
		
	/** dijkstra creates an MST by relaxing the edges of each vertex on the graph
	 *@param g graph to Dijkstra
	 *@param s Waypoint of the desired source vertex.
	 */
	 
	public static void dijkstra(RoadGraph g, int s) {
		// priority queue to order weights with added vertex comparator
		PriorityQueue<Vertex> pQ = new PriorityQueue<>();
		// polled vertex
		Vertex u;
		// adjacent vertex
		Vertex v;	
		// source vertex for init
		Vertex source = g.vertices.get(s);	
		// set source distance to 0
		initialize_single_source(g,source);
		// add each vertex to priority queue
		for(Vertex vertex : g.vertices) {
			pQ.add(vertex);
		}
		// for each vertex on the graph 
		while(!pQ.isEmpty()) {
			// poll the prioritized vertex (pop it from PriorityQueue)_
			u = pQ.poll();
			// for each adjacent vertex v to popped vertex u
			for(RoadSegment edge : u.adjacent()) {
				v = null;
				// make sure we are talking about the correct waypoint of the edge								
				// if Waypoint of u is not point1 then it is point2	
				if(u.point() != edge.point1()) {
					// popped vertex u is point2 of edge
					v = g.vertices.get(edge.point1().ID());
				} else {
					// popped vertex u is point1 of edge
					v = g.vertices.get(edge.point2().ID());
				}
				// if relaxation occurs then refresh the PriorityQueue 
				if(relax(u, v, edge)) {
					pQ.remove(v);
					pQ.add(v);
				}
			}
 		} // end while
	} // end dijkstra
	 
	 
	/** relax compare edge weights of u and v to assign v the shorter distance
	 *@param u	the popped vertex
	 *@param v	adjacent vertex
	 *@param edge	the edge to be relaxed
	 *@return true 	if relaxation occurs
	 */
	public static boolean relax(Vertex u, Vertex v, RoadSegment edge) {
		// if v's current distance is greater, reassign its values to the lighter edge
		if(v.distance() > u.distance() + edge.distance()) {
			v.distance(u.distance() + edge.distance());
			v.parent(u);
			// v has been modified so return true to reset it's position on Q
			return true;
		} else {
			// no change
			return false;
		}
	}
	/** set distance of source to 0, could be in dijkstra but here is fine too */
	public static void initialize_single_source(RoadGraph g, Vertex s) {		
		s.distance(0.0);
	}	
}// end PathFinder