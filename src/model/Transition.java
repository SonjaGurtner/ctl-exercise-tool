package model;

public class Transition {
    private int start;
    private int end;

    public Transition(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
