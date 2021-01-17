package com.game.monopoly.Server;

import com.game.monopoly.common.Comunication.Message;

import java.util.ArrayList;

import static com.game.monopoly.common.Comunication.IDMessage.LOGBOOK;

public class LogMessageFactory {

    private static ArrayList<Player> allPlayers;

    private LogMessageFactory() {}

    public static void sendLogBookMessage(Message message){
        if(allPlayers == null) return;
        StringBuilder logMessage = new StringBuilder();

        switch (message.getIdMessage()) {
            case MOVE -> logMessage.append(String.format("%s se esta moviendo la casilla %d", message.getPlayer().getName(), message.getNumbers()[2]));
            case DICE -> logMessage.append(String.format("%s tira los dados y obtiene %d %d", message.getPlayer().getName(), message.getNumbers()[0], message.getNumbers()[1]));
            case GAMEREADY -> logMessage.append("El juego esta listo para comenzar");
            case GIVEMONEY -> logMessage.append(String.format("Se le da $ %d a %s", message.getNumber(), message.getPlayer().getName()));
            case NAMES -> logMessage.append("Se reciben los nombres: ").append(message.getString().substring(0, message.getString().length()-1));
            case GETTOKEN -> logMessage.append("Se le solicita el token a: ").append(message.getPlayer().getName());

            default -> logMessage.append("El server aun no soporta los mensajes de bitacora para: ").append(message.getIdMessage());
        }

        allPlayers.forEach(p -> p.sendMessage(new Message(logMessage.toString(), LOGBOOK)));
    }

    public static void setAllPlayers(ArrayList<Player> allPlayers) {
        LogMessageFactory.allPlayers = allPlayers;
    }
}
