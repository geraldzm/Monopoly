package main.java.common.Comunication;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Predicate;

public class Connection {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;

    public Connection(Socket socket, Listener listener) throws IOException {
        this.socket = socket;
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket, listener);
        this.receiver.startThread();
    }

    public void sendMessage(Message message){
        try {
            if(sender.getThread() != null)sender.getThread().join();
            sender.send(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(IDMessage message){
        sender.send(new Message(message));
    }

    /**
     * <h3>Send the message and block the calling thread until the messaged is sent</h3>
     * */
    public void sendMessageAndWait(Message message){
        sender.send(message);
        try {
            sender.getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h3>Send the message and block the calling thread until the messaged is sent</h3>
     * */
    public void sendMessageAndWait(IDMessage message){
        sender.send(new Message(message));
        try {
            sender.getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendText(String message, IDMessage idMessage){
        sender.send(new Message(message, idMessage));
    }

    public void sendInt(int n, IDMessage idMessage){
        sender.send(new Message(n, idMessage));
    }

    /**
     * <h3>Prevent the listener from receiving messages</h3>
     * <p>This method does not stop receiver's thread</p>
     * */
    public void stopReceiving(){
        receiver.setFilter(m -> false);
    }

    public void setListener(Listener listener){
        receiver.setListener(listener);
    }

    /**
     * <h3>The listener will receive any message read.</h3>
     * */
    public void removeReceiverFilter(){
        receiver.removeFilter();
    }

    public void setReceiverFilter(Predicate<Message> filter){
        receiver.setFilter(filter);
    }

    /**
     * <h3>Closes the stream associated with the socket</h3>
     * <p>Finishes the receiver and sender thread, and the stream Out/In</p>
     * <br />
     * <p>If the receiver was reading, a exception will be throw</p>
     * */
    public void closeConnection() {
        try {
            receiver.stopThread();

            sender.stopThread();
            sender.getWriter().close();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sender getSender() {
        return sender;
    }

    public Socket getSocket() {
        return socket;
    }

    public Listener getListener(){
        return receiver.getListener();
    }
}
