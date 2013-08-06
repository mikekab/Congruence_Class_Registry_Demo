package edu.clemson.resolve.congruence_class_demo;

public interface Congruence_Cls_Registry_Interface<Op_Name extends Op_Name_Type> {

    void Add_CC_for(CSC_Lst A_Lst, Op_Name Op);

    void Append_CC_of(RBES RBSet, CSC_Lst L);

    RBES Get_RBEC_for(CSC_Lst A_Lst, Op_Name Op);

    boolean Is_Eqv_SC(String S);

    void Make_Congruent(RBES S, RBES T);

    boolean Would_be_Ext(CSC_Lst A_Lst, Op_Name Op);

    String showRegistry(String title);

}
