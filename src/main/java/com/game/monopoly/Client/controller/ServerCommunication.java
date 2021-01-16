package com.game.monopoly.Client.controller;

import com.game.monopoly.common.Comunication.ChatConnection;
import com.game.monopoly.common.Comunication.IDMessage;
import com.game.monopoly.common.Comunication.Listener;
import com.game.monopoly.common.Comunication.Message;

import java.io.IOException;
import java.net.Socket;

import static com.game.monopoly.common.Comunication.IDMessage.*;

/**
 * <p>This is the server for the client, and the only way to communicate with it.</p>
 * */
public class ServerCommunication extends ChatConnection {

    private static ServerCommunication serverCommunication;

    private Listener logbookListener, chatListener;
    private int idClient=-1;

    private ServerCommunication(Socket socket) throws IOException {
        super(socket, null);

        super.setChatListener(m -> {
            switch (m.getIdMessage()) {
                case LOGBOOK -> {
                    if(logbookListener != null) logbookListener.action(m);
                }
                case MESSAGE -> {
                    if(chatListener != null) chatListener.action(m);
                }
            }
        });
    }

    public static ServerCommunication getServerCommunication() throws IOException{
        if(serverCommunication == null) {
            try {
                serverCommunication = new ServerCommunication(new Socket("LocalHost", 42069));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("No fue posible conectarse con el servidor");
                throw e;
            }
        }
        return serverCommunication;
    }


    public void sendMessageChat(String message) {
        System.out.println("Enviando al chat: " + message);
        super.sendMessage(new Message(message, MESSAGE));
    }

    @Override
    public void sendMessage(Message message) {
        super.sendMessage(idClient == -1 ? message : message.setId(idClient));
    }

    @Override
    public void sendMessage(IDMessage message) {
        super.sendMessage(idClient == -1 ? new Message(message) : new Message(message).setId(idClient));
    }

    public void sendDone(){
        super.sendMessage(idClient == -1 ? new Message(DONE) : new Message(DONE).setId(idClient));
    }

    @Override
    public void setChatListener(Listener chat) {
        this.chatListener = chat;
    }

    public void setID(int id){
        idClient = id;
    }

    /**
     * <h3>This listener will receive all the logbook's updates</h3>
     * */
    public void setLogbookListener() {
        this.logbookListener = logbookListener;
    }
}