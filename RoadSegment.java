import java.util.*;
import java.io.*;

public class RoadSegment {

	/** Intersecting point - Vertex (from ArrayList of Waypoints) */
	private final Waypoint point1;
	
	/** Intersecting point - Vertex (from ArrayList of Waypoints) */
	private final Waypoint point2;
	
	/** Distance between waypoints */
	private Double distance;
	
	/**
	* Constructor. Requires endpoints be specified.
	*/
	public RoadSegment(Waypoint point1, Waypoint point2) {
		this.point1 = point1;
		this.point2 = point2;
		calculateDistance();
	} 
	
	@Override
	public String toString() {
		return point1.toString() + " " + point2.toString() + " " + distance;
	}
	
	// Determine the distance between points based on latitude, longitude
	private void calculateDistance() {
		// https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
	
		// conversion from degrees to radians
		Double convert = Math.PI/180.0;

		 // Radius of the earth in km
		int R = 6371;
		
		// calculation from the link above
		Double dLat = convert*(point2.latitude()-point1.latitude());
		Double dLon = convert*(point2.longitude()-point1.longitude()); 
		Double a = 
			Math.sin(dLat/2) * Math.sin(dLat/2) + 
			Math.cos(convert*(point1.latitude())) * 
			Math.cos(convert*(point2.latitude())) * 
			Math.sin(dLon/2) * Math.sin(dLon/2); 
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		distance = (R * c) * 1000.0; // Distance in meters
		
	} // end calculateDistance()
	
	// getters
	public Waypoint point1() {
		return point1;
	}
	
	public Waypoint point2() {
		return point2;
	}
	
	public Double distance() {
		return distance;
	}
	
} // end class RoadSegment
