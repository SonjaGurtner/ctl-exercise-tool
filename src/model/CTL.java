package model;

import java.util.List;

public enum CTL {
    //i chose an enum because it is easier to handle than a class
    INSTANCE;

    //attributes of the ctl tool
    private List<State> states;
    private String formula = "";
    private int counter = 0;

    public void increaseCounter() {
        this.counter++;
    }

    public String generateFormula() {
        this.formula = Formula.INSTANCE.generateFormula();
        return this.formula;
    }

    public String explainFormula() {
        if (this.formula.startsWith("AX")) return Formula.INSTANCE.explainAX;
        else if (this.formula.startsWith("EX")) return Formula.INSTANCE.explainEX;
        else if (this.formula.startsWith("AF")) return Formula.INSTANCE.explainEX;
        else if (this.formula.startsWith("EF")) return Formula.INSTANCE.explainEX;
        else if (this.formula.startsWith("AG")) return Formula.INSTANCE.explainEX;
        else if (this.formula.startsWith("EG")) return Formula.INSTANCE.explainEX;
        else if (this.formula.contains("U")) {
            if (this.formula.contains("A")) return Formula.INSTANCE.explainAU;
            else return Formula.INSTANCE.explainEU;
        } else return "Please generate a Formula first";
    }
}
