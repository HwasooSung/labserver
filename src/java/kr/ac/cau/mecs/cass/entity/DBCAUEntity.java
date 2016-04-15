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
public class DBCAUEntity {
    
    @Id
    @GeneratedValue
    private long id;//cau db id
    
    private String name;
    
    private int version;
    
    @OneToMany(mappedBy = "cau")
    private Set<DBActionEntity> actions;
    
    @ManyToOne
    @JoinColumn(name = "userid")
    private DBUserEntity user;

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
     * @return the actions
     */
    public Set<DBActionEntity> getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(Set<DBActionEntity> actions) {
        this.actions = actions;
    }

    /**
     * @return the user
     */
    public DBUserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(DBUserEntity user) {
        this.user = user;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }
    
    
    
}
