package jfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import model.CTL;


public class Controller {
    /* all parts of the GUI */
    @FXML
    private Button automaton4;
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
    @FXML
    private CheckBox explainBox;


    /* Methods for controlling the CTL Tool */

    public void generateAutomaton4(ActionEvent e) {
    }

    public void generateFormula(ActionEvent e) {
        formulaLabel.setText(CTL.INSTANCE.generateFormula());
    }

    public void explainFormula() {
        explanationLabel.setText(CTL.INSTANCE.explainFormula());
    }

    public void checkFormula(ActionEvent actionEvent) {
    }

}
