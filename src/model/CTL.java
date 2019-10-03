package model;

import java.util.LinkedList;
import java.util.List;

public class CTL {

    // attributes of the CTL Tool
    private List<State> states;
    private String formula;
    private int counter;
    private boolean fourStates;

    // auxiliary fields for checking the formula
    private char quantifier, f;
    private boolean helpF;
    private boolean gFound, gJustFound;

    public CTL() {
        states = new LinkedList<>();
        states.add(new State(0, 40, 20));                       // first 4 states which are always used
        states.add(new State(1, 240, 20));
        states.add(new State(2, 240, 240));
        states.add(new State(3, 40, 240));
        states.add(new State(4, 40, 110));                     // fifth state
        formula = "";
        counter = 0;
        fourStates = false;
        helpF = gFound = gJustFound = false;
    }

    public void increaseCounter() {
        counter++;
    }

    public String generateFormula() {
        this.formula = Formula.generateFormula();
        return this.formula;
    }

    public Formula explainFormula() {
        return Formula.explain(formula);
    }

    public void createAutomaton(int index) {
        if (index == 5) {
            fourStates = false;
            states.get(0).setXY(135, 20);
            states.get(1).setXY(240, 110);
            states.get(2).setXY(200, 240);
            states.get(3).setXY(80, 240);
        } else {
            fourStates = true;
            states.get(0).setXY(40, 20);
            states.get(1).setXY(240, 20);
            states.get(2).setXY(240, 240);
            states.get(3).setXY(40, 240);
        }

        for (int i = 0; i < index; i++) {
            states.get(i).generate(index);
        }
    }

    public void checkFormula() {
        if (formula.contains("X")) checkX();
        else if (formula.contains("U")) checkU();
        else checkFG();
    }

    private void checkX() {
        f = formula.charAt(2);
        quantifier = formula.charAt(0);

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
        quantifier = formula.charAt(0);
        char operator = formula.charAt(1);
        f = formula.charAt(2);

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
                        if (checkRecursiveF(f, quantifier, getState(transition.getEnd()), startingState)) {
                            return true;
                        }
                    } else {
                        if (!checkRecursiveF(f, quantifier, getState(transition.getEnd()), startingState)) {
                            return helpF;
                        }
                    }
                }
            }
        }

        if (quantifier == 'E') {
            return state.getLabels().contains(f);
        } else {
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
        quantifier = formula.charAt(0);
        f = formula.charAt(2);
        char g = formula.charAt(6);

        for (State state : getStates()) {
            for (State s : getStates()) {
                s.setChecked(false);
            }
            gFound = gJustFound = false;
            state.setCorrect(checkRecursiveU(quantifier, f, g, state));
        }
    }

    private boolean checkRecursiveU(char quantifier, char f, char g, State state) {
        if (!state.isChecked()) {
            state.setChecked(true);

            if (state.getLabels().contains(g)) {
                gFound = true;
                gJustFound = true;
            } else {
                for (int i = 0; i < state.getTransitions().size(); i++) {
                    gFound = gJustFound = false;
                    if (quantifier == 'A') {
                        if (!checkRecursiveU(quantifier, f, g, getState(state.getTransitions().get(i).getEnd()))) {
                            return false;
                        }
                    } else {
                        if (checkRecursiveU(quantifier, f, g, getState(state.getTransitions().get(i).getEnd()))) {
                            break;
                        } else {
                            if (i == state.getTransitions().size() - 1) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        if (gJustFound) {
            gJustFound = false;
            return state.getLabels().contains(g);
        } else {
            if (gFound) return state.getLabels().contains(f);
            else return !state.getLabels().contains(f);
        }
    }

    public String getLabel(int i) {
        return states.get(i).getLabels().toString();
    }

    public State getState(int i) {
        return states.get(i);
    }

    public List<State> getStates() {
        return fourStates ? states.subList(0, 4) : states;
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

    // TODO remove
    public void createTest() {
        fourStates = true;
        states.get(0).setXY(40, 20);
        states.get(1).setXY(240, 20);
        states.get(2).setXY(240, 240);
        states.get(3).setXY(40, 240);

        states.get(0).setTransition(1, 2, 3);
        states.get(1).setTransition(0, 2, 3);
        states.get(2).setTransition(0, 1, 3);
        states.get(3).setTransition(0, 1, 2);
    }
}