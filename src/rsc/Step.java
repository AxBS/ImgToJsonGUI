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

	/**Unique id*/
	private String id;
		
	/**Segment it belongs to*/
	private String segment;
	
	/**Coordinates of the line*/
	private int originX, originY, destinationX, destinationY;
	
	/**Default constructor*/
	public Step(){
		
		this.id = UUID.randomUUID().toString();
		this.segment = "";
		this.originX = 0;
		this.originY = 0;
		this.destinationX = 0;
		this.destinationY = 0;
	}
	
	/**Constructor
	 * @param id - Id del step que se va a crear
	 * @param segment - Id del segmento al que va a pertenecer el step
	 * @param originX - X de la representación del origen del step
	 * @param originY - Y de la representación origen del step
	 * @param destinationX - X de la representación destino del step
	 * @param destinationY - Y de la representación destino del step
	 * */
	public Step(String id, String segment, int originX, int originY, 
			    int destinationX, int destinationY){
		
		this.id = id;
		this.segment = segment;
		this.originX = originX;
		this.originY = originY;
		this.destinationX = destinationX;
		this.destinationY = destinationY;
	}
	
	/** Getter del Id del step
	 * @return El string que representa el id del step
	 * */
	public String getId() {
		return id;
	}

	/**Setter del id del Step
	 * @param id  - String con el nuevo id del step*/
	public void setId(String id) {
		this.id = id;
	}

	/**Getter del id del Segmento
	 * @return String que representa el id al que pertenece el step*/
	public String getSegment() {
		return segment;
	}

	/**Setter del id del segmento
	 * @param segment - String con el nuevo id de segmento del step*/
	public void setSegment(String segment) {
		this.segment = segment;
	}

	/**Getter de la coordenada x origen
	 * @return Integer que representa el valor de la coordenada x origen*/
	public int getOriginX() {
		return originX;
	}

	/** Setter de la coordenada x origen
	 * @param originX - Int que representa la x de origen del step
	 * */
	public void setOriginX(int originX) {
		this.originX = originX;
	}

	/** Getter de la coordenada Y de origen
	 * @return Integer que representa la coordenada Y origen del step*/
	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	/**Getter de la coordenada x destino
	 * @return Interger que representa el valor de la coordenada x detino*/
	public int getDestinationX() {
		return destinationX;
	}

	/** Setter de la coordenada x destino
	 * @param destinationX - Int que representa la x de destino del step
	 * */
	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	/** Getter de la coordenada Y de destino
	 * @return Integer que representa la coordenada Y destino del step*/
	public int getDestinationY() {
		return destinationY;
	}

	/** Setter de la coordenada y destino
	 * @param destinationY - Int que representa la y de destino del step
	 * */
	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	/** Representación del Objeto Step como un string.
	 * @return String con la representación del objeto Step. Esta representación se basa en los atributos y
	 * no tiene en cuenta los métodos implementados*/
	@Override
	public String toString() {
		return "Step [id=" + id + ", segment=" + segment+ ", originXY=" + originX + "," + originY
				+ ") , destinationXY=" + destinationX + "," + destinationY + ")]";
	}

	//TODO:Comprobar que al exportar e importar los steps esto funciona
	/** Método para trasformar el objeto a un formato JSON
	 * @return Un String en formato JSON que representa el Step, utilizado a la hora de exportar estos elementos*/
	public String toJSon() {
		JSONObject stepJson = new JSONObject();
		stepJson.put("id", this.getId());
		stepJson.put("idSegment", this.getSegment());

		JSONObject originCoordinates = new JSONObject();
		originCoordinates.put("x", this.getOriginX());
		originCoordinates.put("y", this.getOriginY());

		stepJson.put("originCoordinates", originCoordinates);

		JSONObject destinationCoodinates = new JSONObject();
		destinationCoodinates.put("x", this.getDestinationX());
		destinationCoodinates.put("y", this.getDestinationY());

		stepJson.put("destinationCoordinates", destinationCoodinates);
		// "{\"id\":\""ST-001","idSegment":"S-A1-1","originCoordinates":{"x":82,"y":566},"destinationCoordinates":{"x":208,"y":431}}

		//return "{\"id\":\""+id+"\",\"idSegment\":\""+segment+"\",\"originCoordinates\":{\"x\":"+originX+",\"y\":"+originY+"},\"destinationCoordinates\":{\"x\":"+destinationX+",\"y\":"+destinationY+"}}";
		return stepJson.toString();
	}

	/** Método para transformar un String (formato JSON) en Step, utilizado al importar el JSON con estos elementos
	 * Se utilizan los JSONObject.
	 * @param line String que representa un Step en JSON*/
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
