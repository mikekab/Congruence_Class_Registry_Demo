/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.clemson.resolve.congruence_class_demo;

/**
 *
 * @author Mike
 */
public class RegistryElement implements Comparable<RegistryElement> {
    RBES RootOp; // Allows RBES's to be in Registry
    Op_Name_Type Op;
    CSC_Lst A_Lst;

    RegistryElement() {
        RootOp = null;
        Op = null;
        A_Lst = null;
    }

    RegistryElement(RBES rootOp) {
        RootOp = rootOp;
    }

    RegistryElement(Op_Name_Type op, CSC_Lst a_Lst) {
        Op = op;
        A_Lst = a_Lst;
    }

    RegistryElement(Op_Name_Type op) {
        Op = op;
    }

    public int compareTo(RegistryElement t) {
        int eval;
        if (RootOp != null) {
            if (t.RootOp != null) {
                return RootOp.compareTo(t.RootOp); // both are rootops
                // If both are RootOps, comparison ends since all other
                // fields are null
            } else {
                return -1; // only self is rootop
            }
        } else {
            if (t.RootOp != null) {
                return 1; // only other is rootop
            }
        }
        eval = Op.compareTo(t.Op);
        if (eval != 0) {
            return eval;
        }
        // Same operators
        if (A_Lst != null) {
            if (t.A_Lst != null) {
                return A_Lst.compareTo(t.A_Lst); // both hava args
            } else {
                return 1; // Only self has an arg list
            }
        } else {
            if (t.A_Lst != null) {
                return -1; // only other has arg list
            } else {
                return 0; // same op, no args at all
            }
        }
    }
    
}
