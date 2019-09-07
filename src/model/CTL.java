package model;

import java.util.LinkedList;
import java.util.List;

public class CTL {

    //attributes of the CTL Tool
    private List<State> states;
    private String formula;
    private int counter;
    private boolean fourStates;

    public CTL() {
        states = new LinkedList<>();
        states.add(new State(0, 40, 20));                       // coordinates for automaton with 4 states
        states.add(new State(1, 240, 20));
        states.add(new State(2, 40, 240));
        states.add(new State(3, 240, 240));
        states.add(new State(0, 135, 20));                      // coordinates for automaton with 5 states
        states.add(new State(1, 240, 110));
        states.add(new State(2, 200, 240));
        states.add(new State(3, 80, 240));
        states.add(new State(4, 40, 110));
        formula = "";
        counter = 0;
        fourStates = false;
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
        if (index == 4) {
            fourStates = true;
            for (int i = 0; i < index; i++) {
                states.get(i).generate(index);
            }
        } else {
            fourStates = false;
            for (int i = 4; i < states.size(); i++) {
                states.get(i).generate(index);
            }
        }
    }

    public void checkFormula() {
        if (formula.contains("X")) checkX();
        else if (formula.contains("F")) checkF();
        else if (formula.contains("G")) checkG();
        else checkU();
    }

    private void checkX() {
        char f = formula.split("X")[1].charAt(0);

        if (formula.startsWith("E")) {
            for (State state : getStates()) {
                for (Transition transition : state.getTransitions()) {
                    if (getState(transition.getEnd()).getLabels().contains(f)) {
                        state.setCorrect();
                        break;
                    }
                }
            }
        } else {
            for (State state : getStates()) {
                for (Transition transition : state.getTransitions()) {
                    if (!getState(transition.getEnd()).getLabels().contains(f)) {
                        break;
                    }
                    state.setCorrect();
                }
            }
        }
    }

    private void checkG() {
        // TODO
    }

    private void checkF() {
        // TODO
    }

    private void checkU() {
        // TODO
    }

    public String getLabel(int i) {
        return states.get(i).getLabels().toString();
    }

    public State getState(int i) {
        return fourStates ? states.get(i) : states.get(i + 4);
    }

    public List<State> getStates() {
        return fourStates ? states.subList(0, 4) : states.subList(4, states.size());
    }

    public String getFormula() {
        return formula;
    }

    public int getCounter() {
        return counter;
    }

    public boolean hasFourStates() {
        return fourStates;
    }
}