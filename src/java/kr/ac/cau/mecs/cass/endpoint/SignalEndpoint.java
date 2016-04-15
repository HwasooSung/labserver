/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.endpoint;

import java.io.IOException;
import java.io.PrintWriter;
import javassist.compiler.TokenId;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.ac.cau.mecs.cass.servletcontextlistener;
import kr.ac.cau.mecs.cass.signal.Action;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.PayloadableParser;
import kr.ac.cau.mecs.cass.signal.Signal;
import org.hibernate.Session;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class SignalEndpoint extends servletcontextlistener {

    @Override
    protected JSONObject processGet(Session session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new JSONObject();
    }

    @Override
    protected JSONObject processPost(Session session, HttpServletRequest request, HttpServletResponse response) {
        String strsig = getParameter("signal");
        
        JSONObject jsig = new JSONObject(strsig);
        
        System.out.println(jsig.toString());
        Signal signal = Signal.fromJOSONObject(jsig);
        
        SignalProcessor processor = SignalProcessorParser.getInstance().getParser(signal);
        if(processor==null){
            processor = SignalProcessorParser.getInstance().getDefaultParser();
        }
        
        return processor.preprocess(session, signal).toJSONObject();
    }
}
