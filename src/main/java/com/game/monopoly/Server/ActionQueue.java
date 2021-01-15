package com.game.monopoly.Server;

import com.game.monopoly.common.Comunication.Connection;
import com.game.monopoly.common.Comunication.IDMessage;
import com.game.monopoly.common.Comunication.Listener;
import com.game.monopoly.common.Comunication.Message;

import java.util.*;
import java.util.function.Predicate;

import static com.game.monopoly.common.Comunication.IDMessage.*;


public class ActionQueue {

    private final Queue<Optional<ArrayList<Message>>> queueMessages = new ArrayDeque<>();
    private final Queue<Optional<Predicate<Message>>> filters = new ArrayDeque<>();
    private final Queue<Optional<Listener>> actionsQueue = new ArrayDeque<>();

    private final List<Connection> recipients;
    private final Object lock = new Object();
    private int done;

    private Listener action;

    private HashSet<Integer> ready;
    private final Listener listener = m -> {
        if(action != null) action.action(m);
        synchronized (lock){
            done++;
            if(m.getId() != null) ready.add(m.getId());
            lock.notify();
        }
    };

    public ActionQueue(Player recipient) {
        this(Arrays.asList(recipient));
    }

    public ActionQueue(List<Connection> recipients) {
        this.ready = new HashSet<>();
        this.recipients = recipients;
    }

    public ActionQueue(Hashtable<Integer, Player> recipients) {
        this(new ArrayList<>(recipients.values()));
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
            Predicate<Message> filter = filters.poll().orElse(message -> true).and(m -> {
                return !ready.contains(m.getId());
            } );

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
        ready.clear();
        recipients.forEach(c -> c.setListener(null));
        action = null;
    }

    public void executeQueue() {
        recipients.forEach(c -> c.setListener(listener));
        execute();
    }

}
