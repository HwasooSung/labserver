/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.processor;

import kr.ac.cau.mecs.cass.endpoint.SignalProcessor;
import kr.ac.cau.mecs.cass.signal.Action;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Signal;
import kr.ac.cau.mecs.cass.signal.payload.StringPayload;

/**
 *
 * @author Administrator
 */
public class DefaultProcessor extends SignalProcessor{

    @Override
    public boolean canProcess(Signal signal) {
        return false;//dont show on parser 
    }

    @Override
    public Signal process(Signal signal) {
        Signal resignal = new Signal();
        resignal.setReceiver("");
        resignal.setSender("CASS");
        resignal.setAction(new Action(Action.ACT_GENERAL_MESSAGE));
        resignal.setPayload(new Payload(new StringPayload("invalid signal")));
        
        return resignal;
    }

}
