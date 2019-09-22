package model;

import java.util.Random;

// i chose an Enum for the Formula, because it is easier to handle and doesn't need to be initialised
public enum Formula {
    /* Explanations for the CTL Quantifiers, taken from http://fmv.jku.at/fm/fmslides.pdf (page 55 ff)
    and the link to the self drawn images for better understanding */
    AX("AXf -> formula f holds in all immediate successor states\ns|=AXf iff ∀π[π(0) = s⇒π(1)|= f]", "images/AX.png"),
    EX("EXf -> formula f holds in one immediate successor state\n" +
            "s|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]", "images/EX.png"),
    AG("AGf -> formula f holds always, in every state\n" +
            "s |= AGf iff ∀π[π(0) = s ⇒ ∀i[i ≤ |π| ⇒ π(i) |= f ]]", "images/AG.png"),
    EG("EGf -> in one future formula f holds all the time\n" +
            "s|=EGf iff ∃π[π(0) = s ∧ ∀i[i≤|π|⇒π(i)|= f]]", "images/EG.png"),
    AF("AFf -> formula f holds in all possible futures eventually\n" +
            "s|=AFf iff ∀π[π(0) = s⇒∃i[i≤|π| ∧ π(i)|= f]] ", "images/AF.png"),
    EF("EFf -> in one future formula f holds eventually \n" +
            "s|=EFf iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= f]] ", "images/EF.png"),
    AU("A[f U g] -> formula f always holds until finally g occurs\n" +
            "(g has to hold on all traces eventually)\n" +
            "s|=A[fUg] iff ∀π[π(0)=s⇒∃i[i≤|π| ∧ π(i)|= g ∧ ∀j[j < i⇒π(j)|=f]]]", "images/AU.png"),
    EU("E[f U g] -> formula f holds potentially until finally g occurs\n" +
            "(g has to hold on this trace eventually)\n" +
            "s|=E[fUg] iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= g ∧ ∀j[j < i⇒π(j)|=f]]]", "images/EU.png");

    Formula(String desc, String img) {
        this.desc = desc;
        this.img = img;
    }

    public final String desc;
    public final String img;

    // possibilities of CTL formulas, with mixed up order for better random selection
    public static final String[] FORMULAS = {"A[q U p]", "E[r U p]", "EXp", "E[r U q]", "AGq", "AXr", "EGp", "A[r U p]", "AFq",
            "A[p U q]", "AGr", "AFp", "EFq", "A[r U q]", "EGr", "A[q U r]", "EFr", "A[p U r]", "EXq", "E[q U r]", "AXq",
            "EGq", "EFp", "E[p U q]", "AGp", "AFr", "EXr", "E[q U p]", "AXp", "E[p U r]",};

    // possible labels
    public static final char[] LABELS = {'p', 'q', 'r'};

    // chooses a random formula
    public static String generateFormula() {
        Random r = new Random();
        return FORMULAS[r.nextInt(FORMULAS.length)];
    }

    // chooses a random label
    public static char generateLabel() {
        Random r = new Random();
        return LABELS[r.nextInt(LABELS.length)];
    }

    public static Formula explain(String formula) {
        if (formula.contains("X")) {
            if (formula.startsWith("A")) return AX;
            return EX;
        } else if (formula.contains("F")) {
            if (formula.startsWith("E")) return EF;
            return AF;
        } else if (formula.startsWith("AG")) return AG;
        else if (formula.startsWith("EG")) return EG;
        if (formula.contains("A")) return AU;
        return EU;
    }
}