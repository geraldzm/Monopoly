package com.game.monopoly.common.Comunication;

import java.io.Serializable;

public class Message implements Serializable {

    private final Integer number;
    private final int[] numbers;
    private final String string;
    private final IDMessage idMessage;

    public Message(int number, String string, IDMessage idMessage) {
        this.number = number;
        this.string = string;
        this.idMessage = idMessage;
        this.numbers = null;
    }

    public Message(String string, IDMessage idMessage) {
        this.string = string;
        this.idMessage = idMessage;
        this.number = null;
        this.numbers = null;
    }

    public Message(int number, IDMessage idMessage) {
        this.number = number;
        this.idMessage = idMessage;
        this.string = null;
        this.numbers = null;
    }

    public Message( int[] numbers, IDMessage idMessage) {
        this.number = null;
        this.idMessage = idMessage;
        this.string = null;
        this.numbers = numbers;
    }

    public Message(IDMessage idMessage) {
        this.number = null;
        this.idMessage = idMessage;
        this.string = null;
        this.numbers = null;
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

    public int[] getNumbers() {
        return numbers;
    }
}