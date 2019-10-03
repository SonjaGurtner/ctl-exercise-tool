package jfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.CTL;
import model.Formula;
import model.State;
import model.Transition;

import java.util.List;

public class Controller {
    // important parts of the GUI
    private final int RADIUS = 25;
    @FXML
    private Button automaton4, automaton5, newF, checkF;
    @FXML
    private Label newA, formulaLabel, explanationLabel, counterLabel;
    @FXML
    private CheckBox explainBox;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView image;
    @FXML
    private Label labelS0_4, labelS1_4, labelS2_4, labelS3_4, labelS0_5, labelS1_5, labelS2_5, labelS3_5, labelS4_5;

    // helping fields
    @FXML
    private Circle c0, c1, c2, c3;
    private List<Circle> circles;
    private GraphicsContext gc;
    private Label[] stateLabels;
    private boolean generated;
    private boolean justChecked;

    // The core of the project: the Automaton including Formula, States, Transitions and Counter
    private CTL ctl;

    @FXML
    public void initialize() {
        ctl = new CTL();
        generated = false;
        justChecked = false;
        gc = canvas.getGraphicsContext2D();
        counterLabel.setText("Correct Answers: " + ctl.getCounter());
        stateLabels = new Label[]{labelS0_4, labelS1_4, labelS2_4, labelS3_4, labelS0_5, labelS1_5, labelS2_5,
                labelS3_5, labelS4_5};
    }

    /* generates a new Automaton with either 4 or 5 states */
    public void generateAutomaton4(ActionEvent e) {
        ctl.createTest();
        //ctl.createAutomaton(4); //todo remove
        drawAutomaton();
    }

    public void generateAutomaton5(ActionEvent e) {
        ctl.createAutomaton(5);
        drawAutomaton();
    }

    // generates a random formula and clears the explanation section
    public void generateFormula(ActionEvent e) {
        formulaLabel.setText("Formula: " + ctl.generateFormula());
        explanationLabel.setText("");
        explainBox.setSelected(false);
        image.imageProperty().set(null);
        if (justChecked) {
            drawAutomaton();
        }
    }

    // displays the explanation and image
    public void explainFormula() {
        if (!explainBox.isSelected()) {
            explanationLabel.setText("");
            image.imageProperty().set(null);
        } else if (!ctl.getFormula().equals("")) {
            Formula tempExplain = ctl.explainFormula();
            explanationLabel.setText(tempExplain.desc);
            image.setImage(new Image(tempExplain.img));
        }
    }

    /* checks for which states the formula holds
       counter gets increased for every correct guess (marked & correct / not marked and incorrect */
    public void checkFormula(ActionEvent e) {
        if (ctl.getFormula().equals("") || !generated || justChecked) {
            return;
        }

        justChecked = true;

        ctl.checkFormula();
        for (State state : ctl.getStates()) {
            if (state.isCorrect()) {
                gc.setFill(Color.GREEN);
                if (state.isSelected()) ctl.increaseCounter();
            } else {
                gc.setFill(Color.RED);
                if (!state.isSelected()) ctl.increaseCounter();
            }
            gc.fillOval(state.getX(), state.getY(), RADIUS, RADIUS);

        }
        counterLabel.setText("Correct Answers: " + ctl.getCounter());
    }

    // draws circles for the states to the corresponding coordinates and sets the correct labels
    private void drawAutomaton() {
        generated = true;
        justChecked = false;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);

        for (State state : ctl.getStates()) {
            gc.fillOval(state.getX(), state.getY(), RADIUS, RADIUS);
            for (Transition transition : state.getTransitions()) {
                State end = ctl.getState(transition.getEnd());
                System.out.println(state.getId() + " -> " + end.getId());       // TODO remove
                drawPath(state, end);
            }
        }

        for (int i = 0; i < stateLabels.length; i++) {
            if (ctl.hasFourStates()) {
                if (i < 4) stateLabels[i].setText(ctl.getLabel(i));
                else stateLabels[i].setText(" ");
            } else {
                if (i >= 4) stateLabels[i].setText(ctl.getLabel(i - 4));
                else stateLabels[i].setText(" ");
            }
        }

        System.out.println("----------");  // TODO remove
    }

    // marked states are colored blue, unmarked states black
    public void markState(MouseEvent mouseEvent) {
        if (justChecked) return;
        for (State state : ctl.getStates()) {
            if (mouseEvent.getX() >= state.getX() && mouseEvent.getX() <= state.getX() + RADIUS &&
                    mouseEvent.getY() >= state.getY() && mouseEvent.getY() <= state.getY() + RADIUS) {
                state.changeSelected();
                if (state.isSelected()) {
                    gc.setFill(Color.BLUE);
                    gc.fillOval(state.getX(), state.getY(), RADIUS, RADIUS);
                } else {
                    gc.setFill(Color.BLACK);
                    gc.fillOval(state.getX(), state.getY(), RADIUS, RADIUS);
                }
            }
        }
    }

    // visualizes the transitions by arrows
    private void drawPath(State start, State end) {
        int sx, sy, ex, ey;
        sx = sy = ex = ey = 0;

        if (start.getId() == end.getId()) {
            if (start.getId() == 1 || start.getId() == 2) {
                sx = start.getX() + RADIUS - 6;
                sy = start.getY() + 3;
            } else {
                if (start.getId() == 0 && !ctl.hasFourStates()) {
                    sx = start.getX() + 3;
                    sy = start.getY() - 10;
                } else {
                    sx = start.getX() - 10;
                    sy = start.getY() + 3;
                }
            }
            gc.strokeOval(sx, sy, 15, 15);
            // drawArrowHeadCircle(sx, sy, start.getId());

        } else {
            switch (start.getId()) {
                case 0:
                    switch (end.getId()) {
                        case 1:
                            sx = start.getX() + RADIUS;
                            sy = start.getY() + 8;
                            ex = end.getX();
                            ey = end.getY() + 8;
                            break;
                        case 2:
                            sx = start.getX() + RADIUS + 3;
                            sy = start.getY() + RADIUS;
                            ex = end.getX() + 3;
                            ey = end.getY();
                            break;
                        case 3:
                            sx = start.getX() + RADIUS - 8;
                            sy = start.getY() + RADIUS;
                            ex = end.getX() + RADIUS - 8;
                            ey = end.getY();
                            break;
                        case 4:
                            sx = start.getX() + 7;
                            sy = start.getY() + RADIUS - 7;
                            ex = end.getX() + RADIUS - 7;
                            ey = end.getY() + 3;
                            break;
                    }
                    break;

                case 1:
                    switch (end.getId()) {
                        case 0:
                            sx = start.getX();
                            sy = start.getY() + RADIUS - 8;
                            ex = end.getX() + RADIUS;
                            ey = end.getY() + RADIUS - 8;
                            break;
                        case 2:
                            sx = start.getX() + RADIUS - 8;
                            sy = start.getY() + RADIUS;
                            ex = end.getX() + RADIUS - 8;
                            ey = end.getY();
                            break;
                        case 3:
                            sx = start.getX();
                            sy = start.getY() + RADIUS + 3;
                            ex = end.getX() + RADIUS + 5;
                            ey = end.getY();
                            break;
                        case 4:

                            break;
                    }
                    break;

                case 2:
                    switch (end.getId()) {
                        case 0:
                            sx = start.getX() - 5;
                            sy = start.getY();
                            ex = end.getX() + RADIUS - 5;
                            ey = end.getY() + RADIUS;
                            break;
                        case 1:
                            sx = start.getX() + 8;
                            sy = start.getY();
                            ex = end.getX() + 8;
                            ey = end.getY() + RADIUS;
                            break;
                        case 3:
                            sx = start.getX();
                            sy = start.getY() + RADIUS - 8;
                            ex = end.getX() + RADIUS;
                            ey = end.getY() + RADIUS - 8;
                            break;
                        case 4:

                            break;
                    }
                    break;

                case 3:
                    switch (end.getId()) {
                        case 0:
                            sx = start.getX() + 8;
                            sy = start.getY();
                            ex = end.getX() + 8;
                            ey = end.getY() + RADIUS;
                            break;
                        case 1:
                            sx = start.getX() + RADIUS;
                            sy = start.getY() - 5;
                            ex = end.getX() - 7;
                            ey = end.getY() + RADIUS;
                            break;
                        case 2:
                            sx = start.getX() + RADIUS;
                            sy = start.getY() + 8;
                            ex = end.getX();
                            ey = end.getY() + 8;
                            break;
                        case 4:

                            break;
                    }
                    break;

                case 4:
                    switch (end.getId()) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                    }
                    break;
            }

            gc.strokeLine(sx, sy, ex, ey);
            drawArrowHead(sx, sy, ex, ey);
        }
    }

    // draws an arrow for showing the direction of the transition
    private void drawArrowHead(int sx, int sy, int ex, int ey) {
        int x1, x2, y1, y2;

        if (sy == ey) {
            x1 = x2 = (sx < ex ? ex - 5 : ex + 5);
            y1 = ey + 5;
            y2 = ey - 5;
        } else if (sx == ex) {
            x1 = ex - 5;
            x2 = ex + 5;
            y1 = y2 = (sy < ey ? ey - 5 : ey + 5);
        } else if (sx < ex) {
            x1 = ex - 7;
            x2 = ex - 2;
            y1 = sy > ey ? ey + 3 : ey - 3;
            y2 = sy > ey ? ey + 7 : ey - 7;
        } else {
            x1 = ex + 2;
            x2 = ex + 7;
            y1 = sy < ey ? ey - 7 : ey + 7;
            y2 = sy < ey ? ey - 3 : ey + 3;
        }

        gc.strokeLine(ex, ey, x1, y1);
        gc.strokeLine(ex, ey, x2, y2);
    }

    // transitions to the same state need special arrowheads
    private void drawArrowHeadCircle(int sx, int sy, int id) {
        int x1, x2, x3, y1, y2, y3;

        if (id == 0 && !ctl.hasFourStates()) {
            x1 = sx + 10;
            x2 = sx + 8;
            x3 = sx + 13;
            y1 = sy + 9;
            y2 = sy + 7;
            y3 = sy + 12;
        } else {
            if (sx < ctl.getState(id).getX()) {
                x1 = sx + 9;
                x2 = sx + 7;
                x3 = sx + 13;
                y1 = sy + 9;
                y2 = sy + 7;
                y3 = sy + 13;
            } else {
                x1 = sx + 3;
                x2 = sx + 5;
                x3 = sx + 7;
                y1 = sy + 9;
                y2 = sy + 5;
                y3 = sy + 13;
            }
        }

        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x1, y1, x3, y3);
    }
}