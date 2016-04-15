/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.endpoint;

import kr.ac.cau.mecs.cass.dao.AccessTokenEntityDAO;
import kr.ac.cau.mecs.cass.entity.DBAccessTokenEntity;
import kr.ac.cau.mecs.cass.entity.DBUserEntity;
import kr.ac.cau.mecs.cass.signal.Action;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Payloadable;
import kr.ac.cau.mecs.cass.signal.Signal;
import kr.ac.cau.mecs.cass.signal.payload.StringPayload;
import org.hibernate.Session;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public abstract class SignalProcessor {
    public abstract boolean canProcess(Signal signal);
    
    public abstract Signal process(Signal signal);
    
    protected Session session;
    
    protected DBUserEntity currentUser;
    
    public Signal preprocess(Session session, Signal signal){
        
        this.session = session;
        
        currentUser = null;
        if(signal.getUserToken()!=null && signal.getUserToken().length()>0){
            DBAccessTokenEntity accesstoken = AccessTokenEntityDAO.getAccessTokenByUserToken(session, signal.getUserToken());
            if(accesstoken!=null){
                if(accesstoken.getAccessToken().equals(signal.getAuthToken())){
                    currentUser = accesstoken.getUser();
                }
            }
        }
        
        return process(signal);
    }
    
    public void setGenericMessage(Signal signal, String message){
        signal.setAction(new Action(Action.ACT_GENERAL_MESSAGE));
        signal.setPayload(new Payload(new StringPayload(message)));
        
    }
//    public static Signal process(Signal signal){
//        Signal resignal = new Signal();
//        
//        if(signal!=null){
//            if(signal.getAction().getAid() == Action.ACT_SIGNIN){
//                resignal.setSender("CASS");
//                resignal.setReceiver(signal.getSender());
//                resignal.setAction(signal.getAction());
//                resignal.setPayload(new Payload(new StringPayload("signin")));
//            }else if(signal.getAction().getAid() == Action.ACT_SIGNUP){
//                resignal.setSender("CASS");
//                resignal.setReceiver(signal.getSender());
//                resignal.setAction(signal.getAction());
//                resignal.setPayload(new Payload(new StringPayload("signup")));
//            }else{
//                //generic signal handling
//                resignal.setReceiver("");
//                resignal.setSender("CASS");
//                resignal.setReceiver(signal.getSender());
//                resignal.setAction(signal.getAction());
//                resignal.setPayload(new Payload(new StringPayload("unknown signal")));
//            }
//        }else{
//            resignal.setReceiver("");
//            resignal.setSender("CASS");
//            resignal.setAction(new Action(Action.ACT_GENERAL_MESSAGE));
//            resignal.setPayload(new Payload(new StringPayload("invalid signal")));
//        }
//        
//        return resignal;
//    }
}
