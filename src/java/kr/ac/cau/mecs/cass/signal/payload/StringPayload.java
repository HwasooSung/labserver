/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.signal.payload;

import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Payloadable;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Administrator
 */
public class StringPayload extends Payloadable<String>{
    
    public StringPayload(){
        data = "";
    }
    
    public StringPayload(String data){
        this.data = data;
    }
    
    @Override
    public void serializeRawData(Payload payload) {
        payload.setCrc(0);
        payload.setLength(data.length());
        payload.setRawdata(data);
        payload.setType(0x10);
    }

    @Override
    public void deserializeRawData(Payload payload) {
        this.data = payload.getRawdata();
    }

    @Override
    public boolean canSerializeRawData(Payload payload) {
        if(payload.getType()==0x10)
            return true;
        return false;
    }
}
