package model;

import java.util.Random;

public enum Formula {
    // i chose an Enum for the Formula, because it is easier to handle and doesn't need to be initialised
    INSTANCE;

    /* Explanations for the CTL Quantifiers, taken from http://fmv.jku.at/fm/fmslides.pdf
    and the link to the self drawn images for better understanding */
    public final String[] AX = {"AXf means that in all immediate successor states\nformula f has to hold.\ns|=AXf iff ∀π[π(0) = s⇒π(1)|= f]", "model/AX.png"};
    public final String[] EX = {"EXf means that in one immediate successor state\nformula f has to hold.\ns|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]", "model/EX.png"};
    public final String[] AG = {"", "model/AG.png"};
    public final String[] EG = {"", "model/EG.png"};
    public final String[] AF = {"", "model/AF.png"};
    public final String[] EF = {"", "model/EF.png"};
    public final String[] AU = {"A[f U g] means that ", "model/AU.png"};
    public final String[] EU = {"E[f U g] means that ", "model/EU.png"};

    // some possibilities of CTL formulas
    public final String[] formulas = {"AXp", "AXq", "AXr", "EXp", "EXq", "EXr", "AGp", "AGq", "AGr", "EGp", "EGq", "EGr"};

    // chooses a random formula from the array
    public String generateFormula() {
        Random r = new Random();
        return formulas[r.nextInt(formulas.length)];
    }
}