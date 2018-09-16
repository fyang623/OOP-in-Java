package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PConstants;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	AirportMarker lastSelected;
	AirportMarker lastClicked;
	MarkerManager<Marker> airportsManager = new MarkerManager<Marker> ();
	MarkerManager<Marker> routesManager = new MarkerManager<Marker> ();
	
	public void setup() {
		// setting up PAppler
		size(1000,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 20, 10, 1000, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		//HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		HashMap<Integer, AirportMarker> airports = new HashMap<Integer, AirportMarker>();
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
			m.setId(feature.getId());
			m.setRadius(5);			
			airportList.add(m);
			// put airport in hashmap with OpenFlights unique id for key
			//airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			airports.put(Integer.parseInt(feature.getId()), m);
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			//find connected airports for each airports
			if(!airports.get(source).getConnectedAirports().contains(airports.get(dest)))
				airports.get(source).getConnectedAirports().add(airports.get(dest));
			if(!airports.get(dest).getConnectedAirports().contains(airports.get(source)))
				airports.get(dest).getConnectedAirports().add(airports.get(source));
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source).getLocation());
				route.addLocation(airports.get(dest).getLocation());
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		//map.addMarkers(routeList);
		
		map.addMarkerManager(airportsManager);
		airportsManager.addMarkers(airportList);
		map.addMarkerManager(routesManager);
		
	}
	
	@Override
	public void mouseMoved() {
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
	}
	
	// If there is a marker selected 		
	//loop();
	private void selectMarkerIfHover(List<Marker> markers) {	
		for (Marker m : markers) {
			AirportMarker marker = (AirportMarker) m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	public void mouseClicked() {
		if (lastClicked != null) {
			lastClicked.setCenter(false);
			lastClicked = null;
			unhideAirports();
			removeRoutes();
		}
		else {
			checkClick();
			if (lastClicked != null) {
				hideAirports();
				displayRoutes();
			}
		}
	}
	
	public void checkClick () {
		for (Marker airport : airportList) {
			if (Integer.parseInt(airport.getStringProperty("altitude")) <= 0 
					&& ((AirportMarker)airport).getConnectedAirports().size() != 0
					&& airport.isInside(map, mouseX, mouseY)) {
				lastClicked = (AirportMarker) airport;
				lastClicked.setCenter(true);
				return;
			}
		}
	}
	
	public void hideAirports() {
		for (Marker airport : airportList) {
			if (airport == lastClicked || lastClicked.getConnectedAirports().contains(airport)) {
				((AirportMarker) airport).setClicked(true);
				airport.setHidden(false);
			}
			else airport.setHidden(true);
		}	
	}
	
	public void displayRoutes() {
		for (Marker sl : routeList) {
			if (((SimpleLinesMarker)sl).getLocations().contains(lastClicked.getLocation())) {
				routesManager.addMarker(sl);
			}
		}
	}
	
	public void removeRoutes() {
		routesManager.clearMarkers();
	}
	
	private void unhideAirports() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
			if(((CommonMarker)marker).getClicked() == true)
				((CommonMarker)marker).setClicked(false);
		}
	}
	
	public void draw() {
		background(0);
		map.draw();	
		if (lastClicked != null) {
			textSize(18);
			String title = "the city of " + lastClicked.getStringProperty("city") + " is clicked";
			float titleWidth = textWidth(title) + 6;

			textSize(14);
			String subTitle = "hover over any connected airport to see the information";
			float subTitleWidth = textWidth(subTitle);
			
			fill(255, 255, 255);
			rectMode(PConstants.CORNER);
			rect(490 - subTitleWidth/2, 15, subTitleWidth + 9, 40, 6);
			
			textSize(18);
			fill(0, 0, 255);
			textAlign(PConstants.LEFT, PConstants.TOP);
			text(title, 500 - titleWidth/2, 15);
			
			textSize(14);
			fill(200, 0, 0);
			text(subTitle, 494-subTitleWidth/2, 35);
		}
	}
	
}
