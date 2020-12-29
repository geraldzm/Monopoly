package Server;


import common.Comunication.Connection;
import common.Comunication.IDMessage;
import common.Comunication.Message;
import common.RunnableThread;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection extends RunnableThread {

    private ServerSocket serverSocket;
    private Connection admin; //
    private ArrayList<Player> players;
    private int maxPlayers;

    public ServerConnection(ArrayList<Player> players) {

        try {
            this.serverSocket = new ServerSocket(42069);
        } catch (IOException e) {
            System.err.println("Puerto 42069 en uso");
            e.printStackTrace();
        }

        this.maxPlayers = 6;
        this.players = players;
    }

    @Override
    public void execute() {
        if(players.size() >= maxPlayers)
            initGame();

        System.out.println("::waiting connection...\tcurrent number of connections: " + players.size());

        try {
            Socket newClient  = serverSocket.accept();

            System.out.println("Connecting with: " + newClient.getInetAddress());

            Player player = new Player(newClient);
            players.add(player);

            if(players.size() == 1) { // the first client is the admin
                admin = player;
                admin.setListener(message -> System.out.println(message.getIdMessage()+ " " + message.getString()));
                admin.startReceiving();
                admin.sendMessage(new Message("Congrats, you're the admin", IDMessage.ADMIN));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al connectar un cliente");
        }

    }

    /**
     * <h1>All players are connected and ready</h1>
     * */
    private void initGame() {
        this.stopThread();
    }
}
