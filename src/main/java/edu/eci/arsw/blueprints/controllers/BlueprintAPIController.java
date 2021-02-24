/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
   
   @Autowired
   BlueprintsServices bpp;
   @RequestMapping(method = RequestMethod.GET)
   public ResponseEntity<?> getAllBluePrints(){
    try {
        Set<Blueprint> bPrints  =  bpp.getAllBlueprints();
        return new ResponseEntity<>(bPrints,HttpStatus.ACCEPTED);
    }  catch (BlueprintNotFoundException ex) {        
           Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
       }        
} 
    
    
    
}

