/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.signal;

import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Administrator
 */
public class Payload {
    private int type;
    
    private long crc;
    private int code;
    private long length;
    
    private String rawdata;
    
    private Payloadable payload;
    
    public Payload(){
        
    }
    
    public Payload(Payloadable payload){
        this.payload = payload;
        this.code = 0;
    }
    
    public static Payload fromJOSONObject(JSONObject jobj){
        Payload res = new Payload();
        res.setCode(jobj.optInt("code"));
        
        res.setType(jobj.optInt("type"));
        res.setCrc(jobj.optLong("crc"));
        res.setLength(jobj.optLong("length"));
        res.setRawdata(jobj.optString("rawdata"));
        
        Payloadable parser = PayloadableParser.getInstance().getParser(res);
        if(parser!=null){
            res.payload = parser;
            parser.deserializeRawData(res);
        }
        return res;
    }
    
    public JSONObject toJSONObject(){
        JSONObject jobj = new JSONObject();
        payload.serializeRawData(this);
        
        jobj.putOpt("type", getType());
        jobj.putOpt("crc", getCrc());
        jobj.putOpt("code", getCode());
        jobj.putOpt("length", getLength());
        jobj.putOpt("rawdata", getRawdata());
        
        return jobj;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the crc
     */
    public long getCrc() {
        return crc;
    }

    /**
     * @param crc the crc to set
     */
    public void setCrc(long crc) {
        this.crc = crc;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the length
     */
    public long getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * @return the rawdata
     */
    public String getRawdata() {
        return rawdata;
    }

    /**
     * @param rawdata the rawdata to set
     */
    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
    }

    /**
     * @return the payload
     */
    public Payloadable getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(Payloadable payload) {
        this.payload = payload;
    }
    
    
}
