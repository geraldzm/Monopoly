package common.Comunication;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Message implements Serializable {

    private final Integer number;
    private final String string;
    private final IDMessage idMessage;

    public Message(int number, String string, @NotNull IDMessage idMessage) {
        this.number = number;
        this.string = string;
        this.idMessage = idMessage;
    }

    public Message(String string, @NotNull IDMessage idMessage) {
        this.string = string;
        this.idMessage = idMessage;
        this.number = null;
    }

    public Message(int number, @NotNull IDMessage idMessage) {
        this.number = number;
        this.idMessage = idMessage;
        this.string = null;
    }

    public Message( @NotNull IDMessage idMessage) {
        this.number = null;
        this.idMessage = idMessage;
        this.string = null;
    }



    // ------- GETTERS ---------

    public int getNumber() {
        return number;
    }

    public String getString() {
        return string;
    }

    public IDMessage getIdMessage() {
        return idMessage;
    }
}
