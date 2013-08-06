/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.clemson.resolve.congruence_class_demo;

/**
 *
 * @author Mike
 */
public class RBES implements Comparable<RBES> {
    // comparisons need to be on object refs, or else make id field
    static int static_id = 1;
    int id;
    int numMembers;
    // Tag;

    RBES() {
        id = static_id++;
        numMembers = 0;
    }

    RegistryElement toRegistryElement() {
        return new RegistryElement(this);
    }

    public int compareTo(RBES t) {
        return id - t.id;
    }
    
}
