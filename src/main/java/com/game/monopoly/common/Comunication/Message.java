package com.game.monopoly.common.Comunication;

import com.game.monopoly.Server.Player;

import java.io.Serializable;

public class Message implements Serializable {

    private final Integer number;
    private Integer id;
    private final int[] numbers;
    private final String string;
    private final IDMessage idMessage;
    private transient Player player;

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

    public Message( int[] numbers, String string, IDMessage idMessage) {
        this.number = null;
        this.idMessage = idMessage;
        this.string = string;
        this.numbers = numbers;
    }

    public Message(IDMessage idMessage) {
        this.number = null;
        this.idMessage = idMessage;
        this.string = null;
        this.numbers = null;
    }


    public Message setId(int id){
        this.id = id;
        return this;
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

    public Integer getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
