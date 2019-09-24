package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class State {

    private int id;
    private int x, y;
    private boolean selected;
    private boolean correct;
    private boolean checked;
    private List<Character> labels;
    private List<Transition> transitions;

    State(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        selected = correct = checked = false;
        labels = new LinkedList<>();
        transitions = new LinkedList<>();
    }

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

        Collections.sort(labels);
    }

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    List<Character> getLabels() {
        return labels;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void changeSelected() {
        selected = !selected;
    }

    public boolean isSelected() {
        return selected;
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
}