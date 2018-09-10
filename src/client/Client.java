package client;

import server.Player;
import java.io.*;
import java.net.Socket;

public class Client {

    private static final int PORT = 8888;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader inputUser;
    private Socket socket;


    private Client(String host) {
        try {
            socket = new Socket(host, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            Thread thread = new Thread(new WriteMsg());
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            Player.closeResources(socket, in, out);
        }
    }


    private void startGame() {
        try {
            while (true) {
                String[] serverAnswer = in.readLine().split(";");
                if (serverAnswer[1].equals("exit")) {
                    System.out.println("The game is over. Bye!");
                    out.println("exit");
                    break;
                }
                answerParser(serverAnswer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Player.closeResources(socket, in, out);
        }
    }

    private static void printBoard(String value) {

        String[] board = value.split(",");
        System.out.println("\n\n\n\n");
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---------");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---------");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);

    }

    private void answerParser(String[] string) {

        String type = string[0];
        String value = string[1];

        if (type.equals("1")) {
            System.out.println(value);

        }
        if (type.equals("2")) {
            printBoard(value);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost");
        client.startGame();
    }


    public class WriteMsg implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    String userWord;
                    userWord = inputUser.readLine();
                    if (userWord.equals("exit")) {
                        out.println(userWord);
                        break;
                    }
                    if (userWord.length() != 1 || !Character.isDigit(userWord.charAt(0))) {
                        System.out.println("Try again, please. Enter a number from 1 to 9.");
                        continue;
                    }

                    out.println(userWord);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputUser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

