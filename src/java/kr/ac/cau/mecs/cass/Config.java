/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebListener
public class Config implements ServletContextListener{
    
    public static final String DB_CRYPT_KEY = "d5647b1a1a18717ea116cc5e1f8e0085";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        
        tx.commit(); 
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}