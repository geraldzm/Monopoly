package com.game.monopoly.common.Comunication;

import java.io.*;

public enum IDMessage implements Serializable {
    //main.java.Server->client
    DICE(true),
    DICES(true),
    MOVE(true),
    GIVEMONEY(true),
    TAKEMONEY(true),
    ADMIN,
    REJECTED,
    ACCEPTED,
    STARTED(true),
    GAMEREADY(true),
    END,
    MESSAGE,
    LOGBOOK,
    ID(true),
    NAME,
    NAMES,
    TURNRS(true),
    TURN(true),
    GETTOKEN(true),
    TOKENS(true),
    ADDCARD(true),
    REMOVECARD(true),
    REJECTEDBUYATTEND(true),
    LOOSER(true),
    CANTBUY(true),
    PUTHOUSE(true),
    REMOVEHOUSE(true),
    PUTHOTEL(true),
    REMOVEHOTEL(true),
    NOAVAILABE(true), // dice que algo no esta disponible, comprar casa, hotel etc
    //######main.java.Client -> main.java.Server######
    BUYPROPERTY,
    SELLPROPERTY,
    BUYHOTEL,
    SELLHOTEL,
    BUYHOUSE,
    SELLHOUSE,
    FINISHEDTURN,
    ROLLDICES,
    RESPONSE,
    DONE
    ;

    private boolean done; // if main.java.Server->main.java.Client ID has to return DONE

    IDMessage() {
        done = false;
    }

    IDMessage(boolean done) {
        this.done = done;
    }

    /**
     * <h3>When the communication is S->C</h3>
     * <p>True if the ID has to return done, false if not</p>
     * */
    public boolean returnDone(){
        return done;
    }
}

/*
* NOTAS de qué debe hacer cada ID
*
* abreviaciones de estas notas: main.java.Server=S, cliente=C
*
* -------------------main.java.Server->client------------------------
*
* ADMIN: le indica al C que fue le primero en conectarse, el C debe responder con un número, el número indica la cantidad de jugadores
* que va a permitir en la partida, el número debe estar en [2,7[
*
* REJECTED: Si el C intentó conectarse y fue rechazado
*
* ACCEPTED: indica que el C fue acceptado en la partida
*
* STARTED: Dice que el juego ya comenzó
*
* END: Indica que el server se va a desconectar
*
* MESSAGE: Un mensaje del chat con la forma en el String="Nombre: mensaje"
*
* LOGBOOK: Mensaje a ser cargado en la bitácora
*
* ID: El S le esta asignando al C un id
*
* NAME: El S le esta solicitando un nombre  debe responder con: el nombre en el string, el ID que se le asignó en el numero
* solo se permiten letras normales [Aa-Zz]
*
* NAMES: El S esta enviando los nombres de TODOS los jugadores incluyendo el de el mismo cliente, el orden de los nombres va en orden
* de ID, desde cero para arriba
* e.g. "Carlos,Maria,Mario,valeria"   para el cliente llamado Mario con ID 2
*
* DICES: se esta enviando los resultados de los dados para elegir el orden de turno
* String "Carlos,Maria,valeria"   int [1, 2, 3, 4, 5, 9, 6, 4, 10]
*
* TURNRS: se envia cuando se esta eliguiendo el orden de turno, lleva un int que representa el turno del cliente
*
* TURN: le indica al cliente que es su turno de jugar
*
* TOKENS: resultado de los tokens
* #######################################################################
* --------------------client->main.java.Server------------------------
*
* RESPONSE: cada vez que el S le solicite algo al C el id de la respuesta debe ser este.
* e.g. el S envia ADMIN, el C debe responder con un número, el ID del message es RESPONSE
*
* DONE: el cliente responde esto cuando se le solicito hacer algo para lo que hay que "esperar al C"
* e.g. el S envia DICE; el cliente muestra la animación de los dados y tiene que responder con DONE
* cuando termina. El S va a esperar el DONE eternamente si es necesario.
*
* MESSAGE: Un mensaje para el chat
* */
