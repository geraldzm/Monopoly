package main.java.common.Comunication;

import main.java.common.RunnableThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * <h3>This class is use to send Messages</h3>
 * <p>Each Sender object will create a <b>new</b> thread to send a Message</p>
 *
 * @see RunnableThread
 * */
public class Sender extends RunnableThread {

    private final ObjectOutputStream writer;
    private Message toSend;

    /**
     * @param socketRef This will be taken as a reference, when the sender is closed the socket will be as well
     * */
    public Sender(Socket socketRef) throws IOException {
        writer = new ObjectOutputStream(socketRef.getOutputStream());
    }

    @Override
    public void execute() {
        try {
            writer.writeObject(toSend);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            stopThread();
        }
    }

    /**
     * You simply have to use this method to send a message.
     * */
    public void send(Message message){
        if(message == null) return;

        toSend = message;
        this.startThread();
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }
}
