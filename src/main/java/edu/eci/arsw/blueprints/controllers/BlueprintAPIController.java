/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")


public class BlueprintAPIController {
   
   Object lock= new Object();
   
   @Autowired
   BlueprintsServices bpp;
   @RequestMapping(method = RequestMethod.GET)
   public ResponseEntity<?> getAllBluePrints(){
    try {
        Set<Blueprint> bPrints  =  bpp.getAllBlueprints();
        return new ResponseEntity<>(bPrints,HttpStatus.ACCEPTED);
    }  catch (BlueprintNotFoundException ex) {        
           Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>(ex.toString(),HttpStatus.NOT_FOUND);
       }
   }
  
   @RequestMapping(value="/{author}" , method=RequestMethod.GET)  
   public ResponseEntity<?> getBlueprintsAuthor(@PathVariable("author") String author){
       try {
           Set<Blueprint> bPrints  = bpp.getBlueprintsByAuthor(author);
           return new ResponseEntity<>(bPrints,HttpStatus.ACCEPTED);
       } catch (BlueprintNotFoundException ex) {
           Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>("Http Error Code: 404. Resource not found",HttpStatus.NOT_FOUND);
       }
       
   }
   @RequestMapping(value="/{author}/{bpname}" , method=RequestMethod.GET)
   public ResponseEntity<?> getBlueprint(@PathVariable("author") String author,@PathVariable("bpname") String bpname ){
       try {
           Blueprint bp = bpp.getBlueprint(author, bpname);
           return new ResponseEntity<>(bp,HttpStatus.ACCEPTED);
       } catch (BlueprintNotFoundException ex) {
           Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>("Http Error Code: 404. Resource not found",HttpStatus.NOT_FOUND);
       }
   
   }
   @RequestMapping(value="/add" ,method=RequestMethod.POST)
   public ResponseEntity<?> saveBlueprint(@RequestBody Blueprint bp){
            try{
                synchronized(lock){
                    bpp.addNewBlueprint(bp);
                }
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch(BlueprintPersistenceException ex){
                Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>("Http Error Code: 400. Bad Request",HttpStatus.BAD_REQUEST);
            } 
   }
   @RequestMapping(value="/{author}/{bpname}" ,method=RequestMethod.PUT)
   public ResponseEntity<?> updateBlueprint(@PathVariable("author") String author,@PathVariable("bpname") String bpname, @RequestBody Blueprint bp){
            
       try {
           synchronized(lock){
           Blueprint b = bpp.getBlueprint(author, bpname);  
           b.setAuthor(bp.getAuthor());
           b.setName(bp.getName());
           if(!bp.getPoints().isEmpty()){
               b.setPoints(bp.getPoints());
           }
           }
           return new ResponseEntity<>(HttpStatus.ACCEPTED);           
       } catch (BlueprintNotFoundException ex) {
           Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>("Http Error Code: 400. Bad Request",HttpStatus.BAD_REQUEST);
       }
       
   }
   
   
   
   
    
    
    
}

