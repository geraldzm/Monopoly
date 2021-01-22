package com.game.monopoly.Server;



import com.game.monopoly.common.Comunication.ChatConnection;
import com.game.monopoly.common.Comunication.IDMessage;
import com.game.monopoly.common.Comunication.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;

import static com.game.monopoly.common.Comunication.IDMessage.*;

/**
 * <h1>ClientServer</h1>
 * */
public class Player extends ChatConnection {

    private int cash, id, token, position;
    private int[] dices;
    private String name;
    private boolean go, inJail;
    private int jailTurns;
    private HashSet<Integer> cards;

    private static int count;

    public Player(Socket socket) throws IOException {
        super(socket, null);
        id = count++;
        position = 0;
        cards = new HashSet<>();
        go = false;
        inJail = false;
    }


    public void sendChatMessage(Message message, Player sender) {
        sendMessage(new Message(sender.getName() + ": " +message.getString(), message.getIdMessage()));
    }

    private boolean momento = false;

    public int[] rollDices() {
        if(!momento) {
            Random random = new Random();
            dices = new int[3];
            dices[0] = random.nextInt(6) + 1;
            dices[1] = random.nextInt(6) + 1;
            dices[2] = dices[1] + dices[0];
            momento = true;
        } else{
            dices[0] = 1;
            dices[1] = 1;
            dices[2] = 2;
        }

        return dices;
    }

    /**
     * adds to his current position the given argument
     * @return the new position
     * */
    public int move(int move){
        return position = (position + move) % 40;
    }

    //-----------Setters/Getters-----------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCash() {
        return cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGo() {
        return go;
    }

    public int[] getDices() {
        return dices;
    }

    public void setDices(int[] dices) {
        this.dices = dices;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void addCash(int cash, String messageS) {
        this.cash += cash;
        Message message = new Message(this.cash, messageS, GIVEMONEY);
        sendMessage(message);
        processLog(message);
    }

    public void setGo(boolean go) {
        this.go = go;
    }

    public HashSet<Integer> getCards() {
        return cards;
    }

    public void setCards(HashSet<Integer> cards) {
        this.cards = cards;
    }

    public void addCard(Integer card) {
        cards.add(card);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void toJail(){
        inJail = true;
        jailTurns = 0;
        this.position = 10;
    }

    public void outOfJail(){
        inJail = false;
        jailTurns = 0;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void setJailTurns(int jailTurns) {
        this.jailTurns = jailTurns;
    }

    public void increaseJailTurns() {
        this.jailTurns++;
    }

    public void reduceMoney(int amount, String messageS){
        cash -= amount;
        Message message = new Message(cash, messageS, TAKEMONEY);
        sendMessage(message);
        processLog(message);
    }

    private void processLog(Message message) {
        message.setPlayer(this);
        LogMessageFactory.sendLogBookMessage(message);
    }
}
