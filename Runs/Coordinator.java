package Runs;

import Other.CoordinatorGUI;
import java.net.*;
import java.io.*;
import java.util.ArrayDeque;

public class Coordinator implements Runnable {

    private static ServerSocket coordinator;
    private static Socket server;
    private static boolean reserved; //Is the resource reserved?
    private static final ArrayDeque<Socket> clients = new ArrayDeque<>();
    private static final CoordinatorGUI gui = new CoordinatorGUI();

    public Coordinator() {
        reserved = false;
        try {
            coordinator = new ServerSocket(2000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Socket client;
        String queue; //Holds the clients in the queue
        DataOutputStream cout;
        DataInputStream cin;
        DataOutputStream Sout;

        try {
            server = new Socket("localhost", 4000);
            Sout = new DataOutputStream(server.getOutputStream());
            while (true) {
                client = clients.peek(); //Retrieves the first element in the queue, returns //null if queue is empty
                System.out.println(client); //It's only used to fix an unknown/undefined behaviour that I don't know the
                //source of (If removed, the program never goes past this line)

                if (client != null) {
                    queue = gui.getQueueField().getText();
                    gui.getTextField().setText(queue.substring(0, queue.indexOf('\n')));

                    queue = queue.substring(queue.indexOf('\n') + 1);
                    gui.getQueueField().setText(queue);

                    cout = new DataOutputStream(client.getOutputStream());
                    cin = new DataInputStream(client.getInputStream());

                    cout.writeBoolean(true);
                    Sout.writeUTF(gui.getTextField().getText());
                    reserved = true;
                    reserved = cin.readBoolean();//Receives "false" when the client is done using the resource, otherwise, stays blocked
                    gui.getTextField().setText("");

                    cout.close();
                    cin.close();
                    client.close();
                    clients.remove();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        gui.setVisible(true);
        Thread thread = new Thread(new Coordinator());
        thread.start();

        Socket client;
        DataInputStream cin;
        String name;

        while (true) {
            try {
                client = coordinator.accept();
                cin = new DataInputStream(client.getInputStream());

                name = cin.readUTF();
                gui.getQueueField().append(name + "\n");
                clients.add(client);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
