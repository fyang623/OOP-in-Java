package roadgraph;

import geography.GeographicPoint;

public class WeightedVertex extends MapVertex implements Comparable<WeightedVertex> {

	private double travelled;
	private double untravelled;
	
	public WeightedVertex (GeographicPoint location) {
		super(location);
		travelled = Double.POSITIVE_INFINITY;
		untravelled = Double.POSITIVE_INFINITY;
	}
	
	public WeightedVertex (GeographicPoint location, double travelled) {
		super(location);
		this.travelled = travelled;
		this.untravelled = Double.POSITIVE_INFINITY;
	}
	
	public WeightedVertex (GeographicPoint location, double travelled, double untravelled) {
		super(location);
		this.travelled = travelled;
		this.untravelled = untravelled;
	}
	
	public double getTravelled() {
		return travelled;	
	}
	
	public double getUntravelled() {
		return untravelled;	
	}
	
	public double getDistance() {
		if (untravelled == Double.POSITIVE_INFINITY) 
			return travelled;
		return travelled + untravelled;	
	}
	
	public void setDistance(double travelled) {
		this.travelled = travelled;
		this.untravelled = Double.POSITIVE_INFINITY;
	}
	
	public void setDistance(double travelled, double untravelled) {
		this.travelled = travelled;
		this.untravelled = untravelled;
	}

	@Override
	public int compareTo(WeightedVertex wv) {
		int result = 0;
		if (getDistance() < wv.getDistance()) 
			result = -1;
		else if (getDistance() > wv.getDistance()) 
			result = 1;
		else if (getDistance() == wv.getDistance()) 
			result = 0;
		return result;
	}
	
	public boolean equals(WeightedVertex wv){
		if(this.getLocation().getX() == wv.getLocation().getX()
				&& this.getLocation().getY() == wv.getLocation().getY())
			return true;
		else
			return false;		
	}
}
