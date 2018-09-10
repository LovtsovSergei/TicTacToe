package server;

import java.util.Arrays;

public class Field {

    private static String[] field = new String[9];
    static String whoseMove;

    private static void fieldInitialize() {
        Arrays.fill(field, " ");
    }

    static void gameStart() {
        fieldInitialize();
        whoseMove = "X";
    }

    static String getFieldState() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : field) {
            stringBuilder.append(str).append(",");
        }
        return stringBuilder.toString();
    }

    private static boolean hasMove() {
        for (String str : field) {
            if (str.equals(" ")) {
                return true;
            }
        }
        return false;
    }

    static void setMove(int move, String mark) {
        field[move - 1] = mark;
    }

    static boolean cellIsEmpty(int move) {
        return (field[move - 1]).equals(" ");
    }

    static boolean isWin() {

        return (!field[0].equals(" ") && field[0].equals(field[1]) && field[1].equals(field[2])) ||
                (!field[3].equals(" ") && field[3].equals(field[4]) && field[4].equals(field[5])) ||
                (!field[6].equals(" ") && field[6].equals(field[7]) && field[7].equals(field[8])) ||
                (!field[0].equals(" ") && field[0].equals(field[3]) && field[3].equals(field[6])) ||
                (!field[1].equals(" ") && field[1].equals(field[4]) && field[4].equals(field[7])) ||
                (!field[2].equals(" ") && field[2].equals(field[5]) && field[5].equals(field[8])) ||
                (!field[0].equals(" ") && field[0].equals(field[4]) && field[4].equals(field[8])) ||
                (!field[2].equals(" ") && field[2].equals(field[4]) && field[4].equals(field[6]));

    }

    static boolean isDraw() {
        return (!hasMove() && !isWin());
    }

    static boolean gameFinished() {

        return isDraw() || isWin();
    }

}

