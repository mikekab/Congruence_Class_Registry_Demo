package edu.clemson.resolve.congruence_class_demo;

import java.util.ArrayList;

/**
 * A list of RBES's that comprise an argument list
 */
public class CSC_Lst implements Comparable<CSC_Lst> {
    ArrayList<RBES> members;

    CSC_Lst() {
        members = new ArrayList<RBES>();
    }

    CSC_Lst(ArrayList<RBES> Members) {
        members = Members;
    }

    public int compareTo(CSC_Lst t) {
        int eval = members.size() - t.members.size();
        if (eval != 0) {
            return eval;
        }
        for (int i = 0; i < members.size(); ++i) {
            eval = members.get(i).compareTo(t.members.get(i));
            if (eval != 0) {
                return eval;
            }
        }
        return 0;
    }
    
}
