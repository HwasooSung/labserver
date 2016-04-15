/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.processor;

import java.util.HashSet;
import java.util.List;
import kr.ac.cau.mecs.cas.service.entity.ActionEntity;
import kr.ac.cau.mecs.cas.service.entity.CAUEntity;
import kr.ac.cau.mecs.cas.service.entity.SensorEntity;
import kr.ac.cau.mecs.cass.endpoint.SignalProcessor;
import kr.ac.cau.mecs.cass.entity.DBActionEntity;
import kr.ac.cau.mecs.cass.entity.DBCAUEntity;
import kr.ac.cau.mecs.cass.entity.DBSensorEntity;
import kr.ac.cau.mecs.cass.signal.Action;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Signal;
import kr.ac.cau.mecs.cass.signal.payload.StringPayload;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class CASLaunchProcessor extends SignalProcessor{
    @Override
    public boolean canProcess(Signal signal) {
        if(signal.getAction()!=null &&
                signal.getAction().getAid()==Action.ACT_LAUNCH &&
                signal.getAction().getCid()==0x1){
            return true;
        }
        return false;
    }

    @Override
    public Signal process(Signal signal) {
        Signal resignal = new Signal();
        
        resignal.setReceiver(signal.getSender());
        resignal.setSender("CASS");
        resignal.setAction(new Action(Action.ACT_LAUNCH));
        
        if(currentUser!=null){
            JSONObject jobj = (JSONObject) signal.getPayload().getPayload().getData();
            
            System.out.println(jobj.toString());
            
            JSONObject jcau = jobj.optJSONObject("cau");
            JSONObject jaction = jobj.optJSONObject("action");
            JSONArray jsensor = jobj.optJSONArray("sensor");
            
            CAUEntity cau = CAUEntity.fromJSONObject(jcau);
            ActionEntity action = ActionEntity.fromJSONObject(jaction);
            
            //see if user already has cau entry of that name
            DBCAUEntity dbcau = findCAU(cau.getName(), cau.getVersion());
            if(dbcau==null){
                //create new cau here!
                dbcau = new DBCAUEntity();
                dbcau.setName(cau.getName());
                dbcau.setVersion(cau.getVersion());
                dbcau.setUser(currentUser);
                dbcau.setActions(new HashSet<DBActionEntity>());
            }
            
            //see if cau has that action already
            DBActionEntity dbaction = null;
            
            for(DBActionEntity tmp : dbcau.getActions()){
                if(tmp.getAid()==action.getAid()){
                    dbaction = tmp;
                    break;
                }
            }
            //create new action here!
            if(dbaction==null){
                dbaction = new DBActionEntity();
                dbaction.setAid(action.getAid());
                dbaction.setCau(dbcau);
                dbaction.setName(action.getName());
                dbaction.setSensordata(new HashSet<DBSensorEntity>());
            }
            
            //create new sensor entry
            
            for(int i=0;i<jsensor.length();i++){
                SensorEntity sensor = SensorEntity.fromJSONObject(jsensor.getJSONObject(i));
                DBSensorEntity dbsensor = new DBSensorEntity();
                
                dbsensor.setAction(dbaction);
                dbsensor.setDataType(sensor.getDataType());
                dbsensor.setCaeType(sensor.getCaeType());
                dbsensor.setSensorName(sensor.getSensorName());
                dbsensor.setTimestamp(sensor.getTimestamp());
                dbsensor.setValue(sensor.getValue());
                
                session.saveOrUpdate(dbsensor);
            }
            session.saveOrUpdate(dbcau);
            session.saveOrUpdate(dbaction);
            
        }else{
            setGenericMessage(resignal, "invalid credential");
        }
        
        return resignal;
    }
    
    private DBCAUEntity findCAU(String name, int version){
        Criteria crit = session.createCriteria(DBCAUEntity.class);
        
        crit.add(Property.forName("name").eq(name))
                .add(Property.forName("version").eq(version))
                .add(Property.forName("user").eq(currentUser));
        
        List<DBCAUEntity> list = crit.list();
        
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
    
}
