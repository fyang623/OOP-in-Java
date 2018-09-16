/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	private Map<GeographicPoint, WeightedVertex> nodes;
	private int numVertices, numEdges;
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		nodes = new HashMap<GeographicPoint, WeightedVertex> ();
		numVertices = 0;
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return nodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if (!nodes.containsKey(location)) {
			nodes.put(location, new WeightedVertex(location));
			numVertices++;
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
						String roadType, double length) throws IllegalArgumentException {
		//TODO: Implement this method in WEEK 2
		if (!nodes.containsKey(from) || from == null
				|| !nodes.containsKey(to) || to == null
				|| roadName == null || roadName == null 
				|| length < 0) 
			throw new IllegalArgumentException();
		MapEdge edge = new MapEdge(from, to, roadName, roadType, length);
		nodes.get(from).addOutEdge(edge);
		numEdges++;	
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());	
		if (start==null || goal==null || !nodes.containsKey(start) || !nodes.containsKey(goal)) 
			return null;	
		//Call bfsSearch method 
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = bfsSearch(start, goal, parentMap, nodeSearched);
		//return the path found
		if (!found)
			return null;
		else
			return getPath(start, goal, parentMap);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parentMap An map that contains each visited location as the key and the location's parent as the value
	 * @param nodeSearched A hook for visualization. 
	 * @return a boolean that indicates whether the path is found or not
	 */
	private boolean bfsSearch(GeographicPoint start, GeographicPoint goal,
							  Map<GeographicPoint, GeographicPoint> parentMap,
							  Consumer<GeographicPoint> nodeSearched) {
		
		Queue<GeographicPoint> explore = new LinkedList<GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		GeographicPoint curr = start;
		explore.add(curr);
		visited.add(curr);
		
		while(!explore.isEmpty()) {
			curr = explore.remove();
			nodeSearched.accept(curr);
			// return true if current is the same with goal
			if (curr.getX() == goal.getX() && curr.getY() == goal.getY())
				return true;
			// add each of curr's neighbor, if not in visited set, to visited set and to explore queue,
			// meanwhile, add curr as the parent of its neighbor in parentMap
			for (GeographicPoint neighbor : nodes.get(curr).getNeighbors()) {
				if (!visited.contains(neighbor)) {
					explore.add(neighbor);
					visited.add(neighbor);
					parentMap.put(neighbor, curr);
				}
			}		
		}	
		
		return false;
	}
	
	/** construct and return the path from start to goal using the parentMap
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parentMap An map that contains each visited location as the key and the location's parent as the value
	 * @return a boolean that indicates whether the path is found or not
	 */
	private List<GeographicPoint> getPath(GeographicPoint start,
										  GeographicPoint goal, 
										  Map<GeographicPoint, GeographicPoint> parentMap) {
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		path.add(curr);
		
		while(curr != start) {
			curr = parentMap.get(curr);
			path.add(0, curr);
		}
		
		return path;
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start==null || goal==null || !nodes.containsKey(start) || !nodes.containsKey(goal)) 
			return null;	
		//Call dijkstraSearch method 
		for (GeographicPoint node : nodes.keySet())
			nodes.get(node).setDistance(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = dijkstraSearch(start, goal, parentMap, nodeSearched);
		
		//return the path found
		if (!found)
			return null;
		else
			return getPath(start, goal, parentMap);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parentMap An map that contains each visited location as the key and the location's parent as the value
	 * @param nodeSearched A hook for visualization. 
	 * @return a boolean that indicates whether the path is found or not
	 */
	private boolean dijkstraSearch(GeographicPoint start, GeographicPoint goal,
							  Map<GeographicPoint, GeographicPoint> parentMap,
							  Consumer<GeographicPoint> nodeSearched) {
		
		Queue<WeightedVertex> explore = new PriorityQueue<WeightedVertex>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		nodes.get(start).setDistance(0);
		explore.add(new WeightedVertex(start, 0));
		while(!explore.isEmpty()) {
			GeographicPoint currLoc = explore.remove().getLocation();		
			WeightedVertex curr = nodes.get(currLoc);
			visited.add(currLoc);
			nodeSearched.accept(currLoc);
			//System.out.println("(" + currLoc.getX() + "," + currLoc.getY() + ")\n");
			// return true if current is the same with goal
			if (currLoc.getX() == goal.getX() && currLoc.getY() == goal.getY()) {
				System.out.println("found !");
				return true;
			}
			//else System.out.println("searching...");
			// add each of curr's neighbor, if not in visited set, to explore queue,
			// meanwhile, add curr as the parent of its neighbor in parentMap
			for (GeographicPoint nextLoc : curr.getNeighbors()) {
				if (!visited.contains(nextLoc)) {
					double distance = curr.getDistance() + currLoc.distance(nextLoc);
					if (distance < nodes.get(nextLoc).getDistance())
						nodes.get(nextLoc).setDistance(distance);
					if(!explore.contains(new WeightedVertex(nextLoc, distance))){
						explore.add(new WeightedVertex(nextLoc, distance));
						parentMap.put(nextLoc, currLoc);
					}
				}
			}		
		}			
		return false;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3	
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start==null || goal==null || !nodes.containsKey(start) || !nodes.containsKey(goal)) 
			return null;	
		//Call dijkstraSearch method 
		for (GeographicPoint node : nodes.keySet())
			nodes.get(node).setDistance(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = ASearch(start, goal, parentMap, nodeSearched);
		
		//return the path found
		if (!found)
			return null;
		else
			return getPath(start, goal, parentMap);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parentMap An map that contains each visited location as the key and the location's parent as the value
	 * @param nodeSearched A hook for visualization. 
	 * @return a boolean that indicates whether the path is found or not
	 */
	private boolean ASearch(GeographicPoint start, GeographicPoint goal,
							  Map<GeographicPoint, GeographicPoint> parentMap,
							  Consumer<GeographicPoint> nodeSearched) {
		
		Queue<WeightedVertex> explore = new PriorityQueue<WeightedVertex>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		nodes.get(start).setDistance(0, start.distance(goal));
		explore.add(new WeightedVertex(start, 0, start.distance(goal)));
		int count = 0;
		while(!explore.isEmpty()) {
			count++;
			GeographicPoint currLoc = explore.remove().getLocation();
			WeightedVertex curr = nodes.get(currLoc);
			if (!visited.contains(currLoc)) {
				visited.add(currLoc);
				nodeSearched.accept(currLoc);
				//System.out.println("(" + currLoc.getX() + "," + currLoc.getY() + ")\n");
				// return true if current is the same with goal
				if (currLoc.getX() == goal.getX() && currLoc.getY() == goal.getY()) {
					//System.out.println("found !");
					System.out.println(count);
					return true;
				}
				//else System.out.println("searching...");
				// add each of curr's neighbor, if not in visited set, to visited set and to explore queue,
				// meanwhile, add curr as the parent of its neighbor in parentMap
				for (GeographicPoint nextLoc : curr.getNeighbors()) {
					if (!visited.contains(nextLoc)) {
						double travelled = curr.getTravelled() + currLoc.distance(nextLoc);
						double untravelled = nextLoc.distance(goal);
						double total = travelled + untravelled;
						if (total < nodes.get(nextLoc).getTravelled() + nodes.get(nextLoc).getUntravelled()) {
							nodes.get(nextLoc).setDistance(travelled, untravelled);
							explore.add(new WeightedVertex(nextLoc, travelled, untravelled));
							parentMap.put(nextLoc, currLoc);
						}						
					}
				}
			}
		}	
		
		return false;
	}

	public void printGraph() {
		for (GeographicPoint location : nodes.keySet()) {
			nodes.get(location).printVertex();
			System.out.println();
		}
	}
	
	public void printPath(List<GeographicPoint> path) {
		for (GeographicPoint location : path) {
			System.out.println("(" + location.getX() + "," + location.getY() + ")");
		}
	}
	
	public static void main(String[] args)
	{   
		System.out.print("Making a new map...");
		MapGraph simpleTestMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		System.out.println("DONE.");
		
		
		//System.out.println("\nprint the graph before implementing BFS:\n");
		//simpleTestMap.printGraph();	
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);	
		List<GeographicPoint> testroute = simpleTestMap.bfs(testStart,testEnd);
		System.out.println("\nprint the route found by BFS:\n");
		simpleTestMap.printPath(testroute); 
				
		// You can use this method for testing.  		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */

		//System.out.println("\nprint the graph before implementing Dijkstra:\n");
		//simpleTestMap.printGraph();
		testStart = new GeographicPoint(1.0, 1.0);
		testEnd = new GeographicPoint(8.0, -1.0);		
		testroute = simpleTestMap.dijkstra(testStart,testEnd);	
		System.out.println("\nprint the route found by Dijkstra:\n");
		simpleTestMap.printPath(testroute);	
		
		
		//System.out.println("\nprint the graph before implementing AStar:\n");
		//simpleTestMap.printGraph();
		testStart = new GeographicPoint(1.0, 1.0);
		testEnd = new GeographicPoint(8.0, -1.0);
		testroute = simpleTestMap.aStarSearch(testStart,testEnd);
		System.out.println("\nprint the route found by A*:\n");
		simpleTestMap.printPath(testroute);
		
		
		// A very simple test using real data
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("\nTest 1 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("\nTest 2 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// Use this code in Week 3 End of Week Quiz 
		MapGraph theMap = new MapGraph();
		System.out.print("\nDONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);		
	}
	
}
