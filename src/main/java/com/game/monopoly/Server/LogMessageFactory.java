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

        String playerName = (message.getPlayer() == null) ? "" : message.getPlayer().getName();

        switch (message.getIdMessage()) {
            case MOVE -> logMessage.append(String.format("%s se esta moviendo a la casilla %d", allPlayers.get(message.getNumbers()[0]).getName(), message.getNumbers()[2]));
            case DICE -> logMessage.append(String.format("%s tira los dados y obtiene %d %d", playerName, message.getNumbers()[0], message.getNumbers()[1]));
            case DICES -> logMessage.append("Mostrando resultados de la ronda de dados");
            case GAMEREADY -> logMessage.append("El juego esta listo para comenzar");
            case GIVEMONEY, TAKEMONEY, TOJAIL, OUTOFJAIL -> logMessage.append(message.getString());
            case NAMES -> logMessage.append("Se reciben los nombres: ").append(message.getString().substring(0, message.getString().length()-1));
            case GETTOKEN -> logMessage.append("Se le solicita el token a: ").append(playerName);
            case TURN -> logMessage.append(String.format("Comienza el turno de: %s", playerName));
            case TOKENS -> logMessage.append("Se enviaron todos los tokens a los clientes...");
            case ADDCARD -> logMessage.append(String.format("Se agrego una carta a: %s", allPlayers.get(message.getNumbers()[0]).getName()));
            case REMOVECARD -> logMessage.append(String.format("Se removio una carta a: %s", playerName));
            case REJECTEDBUYATTEND -> logMessage.append(String.format("Se denego la compra a: %s", playerName));
            case LOOSER -> logMessage.append(String.format("%s ha perdido...", playerName));
            case PUTHOUSE -> logMessage.append(String.format("Se agrego una casa en: %d", message.getNumber()));
            case REMOVEHOUSE -> logMessage.append(String.format("Se removio una casa en: %d", message.getNumber()));
            case PUTHOTEL -> logMessage.append(String.format("Se agrego un hotel en: %d y se remueven cuatro casas", message.getNumber()));
            case REMOVEHOTEL -> logMessage.append(String.format("Se removio un hotel en: %d", message.getNumber()));
            default -> logMessage.append("El server aun no soporta los mensajes de bitacora para: ").append(message.getIdMessage());
        }

        allPlayers.forEach(p -> p.sendMessage(new Message(logMessage.toString(), LOGBOOK)));
    }

    public static void setAllPlayers(ArrayList<Player> allPlayers) {
        LogMessageFactory.allPlayers = allPlayers;
    }
}
