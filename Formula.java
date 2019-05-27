/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;
import static java.lang.Boolean.compare;
import java.util.*;

/**
 *
 * @author marcomatarese
 */

public class Formula {
    protected TreeSet<Clausola> listOfC;
    
    /**
     * Constructor
     */
    public Formula(){
        listOfC = new TreeSet<>();
    }
    
    /**
     * 
     * @return true if there're no Clausola or the last is a tautology, false otherwise.
     */
    public boolean ultimateControl(){
        if(this.listOfC.size() == 0) return true;
        //If the last Clausola is a tautology, than the Formula is satisfable.
        else if(this.listOfC.first().isATautology()) return true;
        //Else the last Clausola is the empty clause, than the Formula is unsatisfable.
        else return false;
    }
    
    /**
     * 
     * @return true if this.listOfC exists and is empty.
     */
    public boolean isEmptySetOfClause(){
        if(this.listOfC.size() == 0) return true;
        else return false;
    }
    
    public void emptyClauseControl(){
        if(this.listOfC.size() > 1){
            Iterator<Clausola> it = this.listOfC.iterator();
            while(it.hasNext() && it.next().isEmptyClause()){
                it.remove();
            }
        }
    }
    
    /**
     * Controls all the fomula's Clausola: if one of these is a tautology, it removes it.
     */
    public void deleteAllTautologies(){        
        if(!(this.listOfC.isEmpty())){
            Iterator<Clausola> it = this.listOfC.iterator();
            
            while(it.hasNext()){
                if(it.next().isATautology()) it.remove();
            }
        }        
    }
    
    /**
     * 
     * @return the first Letterale of the first Clausola in the formula.
     */
    public Letterale takePivot(){        
        if(this.listOfC.isEmpty()) return null;
        Clausola cl = this.listOfC.first();
                
        return cl.listOfL.first();
    }
    
    /**
     * Controls how is the greater between ex and ris: add "the smaller in the greater".
     * @param ex the list of the Clausola exonerated.
     * @param ris the list of the Clausola risolved. 
     * @return the union of ex and ris.
     */
    public TreeSet<Clausola> formulasUnion(TreeSet<Clausola> ex, TreeSet<Clausola> ris){        
        if(ex.size() > ris.size()){
            ex.addAll(ris);
            return ex;
        }else{
            ris.addAll(ex);
            return ris;
        }
    }
    
    /**
     * Controls all the formula's Clausola: if in the Clausola there isn't the pivot, than adds the Clausola in ex
     * and removes it from the formula.
     * @param p the pivot.
     * @return a treeSet that is the list of Clausola exonerated.
     */
    public TreeSet<Clausola> identifyExonerated(Letterale p){        
        TreeSet<Clausola> ex = new TreeSet<>();
        Iterator<Clausola> it = this.listOfC.iterator();
        
        while(it.hasNext()){
            Clausola tmp = it.next();
            if(!tmp.customContains(p)){
                ex.add(tmp);
                it.remove();
            }
        }
        
        return ex;
    }
    
    /**
     * The contract says that in this.listOfC there're only Clausola that contains the pivot p or nobody Clausola.
     * @param p
     * @return 
     */
    public TreeSet<Clausola> calculateResolvents(Letterale p){        
        //If there're no Clausola that contains the pivot.
        if(this.listOfC.isEmpty()) return this.listOfC;
        
        TreeSet<Clausola> res = new TreeSet<>(), listTrue = new TreeSet<>(), listFalse = new TreeSet<>();
        Iterator<Clausola> it = listOfC.iterator();
        Iterator<Letterale> it2;
        Clausola tmp = null;
        Letterale tmp2 = null;
        
        it = listOfC.iterator();
        
        while(it.hasNext()){
            tmp = it.next();
            it2 = tmp.listOfL.iterator();
            
            while(it2.hasNext()){
                tmp2 = it2.next();
                
                if(tmp2.customEquals(p)){
                    if(compare(tmp2.getIsNeg(), true) == 0){
                        it2.remove();
                        listTrue.add(tmp);
                    }else{
                        it2.remove();
                        listFalse.add(tmp);
                    }
                }
            }
            it.remove();
        }
        
        for(Clausola x : listTrue){
            for(Clausola y : listFalse){
                tmp = x.clauseUnion(y);
                res.add(tmp);
            }
        }
                
        return res;
    }
    
    /**
     * 
     * @return the list of all clauses
     */
    @Override
    public String toString(){
       String app = "{";
       
       for(Clausola c : listOfC){
           app = app.concat("{");
           for(Letterale l : c.listOfL){
               if(l.getIsNeg()) app = app.concat("-");
               app = app.concat(l.getLitteral());
           }
           app = app.concat("}");
       }
       
       return app.concat("}");
    }
}
