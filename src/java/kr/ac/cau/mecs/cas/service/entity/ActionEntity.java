package kr.ac.cau.mecs.cas.service.entity;

import java.io.Serializable;

import org.json.JSONObject;

public class ActionEntity implements Serializable{
	private long id;
	private long aid;
	private String name;
	
	public JSONObject toJSONObject(){
		JSONObject jobj = new JSONObject();
		
		try{
			jobj.put("id", id);
			jobj.put("aid", aid);
			jobj.put("name", name);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return jobj;
	}
	
	public static ActionEntity fromJSONObject(JSONObject jobj){
		ActionEntity entity = new ActionEntity();
		
		entity.id = jobj.optLong("id");
		entity.aid = jobj.optLong("aid");
		entity.name = jobj.optString("name");
		
		return entity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
