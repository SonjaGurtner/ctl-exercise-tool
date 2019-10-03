package model;

public class Transition {
    private int start;
    private int end;

    Transition(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // getter for IDs of starting and ending state
    int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
