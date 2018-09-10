package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 8888;

    public static void main(String[] args) {

        Field.gameStart();

        try (
                ServerSocket serverSocket = new ServerSocket(PORT)

        ) {

            System.out.println("Server is running...");
            Socket socketX = serverSocket.accept();
            Player playerX = new Player(socketX, "X");
            playerX.sendMessage("1", "Hi! Your mark is Cross (X)");
            System.out.println("PlayerX has joined the game");
            Socket socketO = serverSocket.accept();
            Player playerO = new Player(socketO, "O");
            playerO.sendMessage("1", "Hi! Your mark is Zero (O)");
            System.out.println("PlayerO has joined the game");
            playerX.otherPlayer = playerO;
            playerO.otherPlayer = playerX;
            playerX.sendMessage("1", "Your turn. Please, enter a number from 1 to 9.");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

