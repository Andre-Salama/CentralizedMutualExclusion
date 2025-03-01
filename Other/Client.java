package Other;

import java.net.*;
import java.io.*;

public class Client {

    private String username; //Client's username
    private Socket coordinator; //A socket to connect to the coordinator
    private Socket server; //A socket to connect to the resource server
    public boolean authorized; //Boolean that determines whether the client is authorized or not

    private DataInputStream Cinp; //Coordinator streams
    private DataOutputStream Cout;

    private DataOutputStream Sout; //Resource server streams

    public Client(String username) {
        this.username = username;
        authorized = false;
        try {
            coordinator = new Socket("localhost", 2000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestResource() {
        try {
            Cinp = new DataInputStream(coordinator.getInputStream());
            Cout = new DataOutputStream(coordinator.getOutputStream());

            Cout.writeUTF(username);
            authorized = Cinp.readBoolean(); //Receives "true" when the 
            //coordinator wants to grant permission, otherwise, is blocked

            server = new Socket("localhost", 3000);
            Sout = new DataOutputStream(server.getOutputStream());

            Sout.writeUTF(username);
            Sout.writeBoolean(authorized);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendText(javax.swing.JTextArea text) {
        try {
            Sout.writeUTF(text.getText());
            text.setText("");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            authorized = false;
            Cout.writeBoolean(false);

            Cout.close();
            Cinp.close();
            coordinator.close();

            Sout.writeUTF("#D1$((0///3(7");
            /*#Disconnect, but written in numbers
            , letters and symbols so it can be "random",
            to be read by the server to call .close() function*/
            Sout.close();
            server.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
