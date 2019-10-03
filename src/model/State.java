package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class State {

    // attributes and auxiliary booleans for formula checking
    private int id;
    private int x, y;
    private List<Character> labels;
    private List<Transition> transitions;
    private boolean selected, correct, checked;

    State(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        selected = correct = checked = false;
        labels = new LinkedList<>();
        transitions = new LinkedList<>();
    }

    // generates a new Automaton by generating new Labels and Transitions
    void generate(int index) {
        selected = correct = checked = false;
        transitions.clear();
        labels.clear();

        Random r = new Random();                             // random number of transitions
        Random r2 = new Random();                            // random ending state of transition

        for (int i = 0; i < r.nextInt(index); i++) {
            int i2 = r2.nextInt(index);
            if (transitions.stream().filter(t -> t.getEnd() == i2).collect(Collectors.toList()).size() == 0) {
                transitions.add(new Transition(id, i2));
            }
        }

        for (int i = 1; i <= r.nextInt(2) + 1; i++) {
            char l = Formula.generateLabel();
            if (!labels.contains(l)) {
                labels.add(l);
            }
        }

        Collections.sort(labels);                            // sorting the labels alphabetically
    }

    // helping function for checking corner cases
    boolean checkTransitionsF(State state) {
        List<Transition> listT = state.getTransitions();
        if (listT.size() == 0) return true;
        else {
            for (Transition t : listT) {
                if (t.getEnd() == t.getStart()) return true;
            }
        }
        return false;
    }

    /* getter and setter for the fields */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    List<Character> getLabels() {
        return labels;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public boolean isSelected() {
        return selected;
    }

    public void changeSelected() {
        selected = !selected;
    }

    public int getId() {
        return id;
    }

    public boolean isCorrect() {
        return correct;
    }

    void setCorrect(boolean correct) {
        this.correct = correct;
    }

    boolean isChecked() {
        return checked;
    }

    void setChecked(boolean checked) {
        this.checked = checked;
    }

    //TODO REMOVE BOTH
    void setTransition(int e) {
        selected = correct = checked = false;
        labels.clear();
        Random r2 = new Random();                            // random ending state of transition
        for (int i = 1; i <= r2.nextInt(2) + 1; i++) {
            char l = Formula.generateLabel();
            if (!labels.contains(l)) {
                labels.add(l);
            }
            transitions.clear();
            transitions = new LinkedList<>();
            transitions.add(new Transition(id, e));
        }
    }

    void setTransition(int e1, int e2) {
        selected = correct = checked = false;
        labels.clear();
        Random r2 = new Random();                            // random ending state of transition
        for (int i = 1; i <= r2.nextInt(2) + 1; i++) {
            char l = Formula.generateLabel();
            if (!labels.contains(l)) {
                labels.add(l);
            }

            transitions.clear();
            transitions = new LinkedList<>();
            transitions.add(new Transition(id, e1));
            transitions.add(new Transition(id, e2));
        }
    }

    void setTransition(int e1, int e2, int e3) {
        selected = correct = checked = false;
        Random r2 = new Random();                            // random ending state of transition
        labels.clear();
        for (int i = 1; i <= r2.nextInt(2) + 1; i++) {
            char l = Formula.generateLabel();
            if (!labels.contains(l)) {
                labels.add(l);
            }
            transitions.clear();
            transitions = new LinkedList<>();
            transitions.add(new Transition(id, e1));
            transitions.add(new Transition(id, e2));
            transitions.add(new Transition(id, e3));
        }
    }

    void setTransition(int e1, int e2, int e3, int e4) {
        selected = correct = checked = false;
        Random r2 = new Random();                            // random ending state of transition
        labels.clear();
        for (int i = 1; i <= r2.nextInt(2) + 1; i++) {
            char l = Formula.generateLabel();
            if (!labels.contains(l)) {
                labels.add(l);
            }
            transitions.clear();
            transitions = new LinkedList<>();
            transitions.add(new Transition(id, e1));
            transitions.add(new Transition(id, e2));
            transitions.add(new Transition(id, e3));
            transitions.add(new Transition(id, e4));
        }
    }
}