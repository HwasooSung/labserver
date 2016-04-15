package kr.ac.cau.mecs.cas.service.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

public class DayofWeekSensorEntity extends SensorEntity{
	private int dayofweek;
	
	public static final String name = "CAS.SENSOR.DAYOFWEEK";
	
	public DayofWeekSensorEntity(){
		this.dataType = SENSOR_TYPE_LONG;
		this.sensorName = name;
		this.timestamp = System.currentTimeMillis();
		
		Calendar cal = GregorianCalendar.getInstance();
		
		dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		
		this.value = dayofweek + ""; 
	}
	
	@Override
	protected void setParameters() {
		super.setParameters();
		
		this.dayofweek = Integer.parseInt(this.value);
		
	}

	public int getDayofweek() {
		return dayofweek;
	}
	
	
}
