/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Administrator
 */
@Entity
public class DBSensorEntity {
    @Id
    @GeneratedValue
    private long id;
    
    private String sensorName;
    
    private String _value;
    
    private int dataType;
    
    private int caeType;
    
    private long _timestamp;
    
    @ManyToOne
    @JoinColumn(name = "actionid")
    private DBActionEntity action;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the sensorName
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * @param sensorName the sensorName to set
     */
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    /**
     * @return the _value
     */
    public String getValue() {
        return _value;
    }

    /**
     * @param _value the _value to set
     */
    public void setValue(String _value) {
        this._value = _value;
    }

    /**
     * @return the dataType
     */
    public int getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the _timestamp
     */
    public long getTimestamp() {
        return _timestamp;
    }

    /**
     * @param _timestamp the _timestamp to set
     */
    public void setTimestamp(long _timestamp) {
        this._timestamp = _timestamp;
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
     * @return the caeType
     */
    public int getCaeType() {
        return caeType;
    }

    /**
     * @param caeType the caeType to set
     */
    public void setCaeType(int caeType) {
        this.caeType = caeType;
    }
    
    
    
}

