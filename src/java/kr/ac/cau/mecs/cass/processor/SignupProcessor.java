/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.processor;

import java.security.KeyPair;
import kr.ac.cau.mecs.cass.BCrypt;
import kr.ac.cau.mecs.cass.dao.UserEntityDAO;
import kr.ac.cau.mecs.cass.endpoint.SignalProcessor;
import kr.ac.cau.mecs.cass.entity.DBAccessTokenEntity;
import kr.ac.cau.mecs.cass.entity.DBUserEntity;
import kr.ac.cau.mecs.cass.signal.Action;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Signal;
import kr.ac.cau.mecs.cass.signal.payload.JSONObjectPayload;
import kr.ac.cau.mecs.cass.utils.AccessTokenUtil;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class SignupProcessor  extends SignalProcessor{

    @Override
    public boolean canProcess(Signal signal) {
        if(signal.getAction()!=null &&
                signal.getAction().getAid()==Action.ACT_SIGNUP &&
                signal.getAction().getCid()==0x1){
            return true;
        }
        return false;
    }

    @Override
    public Signal process(Signal signal) {
        Signal resignal = new Signal();
        
        resignal.setReceiver(signal.getSender());
        resignal.setSender("CASS");
        resignal.setAction(new Action(Action.ACT_SIGNUP));
        
        
        if(signal.getPayload()!=null && (signal.getPayload().getPayload() instanceof JSONObjectPayload)){
            JSONObject jobj = (JSONObject)signal.getPayload().getPayload().getData();
            
            if(jobj.has("userid") && jobj.has("userpw")){
                String userid = jobj.optString("userid");
                String userpw = jobj.optString("userpw");
                //valid payload
                
                DBUserEntity _user = UserEntityDAO.getByUserID(session, userid);
                
                if(_user!=null){
                    //user exists
                    setGenericMessage(resignal, "user exists");
                }else{
                    if(userid.length()>4){
                        if(userpw.length()>4){
                            //create here
                            _user = new DBUserEntity();
                            _user.setName(userid);
                            _user.setPassword(userpw);
                            
                            if(_user.getAccessToken()==null){
                                _user.setAccessToken(new DBAccessTokenEntity());
                                _user.getAccessToken().setUser(_user);
                            }

                            KeyPair keypair = AccessTokenUtil.generateKeyPair(System.currentTimeMillis());

                            String usertoken = BCrypt.hashpw(userid, BCrypt.gensalt(12));
                            String authtoken = AccessTokenUtil.signData(usertoken, keypair.getPrivate());

                            _user.getAccessToken().setPrivateKey(AccessTokenUtil.encodePrivateKey(keypair.getPrivate()));
                            _user.getAccessToken().setPublicKey(AccessTokenUtil.encodePublicKey(keypair.getPublic()));
                            _user.getAccessToken().setAccessToken(authtoken);
                            _user.getAccessToken().setUserToken(usertoken);
                            
                            session.save(_user);
                            session.saveOrUpdate(_user.getAccessToken());
                            
                            JSONObject jres = new JSONObject();
                            jres.putOpt("authToken", authtoken);
                            jres.putOpt("userToken", usertoken);


                            resignal.setPayload(new Payload(new JSONObjectPayload(jres)));
                        }else{
                            setGenericMessage(resignal, "pw too short(min 5)");
                        }
                    }else{
                        setGenericMessage(resignal, "id too short(min 5)");
                    }
                }
                
                
            }else{
                setGenericMessage(resignal, "invalid payload type");
            }
        }else{
            //inform user invalid payload type
            setGenericMessage(resignal, "invalid payload type");
        }
        
        return resignal;
    }
}
