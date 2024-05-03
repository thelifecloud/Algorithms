## Homework Assignment 18 BFS, DFS, and Dijkstra's Algorithm

The graph represents real data provided at https://travelmapping.net/graphs/. The data files contain waypoints (i.e. places where roads intersect, each with a description and their location in degrees latitude and longitude). Each waypoint is a Vertex of the graph. In the file, after the waypoints have been listed, connections between waypoints are listed (i.e. road segments that are the __edges__ of the graph). 

A very small sample graph has been provided for testing purposes (amsterdamNY.tmg). The formatting of these files is uniform, so you should be able to download any mapping file of any area (e.g. MN-region.tmg) and it will work with your code.

To execute the code, you will be using command-line arguments in this form:

```
java Main amsterdamNY.tmg 5 bfs
```

In this example, Main will create a graph from the file __amsterdamNY.tmg__, then it will perform a Breadth-First Search (bfs) using Waypoint[5] as its source and determine a path to every other reachable Waypoint. Waypoints have a unique ID that correspond to their ordering within the file, which is also how the edges are indicated in the file.

<hr>

In PathFinder.findPath(), use the SAME CODE to implement the BFS and DFS. Determine what the user wants to run and define the queue appropriately (FIFO for BFS and LIFO for DFS). Also implement Dijkstra's algorithm. Choose which code to execute based on the algo passed to the function.

## Implementing a BFS or DFS

The traversal of the graph to find the path from the user-specified starting point to the final destination is performed in the function findPath() in the file PathFinder.java. One can traverse a graph using a Breadth-First Search or a Depth-First Search. They are essentially the same except the BFS uses a Queue and the DFS uses a Stack. Regardless, the general algorithm is as follows:

```
Create the queue, appropriate for the type of traversal

push the starting waypoint onto the queue
while the queue is not empty
    pop from the queue
    for each adjacent waypoint of that popped vertex
        if adjacent not yet visited (visited will be false)
            push onto the queue
            set parent
            set as visited
```

You do not need to track the length of the path (as is outlined in the textbook). You will calculate the full distance (i.e. the sum of edge weights) along the path (as requested) after the search has completed.

Once all vertices have been explored, you can construct a path from any other vertex back to the source following the parent pointers. 

<hr>

## Implementing Dijkstras Algorithm

Use the algorithm in the textbook to implement Dijkstra's algorithm. The algorithm requires a Priority Queue, which you can implement using Java's builtin queue. You do need to update the Vertex.distance value as indicated by the algorithm, as that is essential in being able to determine the shortest path.



IN MAIN, after the search has been completed, ask the user for a destination (i.e an index of a Waypoint). Print the path from source to destination (in that direction!) and state the total distance of the path.


