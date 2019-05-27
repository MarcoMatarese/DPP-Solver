/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import static java.lang.Boolean.compare;

/**
 *
 * @author marcomatarese
 */

public class Letterale implements Comparable<Letterale>{
    private String litteral;
    private boolean isNeg;
    
    /**
     * @Constructor
     * @param l String representing the litteral
     * @param isN boolean indicating whether the literal is denied
     */
    public Letterale(String l, boolean isN){
        litteral = l;
        isNeg = isN;
    }
    
    /*
    * get & set methods
    */
    public void setLitteral(String l){
        litteral = l;
    }
    public String getLitteral(){
        return litteral;
    }
    public void setIsNeg(boolean cond){
        isNeg = cond;
    }
    public boolean getIsNeg(){
        return isNeg;
    }
    
    /**
     * Two Letterale are equals if they're the same litteral and the same value of verity.
     * @param x object to compare with this.
     * @return true if this and x has the same propositional letter, false otherwise.
     */
    @Override
    public boolean equals(Object x){
        if(!(x instanceof Letterale)) return false;
        Letterale l = (Letterale) x;
        return ((this.customEquals(l) && (compare(this.isNeg,l.isNeg) == 0)));
    }
    
      /**
     * Compares only the litterals.
     * @param l the Letterale to compare.
     * @return true if this and l have the same litteral, false otherwise.
     */
    public boolean customEquals(Letterale l){
        return this.litteral.equals(l.litteral);
    }
        
    /**
     * 
     * @param other
     * @return 
     */
    public int compareTo(Letterale other){
        if(!(other instanceof Letterale)) throw new ClassCastException();
        Letterale l = (Letterale) other;
        int boolCompare = compare(this.isNeg, l.isNeg);

        //If they're totally the same.
        if(this.equals(l)) return 0;
        //If thet're totally different.
        else if(!this.customEquals(l) && boolCompare != 0)
            return this.getLitteral().compareTo(l.getLitteral());
        //If thet've the same litteral but different value of verity.
        else if(this.customEquals(l)){
            if(l.isNeg) return -1;
            else return 1;
        }
        //If thet've the same value of verity but different litteral.
        else return this.getLitteral().compareTo(l.getLitteral());
    }
    
    /**
     * 
     * @return "-litteral" if isNeg == true, "litteral" otherwise.
     */
    @Override
    public String toString(){
        if(this.isNeg) return "-" + litteral;
        else return litteral;
    }
}
