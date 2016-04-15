/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kr.ac.cau.mecs.cass.cae.dbscan;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DBSCANCalculator {
    private double epsilon;
    
    private int minpts;
    
    private List<PointEntity> pointList;
    
    public void runDBSCAN(){
        int idx2 = 0;
        
        while(pointList.size() > idx2){
            PointEntity p = pointList.get(idx2);
            
            if(!p.isVisited){
                p.isVisited = true;
                
                List<PointEntity> neigh = getNeighbors(p);
                if(neigh.size()>= minpts){
                    int idx = 0;
                    while(neigh.size()>idx){
                        PointEntity p2 = neigh.get(idx);
                        
                        if(!p2.isVisited)
                            p2.isVisited = true;
                        
                    }
                    
                }
            }
        }
    }
    
    private List<PointEntity> getNeighbors(PointEntity p){
        List<PointEntity> res = new ArrayList<>();
        
        for(PointEntity pt : pointList){
            if(p.calculateDistance(pt) <= epsilon)
                res.add(pt);
        }
        
        return res;
    }
}
