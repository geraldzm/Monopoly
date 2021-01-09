package main.java.Server;

import main.java.common.Comunication.Connection;
import main.java.common.Comunication.Listener;
import main.java.common.Comunication.Message;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static main.java.common.Comunication.IDMessage.DONE;


/**
 * <h3>This class executes a message queue, and each message waits for a DONE response</h3>
 * */
public class ActionQueue {

    private final Queue<Message> queue;
    private final List<Connection> recipients;
    private final Object lock = new Object();
    private int done;

    private final Listener listener = m -> {
        done++;
        synchronized (lock){
            lock.notify();
        }
    };

    public ActionQueue(Connection recipient) {
        this.recipients = new ArrayList<>();
        recipients.add(recipient);
        queue = new ArrayDeque<>();
    }

    public ActionQueue(List<Connection> recipients) {
        this.recipients = recipients;
        queue = new ArrayDeque<>();
    }


    public void addAction(Message message){
        queue.add(message);
    }

    private void execute() {

        while( queue.size() > 0) {

            done = 0;

            // enviamos a todos el mensaje
            Message message = queue.poll();
            recipients.forEach(c -> c.sendMessage(message));

            // esperamos a que todos hayan terminado
            try {
                synchronized (lock) {
                    while (done < recipients.size())
                        lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        recipients.forEach(c -> c.setListener(null)); // clean listeners
    }

    public void executeQueue() {
        recipients.forEach(c -> c.setListener(listener));
        recipients.forEach(c -> c.setReceiverFilter(m -> m.getIdMessage() == DONE));
        execute();
    }

}
