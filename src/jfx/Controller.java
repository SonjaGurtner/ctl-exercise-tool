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
import model.State;
import model.Transition;

import java.util.List;

public class Controller {
    /* important parts of the GUI */
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
    private String[] tempExplain;
    private List<Circle> circles;
    private GraphicsContext gc;
    private Label[] stateLabels;
    private boolean generated;
    private boolean justChecked;

    // The core of the project: the Automaton including Formula, States, Transitions and Counter
    private CTL ctl;

    /* Methods for controlling the CTL Tool */
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
            tempExplain = ctl.explainFormula();
            explanationLabel.setText(tempExplain[0]);
            image.setImage(new Image(tempExplain[1]));
        }
    }

    public void checkFormula(ActionEvent e) {
        /* TODO */
        if (ctl.getFormula().equals("") || !generated) {
            System.out.println("Kek you tried");
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
        /* TODO */
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
                System.out.println(state.getId() + " -> " + end.getId());       //todo remove
                //drawPath(state, end);
            }
        }

        for (int i = 0; i < stateLabels.length; i++) {
            if (ctl.hasFourStates()) {
                if (i < 4) stateLabels[i].setText(ctl.getLabel(i));
                else stateLabels[i].setText(" ");
            } else {
                if (i >= 4) stateLabels[i].setText(ctl.getLabel(i));
                else stateLabels[i].setText(" ");
            }
        }

        System.out.println("----------");  // TODO remove
    }

    private void drawPath(State state, State end) {
        if (state.getId() == end.getId()) {
            if (state.getId() % 2 == 0) gc.strokeOval(state.getY() + (RADIUS - 5), state.getX() - 7, 10, 10);
            else gc.strokeOval(state.getY() + (RADIUS - 5), state.getX() + RADIUS + 7, 10, 10);
        }

        gc.beginPath();
        float mid = Math.max(state.getX(), end.getX()) - Math.min(state.getX(), end.getX());
        float mid2 = Math.max(state.getY(), end.getY()) - Math.min(state.getY(), end.getY());
        gc.quadraticCurveTo(state.getX(), state.getY(), end.getX(), end.getY());
        gc.closePath();
        gc.stroke();
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
}

/*class ClickEvent implements EventHandler<MouseEvent> {
    private int id;
    private CTL ctl;
    private Circle circle;

    public ClickEvent(int id, CTL ctl, Circle circle) {
        this.id = id;
        this.ctl = ctl;
        this.circle = circle;
    }

    @Override
    public void handle(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        ctl.getState(id).changeSelected();
        if (circle.getFill() == Color.BLACK) circle.setFill(Color.BLUE);
        else if (circle.getFill() == Color.BLUE) circle.setFill(Color.BLACK);
    }
}*/