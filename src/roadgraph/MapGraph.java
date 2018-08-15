/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import geography.GeographicPoint;
import util.GraphLoader;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private HashMap<Node, HashSet<Node>> adjacencyListMap;
	private List<Edge> listOfEdges;
	public int numVertices;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		adjacencyListMap = new HashMap<>();
		numVertices = adjacencyListMap.size();
		listOfEdges = new ArrayList<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		Set<GeographicPoint> vertices = new HashSet<>();
		for (Node n : adjacencyListMap.keySet()) {
			vertices.add(n.getLocation());
		}
		return vertices;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return this.listOfEdges.size();
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
		// TODO: Implement this method in WEEK 3
		if(location == null){
			return false;
		}
		Node toAdd = new Node(location);
		if (!this.adjacencyListMap.containsKey(toAdd)) {
			this.adjacencyListMap.put(toAdd, new HashSet<>());
			this.numVertices++;
			return true;
		} else {
			return false;
		}
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
		//TODO: Implement this method in WEEK 3
		Node fromNode = new Node(from);
		Node toNode = new Node(to);
		Edge e = new Edge(fromNode, toNode, roadName, roadType, length);
		if (this.adjacencyListMap.keySet().contains(fromNode)) {
			this.adjacencyListMap.get(fromNode).add(toNode);
		}

		if (this.adjacencyListMap.keySet().contains(toNode)) {
			this.adjacencyListMap.get(toNode).add(fromNode);
		}

		this.listOfEdges.add(e);
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
		// TODO: Implement this method in WEEK 3
		List<GeographicPoint> path = new ArrayList<>();
		Node startNode = new Node(start);
		Node goalNode = new Node(goal);
		//initialize a queue
		Queue<Node> queue = new LinkedList<>();
		//initialize a parent hashmap
		HashMap<Node, ArrayList<Node>> parentMap = new HashMap<>();
		//initialize a visited set
		Set<Node>visited = new HashSet<>();
		queue.add(startNode);
		while(!queue.isEmpty()) {
			Node curr = queue.remove();
			curr.setVisited(true);
			visited.add(curr);
			nodeSearched.accept(curr.getLocation());
			if(curr.equals(goalNode)) {
				path = findPathFromParentMap(parentMap, startNode, goalNode);
				return path;
			}
			//for each neigbor of curr, mark them as visited and add them as children of curr
			//afterwards, add them to the queue to keep this train a-rollin'
			for (Node neighbor : adjacencyListMap.get(curr)) {
				if(!visited.contains(neighbor)) {
					neighbor.setVisited(true);
					visited.add(neighbor);
					if(parentMap.containsKey(curr)) {
						ArrayList<Node> childList = parentMap.get(curr);
						childList.add(neighbor);
						parentMap.put(curr, childList);
					} else {
						ArrayList<Node> childList = new ArrayList<>();
						childList.add(neighbor);
						parentMap.put(curr, childList);
					}
				}
				queue.add(neighbor);
			}
		}

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		return path;
	}

	private List<GeographicPoint> findPathFromParentMap(HashMap<Node,ArrayList<Node>> parentMap, Node startNode, Node goalNode) {
		Set<Node> keysToDelete = new HashSet<>();
		List<GeographicPoint> path;
		for(Node key: parentMap.keySet()) {
			if(!parentMap.get(key).contains(goalNode)) {
				List<Node> refinedList = parentMap.get(key).stream().filter(x -> parentMap.keySet().contains(x)).collect(Collectors.toList());
				if(refinedList.isEmpty()) {
					keysToDelete.add(key);
				}
				parentMap.put(key, (ArrayList<Node>)refinedList);
			}
		}

		//Remove dead keys and values
		for(Node keyToDelete: keysToDelete) {
			parentMap.remove(keyToDelete);
		}

		//build a path
		path = parentMap.keySet().stream().map(k -> k.getLocation()).collect(Collectors.toList());
		//add final destination
		path.add(goalNode.getLocation());

		return path;

	}

	public static String printPath(List<GeographicPoint> path) {
		StringBuffer sb = new StringBuffer();
		sb.append("PATH: ");
		for(GeographicPoint g: path) {
			sb.append("|");
			sb.append(g.toString()).append("| -> ");
		}
		sb.append("\n");
		return sb.toString().replace(" -> \n", "\n");
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
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
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
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MapGraph has " + this.getNumVertices() + " vertices, " + this.getNumEdges() + " edges.\n");
		sb.append("How many keys: " + this.adjacencyListMap.keySet().size() + "\n");
		for(Node n : this.adjacencyListMap.keySet()) {
			StringBuffer printNeighbors = new StringBuffer();
			for(Node neighbor : this.adjacencyListMap.get(n)){
				printNeighbors.append(neighbor.toString()).append(" ");
			}
			//sb.append(n.toString() + " ==> " +this.adjacencyListMap.get(n).size() +"\n");
			sb.append(n.toString() + " ==> " +printNeighbors.toString() +"\n");
		}
		return sb.toString();
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		System.out.println(firstMap.toString());

		Set<Integer> set = new HashSet<>();
		set.add(3);
		set.add(4);
		set.add(3);

		//System.out.println(set.toString());

		//Map<Node, ArrayList<Node>> myMap = new HashMap<>();
		//System.out.println("FIRST: " + q.remove());
		//System.out.println("SECOND: " + q.remove());
		// You can use this method for testing.

		List<GeographicPoint> path = firstMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(8.0, -1.0));
		System.out.println(printPath(path));

		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
