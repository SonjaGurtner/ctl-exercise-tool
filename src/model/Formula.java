package model;

import java.util.Random;

public enum Formula {
    // i chose an Enum for the Formula, because it is easier to handle and doesn't need to be initialised
    INSTANCE;

    // TODO shuffle order of formulas and add some more
    /* Explanations for the CTL Quantifiers, taken from http://fmv.jku.at/fm/fmslides.pdf
    and the link to the self drawn images for better understanding */
    public final String[] AX = {"AXf -> formula f holds in all immediate successor states\n" +
            "s|=AXf iff ∀π[π(0) = s⇒π(1)|= f]", "model/AX.png"};
    public final String[] EX = {"EXf -> formula f holds in one immediate successor state\n" +
            "s|=EXf iff ∃π[π(0) = s ∧ π(1)|= f]", "model/EX.png"};
    public final String[] AG = {"AGf -> formula f holds always, in every state\n" +
            "s |= AGf iff ∀π[π(0) = s ⇒ ∀i[i ≤ |π| ⇒ π(i) |= f ]]", "model/AG.png"};
    public final String[] EG = {"EGf -> in one future formula f holds all the time\n" +
            "s|=EGf iff ∃π[π(0) = s ∧ ∀i[i≤|π|⇒π(i)|= f]]", "model/EG.png"};
    public final String[] AF = {"AFf -> formula f holds in all possible futures eventually\n" +
            "s|=AFf iff ∀π[π(0) = s⇒∃i[i≤|π| ∧ π(i)|= f]] ", "model/AF.png"};
    public final String[] EF = {"EFf -> in one future formula f holds eventually \n" +
            "s|=EFf iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= f]] ", "model/EF.png"};
    public final String[] AU = {"A[f U g] -> formula f always holds until finally g occurs\n" +
            "(g has to hold on all traces eventually)\n" +
            "s|=A[fUg] iff ∀π[π(0)=s⇒∃i[i≤|π| ∧ π(i)|= g ∧ ∀j[j < i⇒π(j)|=f]]]", "model/AU.png"};
    public final String[] EU = {"E[f U g] -> formula f holds potentially until finally g occurs\n" +
            "(g has to hold on this trace eventually)\n" +
            "s|=E[fUg] iff ∃π[π(0) = s ∧ ∃i[i≤|π| ∧ π(i)|= g ∧ ∀j[j < i⇒π(j)|=f]]]", "model/EU.png"};

    // possibilities of CTL formulas
    public final String[] formulas = {"AXp", "AXq", "AXr", "EXp", "EXq", "EXr", "AGp", "AGq", "AGr", "EGp", "EGq", "EGr",
            "AFp", "AFq", "AFr", "EFp", "EFq", "EFr", "A[p U q]", "A[p U r]", "A[q U p]", "A[q U r]", "A[r U p]",
            "A[r U q]", "E[p U q]", "E[p U r]", "E[q U p]", "E[q U r]", "E[r U p]", "E[r U q]"};

    // possible labels
    public final char[] labels = {'p', 'q', 'r'};

    // chooses a random formula from the array
    public String generateFormula() {
        Random r = new Random();
        return formulas[r.nextInt(formulas.length)];
    }

    public char generateLabel() {
        Random r = new Random();
        return labels[r.nextInt(labels.length)];
    }
}