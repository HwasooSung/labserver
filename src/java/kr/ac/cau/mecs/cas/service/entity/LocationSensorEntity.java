package kr.ac.cau.mecs.cas.service.entity;

import org.json.JSONObject;

public class LocationSensorEntity extends SensorEntity{
	double lat;
	double lng;
	
	public static final String name = "CAS.SENSOR.LOCATION";
	
	public LocationSensorEntity(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
		
		this.dataType = SENSOR_TYPE_2D_DOUBLE;
		this.sensorName = name;
		this.timestamp = System.currentTimeMillis();
		this.value = lat +"," + lng;
		
	}
	
	@Override
	protected void setParameters() {
		super.setParameters();
		
		String[] values = value.split(",");
		
		lat = Double.parseDouble(values[0]);
		lng = Double.parseDouble(values[1]);
	}
	
	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}
	
	
}
