/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.cae;

import java.util.List;
import java.util.Set;
import kr.ac.cau.mecs.cas.service.entity.SensorEntity;
import kr.ac.cau.mecs.cass.entity.DBActionEntity;
import kr.ac.cau.mecs.cass.entity.DBCAUEntity;
import kr.ac.cau.mecs.cass.entity.DBSensorEntity;

/**
 *
 * @author Administrator
 */
public class CAECalculator {
    public ActionScoreEntity calculateActionScore(DBCAUEntity cau, DBActionEntity action, List<SensorEntity> sensors){
        ActionScoreEntity score = new ActionScoreEntity();
        
        score.setAction(action);
        score.setCau(cau);
        
        double _score = 0;
        int sensorcount = 0;
        for(SensorEntity sensor : sensors){
            if(sensor.getCaeType()==SensorEntity.SENSOR_CAE_BOOLEAN){
                _score += calculateScoreBoolean(sensor, action.getSensordata());
                sensorcount++;
            }else if(sensor.getCaeType()==SensorEntity.SENSOR_CAE_DBSCAN){
                //dbscan here
//                sensorcount++;
            }else{
                //????
            }
        }
        if(sensorcount==0)
            _score = 0;
        else
            _score = _score / sensorcount;
        
        score.setScore(_score);
        
        return score;
    }
    
    public double calculateScoreDBSCAN(SensorEntity sensor, Set<DBSensorEntity> dbsensors){
        return 1;
    }
    
    public double calculateScoreBoolean(SensorEntity sensor, Set<DBSensorEntity> dbsensors){
        int sensorhistory = 0;
        int hit = 0;
        for(DBSensorEntity dbsensor : dbsensors){
            if(dbsensor.getSensorName().equals(sensor.getSensorName())){//same sensor
                
                if(isBooleanMatching(sensor, dbsensor))
                    hit++;
                
                sensorhistory++;
            }
        }
        
        if(sensorhistory == 0)
            return 0;
        
        return ((double)hit / (double)sensorhistory);
    }
    
    public boolean isBooleanMatching(SensorEntity sensor, DBSensorEntity dbsensor){
        if(sensor.getValue().equals(dbsensor.getValue())){
            return true;
        }
        return false;
    }
}
