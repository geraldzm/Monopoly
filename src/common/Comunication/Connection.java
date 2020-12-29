package common.Comunication;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;

    public Connection(@NotNull Socket socket, @Nullable Listener listener) throws IOException {
        this.socket = socket;
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket, listener);
    }

    /**
     * <h1>Send a message, no further action is needed</h1>
     * */
    public void sendMessage(Message message){
        sender.send(message);
    }

    public void sendText(String message, IDMessage idMessage){
        sender.send(new Message(message, idMessage));
    }

    public void startReceiving(){
        if(receiver.getListener() != null) receiver.startThread();
    }

    public void stopReceiving(){
        receiver.stopThread();
    }

    public void setListener(Listener listener){
        receiver.setListener(listener);
    }

}
