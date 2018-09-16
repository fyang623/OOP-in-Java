package module6;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routeMarkers;
	public static List<ShapeFeature> routes;
	public static int TRI_SIZE = 5;
	private float rectWidth = 0;
	private boolean center = false;
	public List<AirportMarker> connectedAirports = new ArrayList<AirportMarker> ();
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		
		if ((Integer.parseInt(getStringProperty("altitude")) > 0 
				|| connectedAirports.size() == 0) && !clicked) 
			return;
		if (center == true) {
			pg.fill(255, 0, 0);
			pg.ellipse(x, y, 8, 8);
		}
		else {
			pg.fill(11, 11, 11);
			pg.ellipse(x, y, 5, 5);	
		}
		
	}	
	
	@Override
	public void showTitle(PGraphics pg, float x, float y) {	
		if ((Integer.parseInt(getStringProperty("altitude")) > 0 || connectedAirports.size() == 0) && !clicked) return;
		pg.pushStyle();	
		// show rectangle with title
		String name = getStringProperty("country") + " " + getStringProperty("city") + " " + getStringProperty("code");
	
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-TRI_SIZE-36, pg.textWidth(name) + 6, 36, 4);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text("airport information:", x+3, y-TRI_SIZE-34);
		pg.text(name, x+3, y-TRI_SIZE-16);
		
		// show name of connected cities
		/*
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		if (connectedAirports.size() == 0) {
			pg.rect(x, y+TRI_SIZE, 120, 18, 4);
			pg.fill(0, 0, 0);
			pg.textAlign(PConstants.LEFT, PConstants.TOP);
			pg.text("No routes info found!", x+3, y+TRI_SIZE);
		}
		else {
			decideRectWidth();
			pg.rect(x, y+TRI_SIZE, rectWidth*6, connectedAirports.size()*15 + 18, 4);
			pg.fill(0, 0, 0);
			pg.textAlign(PConstants.LEFT, PConstants.TOP);
			pg.text("connected cities:", x+3, y+TRI_SIZE);
			for (AirportMarker dest : connectedAirports) {
				pg.text(dest.getStringProperty("city"), x+3, y+TRI_SIZE +17);
				y += 15;
			}
		}
		*/

		pg.popStyle();	
	}
	
	public List<AirportMarker> getConnectedAirports () {
		return connectedAirports;
	}
	
	public void decideRectWidth () {
		String str = "connected cities :";
		rectWidth = str.length();
		for (AirportMarker airport : connectedAirports) {
			if(rectWidth < airport.getStringProperty("city").length()) {
				rectWidth = airport.getStringProperty("city").length();
			}
		}
	}
	
	public void setCenter(boolean flag) {
		center = flag;
	}
	
	public boolean getCenter() {
		return center;
	}
}
