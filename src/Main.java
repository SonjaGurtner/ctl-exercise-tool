import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.CTL;
import model.Formula;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    // the mainly used font
    public final Font font = new Font("Calibri", 17);

    /* The core of the project: the Automaton including Formula, States, Transitions and Counter */
    private CTL ctl;

    // all parts of the GUI
    private Pane root;
    private VBox menu;
    private Label newAutomaton, counterLabel, formulaLabel, explanationLabel;
    private Label labelS0, labelS1, labelS2, labelS3;
    private Button automaton4, newFormula, checkFormula;
    private CheckBox explainBox;
    private ImageView explainImage;
    private Circle c0, c1, c2, c3;

    // helping fields
    private String[] tempExplain;
    private List<Circle> circles;
    private Label[] stateLabels;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/sample.fxml"));
        final Parent root = loader.load();
        primaryStage.setTitle("CTL Exercise Tool");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        //ctl = new CTL();
        //root = new Pane();
        //createMenu();
        // Scene scene = new Scene(root, 600, 400);
        //primaryStage.setTitle("CTL Exercise Tool");
        //primaryStage.setScene(scene);
        //primaryStage.show();
    }

    private void createMenu() {
        menu = new VBox(15);
        menu.setTranslateX(15);
        menu.setTranslateY(50);
        newAutomaton = new Label("New Automaton:");
        newAutomaton.setFont(font);
        newAutomaton.setTranslateX(15);
        newAutomaton.setTranslateY(20);
        automaton4 = new Button(" 4 ");
        automaton4.setFont(new Font("Calibri", 15));
        automaton4.setTranslateX(140);
        automaton4.setTranslateY(17);

        newFormula = new Button("New Formula");
        newFormula.setFont(font);
        checkFormula = new Button("Check Formula");
        checkFormula.setFont(font);
        counterLabel = new Label("Correct Answers: " + ctl.getCounter());
        counterLabel.setFont(font);
        formulaLabel = new Label("Formula:");
        formulaLabel.setFont(font);
        explainBox = new CheckBox("Explain");
        explainBox.setFont(font);

        explanationLabel = new Label();
        explanationLabel.setFont(new Font("Calibri", 14.5));
        explanationLabel.setTranslateX(15);
        explanationLabel.setTranslateY(290);

        explainImage = new ImageView();
        explainImage.setX(435);
        explainImage.setY(266);
        explainImage.setFitWidth(160);
        explainImage.setFitHeight(120);

        labelS0 = new Label();
        labelS0.setTranslateX(290);
        labelS0.setTranslateY(50);
        labelS1 = new Label();
        labelS1.setTranslateX(490);
        labelS1.setTranslateY(50);
        labelS2 = new Label();
        labelS2.setTranslateX(290);
        labelS2.setTranslateY(250);
        labelS3 = new Label();
        labelS3.setTranslateX(490);
        labelS3.setTranslateY(250);
        stateLabels = new Label[]{labelS0, labelS1, labelS2, labelS3};

        c0 = new Circle(280, 40, 10);
        c1 = new Circle(480, 40, 10);
        c2 = new Circle(280, 240, 10);
        c3 = new Circle(480, 240, 10);
        circles = new LinkedList<>();
        circles.add(c0);
        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        for (Circle circle : circles) {
            circle.setVisible(false);
        }

        menu.getChildren().addAll(newFormula, checkFormula, counterLabel, formulaLabel, explainBox);

        root.getChildren().addAll(newAutomaton, automaton4, menu, explanationLabel, explainImage, c0, c1, c2, c3, labelS0, labelS1, labelS2, labelS3);

        automaton4.setOnAction(e -> {
            generateAutomaton();
        });

        newFormula.setOnAction(e -> {
            formulaLabel.setText("Formula: " + ctl.generateFormula());
            explanationLabel.setText("");
            explainBox.setSelected(false);
            explainImage.imageProperty().set(null);
        });

        explainBox.setOnAction(e -> {
            if (!explainBox.isSelected()) {
                explanationLabel.setText("");
                explainImage.imageProperty().set(null);
            } else if (!ctl.getFormula().equals("")) {
                Formula f = ctl.explainFormula();
                explanationLabel.setText(f.desc);
                explainImage.setImage(new Image(f.img));
            }
        });
    }

    private void generateAutomaton() {
        ctl.createAutomaton(4);
        if (!c0.isVisible()) {
            for (Circle circle : circles) {
                circle.setVisible(true);
            }
        }
        for (int i = 0; i < stateLabels.length; i++) {
            stateLabels[i].setText(ctl.getLabel(i));
        }
    }
}