package main.java.Server;

import main.java.common.Comunication.*;

import java.util.*;
import java.util.function.Predicate;

import static main.java.common.Comunication.IDMessage.*;

public class ActionQueue {

    private final Queue<Optional<ArrayList<Message>>> queueMessages = new ArrayDeque<>();
    private final Queue<Optional<Predicate<Message>>> filters = new ArrayDeque<>();
    private final Queue<Optional<Listener>> actionsQueue = new ArrayDeque<>();

    private final List<Connection> recipients;
    private final Object lock = new Object();
    private int done;

    private Listener action;
    private final Listener listener = m -> {
        if(action != null) action.action(m);
        synchronized (lock){
            done++;
            lock.notify();
        }
    };

    public ActionQueue(Connection recipient) {
        this.recipients = new ArrayList<>();
        this.recipients.add(recipient);
    }

    public ActionQueue(List<Connection> recipients) {
        this.recipients = recipients;
    }

    public ActionQueue(Hashtable<Integer, Player> recipients) {
        this.recipients = new ArrayList<>(recipients.values());
    }

    public void addAction(Message message){
        addAction(message, null, DONE);
    }

    public void addAction(Message message, Listener action){
        addAction(message, action, RESPONSE);
    }

    public void addAction(Message message, Listener action, IDMessage filter){
        addAction(new ArrayList<>(Collections.nCopies(recipients.size(), message)), action, filter);
    }

    /**
     * @param  messages has to have the same size than recipients
     * */
    public void addAction(ArrayList<Message> messages, Listener action, IDMessage filter) {
        queueMessages.add(Optional.ofNullable(messages));
        actionsQueue.add(Optional.ofNullable(action));
        filters.add(Optional.of(message -> message.getIdMessage() == filter));
    }

    private void execute() {

        while( queueMessages.size() > 0) {

            done = 0;

            // POPS
            action = actionsQueue.poll().orElse(null);
            ArrayList<Message> messages = queueMessages.poll().orElse(null);
            Predicate<Message> filter = filters.poll().orElse(null);

            // SEND
            for (int i = 0; i < recipients.size(); i++) {
                Connection connection = recipients.get(i);
                connection.setReceiverFilter(filter);
                connection.sendMessage(messages.get(i));
            }

            // WAIT
            try {
                synchronized (lock) {
                    while (done < recipients.size())
                        lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // clean
        recipients.forEach(c -> c.setListener(null));
        action = null;
    }

    public void executeQueue() {
        recipients.forEach(c -> c.setListener(listener));
        execute();
    }

}
