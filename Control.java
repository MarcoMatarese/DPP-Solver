/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author marcomatarese
 */

public class Control{
    public static Formula form = new Formula();
    
    /**
     * The program's kernel.
     */
    public static void goToDpp(){
        TreeSet<Clausola> ex = null, ris = null;
        
        //The loop stops when the Formula has 0 or 1 Clausola.
        while(form.listOfC.size() > 1){
            form.deleteAllTautologies();
            form.emptyClauseControl();
                        
            Letterale p = form.takePivot();
                        
            ex = form.identifyExonerated(p);
                        
            ris = form.calculateResolvents(p);
                        
            form.listOfC = form.formulasUnion(ex, ris);  
        }
    }
    
    /**
     * Cleans all the used memory.
     */
    public static void cleanAll(){
        Iterator<Clausola> itC = form.listOfC.iterator();
        Clausola tmpC;
        
        while(itC.hasNext()){
            tmpC = itC.next();
            tmpC.listOfL.removeAll(tmpC.listOfL);
        }
        
        form.listOfC.removeAll(form.listOfC);
        form = new Formula();
    }
}
