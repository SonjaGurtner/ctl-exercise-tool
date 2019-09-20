package model;

import java.util.LinkedList;
import java.util.List;

public class CTL {

    // attributes of the CTL Tool
    private List<State> states;
    private String formula;
    private int counter;
    private boolean fourStates;

    private boolean helpF;

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
        helpF = false;
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
        else if (formula.contains("U")) checkU();
        else checkFG();
    }

    private void checkX() {
        char f = formula.charAt(2);
        char quantifier = formula.charAt(0);

        for (State state : getStates()) {
            for (Transition transition : state.getTransitions()) {
                if (quantifier == 'E') {
                    if (getState(transition.getEnd()).getLabels().contains(f)) {
                        state.setCorrect(true);
                        break;
                    }
                } else {
                    if (!getState(transition.getEnd()).getLabels().contains(f))
                        state.setCorrect(false);
                    break;
                }
            }
        }
    }

    private void checkFG() {
        char quantifier = formula.charAt(0);
        char operator = formula.charAt(1);
        char f = formula.charAt(2);

        for (State state : getStates()) {
            for (State s : getStates()) {
                s.setChecked(false);
            }

            if (operator == 'G') state.setCorrect(checkRecursiveG(f, quantifier, state));
            else state.setCorrect(checkRecursiveF(f, quantifier, state, state.getId()));

        }
    }

    private boolean checkRecursiveG(char f, char quantifier, State state) {
        if (!state.isChecked()) {
            state.setChecked(true);

            for (int i = 0; i < state.getTransitions().size(); i++) {
                if (quantifier == 'A') {
                    if (!checkRecursiveG(f, quantifier, getState(state.getTransitions().get(i).getEnd()))) return false;
                } else {
                    if (checkRecursiveG(f, quantifier, getState(state.getTransitions().get(i).getEnd()))) {
                        break;
                    } else {
                        if (i == state.getTransitions().size() - 1) {
                            return false;
                        }
                    }
                }
            }
        }

        return state.getLabels().contains(f);
    }

    private boolean checkRecursiveF(char f, char quantifier, State state, int startingState) {
        if (!state.isChecked()) {
            state.setChecked(true);

            if (!state.getLabels().contains(f) || state.getId() == startingState) {
                helpF = false;
                for (Transition transition : state.getTransitions()) {
                    helpF = false;
                    if (quantifier == 'E') {
                        if (checkRecursiveF(f, quantifier, getState(transition.getEnd()), startingState))
                            return true;
                    } else {
                        if (!checkRecursiveF(f, quantifier, getState(transition.getEnd()), startingState)) {
                            return helpF;
                        }
                    }
                }
            }
        }

        if (quantifier == 'E') return state.getLabels().contains(f);

        else {
            if (state.getLabels().contains(f)) {
                if (state.getId() == startingState) {
                    helpF = helpF || state.checkTransitionsF(state);
                } else {
                    helpF = true;
                }
            }
            return helpF;
        }
    }

    private void checkU() {
        char quantifier = formula.charAt(0);
        char f = formula.charAt(2);
        char g = formula.charAt(4);

        for (State state : getStates()) {
            for (State s : getStates()) {
                s.setChecked(false);
            }

            state.setCorrect(checkRecursiveU(quantifier, f, g, state));
        }
    }

    private boolean checkRecursiveU(char quantifier, char f, char g, State state) {
        if (!state.isChecked()) {
            state.setChecked(true);


        }
        return true;
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

    public List<Transition> getAllTransitions() {
        List<Transition> allTransitions = new LinkedList<>();
        for (State state : getStates()) {
            allTransitions.addAll(state.getTransitions());
        }
        return allTransitions;
    }
}