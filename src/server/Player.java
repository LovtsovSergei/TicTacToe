package server;

import java.io.*;
import java.net.Socket;

public class Player extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    public Player otherPlayer;
    private String mark;

    public Player(Socket socket, String mark) {
        this.mark = mark;
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public static void closeResources(Socket socket, BufferedReader in, PrintWriter out) {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String messageFromClient = in.readLine();
                if (messageFromClient.equals("exit")) {
                    sendMessage("3", "exit");
                    otherPlayer.sendMessage("3", "exit");
                    break;
                }
                int move = getMove(messageFromClient);
                movesHandler(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources(socket, in, out);
        }
    }


    private int getMove(String move) {
        return Integer.parseInt(move);
    }

    void sendMessage(String type, String value) {
        out.println(type + ";" + value);
    }

    private void sendField() {

        sendMessage("2", Field.getFieldState());
    }

    private boolean rightTurn(String mark) {
        return mark.equals(Field.whoseMove);
    }

    private void movesHandler(int move) {
        if (!rightTurn(mark)) {
            sendMessage("1", "It's not your turn! Please, wait.");
            return;
        }
        if (!Field.cellIsEmpty(move)) {
            sendMessage("1", "This cell is not empty. Please, choose another one.");
            return;
        }
        Field.setMove(move, this.mark);
        sendField();
        otherPlayer.sendField();
        Field.whoseMove = otherPlayer.mark;
        if (!Field.gameFinished()) {
            otherPlayer.sendMessage("1", "Your turn. Please, enter a number from 1 to 9.");
        }

        if (Field.isWin()) {
            sendMessage("1", "You win! Congratulations!");
            otherPlayer.sendMessage("1", "You lose. Try again :)");
            Field.gameStart();
            if (this.mark.equals("X")) {
                sendMessage("1", "Your turn. Please, enter a number from 1 to 9.");
            } else otherPlayer.sendMessage("1", "Your turn. Please, enter a number from 1 to 9.");
        }
        if (Field.isDraw()) {
            sendMessage("1", "There's a draw. Try again.");
            otherPlayer.sendMessage("1", "There's a draw. Try again.");
            Field.gameStart();
            if (this.mark.equals("X")) {
                sendMessage("1", "Your turn. Please, enter a number from 1 to 9.");
            } else otherPlayer.sendMessage("1", "Your turn. Please, enter a number from 1 to 9.");
        }

    }
}







