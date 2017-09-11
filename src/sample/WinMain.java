package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WinMain extends Application implements Initializable {

    private static final int WIDTH_APP = 403;
    private static final int HEIGHT_APP = 700;
    private static final String RESOURCES_APP = "/WinMain.fxml";
    private static final String STYLE_APP = "/Style.css";
    private static final String TITLE_APP = "Tick-Tack-Toe";
    private static final String MESSAGE_ENTER_FIRST = "Ходить первым";
    private static final String MESSAGE_ENTER_SECOND = "Ходить вторым";
    private static final String MESSAGE_INFO = "Info: Ваш ход";

    @FXML
    private GridPane gridPane;

    @FXML
    private Label labelInfo;

    @FXML
    private Label labelPlayerWin;

    @FXML
    private Label labelComputerWin;

    @FXML
    private Label labelPlayerLose;

    @FXML
    private Label labelComputerLose;

    @FXML
    private Label labelCountGames;

    void run(){
        launch();
    }

    private Player player;
    private ComputerPlayer computer;
    private int countGame = 1;
    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(RESOURCES_APP));
        root.setId("root");
        primaryStage.setTitle(TITLE_APP);
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, WIDTH_APP, HEIGHT_APP);
        scene.getStylesheets().add
                (getClass().getResource(STYLE_APP).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player = new Player(TypeCell.X, labelPlayerWin, labelPlayerLose);
        computer = new ComputerPlayer(TypeCell.O, labelComputerWin, labelComputerLose);
        game = new Game(gridPane, labelInfo, player, computer);
        game.createField();
    }

    public void labelNewGame_MouseClicked() {
        game.clearField();
        countGame++;
        labelCountGames.setText(String.valueOf(countGame));
        labelInfo.setText(MESSAGE_INFO);
    }

    public void labelChangePlayer_MouseClicked(Event event) {
        Label label = (Label)event.getSource();
        if(player.getTypeCell() == TypeCell.X) {
            label.setText(MESSAGE_ENTER_FIRST);
            player.setTypeCell(TypeCell.O);
            computer.setTypeCell(TypeCell.X);
        }
        else{
            label.setText(MESSAGE_ENTER_SECOND);
            player.setTypeCell(TypeCell.X);
            computer.setTypeCell(TypeCell.O);
        }
        labelNewGame_MouseClicked();
    }
}
