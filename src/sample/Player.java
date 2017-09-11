package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

class Player {
    private int countWin;
    private int countLose;
    private Label labelLose;
    private Label labelWin;
    private TypeCell typeCell;

    Player(TypeCell typeCell, Label labelWin, Label labelLose){
        countWin = countLose = 0;
        this.typeCell = typeCell;
        this.labelLose = labelLose;
        this.labelWin = labelWin;
    }

    TypeCell getTypeCell(){
        return typeCell;
    }

    void setTypeCell(TypeCell typeCell){
        this.typeCell = typeCell;
    }

    private void setLabelWin(int num){
        labelWin.setText(String.valueOf(num));
    }

    private void setLabelLose(int num){
        labelLose.setText(String.valueOf(num));
    }

    void setWin(){
        setLabelWin(++countWin);
    }

    void setLose(){
        setLabelLose(++countLose);
    }
}
