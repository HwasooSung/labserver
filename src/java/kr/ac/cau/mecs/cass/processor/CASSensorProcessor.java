/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kr.ac.cau.mecs.cas.service.entity.SensorEntity;
import kr.ac.cau.mecs.cass.cae.ActionScoreEntity;
import kr.ac.cau.mecs.cass.cae.CAECalculator;
import kr.ac.cau.mecs.cass.endpoint.SignalProcessor;
import kr.ac.cau.mecs.cass.entity.DBActionEntity;
import kr.ac.cau.mecs.cass.entity.DBCAUEntity;
import kr.ac.cau.mecs.cass.signal.Action;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Signal;
import kr.ac.cau.mecs.cass.signal.payload.JSONObjectPayload;
import kr.ac.cau.mecs.cass.signal.payload.StringPayload;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class CASSensorProcessor extends SignalProcessor{
    @Override
    public boolean canProcess(Signal signal) {
        if(signal.getAction()!=null &&
                signal.getAction().getAid()==Action.ACT_SENSOR &&
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
        resignal.setAction(new Action(Action.ACT_SENSOR));
        
        CAECalculator calc = new CAECalculator();
        
        if(currentUser!=null){
            JSONObject _jobj = (JSONObject) signal.getPayload().getPayload().getData();
            
            JSONArray jsensors = _jobj.optJSONArray("sensors");
            
            List<SensorEntity> sensors = new ArrayList<>();
            
            for(int i=0;i<jsensors.length();i++){
                sensors.add(SensorEntity.fromJSONObject(jsensors.getJSONObject(i)));
            }
            
            List<ActionScoreEntity> scores = new ArrayList<>();
            for(DBCAUEntity cau : currentUser.getCaus()){
                for(DBActionEntity action : cau.getActions()){
                    scores.add(calc.calculateActionScore(cau, action, sensors));
                }
            }
            
            Collections.sort(scores);
            Collections.reverse(scores);//decending order...
            
            JSONObject jobj = new JSONObject();
            JSONArray jarr = new JSONArray();
            
            for(ActionScoreEntity score : scores){
                jarr.put(score.toJSONObject());
            }
            jobj.put("scores", jarr);
            
            resignal.setPayload(new Payload(new JSONObjectPayload(jobj)));
            
        }else{
            setGenericMessage(resignal, "invalid credential");
        }
        
        return resignal;
    }
    
}
