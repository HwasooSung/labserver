/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.signal;

import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Signal {
    private String authToken;
    private String userToken;
    
    private String sender;
    private String receiver;
    
    private Action action;
    private Payload payload;
    
    public static Signal fromJOSONObject(JSONObject jobj){
        Signal res = new Signal();
        
        res.setSender(jobj.optString("sender"));
        res.setReceiver(jobj.optString("receiver"));
        
        
        if(jobj.has("action"))
            res.setAction(Action.fromJOSONObject(jobj.optJSONObject("action")));
        else
            return null;
        
        if(jobj.has("payload"))
            res.setPayload(Payload.fromJOSONObject(jobj.optJSONObject("payload")));
        
        if(jobj.has("authToken"))
            res.setAuthToken(jobj.optString("authToken"));
        if(jobj.has("userToken"))
            res.setUserToken(jobj.optString("userToken"));
        
        return res;
    }
    
    public JSONObject toJSONObject(){
        JSONObject jobj = new JSONObject();
        
        jobj.putOpt("sender", getSender());
        jobj.putOpt("receiver", getReceiver());
        jobj.putOpt("action", getAction().toJSONObject());
        
        if(getPayload()!=null)
            jobj.putOpt("payload", getPayload().toJSONObject());
        
        return jobj;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * @return the payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * @param userToken the userToken to set
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
    
}
