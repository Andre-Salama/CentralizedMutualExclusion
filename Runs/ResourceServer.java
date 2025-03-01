package Runs;

import Other.ResourceServerGUI;
import java.net.*;
import java.io.*;

public class ResourceServer {
    
    private static Socket client;
    private static Socket coordinator;
    private static ServerSocket clientServer; //socket for clients to connect on
    private static ServerSocket coordinatorServer; //socket for the coordinator to connect on
    private static ResourceServerGUI gui;
    
    public static void main(String[] args) {
        String text;
        String usernameFromClient;
        String usernameFromCoordinator;
        gui = new ResourceServerGUI();
        gui.setVisible(true);
        DataInputStream cin; //Client INput
        DataInputStream coin; //COordinator INput

        try {
            clientServer = new ServerSocket(3000);
            coordinatorServer = new ServerSocket(4000);
            
            coordinator = coordinatorServer.accept();
            coin = new DataInputStream(coordinator.getInputStream());
            
            while (true) {
                client = clientServer.accept();
                cin = new DataInputStream(client.getInputStream());
                
                usernameFromClient = cin.readUTF();
                usernameFromCoordinator = coin.readUTF();
                
                if (cin.readBoolean() && usernameFromClient.equals(usernameFromCoordinator)) {
                    gui.getTextField().setText(usernameFromClient); //Get the username
                    while (true) {
                        text = cin.readUTF();
                        if ( ! text.equals("#D1$((0///3(7"))
                            gui.getTextArea().append(text + "\n");
                        else {
                            gui.getTextField().setText("");
                            cin.close();
                            client.close();
                            break;
                        }
                    }
                }
                else
                    continue; //If the client wasn't given permission by the coordinator, he/she is discarded
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
