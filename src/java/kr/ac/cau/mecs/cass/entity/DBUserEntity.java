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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Administrator
 */
@Entity
public class DBUserEntity {
    @Id
    @GeneratedValue
    private long id;
    
    private String name;
    private String password;
    
    
    @OneToMany(mappedBy = "user")
    private Set<DBCAUEntity> caus;
    
    @OneToOne(mappedBy = "user")
    @Cascade(CascadeType.SAVE_UPDATE)
    private DBAccessTokenEntity accessToken;

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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the caus
     */
    public Set<DBCAUEntity> getCaus() {
        return caus;
    }

    /**
     * @param caus the caus to set
     */
    public void setCaus(Set<DBCAUEntity> caus) {
        this.caus = caus;
    }

    /**
     * @return the accessToken
     */
    public DBAccessTokenEntity getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(DBAccessTokenEntity accessToken) {
        this.accessToken = accessToken;
    }
    
    
}
