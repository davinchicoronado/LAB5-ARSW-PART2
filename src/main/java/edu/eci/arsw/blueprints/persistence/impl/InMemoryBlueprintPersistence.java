/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("inMemoryPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] pts1=new Point[]{new Point(1, 15),new Point(24, 27),new Point(30, 40)};
        Point[] pts2=new Point[]{new Point(2, 1),new Point(4, 2),new Point(31, 41),new Point(56, 82)};
        
        
        
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
        Blueprint bp2=new Blueprint("David", "Hopital",pts);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        
        Blueprint bp3=new Blueprint("David", "Escuela",pts1);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        
        Blueprint bp4=new Blueprint("Leonardo", "Casa",pts2);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        
        
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {    
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsForAutor(String author) throws BlueprintNotFoundException{
            Set<Blueprint> bPrint = new HashSet<>();
            
            Blueprint bprintprov;
            for(Map.Entry<Tuple<String,String>,Blueprint>  entry :  blueprints.entrySet()){
                bprintprov=entry.getValue();
                if(bprintprov.getAuthor()==author){
                    bPrint.add(bprintprov);
                }
            }
            if (bPrint.isEmpty()){
                throw new BlueprintNotFoundException("The author does not exist: "+author);
            }
            
            return bPrint;
    }
    
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        
        Set<Blueprint> bSprint = new HashSet<>();
        for(Map.Entry<Tuple<String,String>,Blueprint>  entry :  blueprints.entrySet()){
            bSprint.add(entry.getValue());
        }
        if (bSprint.isEmpty()){
            throw new BlueprintNotFoundException("There are not any blueprint");
        }
        
        return bSprint;
    
    }
    
}
