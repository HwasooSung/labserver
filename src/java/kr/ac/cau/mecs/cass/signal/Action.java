/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.signal;

import kr.ac.cau.mecs.cass.entity.DBActionEntity;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Action {
    
    public final static long ACT_GENERAL_MESSAGE = 0x0;
    public final static long ACT_SIGNIN = 0x10;
    public final static long ACT_SIGNUP = 0x11;
    
    public final static long ACT_SENSOR = 0x20;
    
    public final static long ACT_LAUNCH = 0x1000;
    
    private long aid;
    private long cid;
    
    private DBActionEntity action;
    
    public Action(long aid){
        this.aid = aid;
        this.cid = 0x1;
    }
    
    public Action(DBActionEntity action){
        //TODO: action id here
    }
    
    public static Action fromJOSONObject(JSONObject jobj){
        Action res = new Action(jobj.optLong("aid"));
        return res;
    }
    
    public JSONObject toJSONObject(){
        JSONObject jobj = new JSONObject();
        
        jobj.putOpt("aid", getAid());
        jobj.putOpt("cid", getCid());
        return jobj;
    }

    /**
     * @return the aid
     */
    public long getAid() {
        return aid;
    }

    /**
     * @param aid the aid to set
     */
    public void setAid(long aid) {
        this.aid = aid;
    }

    /**
     * @return the action
     */
    public DBActionEntity getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(DBActionEntity action) {
        this.action = action;
    }

    /**
     * @return the cid
     */
    public long getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(long cid) {
        this.cid = cid;
    }
    
    
}
