/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.signal;

/**
 *
 * @author Administrator
 */
public abstract class Payloadable<T> {
    protected T data;
    public abstract void serializeRawData(Payload payload);
    public abstract void deserializeRawData(Payload payload);
    
    public abstract boolean canSerializeRawData(Payload payload);
    
    public T getData(){
        return data;
    }
    
    public void setData(T data){
        this.data = data;
    }
}
