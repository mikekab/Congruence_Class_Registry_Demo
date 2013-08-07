package edu.clemson.resolve.congruence_class_demo;

/*
 * Demo contains methods that simplify hte use of the Congruence_Cls_Registry_Union_Find_Implementation
 * and a few example of its use.
 */
public class Demo {

    Congruence_Cls_Registry_Interface CCR;

    Demo() {
        CCR = new Congruence_Cls_Registry_Union_Find_Implementation<Op_Name_Type>();
    }

    void print(Object p) {
        System.out.println(p);
    }

    // Takes a CSV string and returns an array of Op_Name_Types
    Op_Name_Type[] getOpIdsForCSVString(String rawStr) {
        String[] rawStrArr = rawStr.split(",");
        Op_Name_Type[] rarr = new Op_Name_Type[rawStrArr.length];
        for (int i = 0; i < rarr.length; ++i) {
            rarr[i] = new Op_Name_Type(rawStrArr[i]);
        }
        return rarr;
    }

    // Adds an expression to the CongruenceMap if not already represented
    // returns that expression's root
    RBES addAnExpression(String expr) {
        Op_Name_Type[] opArray = getOpIdsForCSVString(expr);
        if (opArray.length > 1) {
            CSC_Lst argList = new CSC_Lst();
            // Add params to CongruenceMap
            for (int i = 1; i < opArray.length; ++i) {
                if (CCR.Would_be_Ext(null, opArray[i])) {
                    CCR.Add_CC_for(null, opArray[i]);
                }
                RBES rootOfArg = CCR.Get_RBEC_for(null, opArray[i]);
                assert (rootOfArg != null);
                CCR.Append_CC_of(rootOfArg, argList);
            }

            if (CCR.Would_be_Ext(argList, opArray[0])) {
                CCR.Add_CC_for(argList,
                        opArray[0]);
            }
            return CCR.Get_RBEC_for(argList, opArray[0]);
        } else {
            if (CCR.Would_be_Ext(null, opArray[0])) {
                CCR.Add_CC_for(null,
                        opArray[0]);
            }
            return CCR.Get_RBEC_for(null, opArray[0]);
        }
    }

    // get current RBES for a CSV expr in map
    RBES getRBESforCSVexpr(String expr) {
        Op_Name_Type[] opArray = getOpIdsForCSVString(expr);
        if (opArray.length > 1) {
            CSC_Lst argList = new CSC_Lst();
            for (int i = 1; i < opArray.length; ++i) {
                RBES rootOfArg = CCR.Get_RBEC_for(null, opArray[i]);
                assert (rootOfArg != null);
                CCR.Append_CC_of(rootOfArg, argList);
            }
            return CCR.Get_RBEC_for(argList, opArray[0]);
        } else {
            return CCR.Get_RBEC_for(null, opArray[0]);
        }
    }

    void run_example1() {
        String[] opNames = {"x", "y", "z", "+"};
        new Op_Name_Type(opNames);

        RBES e1 = addAnExpression("+,x,y");
        RBES e2 = addAnExpression("+,x,z");
        print(CCR.showRegistry("added x+y and x+z"));

       //CCR.Make_Congruent(e1, e2);
       //print(CCR.showRegistry("set x+y = x+z"));

        CCR.Make_Congruent(getRBESforCSVexpr("y"), getRBESforCSVexpr("z"));
        print(CCR.showRegistry("set y = z"));

        print(CCR.showRegistry("RBES id of x+y: " + getRBESforCSVexpr("+,x,y").id
                + " RBES id of x+z: " + getRBESforCSVexpr("+,x,z").id));

    }

    void run_example2() {
        CCR = new Congruence_Cls_Registry_Union_Find_Implementation();
        String[] opNames = {"a", "b", "c", "x", "y", "z", "+"};
        new Op_Name_Type(opNames);
         
        RBES e1 = addAnExpression("x");
        RBES e2 = addAnExpression("a");
        CCR.Make_Congruent(e1, e2); // x = a
        addAnExpression("z");
        e1 = addAnExpression("+,x,y");
        e2 = addAnExpression("c");
        CSC_Lst args = new CSC_Lst();
        args.members.add(e1);
        args.members.add(e2);
        if(CCR.Would_be_Ext(args, new Op_Name_Type("+"))){
            CCR.Add_CC_for(args, new Op_Name_Type("+"));
        }
        e1 = CCR.Get_RBEC_for(args, new Op_Name_Type("+"));
        e2 = CCR.Get_RBEC_for(null, new Op_Name_Type("z"));
        CCR.Make_Congruent(e1, e2); // z = (x+y)+c
        
        e1 = addAnExpression("y");
        e2 = addAnExpression("b");
        CCR.Make_Congruent(e1, e2); // y = b
        
        e1 = CCR.Get_RBEC_for(null, new Op_Name_Type("c"));
        e2 = addAnExpression("+,a,b");
        CCR.Make_Congruent(e1, e2); // c = a +b
   
        e1 = addAnExpression("+,x,y"); // will return preexisting rbec for x+y
        args = new CSC_Lst();
        args.members.add(e1);
        args.members.add(e1);
        if(CCR.Would_be_Ext(args, new Op_Name_Type("+"))){
            CCR.Add_CC_for(args, new Op_Name_Type("+"));  // adds (x+y)+(x+y) as an expr
        }
        e1 = CCR.Get_RBEC_for(args, new Op_Name_Type("+"));
        args = new CSC_Lst();
        args.members.add(e1);
        e2 =  addAnExpression("c");
        args.members.add(e2);
        if(CCR.Would_be_Ext(args, new Op_Name_Type("+"))){
            CCR.Add_CC_for(args, new Op_Name_Type("+")); // adds ((x+y)+(x+y))+c
        }
        e1 = CCR.Get_RBEC_for(args, new Op_Name_Type("+"));
        e2 = addAnExpression("+,z,c");
        
        print(CCR.showRegistry("Result of adding the following:\n"
                + "x=a\nz=(x+y)+c\ny=b\nc=a+b\n((x+y)+(x+y))+c Root: " + e1.id + " z+c Root: " + e2.id));
        
    }
    
    // trying differnt order in subsequent
    void run_example3() {
        CCR = new Congruence_Cls_Registry_Union_Find_Implementation();
        String[] opNames = {"a", "b", "c", "x", "y", "z", "+"};
        new Op_Name_Type(opNames);
         
        RBES e1 = addAnExpression("x");
        RBES e2 = addAnExpression("a");
        CCR.Make_Congruent(e1, e2); // x = a
        addAnExpression("z");
        e1 = addAnExpression("+,x,y");
        e2 = addAnExpression("c");
        CSC_Lst args = new CSC_Lst();
        args.members.add(e1);
        args.members.add(e2);
        if(CCR.Would_be_Ext(args, new Op_Name_Type("+"))){
            CCR.Add_CC_for(args, new Op_Name_Type("+"));
        }
        e1 = CCR.Get_RBEC_for(args, new Op_Name_Type("+"));
        e2 = CCR.Get_RBEC_for(null, new Op_Name_Type("z"));
        CCR.Make_Congruent(e1, e2); // z = (x+y)+c
      
        e1 = addAnExpression("y");
        e2 = addAnExpression("b");
        CCR.Make_Congruent(e1, e2); // y = b
        
        e1 = CCR.Get_RBEC_for(null, new Op_Name_Type("c"));
        e2 = addAnExpression("+,a,b");
        CCR.Make_Congruent(e1, e2); // c = a +b
   
        e1 = addAnExpression("+,x,y"); // will return preexisting rbec for x+y
        args = new CSC_Lst();
        args.members.add(e1);
        args.members.add(addAnExpression("c"));
        if(CCR.Would_be_Ext(args, new Op_Name_Type("+"))){
            CCR.Add_CC_for(args, new Op_Name_Type("+"));  // adds (x+y)+c as an expr
        }
        e1 = CCR.Get_RBEC_for(args, new Op_Name_Type("+"));
        args = new CSC_Lst();
        args.members.add(e1);    
        args.members.add(addAnExpression("+,x,y"));
        if(CCR.Would_be_Ext(args, new Op_Name_Type("+"))){
            CCR.Add_CC_for(args, new Op_Name_Type("+")); // adds ((x+y)+c)+(x+y)
        }
        e1 = CCR.Get_RBEC_for(args, new Op_Name_Type("+"));
        e2 = addAnExpression("+,z,c");
        
        print(CCR.showRegistry("Result of adding the following:\n"
                + "x=a\nz=(x+y)+c\ny=b\nc=a+b\n((x+y)+c)+(x+y) Root: " + e1.id + " z+c Root: " + e2.id));
        
    }
}
