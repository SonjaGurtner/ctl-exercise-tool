package model;

import java.util.Random;

public enum Formula {
    // i chose an Enum for the Formula, because it is easier to handle and doesn't need to be initialised
    INSTANCE;

    // TODO finish explanations and add more formulas
    /* Explanations for the CTL Quantifiers, taken from http://fmv.jku.at/fm/fmslides.pdf
    and the link to the self drawn images for better understanding */
    public final String[] AX = {"AXf means that in all immediate successor states\nformula f has to hold.\n" +
            "s|=AXf iff ∀π[π(0) = s⇒π(1)|= f]", "model/AX.png"};
    public final String[] EX = {"EXf means that in one immediate successor state\nformula f has to hold.\n" +
            "s|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]", "model/EX.png"};
    public final String[] AG = {"AGf = 'Always Globally f' means that formula f holds\nalways, in every State. " +
            "\ns |= AGf iff ∀π[π(0) = s ⇒ ∀i[i ≤ |π| ⇒ π(i) |= f ]]", "model/AG.png"};
    public final String[] EG = {"TODO", "model/EG.png"};
    public final String[] AF = {"TODO", "model/AF.png"};
    public final String[] EF = {"TODO", "model/EF.png"};
    public final String[] AU = {"TODO", "model/AU.png"};
    public final String[] EU = {"TODO", "model/EU.png"};

    // possibilities of CTL formulas
    public final String[] formulas = {"AXp", "AXq", "AXr", "EXp", "EXq", "EXr", "AGp", "AGq", "AGr", "EGp", "EGq", "EGr",
            "AFp", "AFq", "AFr", "EFp", "EFq", "EFr", "A[p U q]", "A[p U r]", "A[q U p]", "A[q U r]", "A[r U p]",
            "A[r U q]", "E[p U q]", "E[p U r]", "E[q U p]", "E[q U r]", "E[r U p]", "E[r U q]"};

    // chooses a random formula from the array
    public String generateFormula() {
        Random r = new Random();
        return formulas[r.nextInt(formulas.length)];
    }
}