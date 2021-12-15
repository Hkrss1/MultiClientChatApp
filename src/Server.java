

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    public static Socket client;
    public static ServerSocket server;
    public static ArrayList<ClientHandler> clients = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        server = new ServerSocket(7778);
        System.out.println("waiting for connection: ");

        while (true) {


            client = server.accept();
            System.out.println("client connected");
            ClientHandler clientThread = new ClientHandler(client, clients);
            clients.add(clientThread);
            Thread thread = new Thread(clientThread);
            thread.start();


        }

    }

}
