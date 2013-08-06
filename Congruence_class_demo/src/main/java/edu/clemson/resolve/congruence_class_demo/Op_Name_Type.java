package edu.clemson.resolve.congruence_class_demo;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * All operators used must be in the ArrayOfOpNames passed
 * to the constructor.
 * @author Mike
 */
public class Op_Name_Type implements Comparable<Op_Name_Type>{
    private static BiMap<String, Integer> OpMap;
    private int internalRep;
    private String name;
    
    public Op_Name_Type(String[] ArrayOfOpNames){
        OpMap = HashBiMap.create(ArrayOfOpNames.length);
        int i = 0;
        for(String op :ArrayOfOpNames){
            OpMap.put(op, i++);
        }
    }

    Op_Name_Type(int internalRep){
        this.internalRep = internalRep;
        this.name = OpMap.inverse().get(internalRep);
    }
    Op_Name_Type(String name){
        this.name = name;
        this.internalRep = OpMap.get(name);
    }
    public String getNameOf() {
        return name;
    }

    public int getInternalRepOf() {
        return internalRep;
    }

    public static String getNameFor(int id) {
        return OpMap.inverse().get(id);
    }

    public static int getInternalRepFor(String name) {
       return OpMap.get(name);
    }

    public int compareTo(Op_Name_Type t) {
        return internalRep - t.internalRep;
    }
    
    
    
}
