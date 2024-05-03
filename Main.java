import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String filename;
		String algorithm;
		Integer point1 = 0;
		
		if (args.length != 3) {	
			System.out.print("Expecting 3 arguments ");
			System.out.println("(e.g. java Main amsterdamNY.tmg 5 bfs)");
			return;
		
		} else {
			filename = args[0];
			point1 = Integer.valueOf(args[1]);
			algorithm = args[2];
			
			if (
				!algorithm.equals("bfs") && 
				!algorithm.equals("dfs") &&
				!algorithm.equals("dijkstra")
			) {
				System.out.println("Algorithm must be bfs, dfs, or dijkstra.");
				return;
			}
		}

		RoadGraph graph = LoadGraph.load(filename);
		//graph.display();	

		System.out.println("__________________________________");
		try {
			PathFinder.findPath(graph, point1, algorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}


		

		// display destinations for user to choose / prompt input
		graph.displayPoints();
		System.out.println("Enter the number corresponding to chosen destination");
		// get int in correlation with userDestination
		int userDestination = scanner.nextInt();
		System.out.println("scanned in user destination: " + userDestination);
		// vertex for user destination
		Vertex dest = graph.vertices.get(userDestination);
		ArrayList<Vertex> path = new ArrayList<>();
		int totalcost = 0;
		
		// while vertex is not the source vertex: add to path, increment total, move on
		while(dest != graph.vertices.get(point1)) {
			path.add(dest);
			// add edge distance to total
			totalcost += graph.edges.get(dest.point().ID()).distance();
			// move on
			dest = dest.parent();
		}
		// get the source onto the path
		path.add(dest);
	
		System.out.println("Path to destination: ");
		// print each vertex on the path to the destination
		for(int i = path.size() - 1; i >= 0; i--) {
			System.out.print(graph.vertices.get(i) + ", ");
		}

		System.out.println("total cost: " + totalcost);		
		
	} // end main()
} // end class Main