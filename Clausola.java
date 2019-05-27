/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;
import static java.lang.Boolean.*;
import java.util.*;

/**
 *
 * @author marcomatarese
 */

public class Clausola implements Comparable<Clausola>{
    protected TreeSet<Letterale> listOfL;
    
    /**
     * Constructor.
     */
    public Clausola(){
        listOfL = new TreeSet<>();
    }
    
    /**
     * A Clausola is empty if her list of litterals exists and is empty.
     * @return true is this.listOfl.isEmpty(), false otherwise.
     */
    public boolean isEmptyClause(){
        if(listOfL == null) return false;
        return listOfL.isEmpty();
    }
    
    /**
     * Controls that this has the Letterale other in his listOfL.
     * @param other the Letterale to search in this.listOfL.
     * @return true if other is into this.listOfL, false otherwise.
     */
    public boolean hasThisLetterale(Letterale other){
        for(Letterale x : this.listOfL){
                if(x.equals(other)) return true;
        }
        return false;
    }
    
    /**
     * Controls if in the Clausola there's a tautology (A V -A).
     * @return true if this is a tautology or is empty, false otherwise
     */
    public boolean isATautology(){
        for(Letterale x : this.listOfL){
            for(Letterale y : this.listOfL){
                //If x and y have the same litteral but different value of verity.
                if(x.customEquals(y) && compare(x.getIsNeg(), y.getIsNeg()) != 0) return true;
            }
        }
        return false;
    }
    
    /**
     * Controls what is the Clausola with the greater number of litterales and adds the other Clausola's litterals.
     * @param other the other Clausola.
     * @return a Clausola which is the union of this and other.
     */
    public Clausola clauseUnion(Clausola other){
        if(this.listOfL.isEmpty()) return other;
        else if(other.listOfL.isEmpty()) return this;
        
        if(this.listOfL.size() < other.listOfL.size()){
            other.listOfL.addAll(this.listOfL);
            return other;
        }else{
            this.listOfL.addAll(other.listOfL);
            return this;
        }
    }
    
    /**
     * Removes the pivot from both the Clausola and return clauseUnion. 
     * The remove method of TreeSet is based on equals, that has been modified! Find a solution!!!
     * @param other the other Clausola.
     * @param p the Letterale to resolve.
     * @return this\{p} U other\{-p}
     */
    public Clausola resolvent(Clausola other, Letterale p){
        this.customRemove(p);
        other.customRemove(p);
        return this.clauseUnion(other);
    }
    
    /**
     * Remove from this the Letterale that has the same litteral with p.
     */
    public void customRemove(Letterale p){
        Iterator<Letterale> it = this.listOfL.iterator();
        
        while(it.hasNext()){
            if(it.next().customEquals(p)) it.remove();
        }
    }
    
    /**
     * Search in this.listOfL for a Letterale that "customequals" with p.
     * @param p the other Letterale.
     * @return true if finds a Letterale with the same litteral of p, false otherwise.
     */
    public boolean customContains(Letterale p){
        for(Letterale x : this.listOfL){
            if(x.customEquals(p)) return true;
        }
        return false;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        String ret = "{";
        for(Letterale x : this.listOfL){
            if(x.getIsNeg()) ret = ret.concat("-");
            ret = ret.concat(x.getLitteral());
        }
        return ret.concat("}");
    }
    
    /**
     * 
     * @param x the other Clausola.
     * @return true if this and x have just the same litterals.
     */
    @Override
    public boolean equals(Object x){
        if(!(x instanceof Clausola)) return false;
        Clausola c = (Clausola) x;
        
        //If the list's sizes are different, the lists are different.
        if(this.listOfL.size() != c.listOfL.size()) return false;
        //The list's sizes are equal.
        //When it finds a Letterale present in x but not present in this, return false.
        for(Letterale y : c.listOfL){
            if(!(this.hasThisLetterale(y))) return false;
        }
        //The list's sizes are equal and all the x's Letterale are present also in this.
        return true;
    }
    
    /**
     * 
     * @param o the other Clausola.
     * @return 0 if this.equals(o) == 0, 1 if this is greater than 0, -1 otherwise.
     */
    @Override
    public int compareTo(Clausola o){
        if(!(o instanceof Clausola)) throw new ClassCastException();
        
        Clausola x = (Clausola) o;
        Iterator<Letterale> it1 = this.listOfL.iterator();
        Iterator<Letterale> it2 = x.listOfL.iterator();
        
        //If they've the same size.
        if(this.listOfL.size() == x.listOfL.size()){
            //If they're equal for equals, they're also equal for compareTo.
            if(this.equals(x)) return 0;
            
            //Order by the first different Letterale that find.
            while(it1.hasNext()){
                Letterale l1 = it1.next();
                Letterale l2 = it2.next();
                if(!(l1.equals(l2))) return l1.compareTo(l2);
            }
        }
        //If this is smaller than o.
        if(this.listOfL.size() < o.listOfL.size()) return -1;
        //If this is greater than o.
        else return 1;
    }
}
