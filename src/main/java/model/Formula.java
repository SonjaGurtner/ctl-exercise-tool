package main.java.model;

import java.util.Random;

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
            "s|=E[fUg] iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= g ∧ ∀j[j < i⇒π(j)|=f]]]", "images/EU.png"),
    EFEG("EFf -> in one future formula f holds eventually \n" +
            "s|=EFf iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= f]] \n" +
            "EGf -> in one future formula f holds all the time \n" +
            "s|=EGf iff ∃π[π(0) = s ∧ ∀i[i≤|π|⇒π(i)|= f]]", "images/EG.png"),
    EFAG("EFf -> in one future formula f holds eventually \n" +
            "s|=EFf iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= f]] \n" +
            "AGf -> formula f holds always, in every state\n" +
            "s |= AGf iff ∀π[π(0) = s ⇒ ∀i[i ≤ |π| ⇒ π(i) |= f ]]", "images/AG.png"),
    AGEF("AGf -> formula f holds always, in every state\n" +
            "s |= AGf iff ∀π[π(0) = s ⇒ ∀i[i ≤ |π| ⇒ π(i) |= f ]]\n" +
            "EFf -> in one future formula f holds eventually \n" +
            "s|=EFf iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= f]]", "images/EF.png"),
    AGAF("AGf -> formula f holds always, in every state\n" +
            "s |= AGf iff ∀π[π(0) = s ⇒ ∀i[i ≤ |π| ⇒ π(i) |= f ]]\n" +
            "AFf -> formula f holds in all possible futures eventually\n" +
            "s|=AFf iff ∀π[π(0) = s⇒∃i[i≤|π| ∧ π(i)|= f]]", "images/AF.png"),
    EXEF("EXf -> formula f holds in one immediate successor state\n" +
            "s|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]\n" +
            "EFf -> in one future formula f holds eventually \n" +
            "s|=EFf iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= f]]", "images/EF.png");

    Formula(String desc, String img) {
        this.desc = desc;
        this.img = img;
    }

    public final String desc;
    public final String img;

    // possibilities of CTL formulas, with mixed up order for better random selection

    public static final String[] FORMULAS = {"AG(p -> EFq)"};

    //public static final String[] FORMULAS = {"AG(p -> AFq)", };

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

    // returns the corresponding explanation for the quantifier and operator of the formula
    public static Formula explain(String formula) {
        if (formula.contains("X")) {
            if (formula.length() > 3) return EXEF;
            else if (formula.startsWith("A")) return AX;
            return EX;
        } else if (formula.contains("U")) {
            return formula.charAt(0) == 'E' ? EU : AU;
        } else if (formula.length() == 3) {
            if (formula.contains("F")) {
                if (formula.contains("A")) return AF;
                else return EF;
            } else {
                if (formula.contains("A")) return AG;
                else return EG;
            }
        } else {
            if (formula.charAt(1) == 'F') {
                if (formula.charAt(2) == 'A') return EFAG;
                else return EFEG;
            } else {
                if (formula.charAt(8) == 'E') return AGEF;
                else return AGAF;
            }
        }
    }
}
