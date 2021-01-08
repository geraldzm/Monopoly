package main.java.common.Comunication;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Predicate;

import static main.java.common.Comunication.IDMessage.LOGBOOK;
import static main.java.common.Comunication.IDMessage.MESSAGE;

public class ChatConnection extends Connection {


    private Listener chat, subListener;

    public ChatConnection(@NotNull Socket socket, @Nullable Listener listener) throws IOException {
        super(socket, listener);

        // CHAT listenner
        super.setListener( m -> {
            switch (m.getIdMessage()) {
                case LOGBOOK, MESSAGE -> {
                    if(chat != null) chat.action(m);
                }
                default -> {
                    if (subListener != null) subListener.action(m);
                }
            }
        });
    }

    @Override
    public void setListener(Listener listener) {
        this.subListener = listener;
    }

    @Override
    public void setReceiverFilter(@NotNull Predicate<Message> filter) {
        super.setReceiverFilter(filter.or(m -> m.getIdMessage() == MESSAGE).or(m -> m.getIdMessage() == LOGBOOK));
    }

    /**
     * <h3>This listener will receive all the chat's messages</h3>
     * */
    public void setChatListener(@Nullable Listener chat) {
        this.chat = chat;
    }
}
