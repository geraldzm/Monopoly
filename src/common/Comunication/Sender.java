package common.Comunication;

import common.RunnableThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Sender extends RunnableThread {

    private final ObjectOutputStream writer;
    private Message toSend;

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

}
