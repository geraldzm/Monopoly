package com.game.monopoly.common.Comunication;

import com.game.monopoly.common.RunnableThread;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.function.Predicate;

/**
 * <h3>This class will be use to receive Messages</h3>
 * <p>This class will start a thread that will run constantly</p>
 * <p>It will start reading as soon as the object is instantiated</p>
 */
public class Receiver extends RunnableThread {

    private Listener listener;
    private final ObjectInputStream reader;
    private Predicate<Message> filter;

    /**
     * @param socketRef This will be taken as a reference, when the receiver is closed the socket will be as well
     * */
    public Receiver(Socket socketRef, Listener listener) throws IOException {
        this.listener = listener;
        filter = message -> false;
        reader = new ObjectInputStream(socketRef.getInputStream());
    }

    @Override
    public void execute() {
        try {
            Message message = (Message) reader.readObject();
            if(listener != null && filter.test(message)){
                System.out.println("--->se recibe : " + message.getIdMessage());
                listener.action(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Reading interrupted");
            super.stopThread();
        }
    }

    @Override
    public synchronized void stopThread() {
        super.stopThread();
        setFilter(m -> false);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h3>The listener will receive any message read.</h3>
     * */
    public void removeFilter(){
        filter = message -> true; // no filter
    }

    //----- setters / getters
    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setFilter(Predicate<Message> filter){
        this.filter = filter;
    }

    public ObjectInputStream getReader() {
        return reader;
    }
}
