
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public Socket socket;
    public String Username;
    public String Message;
    public String IP;
    public PrintWriter sender;
    public BufferedReader reciever;
    public MessageThread msgThread;
    public ExecutorService pool = Executors.newFixedThreadPool(6);

    @FXML public TextField chatInput;
    @FXML public TextArea chatOutput;
    @FXML public Button loginBtn;
    @FXML public TextField usernameInput;
    @FXML public TextArea userList;
    @FXML public Label usernameLabel;
    @FXML public Label onlineLabel;
    @FXML public Button logoutBtn;
    @FXML public TextField ipField;
    //public Button temp;


    //client elements
    public  void runClient() throws IOException {


        socket = new Socket(IP, 7778);
        msgThread = new MessageThread(this);
        pool.execute(msgThread);
        sender = new PrintWriter(socket.getOutputStream(), true);





    }

    public void sendMessage(String Message) throws IOException {


            if (Message.equals("quit")) {
                sender.println("Server Ended Connection");
                sender.close();
                socket.close();
                System.exit(0);
            }
            else if(Message.equals(""))
            {
                //do nothing
            }
            else {
                sender.println(Username + ": " + Message);
            }


    }

    public void loginBtnClicked(MouseEvent mouseEvent) throws IOException {

        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED)
        {
            this.Username = usernameInput.getText();
            this.IP = ipField.getText();
            ipField.setText("");
            usernameInput.setText("");
            onlineLabel.setVisible(true);
            usernameLabel.setText(Username);
            logoutBtn.setVisible(true);
            loginBtn.setVisible(false);
            chatOutput.appendText("************ " + Username +" Logged in **********\n");
            runClient();
        }

    }

    public void onSendBtnClicked(MouseEvent mouseEvent) throws IOException {

        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED)
        {
            this.Message = chatInput.getText();
            sendMessage(this.Message);
            chatInput.setText("");

        }

    }


    //unimplemented
    public void printOnlineUserList()
    {

    }

    public void logoutBtnClicked(MouseEvent mouseEvent) throws IOException {


        usernameLabel.setText("");
        usernameLabel.setVisible(false);
        onlineLabel.setVisible(false);
        loginBtn.setVisible(true);
        logoutBtn.setVisible(false);
        chatOutput.appendText("********** "+ Username + " has logged out ********** \n");
        this.Username = "";
        sendMessage("quit");

    }
}