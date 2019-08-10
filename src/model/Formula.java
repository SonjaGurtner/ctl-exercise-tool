package model;

import java.util.Random;

public enum Formula {
    // i chose an Enum for the Formula, because it is easier to handle and doesn't need to be initialised
    INSTANCE;

    // Explanations for the CTL Quantifiers, taken from http://fmv.jku.at/fm/fmslides.pdf
    public final String AX = "AXf means that in all successor states f holds.\n s|=AXf iff ∀π[π(0) = s⇒π(1)|= f]";
    public final String EX = "EXf means that in one immediate successor state f holds.\n s|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]";
    public final String AG = "";
    public final String EG = "";
    public final String AF = "";
    public final String EF = "";
    public final String AU = "";
    public final String EU = "";
    //Some possibilities of CTL formulas
    public final String[] formulas = {"AXp", "AXq", "AXr", "EXp", "EXq", "EXr", "AGp", "AGq", "AGr", "EGp", "EGq", "EGr"};

    public String generateFormula() {
        Random r = new Random();
        return formulas[r.nextInt(formulas.length)];
    }
}
