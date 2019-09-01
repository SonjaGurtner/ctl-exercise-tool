package jfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    /* all parts of the GUI */
    @FXML
    private Button automaton4, automaton5, newF, checkF;
    @FXML
    private Label formulaLabel, explanationLabel, newA, counterLabel;
    @FXML
    private Label labelS0, labelS1, labelS2, labelS3;
    @FXML
    private CheckBox explainBox;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView image;
    private final MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {

        }
    };

    // The core of the project: the Automaton including Formula, States, Transitions and Counter
    private CTL ctl;
    @FXML
    private Circle c0, c1, c2, c3;
    private Label[] stateLabels;
    // helping fields
    private String[] tempExplain;
    private List<Circle> circles;

    /* Methods for controlling the CTL Tool */

    @FXML
    public void initialize() {
        ctl = new CTL();
        counterLabel.setText("Correct Answers: " + ctl.getCounter());
        stateLabels = new Label[]{labelS0, labelS1, labelS2, labelS3};
        circles = new LinkedList<>();
        circles.add(c0);
        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        for (Circle circle : circles) {
            circle.setVisible(false);
        }
    }

    public void generateAutomaton4(ActionEvent e) {
        /* TODO generate transitions and labels */
        ctl.createAutomaton(4);
        drawAutomaton();
    }

    public void generateAutomaton5(ActionEvent e) {
        // todo transitions and labels
        //ctl.createAutomaton(5);
        //drawAutomaton(5);
    }

    public void generateFormula(ActionEvent e) {
        formulaLabel.setText("Formula: " + ctl.generateFormula());
        explanationLabel.setText("");
        explainBox.setSelected(false);
        image.imageProperty().set(null);
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
        if (ctl.getFormula().equals("")) return;
    }

    private void drawAutomaton() {
        /* TODO */
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);

        c0.setVisible(true);
        c1.setVisible(true);
        c2.setVisible(true);
        c3.setVisible(true);

        for (int i = 0; i < stateLabels.length; i++) {
            stateLabels[i].setText(ctl.getLabel(i));
        }
    }

    public void markState(MouseEvent mouseEvent) {
        System.out.printf("X koord:" + mouseEvent.getX() + " Y koord: " + mouseEvent.getY() + "\n");
    }
}


class ClickEvent implements EventHandler<MouseEvent> {
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
}