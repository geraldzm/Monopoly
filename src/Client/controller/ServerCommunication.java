package Client.controller;

import common.Comunication.Connection;
import common.Comunication.IDMessage;
import common.Comunication.Listener;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;

/**
 * <p>This is the server for the client, and the only way to communicate with it.</p>
 * */
public class ServerCommunication extends Connection {

    private static ServerCommunication serverCommunication;
    private final Connection chat;
    private Listener chatListener, logbookListener;

    private  ServerCommunication(Socket socket) throws IOException {
        super(socket, null);

        // CHAT listenner
        Listener listener = m -> {
            switch (m.getIdMessage()) {
                case MESSAGE -> {
                    if (chatListener != null) chatListener.action(m);
                }
                case LOGBOOK -> {
                    if (logbookListener != null) logbookListener.action(m);
                }
            }
        };

        chat = new Connection(socket, listener);
        chat.removeReceiverFilter();
    }

    public static ServerCommunication getServerCommunication() {
        if(serverCommunication == null) {
            try {
                serverCommunication = new ServerCommunication(new Socket("LocalHost", 42069));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("No fue posible conectarse con el servidor");
            }
        }
        return serverCommunication;
    }


    @Override
    public void closeConnection() {
        super.closeConnection();
        chat.closeConnection();
    }

    public void sendMessageChat(String message) {
        chat.sendText(message, IDMessage.MESSAGE);
    }

    /**
     * <h3>This listener will receive all the chat's messages</h3>
     * */
    public void setChatListener(@Nullable Listener chatListener) {
        this.chatListener = chatListener;
    }

    /**
     * <h3>This listener will receive all the logbook's updates</h3>
     * */
    public void setLogbookListener(@Nullable Listener logbookListener) {
        this.logbookListener = logbookListener;
    }
}
