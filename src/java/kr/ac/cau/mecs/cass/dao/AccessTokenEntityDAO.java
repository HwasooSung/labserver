/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.dao;

import java.util.List;
import kr.ac.cau.mecs.cass.entity.DBAccessTokenEntity;
import kr.ac.cau.mecs.cass.entity.DBUserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

/**
 *
 * @author Administrator
 */
public class AccessTokenEntityDAO {
    public static DBAccessTokenEntity getAccessTokenByUserToken(Session session, String usertoken){
        List<DBAccessTokenEntity> list;
        
        Criteria criteria = session.createCriteria(DBAccessTokenEntity.class);
        
        list = criteria.add(Property.forName("userToken").eq(usertoken))
                .list();
        
        if(list!=null && list.size()>0)
            return list.get(0);
        return null;
    }
}
