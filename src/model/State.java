package model;

import java.util.List;

/* TODO  */
public class State {

    private int id;
    private int x, y;
    private boolean selected;
    private List<Character> labels;
    private List<Transition> transitions;

    State(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.selected = false;
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

    public void changeSelected() {
        selected = !selected;
    }

    public boolean isSelected() {
        return selected;
    }
}