package common.Comunication;

import com.monopoly.common.RunnableThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * <h1></h1>
 */
public class Receiver extends RunnableThread {

    private Listener listener;
    private final ObjectInputStream reader;

    public Receiver(Socket socketRef, Listener listener) throws IOException {
        this.reader = new ObjectInputStream(socketRef.getInputStream());
        this.listener = listener;
    }

    @Override
    public void execute() {
        if(listener == null) return;

        try {
            Message message = (Message) reader.readObject();
            listener.action(message);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            stopThread();
        }
    }

    //----- setters / getters
    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
