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
    private char quantifier, quantifier2, f;
    private boolean helpF, gFound, gJustFound;
    private List<State> tree;

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
        tree = new LinkedList<>();
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
        if (formula.length() == 3) {
            if (formula.contains("X")) checkX();
            else checkFG();
        } else {
            if (formula.contains("U")) checkU();
            else if (formula.charAt(1) == 'F') checkNestedF();
            else if (formula.charAt(1) == 'X') checkNestedX();
            else checkNestedG();
        }
    }

    private void checkX() {
        f = formula.charAt(2);
        quantifier = formula.charAt(0);

        for (State state : getStates()) {
            boolean help = true;
            for (Transition transition : state.getTransitions()) {
                if (quantifier == 'E') {
                    if (getState(transition.getEnd()).getLabels().contains(f)) {
                        state.setCorrect(true);
                        break;
                    }
                } else {
                    if (!getState(transition.getEnd()).getLabels().contains(f)) {
                        help = false;
                        break;
                    }
                }
            }
            if (quantifier == 'A' && help) state.setCorrect(true);
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
            state.setCorrect(checkRecursiveU(f, g, quantifier, state));
        }
    }

    private boolean checkRecursiveU(char f, char g, char quantifier, State state) {
        if (!state.isChecked()) {
            state.setChecked(true);

            if (state.getLabels().contains(g)) {
                gFound = true;
                gJustFound = true;
            } else {
                for (int i = 0; i < state.getTransitions().size(); i++) {
                    gFound = gJustFound = false;
                    if (quantifier == 'A') {
                        if (!checkRecursiveU(f, g, quantifier, getState(state.getTransitions().get(i).getEnd()))) {
                            return false;
                        }
                    } else {
                        if (checkRecursiveU(f, g, quantifier, getState(state.getTransitions().get(i).getEnd()))) {
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
            if (gFound) {
                return state.getLabels().contains(f);
            } else {
                if (quantifier == 'A') {
                    return !state.getLabels().contains(f);
                } else {
                    return false;
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

    private boolean checkRecursiveG(char f, char quantifier, State state) {
        if (!state.isChecked()) {
            state.setChecked(true);

            for (int i = 0; i < state.getTransitions().size(); i++) {
                if (quantifier == 'A') {
                    if (!checkRecursiveG(f, quantifier, getState(state.getTransitions().get(i).getEnd())))
                        return false;
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

    private void checkNestedF() {
        quantifier2 = formula.charAt(2);
        f = formula.charAt(4);

        for (State state : getStates()) {
            for (State s : getStates()) {
                s.setChecked(false);
                s.setTreeVisited(false);
            }

            tree.clear();
            makeTreeToList(state);

            for (State ts : tree) {
                for (State t : tree) {
                    t.setChecked(false);
                }

                if (checkRecursiveG(f, quantifier2, ts)) {
                    System.out.println(ts.getId());
                    state.setCorrect(true);
                    break;
                }
            }
        }
    }

    private void checkNestedG() {
        quantifier2 = formula.charAt(8);
        f = formula.charAt(3);
        char g = formula.charAt(10);

        for (State state : getStates()) {
            for (State s : getStates()) {
                s.setChecked(false);
                s.setTreeVisited(false);
            }

            tree.clear();
            makeTreeToList(state);
            boolean temp = true;
            for (State ts : tree) {
                for (State t : tree) {
                    t.setChecked(false);
                }

                if (ts.getLabels().contains(f)) {
                    if (!checkRecursiveF(g, quantifier2, ts, ts.getId())) {
                        temp = false;
                        break;
                    }
                }
            }
            state.setCorrect(temp);
        }
    }

    private void checkNestedX() {
        f = formula.charAt(4);
        for (State state : getStates()) {

            for (State s : getStates()) {
                s.setChecked(false);
            }

            for (Transition transition : state.getTransitions()) {
                int end = transition.getEnd();
                if (checkRecursiveF(f, 'E', getState(end), end)) {
                    state.setCorrect(true);
                    break;
                }
            }
        }
    }

    private void makeTreeToList(State state) {
        if (!tree.contains(state)) tree.add(state);

        if (!state.isTreeVisited()) {
            state.setTreeVisited(true);

            List<Transition> transitions = state.getTransitions();
            for (Transition t : transitions) {
                if (!tree.contains(getState(t.getEnd()))) tree.add(getState(t.getEnd()));
            }
            for (Transition t : transitions) {
                makeTreeToList(getState(t.getEnd()));
            }
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
}