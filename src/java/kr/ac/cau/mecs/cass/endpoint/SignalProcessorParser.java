/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.endpoint;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kr.ac.cau.mecs.cass.processor.DefaultProcessor;
import kr.ac.cau.mecs.cass.signal.Payload;
import kr.ac.cau.mecs.cass.signal.Payloadable;
import kr.ac.cau.mecs.cass.signal.PayloadableParser;
import kr.ac.cau.mecs.cass.signal.Signal;

/**
 *
 * @author Administrator
 */
public class SignalProcessorParser {
    
    private Class[] processors;
    
    private static final SignalProcessorParser instance = new SignalProcessorParser();
    
    private SignalProcessorParser(){
        processors = getClasses("kr.ac.cau.mecs.cass.processor");
    }
    
    public static SignalProcessorParser getInstance(){
        return instance;
    }
    public SignalProcessor getDefaultParser(){
        return new DefaultProcessor();
    }
    public SignalProcessor getParser(Signal signal){
        for(Class clazz : processors){
            try {
                SignalProcessor parser = (SignalProcessor)clazz.newInstance();
                
                if(parser.canProcess(signal))
                    return parser;
                
            } catch (InstantiationException ex) {
                Logger.getLogger(PayloadableParser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PayloadableParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    private static Class<Payloadable>[] getClasses(String packageName)
    {
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            ArrayList<Class> classes = new ArrayList();
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
            return classes.toArray(new Class[classes.size()]);
        }catch(Exception e){
            return new Class[0];
        }
    }
    
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                if(clazz.getSuperclass() == SignalProcessor.class){
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
}
