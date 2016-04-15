/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.signal.payload;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Payloadable;
import kr.ac.cau.mecs.cass.utils.Base64;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class JSONObjectPayload extends Payloadable<JSONObject>{
    
    public JSONObjectPayload(){
        
    }
    
    public JSONObjectPayload(JSONObject jobj){
        this.data = jobj;
    }
    
    @Override
    public void serializeRawData(Payload payload) {
        String data = this.data.toString();
        
        Checksum crc32 = new CRC32();
        Base64 base64 = new Base64();
        
        byte[] bytedata = data.getBytes(Charset.forName("UTF-8"));
        
        crc32.reset();
        crc32.update(bytedata, 0, bytedata.length);
        
        String encoded = base64.encode(bytedata);
        
        payload.setCrc(crc32.getValue());
        payload.setLength(encoded.length());
        payload.setRawdata(encoded);
        
        payload.setType(0x11);
    }

    @Override
    public void deserializeRawData(Payload payload) {
            String rawdata = payload.getRawdata();
            
            Base64 base64 = new Base64();
            
            byte[] bytedata = base64.decode(rawdata);
            
            String data = new String(bytedata, Charset.forName("UTF-8"));
            
            this.data = new JSONObject(data);
        
    }

    @Override
    public boolean canSerializeRawData(Payload payload) {
        if(payload.getType()==0x11)
            return true;
        return false;
    }
    
}
