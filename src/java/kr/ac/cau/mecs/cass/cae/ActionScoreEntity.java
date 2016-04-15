/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.cae;

import kr.ac.cau.mecs.cas.service.entity.ActionEntity;
import kr.ac.cau.mecs.cas.service.entity.CAUEntity;
import kr.ac.cau.mecs.cass.entity.DBActionEntity;
import kr.ac.cau.mecs.cass.entity.DBCAUEntity;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class ActionScoreEntity implements Comparable<ActionScoreEntity>{
    private DBCAUEntity cau;
    private DBActionEntity action;
    
    private double score;

    @Override
    public int hashCode() {
        return ((Long)action.getId()).hashCode();
    }

    public JSONObject toJSONObject(){
        JSONObject jobj = new JSONObject();
        
        CAUEntity cauentity = new CAUEntity();
        cauentity.setId(cau.getId());
        cauentity.setName(cau.getName());
        cauentity.setVersion(cau.getVersion());
        
        ActionEntity actionentity = new ActionEntity();
        actionentity.setAid(action.getAid());
        actionentity.setId(action.getId());
        actionentity.setName(action.getName());
        
        jobj.putOpt("cau", cauentity.toJSONObject());
        jobj.putOpt("action", actionentity.toJSONObject());
        
        jobj.put("score", score);
        
        return jobj;
    }
    
    /**
     * @return the cau
     */
    public DBCAUEntity getCau() {
        return cau;
    }

    /**
     * @param cau the cau to set
     */
    public void setCau(DBCAUEntity cau) {
        this.cau = cau;
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
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(ActionScoreEntity o) {
        return Double.compare(this.score, o.score);
    }
    
    
}
