package rsc;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

/**
 * This class represents a step in a polyline of a segment.
 *
 */
public class Step implements Serializable {

	private static final long serialVersionUID = 111995236759060625L;

	//Unique id
	private String id;
		
	//Segment it belongs to
	private String segment;
	
	//Coordinates of the line
	private int originX, originY, destinationX, destinationY;
	
	//Default constructor
	public Step(){
		
		this.id = UUID.randomUUID().toString();
		this.segment = "";
		this.originX = 0;
		this.originY = 0;
		this.destinationX = 0;
		this.destinationY = 0;
	}
	
	//Constructor
	public Step(String id, String segment, int originX, int originY, 
			    int destinationX, int destinationY){
		
		this.id = id;
		this.segment = segment;
		this.originX = originX;
		this.originY = originY;
		this.destinationX = destinationX;
		this.destinationY = destinationY;
	}
	
	//Setters and getters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public int getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	@Override
	public String toString() {
		return "Step [id=" + id + ", segment=" + segment+ ", originXY=" + originX + "," + originY
				+ ") , destinationXY=" + destinationX + "," + destinationY + ")]";
	}	
	
	public String toJSon() {
		
		// "{\"id\":\""ST-001","idSegment":"S-A1-1","originCoordinates":{"x":82,"y":566},"destinationCoordinates":{"x":208,"y":431}}

		return "{\"id\":\""+id+"\",\"idSegment\":\""+segment+"\",\"originCoordinates\":{\"x\":"+originX+",\"y\":"+originY+"},\"destinationCoordinates\":{\"x\":"+destinationX+",\"y\":"+destinationY+"}}";
	}
	
	public void fromJSon(String line) {
		JSONObject stepJson = new JSONObject(line);
		this.setId(stepJson.getString("id"));
		this.setSegment(stepJson.getString("idSegment"));
		this.setOriginX(stepJson.getJSONObject("originCoordinates").getInt("x"));
		this.setDestinationX(stepJson.getJSONObject("destinationCoordinates").getInt("x"));
		this.setOriginY(stepJson.getJSONObject("originCoordinates").getInt("y"));
		this.setDestinationY(stepJson.getJSONObject("destinationCoordinates").getInt("y"));
	}
	
}
