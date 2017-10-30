package rsc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import rsc.Segment;

/**
 * Represents the union of several {@link Segment}. A roundabout, 
 * a crossroad, a change of route...
 */
public class Intersection implements Serializable{
	

	private static final long serialVersionUID = 3885620456847709732L;

	//Unique id
	private String id;
	
	//In segments
	private ArrayList<Segment> in;
	
	//Out segments
	private ArrayList<Segment> out;
	
	//Map of prohibitions
	private HashMap<String, ArrayList<String>> prohibitions;
	
	//Coordinates
	private int x, y;
	
	/**
	 * Default constructor. 
	 */
	public Intersection(){
		
		this.id = "";
		this.in = new ArrayList<Segment>();
		this.out = new ArrayList<Segment>();
		this.prohibitions = new HashMap<String, ArrayList<String>>();
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Constructor. 
	 *
	 * @param  in   A list of {@link Segment} that go into this
	 *              intersection.
	 * @param  out  A list of {@link Segment} that leave this
	 *              intersection.
	 */
	public Intersection(String id, ArrayList<Segment> in, 
			            ArrayList<Segment> out, int x, int y) {
		
		this.id = id;
		this.in = in;
		this.out = out;
		this.prohibitions = new HashMap<String, ArrayList<String>>();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor. 
	 *
	 * @param x A double with the x coordinate.
	 * @param y A double with the y coordinate.
	 */
	public Intersection(String id, int x, int y){
		
		this.id = id;
		this.in = new ArrayList<Segment>();
		this.out = new ArrayList<Segment>();
		this.prohibitions = new HashMap<String, ArrayList<String>>();
		this.x = x;
		this.y = y;
	}

	/** Contructor
	 * @param id - String que representa el id de la intersección
	 * @param in - Lista de Segmentos ({@link Segment}) de entrada a la intersección. Es decir estos segmentos tienen como destino la
	 *           intersección sobre la que trabajamos
	 * @param out - Lista de Segmentos ({@link Segment}) de salida a la intersección. Es decir estos segmentos tienen como origen la
	 *            intersección sobre la que trabajamos
	 * @param prohibitions - Mapa cuya clave es el id del segmento de entrada y el valor es una lista de id de segmentos de salida a
	 *                     los que no podremos ir. El ejemplo con carreteras es como una dirección no permitida
	 * @param x - Integer con la coordenada x de esta intersección
	 * @param y - Integer con la coordenada y de esta intersección*/
	public Intersection(String id, ArrayList<Segment> in, 
            ArrayList<Segment> out,HashMap<String, ArrayList<String>> prohibitions, int x, int y) {

		this.id = id;
		this.in = in;
		this.out = out;
		this.prohibitions = prohibitions;
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds an in {@link Segment} to the Intersection. 
	 *
	 * @param  segment  {@link Segment} to be added.
	 * @return A boolean whether the collection has been modified 
	 *         or not.
	 */
	public boolean addInSegment(Segment segment){
		
		return this.in.add(segment);
	}
	
	/**
	 * Adds an out {@link Segment} to the Intersection. 
	 *
	 * @param  segment  {@link Segment} to be added.
	 * @return A boolean whether the {@link Segment} has been 
	 *         added or not.
	 */
	public boolean addOutSegment(Segment segment) {
		
		return this.out.add(segment);
	}

	/** Getter del id de la intersección
	 * @return String con el valor del id de la intersección*/
	public String getId() {
		return id;
	}

	/**Getter de la lista de Segmentos de entrada de la intersección
	 * @return ArrayList de segmentos de entrada*/
	public ArrayList<Segment> getInSegments() {
		return in;
	}

	/**Getter de la lista de Segmentos de salida de la intersección
	 * @return ArrayList de los segmentos de salida de la intersección*/
	public ArrayList<Segment> getOutSegments() {
		return out;
	}

	/** Getter de la coordenada X de la intersección
	 * @return Integer que representa la coodenada x de la intersección*/
	public int getX() {
		return x;
	}

	/** Getter de la coordenada Y de la intersección
	 * @return Integet que representa la coordenada y de la intersección*/
	public int getY() {
		return y;
	}

	/**Setter del id de la intersección
	 * @param id - String que representa el identificador de la intersección*/
	public void setId(String id) {
		this.id = id;
	}

	/** Setter de la lista de Segmentos de entrada a la intersección
	 * @param in - ArrayList de segmentos de entrada a la interseción*/
	public void setIn(ArrayList<Segment> in) {
		this.in = in;
	}

	/**Setter de la lista de Segmentos de salida a la intersección
	 * @param out - ArrayLisat de segmentos de salida de la intersección*/
	public void setOut(ArrayList<Segment> out) {
		this.out = out;
	}

	/**Setter de la lista de prohibiciones de la intersección
	 * @param prohibitions - Mapa de id del segmento de entrada, valor - lista de segmentos de salida no permitidos*/
	public void setProhibitions(HashMap<String, ArrayList<String>> prohibitions) {
		this.prohibitions = prohibitions;
	}

	/** Setter de la coordenada x de la intersección
	 * @param x - Integer de la coordenada x de la intersección*/
	public void setX(int x) {
		this.x = x;
	}

	/** Setter de la coordenada Y de la intersección
	 * @param y - Integer de la  coordenada Y de la interswcción*/
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "ID: "+ id + " X:"+ x + " Y:"+ y; //+ " Inputs:"+getInSegments().toString()+ " Outputs:" + getOutSegments().toString();
	}
	
	public void insertProhibition(String input, ArrayList<String> outputs) {
		this.prohibitions.put(input, outputs);
	}
	
	public void eraseProhibitions() {
		this.prohibitions.clear();
	}
	
	public HashMap<String, ArrayList<String>> getProhibitions(){
		return this.prohibitions;
	}
	
	
	public String toJSon() {
		
		// {"id":"I-A1-0","coordinates":{"x":82,"y":566}}

		return "{\"id\":\""+id+"\",\"coordinates\":{\"x\":"+x+",\"y\":"+y+"}}";
	}

	public void fromJSon(String line) {
		JSONObject intersectionJson = new JSONObject(line);
		this.setId(intersectionJson.getString("id"));
		this.setX(intersectionJson.getJSONObject("coordinates").getInt("x"));
		this.setY(intersectionJson.getJSONObject("coordinates").getInt("y"));
	}
	
	public String prohibitionstoJSon() {
		String res = "";
		
		for(String input:prohibitions.keySet()) {
			res+="{\"intersectionId\":\""+id+"\",\"input\":\""+input+"\",\"outputs\":"+prohibitions.get(input).toString()+"}\n";
		}

		return res;
		
	}

	public void prohibitionsFromJSon(String line){
		JSONObject prohibition = new JSONObject(line);
		String intersectionId = prohibition.getString("intersectionId");
		if(intersectionId.compareTo(this.getId()) == 0){
			String input = prohibition.getString("input");
			JSONArray outputs = prohibition.getJSONArray("output");
			for (int i = 0; i < outputs.length(); i++){
				this.prohibitions.get(input).add((String)outputs.get(i));
			}
		} else {
			System.out.println("Las prohibiciones no están en la intersección correcta");
		}
	}

	
	
}
