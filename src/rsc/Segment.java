package rsc;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import agents.SegmentAgent;
//import jade.wrapper.AgentController;
//import jade.wrapper.StaleProxyException;

/**
 * Represents a section of a road in a single direction.
 * This section is only accessible from its origin and can only be 
 * left by its destination.
 */
public class Segment implements Serializable{

	private static final long serialVersionUID = -6853406084306746147L;

	//Unique id
	private String id;

	//Where the segment is accessed from
	private String origin;

	//Where the segment is left
	private String destination;

	//Length in kilometers of the segment
	private String length;

	//Capacity
	private String capacity;
	
	//Density
	private String density;

	//Number of tracks
	private String numberTracks;

	//Max speed
	private String maxSpeed;

	//Kilometric points
	private String pkIni;
	
	//Direction
	private String direction;

	//List with the twins segments
	private ArrayList<String> twinSegments;
	
	//List of Steps
	private List<Step> steps;



	/**
	 * Default constructor. 
	 */
	public Segment(){

		this.id = "";
		this.origin = "";
		this.destination = "";
		this.length ="";
		this.capacity = "";
		this.numberTracks = "";
		this.maxSpeed = "";
		this.pkIni = "";
		this.direction = "";
		this.twinSegments = new ArrayList<String>();

	}

	/**
	 * Constructor. 
	 *
	 * @param  origin {@link Intersection} where this {@link Segment} starts.
	 * @param  destination {@link Intersection} where this {@link Segment} ends.
	 * @param  length The length of this {@link Segment} in Km.
	 */
	public Segment(String id, String origin, String destination, 
			String length, String maxSpeed, String capacity, String density, 
			String numberTracks, String direction, String pkstart, ArrayList<String> segTwinsList){

		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.capacity = capacity;
		this.density = density;
		this.numberTracks = numberTracks;
		this.direction = direction;
		this.pkIni =  pkstart;
		this.twinSegments = segTwinsList;
		
	}

 
	//Setters and getters
	public String getId() {
		return id;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public String getLength() {
		return length;
	}

	public String getCapacity() {
		return capacity;
	}

	public String getNumberTracks() {
		return numberTracks;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
	public void addStep(Step step) {
		this.steps.add(step);
	}

	public String getMaxSpeed() {
		return maxSpeed;
	}

	public String getPkIni() {
		return pkIni;
	}


	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}
	
	public ArrayList<String> getTwinSegments() {
		return twinSegments;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public void setNumberTracks(String numberTracks) {
		this.numberTracks = numberTracks;
	}

	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void setPkIni(String pkIni) {
		this.pkIni = pkIni;
	}

	public void setTwinSegments(ArrayList<String> twinSegments) {
		this.twinSegments = twinSegments;
	}

	public void addTwinSegment(String twinSegment) {
		this.twinSegments.add(twinSegment);
	}

	@Override
	public String toString() {
		return "Segment [id=" + id + ", origin=" + origin + ", destination=" + destination + ", length=" + length
				+ ", capacity=" + capacity + ", density=" + density + ", numberTracks=" + numberTracks + ", maxSpeed="
				+ maxSpeed + ", pkIni=" + pkIni + ", direction=" + direction + ", twinSegments=" + twinSegments + "]";
	}
	
	public String toJSon() {
		
		// {"id":"S-A1-1","origin":"I-A1-0","destination":"I-A1-1","length":"15","maxSpeed":"120","capacity":"3800","density":"0","numberTracks":"2", "twins":["S-A1-6"], "direction":"up", "pkstart": "0"}

		return "{\"id\":\""+id+"\",\"origin\":\""+origin+"\",\"destination\":\""+destination+"\",\"length\":\""+length+"\"\",\"maxSpeed\":\""+120+"\",\"capacity\":\""+capacity+"\",\"density\":\""+density+"\",\"numberTracks\":\""+numberTracks+"\", \"twins\":[\""+twinSegments+"\"], \"direction\":\""+direction+"\", \"pkstart\": \""+pkIni+"\"}";
	}
	
	public void fromJSon(String line) {
		JSONObject segmentJson = new JSONObject(line);
		this.setId(segmentJson.getString("id"));
		this.setOrigin(segmentJson.getString("origin"));
		this.setDestination(segmentJson.getString("destination"));
		this.setLength(segmentJson.getString("length"));
		this.setMaxSpeed(segmentJson.getString("maxSpeed"));
		this.setCapacity(segmentJson.getString("capacity"));
		this.setDensity(segmentJson.getString("density"));
		this.setNumberTracks(segmentJson.getString("numberTracks"));

		JSONArray segTwinsJSON = segmentJson.getJSONArray("twins");
		for (int i = 0; i < segTwinsJSON.length(); i++){
			this.addTwinSegment((String)segTwinsJSON.get(i));
		}
		this.setDirection(segmentJson.getString("direction"));
		this.setPkIni(segmentJson.getString("pkstart"));
	}
	
}