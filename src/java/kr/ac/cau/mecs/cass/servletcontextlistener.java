/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public abstract class servletcontextlistener extends HttpServlet{

    private HttpServletRequest _request;

    private Map<String, Object> parameterMap;
    
    private void onServletInit(HttpServletRequest request, HttpServletResponse response){
        _request = request;

    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        onServletInit(request, response);
        
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();
        
        Transaction tx = null;
        Session hsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();

        try {
            tx = hsession.beginTransaction();
            
            jobj = processGet(hsession,request,response);
            
            tx.commit();
        } catch (Exception e) {
            jobj.putOpt("error", 1);
            jobj.putOpt("reason", "server transaction error");
            jobj.putOpt("msg", e.getMessage());
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (HibernateException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        jobj.write(out);
        out.close();
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        onServletInit(request, response);
        
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();
        
        Transaction tx = null;
        Session hsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();

        try {
            tx = hsession.beginTransaction();
            
            jobj = processPost(hsession,request,response);
            
            tx.commit();
        } catch (Exception e) {
            jobj.putOpt("error", 1);
            jobj.putOpt("reason", "server transaction error");
            jobj.putOpt("msg", e.getMessage());
            
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (HibernateException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        jobj.write(out);
        out.close();
    }
    
    abstract protected JSONObject processGet(Session session,HttpServletRequest request, HttpServletResponse response) throws IOException;
    abstract protected JSONObject processPost(Session session,HttpServletRequest request, HttpServletResponse response);
    
    protected String getParameter(String key){
        
        return _request.getParameter(key);
    }
    
}
