/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Administrator
 */
@Entity
public class DBActionEntity {
    @Id
    @GeneratedValue
    private long id;
    
    private long aid;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "cauid")
    private DBCAUEntity cau;
    
    @OneToMany(mappedBy = "action")
    private Set<DBSensorEntity> sensordata;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the sensordata
     */
    public Set<DBSensorEntity> getSensordata() {
        return sensordata;
    }

    /**
     * @param sensordata the sensordata to set
     */
    public void setSensordata(Set<DBSensorEntity> sensordata) {
        this.sensordata = sensordata;
    }
    
    
}
