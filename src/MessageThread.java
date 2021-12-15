import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MessageThread implements Runnable{
    BufferedReader reciever;
    Client client;


    public MessageThread(Client client) throws IOException {
        this.client = client;

        reciever = new BufferedReader(new InputStreamReader(this.client.socket.getInputStream()));
    }



    @Override
    public void run() {
        while(client.socket.isConnected())
        {

            try {
                String msg = reciever.readLine();
                if(msg == null)
                {

                }
                else if(msg.contains("quit"))
                {
                    client.socket.close();
                    break;
                }
                else
                {
                    client.chatOutput.appendText(msg + "\n");
                }
            } catch (IOException e) {
                System.out.println(e);
                int threadCount = Thread.activeCount();
                System.out.println(threadCount-1);
                Thread.currentThread().interrupt();
                System.exit(0);
            }

        }
        client.chatOutput.appendText(client.Username + " has Disconnected");


    }
}
