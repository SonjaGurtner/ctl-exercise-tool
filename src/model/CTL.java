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
        states.add(new State(0, 40, 20));
        states.add(new State(1, 40, 240));
        states.add(new State(2, 240, 20));
        states.add(new State(3, 240, 240));
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

    public String[] explainFormula() {
        if (formula.contains("X")) {
            if (formula.startsWith("A")) return Formula.INSTANCE.AX;
            return Formula.INSTANCE.EX;
        } else if (formula.contains("F")) {
            if (formula.startsWith("E")) return Formula.INSTANCE.EF;
            return Formula.INSTANCE.AF;
        } else if (formula.startsWith("AG")) return Formula.INSTANCE.AG;
        else if (formula.startsWith("EG")) return Formula.INSTANCE.EG;
        if (formula.contains("A")) return Formula.INSTANCE.AU;
        return Formula.INSTANCE.EU;
    }

    public void createAutomaton(int index) {
        for (State state : states) {
            state.generate(index);
        }
    }

    public String getLabel(int i) {
        return states.get(i).getLabels().toString();
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
