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
import model.CTL;
import model.State;

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

    // helping fields
    String[] tempExplain;
    @FXML
    private ImageView image;
    // The core of the project: the Automaton including Formula, States, Transitions and Counter
    private CTL ctl;
    private StringBuilder builder;
    private Label[] stateLabels;

    /* Methods for controlling the CTL Tool */

    @FXML
    public void initialize() {
        ctl = new CTL();
        counterLabel.setText("Correct Answers: " + ctl.getCounter());
        stateLabels = new Label[]{labelS0, labelS1, labelS2, labelS3};
    }

    public void generateAutomaton4(ActionEvent e) {
        /* TODO generate transitions and labels */
        ctl.createAutomaton(4);
        drawAutomaton(4);
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

    private void drawAutomaton(int index) {
        /* TODO */
        GraphicsContext gc = canvas.getGraphicsContext2D();
        /*List<State> temp = CTL.INSTANCE.getStates();
        for (int i = 0; i < index; i++) {
            gc.fillOval(temp.get(i).getX(), temp.get(i).getY(), 8, 8);
        }*/
        for (State state : ctl.getStates()) {
            gc.fillOval(state.getX(), state.getY(), 15, 15);
        }

        for (int i = 0; i < stateLabels.length; i++) {
            stateLabels[i].setText(ctl.getLabel(i));
        }
    }
}
