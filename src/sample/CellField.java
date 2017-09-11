package sample;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CellField {

    private Label label;
    private TypeCell typeCell;
    private int field = 0;
    private final static Image imageX = new Image("img/x.png");
    private final static Image imageO = new Image("img/zero.png");
    private final static Image imageFree = new Image("img/free.png");
    private final static Image imageFree2 = new Image("img/free2.png");

    CellField() {
        label = new Label();
        label.setVisible(true);
        typeCell = TypeCell.Empty;
    }

    Label getCell() {
        return label;
    }

    public void drawFree() {

        label.setGraphic(new ImageView(imageFree));
        typeCell = TypeCell.Empty;
        field = 0;
    }

    void drawFree2() {
        label.setGraphic(new ImageView(imageFree2));
        typeCell = TypeCell.Empty;
        field = 0;
    }

    void drawX() {
        label.setGraphic(new ImageView(imageX));
        typeCell = TypeCell.X;
        field = 1;
    }

    void drawO() {
        label.setGraphic(new ImageView(imageO));
        typeCell = TypeCell.O;
        field = -1;
    }

    TypeCell getTypeCell() {
        return typeCell;
    }

    int getField() {
        return field;
    }

    void setField(int field) {
        this.field = field;
    }
}
