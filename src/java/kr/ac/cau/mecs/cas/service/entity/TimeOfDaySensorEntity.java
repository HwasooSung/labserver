package kr.ac.cau.mecs.cas.service.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeOfDaySensorEntity extends SensorEntity{
	
	private int timeofday;
	
	public static final String name = "CAS.SENSOR.TIMEOFDAY";
	
	public TimeOfDaySensorEntity(){
		this.dataType = SENSOR_TYPE_LONG;
		this.sensorName = name;
		this.timestamp = System.currentTimeMillis();
		
		Calendar cal = GregorianCalendar.getInstance();
		
		int hod = cal.get(Calendar.HOUR_OF_DAY);
		
		this.value = hod + ""; 
	}
	
	@Override
	protected void setParameters() {
		super.setParameters();
		
		this.timeofday = Integer.parseInt(this.value);
	}

	public int getTimeofday() {
		return timeofday;
	}
}
