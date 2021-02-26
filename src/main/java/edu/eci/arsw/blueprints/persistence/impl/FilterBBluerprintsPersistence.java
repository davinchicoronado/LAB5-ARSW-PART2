/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Coronado
 */
public class FilterBBluerprintsPersistence extends FilterABlueprintsPersistence{
    
    public FilterBBluerprintsPersistence(){
        super();      
    }
    @Override
    protected Blueprint filtrar(Blueprint b){
        List<Point> points = b.getPoints();
        Blueprint b2;
        
        
        if (points.isEmpty()){
            b2=b;
        }
        else{
            List<Point> pfiltered = new ArrayList<>();
            boolean flag=true;
            for(Point p:points){
                if(flag){
                    pfiltered.add(p);
                    flag=false;
                }else{flag=true;}
            }
            Point[] pfiltered2 = new Point[pfiltered.size()];
            pfiltered.toArray(pfiltered2);
            
            b2= new Blueprint(b.getAuthor(),b.getName(),pfiltered2);
        
        }
        
    return b2;
    }

}