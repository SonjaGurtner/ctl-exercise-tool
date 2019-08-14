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
    private Button automaton4;
    @FXML
    private Button automaton5;
    @FXML
    private Button newF;
    @FXML
    private Button checkF;
    @FXML
    private Label formulaLabel;
    @FXML
    private Label explanationLabel;
    @FXML
    private Label newA;
    @FXML
    private Label counterLabel;
    // helping variables
    String[] tempExplain;
    @FXML
    private CheckBox explainBox;
    @FXML
    private Canvas canvas;

    // The most important part: the Automaton including Formula, States, Transitions and Counter
    private CTL ctl;
    @FXML
    private ImageView image;

    /* Methods for controlling the CTL Tool */
    @FXML
    public void initialize() {
        ctl = new CTL();
        counterLabel.setText("Correct Answers: " + ctl.getCounter());
    }

    public void generateAutomaton4(ActionEvent e) {
        /* TODO generate transitions and labels */
        drawAutomaton(4);
    }

    public void generateAutomaton5(ActionEvent e) {
        // todo transitions and labels
        drawAutomaton(5);
    }

    public void generateFormula(ActionEvent e) {
        formulaLabel.setText("Formula: " + ctl.generateFormula());
        explanationLabel.setText("");
        explainBox.setSelected(false);
    }

    public void explainFormula() {
        if (!explainBox.isSelected()) {
            explanationLabel.setText("");
            image.imageProperty().set(null);
        }
        if (!ctl.getFormula().equals("")) {
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
    }

}
