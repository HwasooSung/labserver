package kr.ac.cau.mecs.cas.service.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorEntity {
	
	public static final int SENSOR_TYPE_BOOLEAN = 1;
	public static final int SENSOR_TYPE_LONG = 2;
	public static final int SENSOR_TYPE_DOUBLE = 3;
	public static final int SENSOR_TYPE_2D_DOUBLE = 4;
	
	public static final int SENSOR_CAE_BOOLEAN = 1;
	public static final int SENSOR_CAE_DBSCAN = 2;
	
	protected long id;
	protected String sensorName;
	protected String value;
	
	protected int dataType;
	protected int caeType;
	
	protected long timestamp;
	
	public JSONObject toJSONObject(){
		JSONObject jobj = new JSONObject();
		
		try {
			jobj.putOpt("name", sensorName);
			jobj.putOpt("sensorName", sensorName);
			jobj.putOpt("value", value);
			jobj.putOpt("dataType", dataType);
			jobj.putOpt("caeType", caeType);
			jobj.putOpt("timestamp", timestamp);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jobj;
	}
	
	protected void setParameters(){
		
	}
	
	public static SensorEntity fromJSONObject(JSONObject jobj){
		SensorEntity entity = new SensorEntity();
		
		entity.id = jobj.optLong("id");
		entity.sensorName = jobj.optString("sensorName");
		entity.value = jobj.optString("value");
		entity.dataType = jobj.optInt("dataType");
		entity.caeType = jobj.optInt("caeType");
		entity.timestamp = jobj.optLong("timestamp");
		
		return entity;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getCaeType() {
		return caeType;
	}

	public void setCaeType(int caeType) {
		this.caeType = caeType;
	}
	
}
