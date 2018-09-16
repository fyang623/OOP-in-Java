package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	GeographicPoint start, end;
	String roadName, roadType;
	Double length;
	
	public MapEdge(GeographicPoint start, GeographicPoint end) {
		this.start = start;
		this.end = end;
	}
	
	public MapEdge(GeographicPoint start, GeographicPoint end, String roadName) {
		this.start = start;
		this.end = end;
		this.roadName = roadName;
	}
	
	public MapEdge(GeographicPoint start, GeographicPoint end, String roadName, String roadType, double length) {
		this.start = start;
		this.end = end;
		this.roadName = roadName;
		this.length = length;
		this.roadType = roadType;
	}
	
	public GeographicPoint getStart() {
		return start;
	}
	
	public GeographicPoint getEnd() {
		return end;
	}
	
	public double getLength() {
		return length;
	}
	
	public String getRoadName() {
		return roadName;
	}
	
	public String getRoadType() {
		return roadType;
	}
}
