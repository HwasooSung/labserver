package kr.ac.cau.mecs.cas.service.entity;

import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CAUEntity implements Serializable{
	private long id;
	private String name;
	private int version;
	
	public JSONObject toJSONObject(){
		JSONObject jobj = new JSONObject();
		
		try {
			jobj.putOpt("id", id);
			jobj.putOpt("name", name);
			jobj.putOpt("version", version);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jobj;
	}
	
	public static CAUEntity fromJSONObject(JSONObject jobj){
		CAUEntity entity = new CAUEntity();
		
		entity.id = jobj.optLong("id");
		entity.name =jobj.optString("name");
		entity.version = jobj.optInt("version");
		
		
		return entity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
