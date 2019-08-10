package model;

import java.util.List;

/* TODO  */
public class State {

    private int id;
    private int x, y;
    private List<Character> labels;
    private List<Transition> transitions;

    State(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Character> getLabels() {
        return labels;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}