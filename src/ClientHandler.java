import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    private Socket client;
    private BufferedReader reciever;
    private PrintWriter sender;
    private ArrayList<ClientHandler> clients;

    //constructor
    public ClientHandler(Socket clientsocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientsocket;
        this.clients = clients;
        reciever = new BufferedReader(new InputStreamReader(client.getInputStream()));
        sender = new PrintWriter(client.getOutputStream(), true);

    }

    @Override
    public void run() {
        try {
            String msg;
            do{
                msg = reciever.readLine();
                if(msg == null){

                }
                else if(msg == "Server Ended Connection")
                {
                    System.out.println(msg);
                    clients.remove(this);
                    Thread.currentThread().interrupt();
                }
                else {
                    System.out.println(msg);
                    outToAll(msg);
                }
            }
            while(true);

        }catch(Exception e)
        {
              System.err.println("Client Disconnected.");
              Thread.currentThread().interrupt();
        }
    }

    public void outToAll(String msg)
    {
        for(ClientHandler aClient : clients)
        {
            aClient.sender.println(msg);
        }
    }


}

