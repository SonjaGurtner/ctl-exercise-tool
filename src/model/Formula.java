package model;

import java.util.Random;

public enum Formula {

    INSTANCE;

    public final String explainAX = "AXf means that in all successor states f holds. \n s|=AXf iff ∀π[π(0) = s⇒π(1)|= f]";
    public final String explainEX = "EXf means that in one immediate successor state f holds. \n s|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]";
    public String explainAG;
    public String explainEG;
    public String explainAF;
    public String explainEF;
    public String explainAU;
    public String explainEU;
    private String[] formulas = {"AXp", "AXq", "AXr", "EXp", "EXq", "EXr"};

    public String generateFormula() {
        Random r = new Random();
        return formulas[r.nextInt(formulas.length)];
    }

}
