import java.util.*;
import java.io.*;

/**
Holds information regarding a single intersecting roadway point.
Information of the waypoint corresponds to the corresponding file.
*/
public class Waypoint {

	/** Unique ID based on order listed in original file (thus index in list)*/
	private final int ID;

	/** Describes the crossroads a this waypoint. */
	private String description;
	
	/** Latitude in degrees of waypoint */
	private Double latitude;
	
	/** Longitude in degrees of waypoint */
	private Double longitude;
	
	/** 
	* Define all object values at instantiation.
	* @param description Specifies intersecting roadways.
	* @param latitude (degrees) of this waypoint.
	* @param longitude (degrees) of this waypoint.
	*/
	public Waypoint(int ID, String description, Double latitude, Double longitude) {
		this.ID = ID;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}
		
	@Override
	public String toString() {
		return "["+ID+"] "+description + " " + latitude + " " + longitude;
	}
	
	// SETTERS AND GETTERS
	
	// getter only - it is a final value and cannot be changed
	public int ID() {
		return ID;
	}
	
	public String description() { 
		return description; 
	}
	public void description(String d) {
		description = d;
	}
	
	public Double latitude() { 
		return latitude; 
	}
	public void latitutde(Double lat) {
		latitude = lat;
	}
	
	public Double longitude() { 
		return longitude;
	}
	public void longitude(Double l) {
		longitude = l;
	}	
}
	