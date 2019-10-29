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
        ctl.createAutomaton(4);
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
                sx = start.getX() + RADIUS - 4;
                sy = start.getY() + 3;
            } else {
                if (start.getId() == 0 && !ctl.hasFourStates()) {
                    sx = start.getX() + 5;
                    sy = start.getY() - 10;
                } else {
                    sx = start.getX() - 10;
                    sy = start.getY() + 3;
                }
            }
            gc.strokeOval(sx, sy, 15, 15);
            drawArrowHeadCircle(sx, sy, start.getId());

        } else {
            switch (start.getId()) {
                case 0:
                    switch (end.getId()) {
                        case 1:
                            sx = ctl.hasFourStates() ? start.getX() + RADIUS : start.getX() + RADIUS + 1;
                            sy = ctl.hasFourStates() ? start.getY() + 8 : start.getY() + 10;
                            ex = ctl.hasFourStates() ? end.getX() : end.getX() + 5;
                            ey = ctl.hasFourStates() ? end.getY() + 8 : end.getY() - 3;
                            break;
                        case 2:
                            sx = ctl.hasFourStates() ? start.getX() + RADIUS + 3 : start.getX() + RADIUS - 3;
                            sy = ctl.hasFourStates() ? start.getY() + RADIUS : start.getY() + RADIUS + 3;
                            ex = ctl.hasFourStates() ? end.getX() + 3 : end.getX() + 15;
                            ey = end.getY() - 5;
                            break;
                        case 3:
                            sx = ctl.hasFourStates() ? start.getX() + RADIUS - 8 : start.getX() + 10;
                            sy = ctl.hasFourStates() ? start.getY() + RADIUS : start.getY() + RADIUS + 3;
                            ex = end.getX() + RADIUS - 8;
                            ey = ctl.hasFourStates() ? end.getY() : end.getY() - 5;
                            break;
                        case 4:
                            sx = start.getX() - 2;
                            sy = start.getY() + RADIUS - 5;
                            ex = end.getX() + RADIUS - 3;
                            ey = end.getY();
                            break;
                    }
                    break;

                case 1:
                    switch (end.getId()) {
                        case 0:
                            sx = ctl.hasFourStates() ? start.getX() + 3 : start.getX() - 3;
                            sy = ctl.hasFourStates() ? start.getY() + RADIUS - 8 : start.getY();
                            ex = ctl.hasFourStates() ? end.getX() + RADIUS : end.getX() + RADIUS + 3;
                            ey = ctl.hasFourStates() ? end.getY() + RADIUS - 8 : end.getY() + RADIUS - 3;
                            break;
                        case 2:
                            sx = start.getX() + RADIUS - 8;
                            sy = ctl.hasFourStates() ? start.getY() + RADIUS : start.getY() + RADIUS + 5;
                            ex = ctl.hasFourStates() ? end.getX() + RADIUS - 8 : end.getX() + RADIUS - 3;
                            ey = end.getY();
                            break;
                        case 3:
                            sx = ctl.hasFourStates() ? start.getX() : start.getX() + 3;
                            sy = start.getY() + RADIUS + 3;
                            ex = end.getX() + RADIUS + 5;
                            ey = end.getY();
                            break;
                        case 4:
                            sx = start.getX();
                            sy = start.getY() + RADIUS - 8;
                            ex = end.getX() + RADIUS;
                            ey = end.getY() + RADIUS - 8;
                            break;
                    }
                    break;

                case 2:
                    switch (end.getId()) {
                        case 0:
                            sx = ctl.hasFourStates() ? start.getX() - 5 : start.getX() + 10;
                            sy = ctl.hasFourStates() ? start.getY() + 3 : start.getY() - 3;
                            ex = ctl.hasFourStates() ? end.getX() + RADIUS - 5 : end.getX() + RADIUS - 8;
                            ey = end.getY() + RADIUS + 3;
                            break;
                        case 1:
                            sx = ctl.hasFourStates() ? start.getX() + 8 : start.getX() + RADIUS - 8;
                            sy = start.getY();
                            ex = ctl.hasFourStates() ? end.getX() + 8 : end.getX() + 10;
                            ey = ctl.hasFourStates() ? end.getY() + RADIUS : end.getY() + RADIUS + 5;
                            break;
                        case 3:
                            sx = start.getX();
                            sy = start.getY() + RADIUS - 8;
                            ex = end.getX() + RADIUS;
                            ey = end.getY() + RADIUS - 8;
                            break;
                        case 4:
                            sx = start.getX() + 3;
                            sy = start.getY() - 5;
                            ex = end.getX() + RADIUS;
                            ey = end.getY() + RADIUS;
                            break;
                    }
                    break;

                case 3:
                    switch (end.getId()) {
                        case 0:
                            sx = ctl.hasFourStates() ? start.getX() + 8 : start.getX() + 10;
                            sy = ctl.hasFourStates() ? start.getY() : start.getY() - 5;
                            ex = ctl.hasFourStates() ? end.getX() + 8 : end.getX() + 3;
                            ey = ctl.hasFourStates() ? end.getY() + RADIUS : end.getY() + RADIUS + 3;
                            break;
                        case 1:
                            sx = start.getX() + RADIUS;
                            sy = start.getY() - 5;
                            ex = ctl.hasFourStates() ? end.getX() - 7 : end.getX() - 3;
                            ey = end.getY() + RADIUS;
                            break;
                        case 2:
                            sx = start.getX() + RADIUS;
                            sy = start.getY() + 8;
                            ex = end.getX();
                            ey = end.getY() + 8;
                            break;
                        case 4:
                            sx = start.getX();
                            sy = start.getY();
                            ex = end.getX() + 10;
                            ey = end.getY() + RADIUS + 5;
                            break;
                    }
                    break;

                case 4:
                    switch (end.getId()) {
                        case 0:
                            sx = start.getX() + 12;
                            sy = start.getY() - 2;
                            ex = end.getX() - 3;
                            ey = end.getY() + 10;
                            break;
                        case 1:
                            sx = start.getX() + RADIUS;
                            sy = start.getY() + 8;
                            ex = end.getX();
                            ey = end.getY() + 8;
                            break;
                        case 2:
                            sx = start.getX() + RADIUS - 5;
                            sy = start.getY() + RADIUS + 5;
                            ex = end.getX() - 3;
                            ey = end.getY() - 3;
                            break;
                        case 3:
                            sx = start.getX() + RADIUS - 8;
                            sy = start.getY() + RADIUS + 5;
                            ex = end.getX() + 5;
                            ey = end.getY() - 5;
                            break;
                    }
                    break;
            }

            gc.strokeLine(sx, sy, ex, ey);
            drawArrowHead(sx, sy, ex, ey);
        }
    }

    /* draws an arrow for showing the direction of the transition
    calculations for the arrow head taken from https://www.java-forum.org/thema/pfeil-klasse.86645/ */
    private void drawArrowHead(int sx, int sy, int ex, int ey) {

        double arrowX = ex - sx;
        double arrowY = ey - sy;
        double aLen = Math.sqrt(arrowX * arrowX + arrowY * arrowY);

        arrowX /= aLen;
        arrowY /= aLen;

        gc.strokeLine(ex, ey, ex - 10 * arrowX + 5 * arrowY, ey - 10 * arrowY - 5 * arrowX);
        gc.strokeLine(ex, ey, ex - 10 * arrowX - 5 * arrowY, ey - 10 * arrowY + 5 * arrowX);

    }

    // transitions to the same state need smaller arrowheads
    private void drawArrowHeadCircle(int sx, int sy, int id) {
        int x1, x2, x3, y1, y2, y3;

        if (id == 0 && !ctl.hasFourStates()) {
            x1 = sx + 15;
            x2 = sx + 10;
            x3 = sx + 20;
            y1 = sy + 12;
            y2 = y3 = sy + 7;
        } else {
            if (sx < ctl.getState(id).getX()) {
                x1 = sx + 11;
                x2 = x3 = sx + 6;
                y1 = sy + 14;
                y2 = sy + 10;
                y3 = sy + 18;
            } else {
                x1 = sx + 3;
                x2 = x3 = sx + 7;
                y1 = sy + 14;
                y2 = sy + 10;
                y3 = sy + 18;
            }
        }

        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x1, y1, x3, y3);
    }
}