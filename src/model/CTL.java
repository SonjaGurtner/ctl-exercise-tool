package model;

import java.util.LinkedList;
import java.util.List;

public class CTL {

    //attributes of the CTL Tool
    private List<State> states;
    private String formula;
    private int counter;

    public CTL() {
        states = new LinkedList<>();
        states.add(new State(30, 20));
        states.add(new State(30, 200));
        states.add(new State(200, 20));
        states.add(new State(200, 200));
        formula = "";
        counter = 0;
    }

    public void increaseCounter() {
        counter++;
    }

    public String generateFormula() {
        this.formula = Formula.INSTANCE.generateFormula();
        return this.formula;
    }

    public String explainFormula() {
        if (formula.startsWith("AX")) return Formula.INSTANCE.AX;
        else if (formula.startsWith("EX")) return Formula.INSTANCE.EX;
        else if (formula.startsWith("AF")) return Formula.INSTANCE.AF;
        else if (formula.startsWith("EF")) return Formula.INSTANCE.EF;
        else if (formula.startsWith("AG")) return Formula.INSTANCE.AG;
        else if (formula.startsWith("EG")) return Formula.INSTANCE.EG;
        else if (formula.contains("U")) {
            if (formula.contains("A")) return Formula.INSTANCE.AU;
            else return Formula.INSTANCE.EU;
        } else return "Please generate a Formula first";
    }

    public List<State> getStates() {
        return states;
    }

    public String getFormula() {
        return formula;
    }

    public int getCounter() {
        return counter;
    }
}
