/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kr.ac.cau.mecs.cass.utils;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author Administrator
 */
public class AccessTokenUtil {
 
    public static String encrypt(String data, PublicKey key){
            byte[] cipherText = null;

            try{
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    cipherText = cipher.doFinal(data.getBytes(Charset.forName("UTF-8")));

                    return Base64.toBase64String(cipherText);
            }catch(Exception e){
                    e.printStackTrace();
            }

            return null;
    }

    public static String decrypt(String data, PrivateKey key){
            byte[] bdata = Base64.decode(data);
            byte[] dectext = null;

            try{
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.DECRYPT_MODE, key);

                    dectext = cipher.doFinal(bdata);

                    return new String(dectext, Charset.forName("UTF-8"));
            }catch(Exception e){
                    e.printStackTrace();
            }
            return null;

    }

    public static String signData(String data, PrivateKey key) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        try {
            byte[] _data = data.getBytes(Charset.forName("UTF-8"));
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(key);
            signer.update(_data);
            byte[] signature = signer.sign();

            return Base64.toBase64String(signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static boolean verifySig(String data, PublicKey key, String signature) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        try{
            byte[] _data = data.getBytes(Charset.forName("UTF-8"));
            byte[] _signature = Base64.decode(signature);

            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initVerify(key);
            signer.update(_data);

            return signer.verify(_signature);
        }catch(Exception e){
            System.out.println("signature:" + signature);
            e.printStackTrace();
            return false;
        }
    }

    public static KeyPair generateKeyPair(long seed) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom rng = SecureRandom.getInstance("SHA1PRNG");

            rng.setSeed(seed);
            keyGenerator.initialize(2048, rng);
            return keyGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodePublicKey(PublicKey key) {
        try {
            byte[] _key = key.getEncoded();

            return Base64.toBase64String(_key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodePrivateKey(PrivateKey key) {
        try {
            byte[] _key = key.getEncoded();

            return Base64.toBase64String(_key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PublicKey getPublicKey(String key) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        byte[] _key = Base64.decode(key);
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(_key);
            KeyFactory keyFacetory = KeyFactory.getInstance("RSA");
            PublicKey publickey = keyFacetory.generatePublic(spec);
            return publickey;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }
    }

    public static PrivateKey getPrivateKey(String key) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        byte[] _key = Base64.decode(key);
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(_key);
            KeyFactory keyFacetory = KeyFactory.getInstance("RSA");
            PrivateKey privatekey = keyFacetory.generatePrivate(spec);
            return privatekey;
        } catch (Exception e) {
            return getPrivateKeyPkcs1(_key);
        }
    }
    
    private static PrivateKey getPrivateKeyPkcs1(byte[] key){
        try{
            RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(key));
            RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = (PrivateKey) kf.generatePrivate(rsaPrivKeySpec);
            
            return privKey;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
