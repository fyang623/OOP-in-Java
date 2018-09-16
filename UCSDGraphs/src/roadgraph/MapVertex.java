package roadgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import geography.GeographicPoint;

public class MapVertex {
	private GeographicPoint location;
	private List<MapEdge> outEdges;
	
	public MapVertex(GeographicPoint location) {
		this.location = location;
		outEdges = new ArrayList<MapEdge>();
	}
	
	/** get a list of locations that are connected to this by edges.
	 * 
	 * @return The list of locations that are connected to this
	 */
	public List<GeographicPoint> getNeighbors() {
		List<GeographicPoint> neighbors = new LinkedList<GeographicPoint>();
		for (MapEdge edge : outEdges) {
			neighbors.add(edge.getEnd());
		}
		return neighbors;
	}
	
	public void addOutEdge(MapEdge edge) {
		outEdges.add(edge);
	}
	
	public GeographicPoint getLocation() {
		return location;
	}
	
	public List<MapEdge> getOutEdges() {
		return outEdges;
	}
	
	// print this vertex and all of its neighbors
	public void printVertex() {
		System.out.print("(" + location.getX() + "," + location.getY() + ")");
		System.out.println("\t\tneighbors:");
		for (MapEdge edge : outEdges) {
			System.out.println("\t\t\t(" + edge.getEnd().getX() + "," + edge.getEnd().getY() + ")");
		}
	}

}
