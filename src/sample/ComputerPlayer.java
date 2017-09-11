package sample;

import javafx.scene.control.Label;

class ComputerPlayer extends Player {

    int nextStep;

    ComputerPlayer(TypeCell typeCell, Label labelWin, Label labelLose){
        super(typeCell, labelWin, labelLose);
    }
}
