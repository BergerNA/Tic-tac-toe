package sample;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

class Game {

    private static final int CELLS_GAMES = 9;
    private static final String INFO_START_NEW_GAME = "Info: Начните новую игру.";
    private static final String INFO_YOU_LOSE = "Info: Вам повезет в следующей игре!";

    private ArrayList<CellField> gameField;
    private int side;
    private GridPane gridPane;
    private TypeCell nextStep;
    private Player player;
    private ComputerPlayer computer;
    private Label labelInfo;
    private boolean endGame = false;

    /**
     * Инициализация игровой логики
     *
     * @param gridPane  Игровое поле
     * @param labelInfo Информациооное поле
     * @param player    Игрок
     * @param computer  Компьютер
     */
    Game(GridPane gridPane, Label labelInfo, Player player, ComputerPlayer computer) {
        this.gridPane = gridPane;
        gameField = new ArrayList<>(CELLS_GAMES);
        side = 3;
        nextStep = TypeCell.X;
        this.labelInfo = labelInfo;
        this.player = player;
        this.computer = computer;
    }

    /**
     * Загрузка игрового поля,
     * Подписка на события нажатия мышки по клеткам поля.
     */
    void createField() {
        int index = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                CellField cellField = new CellField();
                cellField.getCell().setOnMouseClicked(event -> {
                    if (!endGame && cellField.getTypeCell() == TypeCell.Empty) {
                        switch (nextStep) {
                            case X:
                                cellField.drawX();
                                computerStep(player.getTypeCell().getIntValue());
                                nextStep = TypeCell.X;
                                break;
                            case O:
                                cellField.drawO();
                                computerStep(player.getTypeCell().getIntValue());
                                nextStep = TypeCell.O;
                        }
                    }
                });
                cellField.drawFree2();
                GridPane.setHalignment(cellField.getCell(), HPos.RIGHT);
                gameField.add(cellField);
                gridPane.add(cellField.getCell(), j, i);
            }
        }
    }

    /**
     * Совершение хода компьютером
     */
    private void computerStep(int i) {
        nextStep(i);
        if (computer.nextStep < 0) {
            labelInfo.setText(INFO_START_NEW_GAME);
            return;
        }
        if (player.getTypeCell() == TypeCell.X) {
            gameField.get(computer.nextStep).drawO();
        } else gameField.get(computer.nextStep).drawX();
        if (checkWin(getStartRowIndex(computer.nextStep), getStartColumnIndex(computer.nextStep))) {
            labelInfo.setText(INFO_YOU_LOSE);
            computer.setWin();
            player.setLose();
            endGame = true;
        }
    }

    private int getCellValue(int x, int y) {
        return gameField.get(y * side + x).getField();
    }

    private int getStartRowIndex(int i) {
        return i % side;
    }

    private int getStartColumnIndex(int i) {
        return i / side;
    }

    /**
     * Проверка на наличие выигрышной комбинации
     */
    private boolean checkWin(int x, int y) {
        int i, j;
        for (i = 0, j = 0; i < side; i++) {
            j += getCellValue(i, y);
        }
        max += Math.abs(j);
        if (Math.abs(j) == side)
            return true;
        for (i = 0, j = 0; i < side; i++) {
            j += getCellValue(x, i);
        }
        max += Math.abs(j);
        if (Math.abs(j) == side)
            return true;
        if (x == y) {
            for (i = 0, j = 0; i < side; i++) {
                j += getCellValue(i, i);
            }
            max += Math.abs(j);
            if (Math.abs(j) == side)
                return true;
        }
        if (x + y + 1 == side) {
            for (i = 0, j = 0; i < side; i++) {
                j += getCellValue(i, side - i - 1);
            }
            max += Math.abs(j);
            if (Math.abs(j) == side)
                return true;
        }
        return false;
    }

    private int max = 0;

    private void nextStep(int num) {
        int prevMax = 0;
        for (int i = 0; i < gameField.size(); i++) {
            if (gameField.get(i).getField() != 0)
                continue;
            max = 0;
            gameField.get(i).setField(num);
            if (checkWin(getStartRowIndex(i), getStartColumnIndex(i))) {
                computer.nextStep = i;
                return;
            }
            gameField.get(i).setField(0);
            gameField.get(i).setField(-num);
            if (checkWin(getStartRowIndex(i), getStartColumnIndex(i))) {
                computer.nextStep = i;
                return;
            }
            gameField.get(i).setField(0);
            if (max > prevMax) {
                prevMax = max;
                computer.nextStep = i;
            }
        }
    }

    /**
     * Поиск следующего шага для компьютера
     */
    private int nextStepRating(int side) {
        int best = -1, bestVal = -2, curVal;

        for (int i = 0; i < gameField.size(); i++) {
            if (gameField.get(i).getField() != 0)
                continue;
            gameField.get(i).setField(side);

            if (checkWin(getStartRowIndex(i), getStartColumnIndex(i))) {
                gameField.get(i).setField(0);
                computer.nextStep = i;
                return side;
            }
            curVal = nextStepRating(-side);

            gameField.get(i).setField(0);
            if (curVal * side > bestVal) {
                bestVal = curVal * side;
                best = i;
            }
        }
        computer.nextStep = best;
        if (best < 0)
            return 0;
        return bestVal * side;
    }

    /**
     * Очистка поля, переинициализация переменных
     */
    void clearField() {
        for (CellField cf : gameField) {
            cf.drawFree2();
        }

        endGame = false;
        nextStep = TypeCell.X;
        if (player.getTypeCell() == TypeCell.O) {
            nextStepRating(TypeCell.O.getIntValue());
            computerStep(TypeCell.O.getIntValue());
            nextStep = TypeCell.O;
        }
    }

}
