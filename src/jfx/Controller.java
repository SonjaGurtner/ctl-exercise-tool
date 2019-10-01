package jfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private Label formulaLabel, explanationLabel, counterLabel;
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

    // Methods for controlling the CTL Tool
    @FXML
    public void initialize() {
        ctl = new CTL();
        generated = false;
        justChecked = false;
        counterLabel.setText("Correct Answers: " + ctl.getCounter());
        stateLabels = new Label[]{labelS0_4, labelS1_4, labelS2_4, labelS3_4, labelS0_5, labelS1_5, labelS2_5,
                labelS3_5, labelS4_5};
        /*circles = new LinkedList<>();
        circles.add(c0);
        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        for (Circle circle : circles) {
            circle.setVisible(false);
        }*/
        gc = canvas.getGraphicsContext2D();
    }

    public void generateAutomaton4(ActionEvent e) {
        ctl.createAutomaton(4);
        drawAutomaton();
    }

    public void generateAutomaton5(ActionEvent e) {
        ctl.createAutomaton(5);
        drawAutomaton();
    }

    public void generateFormula(ActionEvent e) {
        formulaLabel.setText("Formula: " + ctl.generateFormula());
        explanationLabel.setText("");
        explainBox.setSelected(false);
        image.imageProperty().set(null);
        if (justChecked) {
            drawAutomaton();
        }
    }

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

    private void drawAutomaton() {
        generated = true;
        justChecked = false;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);

        //c0.setVisible(true);
        //c1.setVisible(true);
        //c2.setVisible(true);
        //c3.setVisible(true);

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

    private void drawPath(State start, State end) {
        int sx, sy, ex, ey;
        sx = sy = ex = ey = 0;

        if (start.getId() == end.getId()) {
            if (start.getId() == 1 || start.getId() == 2) {
                sx = start.getX() + RADIUS - 4;
                sy = start.getY() + 5;
            } else {
                if (start.getId() == 0 && !ctl.hasFourStates()) {
                    sx = start.getX() + 3;
                    sy = start.getY() - 6;
                } else {
                    sx = start.getX() - 6;
                    sy = start.getY() + 3;
                }
            }
            gc.strokeOval(sx, sy, 10, 10);
        } else {
            switch (start.getId()) {
                case 0:
                    switch (end.getId()) {
                        case 1:
                            sx = start.getX() + RADIUS - 3;
                            sy = start.getY() + 7;
                            ex = end.getX() + 3;
                            ey = ctl.hasFourStates() ? end.getY() + 7 : end.getY() + 4;
                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                    }
                    break;
                case 1:
                    switch (end.getId()) {
                        case 0:
                            sx = start.getX() + 3;
                            sy = start.getY() + RADIUS - 7;
                            ex = end.getX() + RADIUS - 3;
                            ey = end.getY() + RADIUS - 7;
                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                    }
                    break;

                case 2:
                    switch (end.getId()) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                    }
                    break;

                case 3:
                    switch (end.getId()) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

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

    private void drawArrowHead(int sx, int sy, int ex, int ey) {
        //int x1, x2, x3, y1, y2, y3;
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
            x1 = ex - 10;
            x2 = ex - 5;
            y1 = (sy > ey ? ey + 5 : ey - 5);
            y2 = (sy > ey ? ey + 10 : ey - 10);
        } else {
            x1 = ex + 5;
            x2 = ex + 10;
            y1 = (sy > ey ? ey - 10 : ey - 5);
            y2 = (sy > ey ? ey - 5 : ey - 10);
        }

        gc.strokeLine(ex, ey, x1, y1);
        gc.strokeLine(ex, ey, x2, y2);

        /*if (sy == ey) {
            x1 = ex;
            x2 = x3 = (sx < ex ? ex - 5 : ex + 5);
            y1 = ey;
            y2 = ey - 5;
            y3 = ey + 5;
        } else if (sx == ex) {
            x1 = ex;
            x2 = ex - 5;
            x3 = ex + 5;
            y1 = ey;
            y2 = y3 = (sy < ey ? ey - 5 : ey + 5);
        } else if (sx < ex) {
            x1 = ex;
            x2 = (sy < ey ? ex - 3 : ex - 6);
            x3 = (sy < ey ? ex - 6 : ex - 3);
            y1 = ey;
            y2 = ey - 3;
            y3 = ey + 3;
        } else {
            x1 = ex;
            x2 = (sy < ey ? ex + 6 : ex + 3);
            x3 = (sy < ey ? ex + 3 : ex + 6);
            y1 = ey;
            y2 = ey - 3;
            y3 = ey - 3;
        }

        double[] x = new double[]{x1, x2, x3};
        double[] y = new double[]{y1, y2, y3};
        gc.fillPolygon(x, y, 3); */
    }
}